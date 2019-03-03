package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.manager.PageManager;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.User.Role;
import com.epam.zhuckovich.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>The class contains methods for performing various actions with users,
 * such as registering, login, logout, editing and deleting an account</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         User
 * @since       1.0
 */

class UserCommand extends AbstractCommand{

    private static final Logger LOGGER = LogManager.getLogger(UserCommand.class);
    private UserService service;

    /**
     * Class constructor
     */

    UserCommand(){
        this.service = new UserService();
    }

    /**
     * <p>Method for authorizing the user in the program. Depending on
     * the type of account, redirects to the page of the reader, librarian
     * or administrator menu are performed. In case of incorrect data entry,
     * the method returns the user to the authorization page</p>
     * @param request parameter for receiving email and password from the client
     * @return        the Router object that redirects a user to a corresponding page
     */

    Router login(HttpServletRequest request) {
        var email = request.getParameter(EMAIL_USER);
        var password = request.getParameter(PASSWORD_USER);
        if(email == null || password == null){
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(LOGIN_PAGE));
        }
        var user = service.findUserByEmailPassword(email, password);
        var member = user.getRole();
        if(member == null){
            request.getSession().setAttribute(ERROR_LOGIN_MESSAGE, ERROR_LOGIN_MESSAGE);
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LOGIN_PAGE));
        }
            switch (member) {
                case ADMINISTRATOR:
                    request.getSession().setAttribute(USER, user);
                    return new Router(Router.RouterType.REDIRECT, PageManager.getPage(ADMINISTRATOR_MENU_PAGE));
                case LIBRARIAN:
                    request.getSession().setAttribute(USER, user);
                    return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LIBRARIAN_MENU_PAGE));
                case MEMBER:
                    request.getSession().setAttribute(USER, user);
                    return new Router(Router.RouterType.REDIRECT, PageManager.getPage(MEMBER_MENU_PAGE));
                default:
                    request.getSession().setAttribute(ERROR_LOGIN_MESSAGE, ERROR_LOGIN_MESSAGE);
                    return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LOGIN_PAGE));
            }
    }

    /**
     * <p>Method for destroying a user session and to sign out of account</p>
     * @param request the request object that contains a user session
     * @return        the Router object that redirects a user to a login page
     */

    Router logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LOGIN_PAGE));
    }

    /**
     * <p>Method for registering the reader or librarian in the program</p>
     * @param request contains name, surname, email and password for creating a user account
     * @return        the Router object that redirects a user to a login page
     */

    Router registration(HttpServletRequest request, Role registrationType) {
        var name = request.getParameter(NAME_USER);
        var surname = request.getParameter(SURNAME_USER);
        var email = request.getParameter(EMAIL_USER);
        var password = request.getParameter(PASSWORD_USER);
        InputStream photo = null;
        try {
            photo = request.getPart(AVATAR_USER).getInputStream();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR,"IOException was occurred during user registration");
        } catch (ServletException e) {
            LOGGER.log(Level.ERROR,"ServletException was occurred during user registration");
        }
        if(service.registerUser(name, surname, email, password, photo, registrationType) == 0){
            request.getSession().setAttribute(REGISTRATION_RESULT, ERROR);
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(REGISTRATION_PAGE));
        } else {
            request.getSession().setAttribute(USER_REGISTRATION,name + SPACE + surname);
            request.getSession().setAttribute(REGISTRATION_RESULT, SUCCESS);
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LOGIN_PAGE));
        }
    }

    /**
     * <p>Method for removing the reader or librarian from the program</p>
     * @param request the request object that contains a user id and current web-page
     * @return        the Router object that redirects a user to a reader or librarian page
     */

    Router deleteUser(HttpServletRequest request) {
        request.setAttribute(OPERATION_SUCCESS, service.deleteUser(request.getParameter(USER_ID)));
        if(request.getParameter(PAGE).equals(LIBRARIAN_PAGE)){
            return deleteUserMenu(request, Role.LIBRARIAN);
        } else {
            return deleteUserMenu(request, Role.MEMBER);
        }
    }

    /**
     * <p>A method for searching all members who requested an account deletion</p>
     * @param request sends a list of members to remove to the client
     * @return        the Router object that forwards a user to the administrator menu page
     *                if list of removable members is empty, otherwise forwards a user to
     *                the delete member page
     */

    Router deleteUserMenu(HttpServletRequest request, Role role){
        var removableUsers = service.findAllRemovableUsers(role);
        if(removableUsers.isEmpty()){
            if(role == Role.LIBRARIAN){
                request.getSession().setAttribute(EMPTY_DELETE_LIBRARIAN, SUCCESS);
            } else {
                request.getSession().setAttribute(EMPTY_DELETE_MEMBER, SUCCESS);
            }
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(ADMINISTRATOR_MENU_PAGE));
        } else {
            if(role == Role.LIBRARIAN){
                request.setAttribute(LIBRARIAN_LIST, removableUsers);
                return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_LIBRARIAN_PAGE));
            } else {
                request.setAttribute(MEMBER_LIST, removableUsers);
                return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_MEMBER_PAGE));
            }
        }
    }

    /**
     * <p>Method for editing an account of all types of users: the reader, the librarian or the administrator</p>
     * @param request receives data to edit an account
     * @return        redirects user to the edit account page with a report on the success of the operation
     */

    Router editAccount(HttpServletRequest request){
        try {
            var operationResult = 0;
            var user = (User) request.getSession().getAttribute(USER);
            var userBuilder = User.newBuilder()
                    .setId(user.getId())
                    .setName(request.getParameter(NAME_USER))
                    .setSurname(request.getParameter(SURNAME_USER));
            var password = request.getParameter(PASSWORD_USER);
            var repeatPassword = request.getParameter(REPEAT_PASSWORD_USER);
            if(password != null && repeatPassword != null){
                if(password.equals(repeatPassword) && password.length() >= 5 && password.length() <= 40){
                    userBuilder.setPassword(password);
                }
            }
            var editableUser = userBuilder.build();
            if(service.editAccountMainInformation(editableUser) != 0){
                operationResult++;
            }
            var city = request.getParameter(CITY_USER);
            var street = request.getParameter(STREET_USER);
            var house = request.getParameter(HOUSE_USER);
            var telephone = request.getParameter(TELEPHONE_USER);
            if(user.getAddress()!= null && city.isEmpty() && street.isEmpty() && house.isEmpty() && telephone.isEmpty()){
                if(service.deleteUserAddress(user.getAddress().getId()) != 0){
                    operationResult++;
                }
            } else if (user.getAddress() == null && !city.isEmpty() && !street.isEmpty() && !house.isEmpty() && !telephone.isEmpty()){
                int addressID = service.updateAddress(user, city, street, house, telephone, 0);
                if(service.addNewAddressToUser(addressID,user.getId()) != 0){
                    operationResult++;
                }
            } else if (user.getAddress()!= null && !city.isEmpty() && !street.isEmpty() && !house.isEmpty() && !telephone.isEmpty()){
                if(service.updateAddress(user,city, street, house, telephone, 1) != 0){
                    operationResult++;
                }
            }
            var photoPart = request.getPart(AVATAR_USER);
            if(photoPart.getSize() != 0){
                if(service.updateAvatar(photoPart.getInputStream(),user.getId())!=0){
                    operationResult++;
                }
            }
            if(operationResult == 0){
                request.getSession().setAttribute(OPERATION_SUCCESS, ERROR);
            } else {
                request.getSession().setAttribute(OPERATION_SUCCESS, SUCCESS);
            }
            request.getSession().setAttribute(USER,service.findMemberById(user.getId()));
        } catch(IOException e){
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the photo from the client while user editing account");
        } catch(ServletException e){
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the photo from the client while user editing account");
        }
        return new Router(Router.RouterType.REDIRECT, PageManager.getPage(EDIT_ACCOUNT_PAGE));
    }

    /**
     * <p>The method used to perform the account deletion procedure</p>
     * @param request receives memberID to account deletion
     * @return        redirects user to the menu page depending on the type of user
     */

    Router deleteAccountRequest(HttpServletRequest request){
        var user = (User) request.getSession().getAttribute(USER);
        var role = user.getRole();
        if(service.deleteAccount(user.getId(), role) == 0){
            request.getSession().setAttribute(DELETE_ACCOUNT, ERROR);
        } else {
            request.getSession().setAttribute(DELETE_ACCOUNT, SUCCESS);
        }
        switch (role){
            case MEMBER:
                return new Router(Router.RouterType.REDIRECT, PageManager.getPage(MEMBER_MENU_PAGE));
            case LIBRARIAN:
                return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LIBRARIAN_MENU_PAGE));
            default:
                return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LOGIN_PAGE));
        }
    }
}

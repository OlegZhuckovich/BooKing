package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.entity.Address;
import com.epam.zhuckovich.entity.Order;
import com.epam.zhuckovich.entity.UserType;
import com.epam.zhuckovich.manager.PageManager;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>A class that contains methods for various actions
 * with the authors of the books presented in the library's fund.</p>
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
        String email = request.getParameter(EMAIL_USER);
        String password = request.getParameter(PASSWORD_USER);
        if(email == null || password == null){
            return new Router(Router.RouterType.FORWARD,PageManager.getPage(LOGIN_PAGE));
        }
        User user = service.checkUser(email, password);
        UserType member = user.getUserType();
        if(member == null){
            request.getSession().setAttribute(ERROR_LOGIN_MESSAGE, ERROR_LOGIN_MESSAGE);
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LOGIN_PAGE));
        }
            switch (member) {
                case ADMINISTRATOR:
                    request.getSession().setAttribute(USER, user);
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ADMINISTRATOR_MENU_PAGE));
                case LIBRARIAN:
                    request.getSession().setAttribute(USER, user);
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LIBRARIAN_MENU_PAGE));
                case MEMBER:
                    request.getSession().setAttribute(USER, user);
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(MEMBER_MENU_PAGE));
                default:
                    request.getSession().setAttribute(ERROR_LOGIN_MESSAGE, ERROR_LOGIN_MESSAGE);
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LOGIN_PAGE));
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
     * <p>Method for registering the reader in the program</p>
     * @param request contains name, surname, email and password for creating a reader account
     * @return        the Router object that redirects a user to a login page
     */

    Router registration(HttpServletRequest request, UserType registrationType) {
        String name = request.getParameter(NAME_USER);
        String surname = request.getParameter(SURNAME_USER);
        String email = request.getParameter(EMAIL_USER);
        String password = request.getParameter(PASSWORD_USER);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD_USER);
        Part photoPart;
        InputStream photo = null;
        try {
            photoPart = request.getPart(AVATAR_USER);
            photo = photoPart.getInputStream();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR,"IOException was occurred during user registration");
        } catch (ServletException e) {
            LOGGER.log(Level.ERROR,"ServletException was occurred during user registration");
        }
        int isUserAdded = service.registerUser(name, surname, email, password, photo, registrationType);
        if(isUserAdded == 0){
            request.getSession().setAttribute(REGISTRATION_RESULT,ERROR);
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(REGISTRATION_PAGE));
        } else {
            request.getSession().setAttribute(USER_REGISTRATION,name + SPACE + surname);
            request.getSession().setAttribute(REGISTRATION_RESULT,SUCCESS);
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LOGIN_PAGE));
        }
    }

    /**
     * <p>Method for removing the reader or librarian from the program</p>
     * @param request the request object that contains a user id and current web-page
     * @return        the Router object that redirects a user to a reader or librarian page
     */

    Router deleteUser(HttpServletRequest request) {
        String page = request.getParameter(PAGE_PARAMETER);
        String userID = request.getParameter(USER_ID);
        boolean operationSuccess = service.deleteUser(userID);
        System.out.println("UserID = " + userID);
        request.setAttribute(OPERATION_PARAMETER,operationSuccess);
        if(page.equals(LIBRARIAN_PAGE)){
            return deleteLibrarianMenu(request);
        } else {
            return deleteMemberMenu(request);
        }
    }

    /**
     *
     *
     * @param request
     * @return
     */

    Router deleteLibrarianMenu(HttpServletRequest request) {
        List<User> removableLibrarians = service.findAllRemovableUsers(UserType.LIBRARIAN);
        if(removableLibrarians.isEmpty()){
            request.getSession().setAttribute(EMPTY_DELETE_LIBRARIAN, SUCCESS);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(ADMINISTRATOR_MENU_PAGE));
        } else {
            request.setAttribute(LIBRARIAN_LIST_PARAMETER,removableLibrarians);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_LIBRARIAN_PAGE));
        }
    }
    
    
    Router deleteMemberMenu(HttpServletRequest request) {
        List<User> removableMembers = service.findAllRemovableUsers(UserType.MEMBER);
        if(removableMembers.isEmpty()){
            request.getSession().setAttribute(EMPTY_DELETE_MEMBER, SUCCESS);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(ADMINISTRATOR_MENU_PAGE));
        } else {
            request.setAttribute(MEMBER_LIST_PARAMETER,removableMembers);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_MEMBER_PAGE));
        }
    }


    Router editAccount(HttpServletRequest request){
        try {
            int operationResult = 0;
            User user = (User) request.getSession().getAttribute(USER);
            User.Builder editableUserBuilder = User.newBuilder()
                    .setId(user.getId())
                    .setName(request.getParameter(NAME_USER))
                    .setSurname(request.getParameter(SURNAME_USER));
            String password = request.getParameter(PASSWORD_USER);
            String repeatPassword = request.getParameter(REPEAT_PASSWORD_USER);
            if(password != null && repeatPassword != null){
                if(password.equals(repeatPassword) && password.length() >= 5 && password.length() <= 40){
                    editableUserBuilder.setPassword(password);
                }
            }
            User editableUser = editableUserBuilder.build();
            if(service.editAccountMainInformation(editableUser)!=0){
                operationResult++;
            }
            String city = request.getParameter(CITY_USER);
            String street = request.getParameter(STREET_USER);
            String house = request.getParameter(HOUSE_USER);
            String telephone = request.getParameter(TELEPHONE_USER);
            if(city.isEmpty() && street.isEmpty() && house.isEmpty() && telephone.isEmpty()){
                if(service.deleteUserAddress(user.getAddress().getId())!=0){
                    operationResult++;
                }
            } else if (user.getAddress() == null){
                int addressID = service.updateAddress(user, city, street, house, telephone, 0);
                if(service.addNewAddressToUser(addressID,user.getId())!=0){
                    operationResult++;
                }
            } else {
                if(service.updateAddress(user,city, street, house, telephone, 1)!=0){
                    operationResult++;
                }
            }
            Part photoPart = request.getPart(AVATAR_USER);
            if(photoPart != null){
                InputStream avatar = photoPart.getInputStream();
                if(service.updateAvatar(avatar,user.getId())!=0){
                    operationResult++;
                }
            }
            if(operationResult==0){
                request.getSession().setAttribute(OPERATION_PARAMETER,ERROR);
            } else {
                request.getSession().setAttribute(OPERATION_PARAMETER,SUCCESS);
            }
            request.getSession().setAttribute(USER,service.findMemberById(user.getId()));
        } catch(IOException e){
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the photo from the client while user editing account");
        } catch(ServletException e){
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the photo from the client while user editing account");
        }
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(EDIT_ACCOUNT_PAGE));
    }


    Router deleteAccountRequest(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER);
        Integer userID = user.getId();
        UserType userType = user.getUserType();
        int operationResult = 0;
        operationResult = service.deleteAccount(userID, userType);
        if(operationResult == 0){
            request.getSession().setAttribute(DELETE_ACCOUNT_PARAMETER,ERROR);
        } else {
            request.getSession().setAttribute(DELETE_ACCOUNT_PARAMETER,SUCCESS);
        }
        switch (userType){
            case MEMBER:
                return new Router(Router.RouterType.REDIRECT,PageManager.getPage(MEMBER_MENU_PAGE));
            case LIBRARIAN:
                return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LIBRARIAN_MENU_PAGE));
            default:
                return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LOGIN_PAGE));
        }
    }
}

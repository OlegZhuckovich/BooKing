package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
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

/**
 * <p>A class that contains methods for various actions
 * with the authors of the books presented in the library's fund.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         User
 * @since       1.0
 */

class UserCommand {

    private static final Logger LOGGER = LogManager.getLogger(UserCommand.class);

    private static final String USER_PARAMETER = "user";
    private static final String USER_ID_PARAMETER = "userID";
    private static final String OPERATION_PARAMETER = "operationSuccess";
    private static final String LIBRARIAN_PAGE = "deleteLibrarian";
    private static final String EDIT_ACCOUNT_PAGE = "editAccount";
    private static final String PAGE_PARAMETER = "page";

    private static final String LIBRARIAN_LIST_PARAMETER = "librarianList";
    private static final String DELETE_LIBRARIAN_PAGE = "deleteLibrarian";

    private static final String MEMBER_LIST_PARAMETER = "memberList";
    private static final String DELETE_MEMBER_PAGE = "deleteMember";

    private static final String LOGIN_INPUT = "emailInput";
    private static final String PASSWORD_INPUT = "passwordInput";
    private static final String MEMBER_MENU_PAGE = "memberMenu";
    private static final String LIBRARIAN_MENU_PAGE = "librarianMenu";
    private static final String ADMINISTRATOR_MENU_PAGE = "administratorMenu";
    private static final String LOGIN_PAGE = "loginPage";
    private static final String ERROR_LOGIN_MESSAGE = "errorLoginMessage";

    private static final String NAME_REGISTER= "nameRegister";
    private static final String SURNAME_REGISTER = "surnameRegister";
    private static final String EMAIL_REGISTER = "emailRegister";
    private static final String PASSWORD_REGISTER = "passwordRegister";

    private static final String DELETE_ACCOUNT_PARAMETER = "deleteAccount";

    private static final String NAME_USER = "nameUser";
    private static final String SURNAME_USER = "surnameUser";
    private static final String EMAIL_USER = "emailUser";
    private static final String AVATAR_USER = "avatarUser";
    private static final String CITY_USER = "cityUser";
    private static final String STREET_USER = "streetUser";
    private static final String HOUSE_USER = "houseUser";
    private static final String TELEPHONE_USER = "telephoneUser";
    private static final String PASSWORD_USER = "passwordUser";
    private static final String REPEAT_PASSWORD_USER = "repeatPasswordUser";

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
        String email = request.getParameter(LOGIN_INPUT);
        String password = request.getParameter(PASSWORD_INPUT);
        User user = service.checkUser(email, password);
        UserType member = user.getUserType();
        if (member == null) {
            request.setAttribute(ERROR_LOGIN_MESSAGE, "Вход не выполнен");
            return new Router(Router.RouterType.FORWARD,PageManager.getPage(LOGIN_PAGE));
        } else {
            switch (member) {
                case ADMINISTRATOR:
                    request.getSession().setAttribute(USER_PARAMETER, user);
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ADMINISTRATOR_MENU_PAGE));
                case LIBRARIAN:
                    request.getSession().setAttribute(USER_PARAMETER, user);
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LIBRARIAN_MENU_PAGE));
                case MEMBER:
                    request.getSession().setAttribute(USER_PARAMETER, user);
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(MEMBER_MENU_PAGE));
                default:
                    request.getSession().setAttribute(ERROR_LOGIN_MESSAGE, "Вход не выполнен");
                    return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LOGIN_PAGE));
            }
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

    Router registration(HttpServletRequest request) {
        String name = request.getParameter(NAME_REGISTER);
        String surname = request.getParameter(SURNAME_REGISTER);
        String email = request.getParameter(EMAIL_REGISTER);
        String password = request.getParameter(PASSWORD_REGISTER);
        boolean isUserAdded = service.insertUser(name, surname, email, password);
        if(isUserAdded){
            request.setAttribute("registerMessage","Пользователь зарегистрирован");
        } else {
            request.setAttribute("registerMessage","Что-то пошло не так");
        }
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LOGIN_PAGE));
    }

    /**
     * <p>Method for removing the reader or librarian from the program</p>
     * @param request the request object that contains a user id and current web-page
     * @return        the Router object that redirects a user to a reader or librarian page
     */

    Router deleteUser(HttpServletRequest request) {
        String page = request.getParameter(PAGE_PARAMETER);
        String userID = request.getParameter(USER_ID_PARAMETER);
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
        request.setAttribute(LIBRARIAN_LIST_PARAMETER,service.findAllRemovableUsers(UserType.LIBRARIAN));
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_LIBRARIAN_PAGE));
    }
    
    
    Router deleteMemberMenu(HttpServletRequest request) {
        request.setAttribute(MEMBER_LIST_PARAMETER,service.findAllRemovableUsers(UserType.MEMBER));
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_MEMBER_PAGE));
    }


    Router editAccount(HttpServletRequest request){
        try {
            /*User.Builder editableUser = User.newBuilder()
                    .setName(request.getParameter(NAME_USER))
                    .setSurname(request.getParameter(SURNAME_USER))
                    .setEmail(request.getParameter(EMAIL_USER));
            if (!request.getParameter(CITY_USER).isEmpty()) {
                editableUser = editableUser.setAddress(Address.newBuilder()
                        .setCity(request.getParameter(CITY_USER))
                        .setStreet(request.getParameter(STREET_USER))
                        .setHouse(Integer.parseInt(request.getParameter(HOUSE_USER)))
                        .setTelephoneNumber(Integer.parseInt(request.getParameter(TELEPHONE_USER)))
                        .build());
            }
            if (!request.getParameter(PASSWORD_USER).isEmpty()) {
                editableUser = editableUser.setPassword(request.getParameter(PASSWORD_USER));
            } else {*/
                User user = (User) request.getSession().getAttribute(USER_PARAMETER);
           //     editableUser = editableUser.setId(user.getId());
           //   editableUser = editableUser.setPassword(user.getPassword());
           // }
           // if (!request.getParameter(AVATAR_USER).isEmpty()) {
                Part photoPart = request.getPart(AVATAR_USER);
                InputStream photo = photoPart.getInputStream();
           //     editableUser = editableUser.setPhoto(photo);
           // }
          //  User user = editableUser.build();
          //  System.out.println(user.toString());
            service.editAccount(photo,user.getId());
        } catch(IOException e){
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the photo from the client while user editing account");
        } catch(ServletException e){
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the photo from the client while user editing account");
        }
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(EDIT_ACCOUNT_PAGE));
    }


    Router deleteAccountRequest(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_PARAMETER);
        Integer userID = user.getId();
        UserType userType = user.getUserType();
        service.deleteAccount(userID, userType);
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

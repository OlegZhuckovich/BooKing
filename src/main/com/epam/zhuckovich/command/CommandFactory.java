package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.entity.Order;
import com.epam.zhuckovich.entity.UserType;
import com.epam.zhuckovich.manager.PageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Class issuing various implementations of the Command interface
 * depending on the action of the incoming request object</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Command
 * @since       1.0
 */

public class CommandFactory {

    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class.getName());
    private static final String LOGIN_PAGE = "loginPage";

    /**
     * <p>Defines the type of command that came from the client in the request
     * object</p>
     * @param commandValue the string that converts to one of the CommandType enumeration item
     * @param request      sends data from the client to the server
     * @return             the Command object which will be executed in the future
     */

    public static Command defineCommand(String commandValue, HttpServletRequest request) {
        Command command = null;
        CommandType commandType = null;
        try {
            commandType = CommandType.valueOf(commandValue);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, "IllegalArgumentException was occurred during definition of the command");
        }
        if (commandType != null) {
            switch (commandType) {
                case ADD_LIBRARIAN:
                    command = httpRequest -> new UserCommand().registration(request,UserType.LIBRARIAN);
                    break;
                case DELETE_ACCOUNT:
                    command = httpRequest -> new UserCommand().deleteAccountRequest(request);
                    break;
                case DELETE_LIBRARIAN_MENU:
                    command = httpRequest -> new UserCommand().deleteUserMenu(request,UserType.LIBRARIAN);
                    break;
                case DELETE_MEMBER_MENU:
                    command = httpRequest -> new UserCommand().deleteUserMenu(request,UserType.MEMBER);
                    break;
                case DELETE_USER:
                    command = httpRequest -> new UserCommand().deleteUser(request);
                    break;
                case EDIT_ACCOUNT:
                    command = httpRequest -> new UserCommand().editAccount(request);
                    break;
                case LOGIN:
                    command = httpRequest -> new UserCommand().login(request);
                    break;
                case LOGOUT:
                    command = httpRequest -> new UserCommand().logout(request);
                    break;
                case REGISTER:
                    command = httpRequest -> new UserCommand().registration(request, UserType.MEMBER);
                    break;
                case ADD_BOOK:
                    command = httpRequest -> new BookCommand().addBook(request);
                    break;
                case ADD_BOOK_MENU:
                    command = httpRequest -> new BookCommand().openAddBookMenu(request);
                    break;
                case DELETE_BOOK:
                    command = httpRequest -> new BookCommand().deleteBook(request);
                    break;
                case DELETE_BOOK_MENU:
                    command = httpRequest -> new BookCommand().deleteBookMenu(request);
                    break;
                case EDIT_BOOK:
                    command = httpRequest -> new BookCommand().editCurrentBookMenu(request);
                    break;
                case EDIT_BOOKS_MENU:
                    command = httpRequest -> new BookCommand().editBookMenu(request);
                    break;
                case EDIT_CURRENT_BOOK:
                    command = httpRequest -> new BookCommand().editCurrentBook(request);
                    break;
                case SEARCH:
                    command = httpRequest -> new BookCommand().searchBook(request);
                    break;
                case ORDER_BOOK:
                    command = httpRequest -> new OrderCommand().orderBook(request);
                    break;
                case READING_ROOM_ISSUE:
                    command = httpRequest -> new OrderCommand().issueBookInReadingRoom(request);
                    break;
                case READING_ROOM_MENU:
                    command = httpRequest -> new OrderCommand().openOrderMenu(request, Order.OrderType.READING_ROOM);
                    break;
                case RETURN_BOOK:
                    command = httpRequest -> new OrderCommand().returnBook(request);
                    break;
                case SUBSCRIPTION_ISSUE:
                    command = httpRequest -> new OrderCommand().issueBookOnSubscription(request);
                    break;
                case SUBSCRIPTION_MENU:
                    command = httpRequest -> new OrderCommand().openOrderMenu(request, Order.OrderType.SUBSCRIPTION);
                    break;
                case VIEW_ORDERED_BOOKS:
                    command = httpRequest -> new OrderCommand().viewOrderedBooks(request);
                    break;
                case ADD_AUTHOR:
                    command = httpRequest -> new AuthorCommand().addAuthor(request);
                    break;
                case VIEW_AUTHORS:
                    command = httpRequest -> new AuthorCommand().viewAuthors(request);
                    break;
                default:
                    command = httpRequest -> new Router(Router.RouterType.FORWARD, PageManager.getPage(LOGIN_PAGE));
            }
        }
        return command;
    }
}

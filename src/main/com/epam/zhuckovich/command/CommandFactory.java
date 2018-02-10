package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
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
     *
     * @param commandValue
     * @param request
     * @return
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
                case LOGIN:
                    command = httpRequest -> new UserCommand().login(request);
                    break;
                case LOGOUT:
                    command = httpRequest -> new UserCommand().logout(request);
                    break;
                case REGISTER:
                    command = httpRequest -> new UserCommand().registration(request);
                    break;
                case EDIT_ACCOUNT:
                    command = httpRequest -> new UserCommand().editAccount(request);
                    break;
                case DELETE_ACCOUNT:
                    command = httpRequest -> new UserCommand().deleteAccountRequest(request);
                    break;
                case DELETE_USER:
                    command = httpRequest -> new UserCommand().deleteUser(request);
                    break;
                case DELETE_LIBRARIAN_MENU:
                    command = httpRequest -> new UserCommand().deleteLibrarianMenu(request);
                    break;
                case DELETE_MEMBER_MENU:
                    command = httpRequest -> new UserCommand().deleteMemberMenu(request);
                    break;
                case ADD_BOOK:
                    command = httpRequest -> new BookCommand().addBook(request);
                    break;
                case ADD_BOOK_MENU:
                    command = httpRequest -> new BookCommand().openAddBookMenu(request);
                    break;
                case SEARCH:
                    command = httpRequest -> new BookCommand().searchBook(request);
                    break;
                case DELETE_BOOK:
                    command = httpRequest -> new BookCommand().deleteBook(request);
                    break;
                case EDIT_BOOK:
                    command = httpRequest -> new BookCommand().editCurrentBookMenu(request);
                    break;
                case EDIT_BOOKS_MENU:
                    command = httpRequest -> new BookCommand().editBookMenu(request);
                    break;
                case DELETE_BOOK_MENU:
                    command = httpRequest -> new BookCommand().deleteBookMenu(request);
                    break;
                case READING_ROOM_MENU:
                    command = httpRequest -> new OrderCommand().openReadingRoomMenu(request);
                    break;
                case READING_ROOM_ISSUE:
                    command = httpRequest -> new OrderCommand().issueBookInReadingRoom(request);
                    break;
                case SUBSCRIPTION_ISSUE:
                    command = httpRequest -> new OrderCommand().issueBookOnSubscription(request);
                    break;
                case SUBSCRIPTION_MENU:
                    command = httpRequest -> new OrderCommand().openSubscriptionMenu(request);
                    break;
                case ORDER_BOOK:
                    command = httpRequest -> new OrderCommand().orderBook(request);
                    break;
                case VIEW_ORDERED_BOOKS:
                    command = httpRequest -> new OrderCommand().viewOrderedBooks(request);
                    break;
                case ADD_AUTHOR:
                    command = httpRequest -> new AuthorCommand().addNewAuthor(request);
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

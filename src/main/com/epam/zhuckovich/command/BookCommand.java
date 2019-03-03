package com.epam.zhuckovich.command;

import com.epam.zhuckovich.manager.PageManager;
import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.entity.Author;
import com.epam.zhuckovich.entity.Book;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.service.BookService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>The class contains methods for operations with books that are
 * located in the library's fund. Operations such as adding, deleting,
 * editing, and various actions related to loading book data on web
 * pages are supported.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Book
 * @since       1.0
 */

class BookCommand extends AbstractCommand{

    private static final Logger LOGGER = LogManager.getLogger(BookCommand.class);
    private BookService service;

    /**
     * Class constructor
     */

    BookCommand(){
        this.service = new BookService();
    }

    /**
     * <p>Turns to the service layer to find all the books that are in
     * the library. The found data is attached to the request object
     * using the setAttribute method. Returns a Router object that
     * flushes request to the page for deleting books using the forward
     * operation.</p>
     * @param  request the request object to which data about books are attached
     * @return         a router that flushes the request to the page for deleting books by the forward operation
     */

    Router deleteBookMenu(HttpServletRequest request) {
        request.setAttribute(BOOK_LIST, service.findAllBooks());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_BOOK_PAGE));
    }

    /**
     * <p>Method that finds all authors and all genres and sends them to
     * book editing page</p>
     * @param request used to send lists of authors and genres to the client
     * @return        router that forwards user to book editing page
     */

    Router editBookMenu(HttpServletRequest request){
        var authors = service.findAllAuthors();
        var genres = Arrays.asList(Book.Genre.values());
        request.setAttribute(AUTHOR_LIST, authors);
        request.setAttribute(GENRE_LIST, genres);
        request.setAttribute(BOOK_LIST, service.findAllBooks());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(EDIT_BOOK_PAGE));
    }

    /**
     * <p>Gets information about a particular book for editing, and then sends
     * the data for processing to the edit page of a particular book</p>
     * @param request sends bookID parameter to the server
     * @return        router that forwards user to edit current book page
     */

    Router editCurrentBookMenu(HttpServletRequest request){
        var authors = service.findAllAuthors();
        var books = service.findAllBooks();
        var genres = Arrays.asList(Book.Genre.values());
        var bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        books.forEach(book -> {
            if(book.getId() == bookID){
                request.getSession().setAttribute(BOOK, book);
            }
        });
        request.getSession().setAttribute(AUTHOR_LIST, authors);
        request.getSession().setAttribute(GENRE_LIST, genres);
        return new Router(Router.RouterType.REDIRECT, PageManager.getPage(EDIT_CURRENT_BOOK_PAGE));
    }

    /**
     * <p>Function for editing current book</p>
     * @param request sends information about book to server
     * @return        the Router that redirects user to edit book page
     */

    Router editCurrentBook(HttpServletRequest request){
        var authorIds = Arrays.asList(request.getParameterValues(BOOK_AUTHOR));
        var bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        List<Integer> authorsList = new ArrayList<>();
        authorIds.forEach(authorId -> authorsList.add(Integer.parseInt(authorId)));
        try {
            var title = request.getParameter(BOOK_TITLE);
            var publishingHouse = request.getParameter(BOOK_PUBLISHING_HOUSE);
            var metadata = new Book.Metadata(Integer.parseInt(request.getParameter(BOOK_YEAR)),
                                             Integer.parseInt(request.getParameter(BOOK_PAGES)),
                                             Integer.parseInt(request.getParameter(BOOK_QUANTITY)));
            var genre = Book.Genre.valueOf(request.getParameter(BOOK_GENRE));
            var contentPart = request.getPart(BOOK_CONTENT);
            if (contentPart.getSize() != 0) {
                service.editCurrentBook(bookID, authorsList, title, publishingHouse, metadata, genre, contentPart.getInputStream());
            } else {
                service.editCurrentBook(bookID, authorsList, title, publishingHouse, metadata, genre);
            }
            return editBookMenu(request);
        } catch (IOException e){
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the book content from the client");
        } catch (ServletException e){
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the book content from the client");
        }
        return new Router(Router.RouterType.REDIRECT, PageManager.getPage(EDIT_BOOK_PAGE));
    }

    /**
     * <p>Method that delete book from library</p>
     * @param request contains book id parameter for delete operation
     * @return        request that contains information about books left in the library
     */

    Router deleteBook(HttpServletRequest request) {
        service.deleteBook(request.getParameter(BOOK_ID));
        return deleteBookMenu(request);
    }

    /**
     * <p>Looking for a book on a request from a user</p>
     * @param request contains information for conducting a search
     * @return        router that forwards a user to a search results page
     */

    Router searchBook(HttpServletRequest request) {
        var searchValue = request.getParameter(SEARCH_VALUE);
        var searchCriteria = request.getParameter(CRITERIA);
        var user = (User) request.getSession().getAttribute(USER);
        var foundBooks = service.findBooksByCriteria(searchValue, searchCriteria, String.valueOf(user.getId()));
        if(foundBooks.isEmpty()){
            request.setAttribute(SEARCH_RESULT, EMPTY_LIST);
        } else {
            request.setAttribute(BOOK_LIST, foundBooks);
        }
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(ORDER_BOOK_PAGE));
    }

    /**
     * <p>A method that searches for all authors and genres of books and
     * sends them to the page for adding a new book</p>
     * @param request sends information about authors and genres of books to the user
     * @return        router that forwards a user to a add book page
     */

    Router openAddBookMenu(HttpServletRequest request){
        var authors = service.findAllAuthors();
        var genres = Arrays.asList(Book.Genre.values());
        request.setAttribute(AUTHOR_LIST, authors);
        request.setAttribute(GENRE_LIST, genres);
        return new Router(Router.RouterType.FORWARD,PageManager.getPage(ADD_BOOK_PAGE));
    }

    /**
     * <p>A method that receives data from the client to add a new book to the
     * library. In case of success, sends a message about adding a book and redirects
     * the user to the administrator's menu</p>
     * @param request sends data to the server to add a book
     * @return        router that redirects a user to an administrator menu page
     */

    Router addBook(HttpServletRequest request){
        var authorIds = Arrays.asList(request.getParameterValues(BOOK_AUTHOR));
        var authors = new ArrayList<Author>();
        authorIds.forEach(authorId -> authors.add(Author.newBuilder()
                .setId(Integer.parseInt(authorId))
                .build()));
        try {
            var bookBuilder = Book.newBuilder()
                    .setTitle(request.getParameter(BOOK_TITLE))
                    .setPublishingHouse(request.getParameter(BOOK_PUBLISHING_HOUSE))
                    .setNumberInformation(new Book.Metadata(Integer.parseInt(request.getParameter(BOOK_YEAR)),
                            Integer.parseInt(request.getParameter(BOOK_PAGES)),
                            Integer.parseInt(request.getParameter(BOOK_QUANTITY))))
                    .setGenre(Book.Genre.valueOf(request.getParameter(BOOK_GENRE)))
                    .setAuthors(authors);
            var contentPart = request.getPart(BOOK_CONTENT);
            if (contentPart.getSize() != 0) {
                bookBuilder.setBookContent(contentPart.getInputStream());
            }
            var book = bookBuilder.build();
            if(service.addBook(book)){
                request.getSession().setAttribute(BOOK_ADDED_RESULT,SUCCESS);
            } else {
                request.getSession().setAttribute(BOOK_ADDED_RESULT,ERROR);
            }
            request.getSession().setAttribute(BOOK_TITLE, book.getTitle());
        } catch (IOException e){
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the book content from the client");
        } catch (ServletException e){
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the book content from the client");
        } finally {
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ADMINISTRATOR_MENU));
        }
    }

}

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
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
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
     * the library's library. The found data is attached to the request
     * object using the setAttribute method. Returns a Router object that
     * flushes request to the page for deleting books using the forward
     * operation.</p>
     * @param  request the request object to which data about books are attached
     * @return         a router that flushes the request to the page for deleting books by the forward operation
     */

    Router deleteBookMenu(HttpServletRequest request) {
        request.setAttribute(BOOK_LIST_PARAMETER,service.findAllBooks());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_BOOK_PAGE));
    }

    Router editBookMenu(HttpServletRequest request){
        List<Book.BookType> genreList = Arrays.asList(Book.BookType.values());
        List<Author> authorList = service.findAllAuthors();
        request.setAttribute(GENRE_LIST_PARAMETER,genreList);
        request.setAttribute(AUTHOR_LIST,authorList);
        request.setAttribute(BOOK_LIST_PARAMETER,service.findAllBooks());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(EDIT_BOOK_PAGE));
    }

    Router editCurrentBookMenu(HttpServletRequest request){
        Book book = null;
        String bookID = request.getParameter(BOOK_ID);
        int numberBookID = Integer.parseInt(bookID);
        List<Book> bookList = service.findAllBooks();
        for(Book tempBook: bookList){
            if(tempBook.getId() == numberBookID){
                book = tempBook;
            }
        }
        request.setAttribute(BOOK,book);
        return new Router(Router.RouterType.FORWARD,PageManager.getPage(EDIT_CURRENT_BOOK_PAGE));
    }



    /**
     * <p>Method that delete book from library</p>
     * @param request contains book id parameter for delete operation
     * @return        request that contains information about books left in the library
     */

    Router deleteBook(HttpServletRequest request) {
        String bookID = request.getParameter(BOOK_ID);
        System.out.println("bookID parameter: " + bookID);
        boolean operationSuccess = service.deleteBook(bookID);
        return deleteBookMenu(request);
    }

    /**
     *
     *
     * @param request
     * @return
     */

    Router searchBook(HttpServletRequest request) {
        String searchValue = request.getParameter(SEARCH_VALUE);
        String searchCriteria = request.getParameter(CRITERIA_PARAMETER);
        User user = (User) request.getSession().getAttribute(USER);
        List<Book> findBooks = service.findBooksByCriteria(searchValue, searchCriteria, String.valueOf(user.getId()));
        if(findBooks.isEmpty()){
            request.setAttribute(SEARCH_RESULT, EMPTY_LIST);
        } else {
            request.setAttribute(BOOK_LIST_PARAMETER,findBooks);
        }
        return new Router(Router.RouterType.FORWARD,PageManager.getPage(ORDER_BOOK_PAGE));
    }

    /**
     *
     * @param request
     * @return
     */

    Router openAddBookMenu(HttpServletRequest request){
        List<Book.BookType> genreList = Arrays.asList(Book.BookType.values());
        List<Author> authorList = service.findAllAuthors();
        request.setAttribute(GENRE_LIST_PARAMETER,genreList);
        request.setAttribute(AUTHOR_LIST,authorList);
        return new Router(Router.RouterType.FORWARD,PageManager.getPage(ADD_BOOK_PAGE));
    }

    /**
     *
     * @param request
     * @return
     */

    Router addBook(HttpServletRequest request){
        String[] authors = request.getParameterValues(BOOK_AUTHOR);
        List<Author> authorsList = new ArrayList<>();
        for(String authorID:authors){
            authorsList.add(Author.newBuilder().setId(Integer.parseInt(authorID)).build());
        }
        try {
            Book.Builder newBook = Book.newBuilder()
                    .setTitle(request.getParameter(BOOK_TITLE))
                    .setPublishingHouse(request.getParameter(BOOK_PUBLISHING_HOUSE))
                    .setNumberInformation(new Book.NumberInformation(Integer.parseInt(request.getParameter(BOOK_YEAR)),Integer.parseInt(request.getParameter(BOOK_PAGES)),Integer.parseInt(request.getParameter(BOOK_QUANTITY))))
                    .setGenre(Book.BookType.valueOf(request.getParameter(BOOK_GENRE)))
                    .setAuthors(authorsList);
            Part bookContentPart = request.getPart(BOOK_CONTENT);
            if (bookContentPart != null) {
                InputStream bookContent = bookContentPart.getInputStream();
                newBook.setBookContent(bookContent);
            }
            Book book = newBook.build();
            if(service.addBook(book)){
                request.getSession().setAttribute(BOOK_ADDED_RESULT,SUCCESS);
                request.getSession().setAttribute(BOOK_TITLE,book.getTitle());
                return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ADMINISTRATOR_MENU));
            } else {
                request.getSession().setAttribute(BOOK_ADDED_RESULT,ERROR);
                request.getSession().setAttribute(BOOK_TITLE,book.getTitle());
                return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ADMINISTRATOR_MENU));
            }
        } catch (IOException e){
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the book content from the client");
        } catch (ServletException e){
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the book content from the client");
        }
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ADMINISTRATOR_MENU));
    }

}

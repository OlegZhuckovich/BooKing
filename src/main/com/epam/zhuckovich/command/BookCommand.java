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
     * the library. The found data is attached to the request object
     * using the setAttribute method. Returns a Router object that
     * flushes request to the page for deleting books using the forward
     * operation.</p>
     * @param  request the request object to which data about books are attached
     * @return         a router that flushes the request to the page for deleting books by the forward operation
     */

    Router deleteBookMenu(HttpServletRequest request) {
        request.setAttribute(BOOK_LIST_PARAMETER,service.findAllBooks());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(DELETE_BOOK_PAGE));
    }

    /**
     * <p>Method that finds all authors and all genres and sends them to
     * book editing page</p>
     * @param request used to send lists of authors and genres to the client
     * @return        router that forwards user to book editing page
     */

    Router editBookMenu(HttpServletRequest request){
        List<Book.BookType> genreList = Arrays.asList(Book.BookType.values());
        List<Author> authorList = service.findAllAuthors();
        request.setAttribute(GENRE_LIST_PARAMETER,genreList);
        request.setAttribute(AUTHOR_LIST,authorList);
        request.setAttribute(BOOK_LIST_PARAMETER,service.findAllBooks());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(EDIT_BOOK_PAGE));
    }

    /**
     * <p>Gets information about a particular book for editing, and then sends
     * the data for processing to the edit page of a particular book</p>
     * @param request sends bookID parameter to the server
     * @return        router that forwards user to edit current book page
     */

    Router editCurrentBookMenu(HttpServletRequest request){
        List<Author> authorList = service.findAllAuthors();
        List<Book.BookType> genreList = Arrays.asList(Book.BookType.values());
        request.getSession().setAttribute(AUTHOR_LIST,authorList);
        request.getSession().setAttribute(GENRE_LIST_PARAMETER,genreList);
        Book book = null;
        String bookID = request.getParameter(BOOK_ID);
        int numberBookID = Integer.parseInt(bookID);
        List<Book> bookList = service.findAllBooks();
        for(Book tempBook: bookList){
            if(tempBook.getId() == numberBookID){
                book = tempBook;
            }
        }
        request.getSession().setAttribute(BOOK,book);
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(EDIT_CURRENT_BOOK_PAGE));
    }

    /**
     * <p>Function for editing current book</p>
     * @param request sends information about book to server
     * @return        the Router that redirects user to edit book page
     */

    Router editCurrentBook(HttpServletRequest request){
        int bookID = Integer.parseInt(request.getParameter(BOOK_ID));
        String[] authors = request.getParameterValues(BOOK_AUTHOR);
        List<Integer> authorsList = new ArrayList<>();
        for(String authorID:authors){
            authorsList.add(Integer.parseInt(authorID));
        }
        try {
            String bookTitle = request.getParameter(BOOK_TITLE);
            String bookPublishingHouse = request.getParameter(BOOK_PUBLISHING_HOUSE);
            Book.NumberInformation bookNumberInformation = new Book.NumberInformation(Integer.parseInt(request.getParameter(BOOK_YEAR)),Integer.parseInt(request.getParameter(BOOK_PAGES)),Integer.parseInt(request.getParameter(BOOK_QUANTITY)));
            Book.BookType bookGenre = Book.BookType.valueOf(request.getParameter(BOOK_GENRE));
            Part bookContentPart = request.getPart(BOOK_CONTENT);
            if (bookContentPart.getSize() != 0) {
                InputStream bookContent = bookContentPart.getInputStream();
                service.editCurrentBook(bookID,authorsList,bookTitle,bookPublishingHouse,bookNumberInformation,bookGenre,bookContent);
            } else {
                service.editCurrentBook(bookID,authorsList,bookTitle,bookPublishingHouse,bookNumberInformation,bookGenre);
            }
            return editBookMenu(request);
        } catch (IOException e){
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the book content from the client");
        } catch (ServletException e){
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the book content from the client");
        }
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(EDIT_BOOK_PAGE));
    }

    /**
     * <p>Method that delete book from library</p>
     * @param request contains book id parameter for delete operation
     * @return        request that contains information about books left in the library
     */

    Router deleteBook(HttpServletRequest request) {
        String bookID = request.getParameter(BOOK_ID);
        service.deleteBook(bookID);
        return deleteBookMenu(request);
    }

    /**
     * <p>Looking for a book on a request from a user</p>
     * @param request contains information for conducting a search
     * @return        router that forwards a user to a search results page
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
     * <p>A method that searches for all authors and genres of books and
     * sends them to the page for adding a new book</p>
     * @param request sends information about authors and genres of books to the user
     * @return        router that forwards a user to a add book page
     */

    Router openAddBookMenu(HttpServletRequest request){
        List<Author> authorList = service.findAllAuthors();
        List<Book.BookType> genreList = Arrays.asList(Book.BookType.values());
        request.setAttribute(AUTHOR_LIST,authorList);
        request.setAttribute(GENRE_LIST_PARAMETER,genreList);
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
        String[] authors = request.getParameterValues(BOOK_AUTHOR);
        List<Author> authorsList = new ArrayList<>();
        for(String authorID:authors){
            authorsList.add(Author.newBuilder().setId(Integer.parseInt(authorID)).build());
        }
        try {
            Book.Builder newBookBuilder = Book.newBuilder()
                    .setTitle(request.getParameter(BOOK_TITLE))
                    .setPublishingHouse(request.getParameter(BOOK_PUBLISHING_HOUSE))
                    .setNumberInformation(new Book.NumberInformation(Integer.parseInt(request.getParameter(BOOK_YEAR)),Integer.parseInt(request.getParameter(BOOK_PAGES)),Integer.parseInt(request.getParameter(BOOK_QUANTITY))))
                    .setGenre(Book.BookType.valueOf(request.getParameter(BOOK_GENRE)))
                    .setAuthors(authorsList);
            Part bookContentPart = request.getPart(BOOK_CONTENT);
            if (bookContentPart.getSize() != 0) {
                InputStream bookContent = bookContentPart.getInputStream();
                newBookBuilder.setBookContent(bookContent);
            }
            Book newBook = newBookBuilder.build();
            if(service.addBook(newBook)){
                request.getSession().setAttribute(BOOK_ADDED_RESULT,SUCCESS);
                request.getSession().setAttribute(BOOK_TITLE,newBook.getTitle());
                return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ADMINISTRATOR_MENU));
            } else {
                request.getSession().setAttribute(BOOK_ADDED_RESULT,ERROR);
                request.getSession().setAttribute(BOOK_TITLE,newBook.getTitle());
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

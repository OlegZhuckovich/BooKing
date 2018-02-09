package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.entity.Author;
import com.epam.zhuckovich.manager.PageManager;
import com.epam.zhuckovich.service.AuthorService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

//InterruptedException
//SQLException
//ServletException
//IllegalArgumentException
//IOException

//Only for classes and interfaces ::::::: @author (required), @version (required) @param, @see
//Only for methods @param, @return, @throws, @see, @serialData
//Constructors @param

/**
 * <p>A class that contains methods for various actions
 * with the authors of the books presented in the library's fund.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Author
 * @since       1.0
 */

class AuthorCommand {

    private static final Logger LOGGER = LogManager.getLogger(AuthorCommand.class);

    private static final String SPACE = " ";
    private static final String NAME_AUTHOR = "nameAuthor";
    private static final String SURNAME_AUTHOR = "surnameAuthor";
    private static final String BIOGRAPHY_AUTHOR = "biographyAuthor";
    private static final String PHOTO_AUTHOR = "photoAuthor";
    private static final String ADD_AUTHOR_PAGE = "addAuthor";
    private static final String AUTHOR_LIST = "authorList";
    private static final String AUTHOR_GALLERY_PAGE = "authorGallery";
    private static final String LIBRARIAN_MENU = "librarianMenu";
    private static final String AUTHOR = "author";
    private static final String AUTHOR_ADDED_RESULT = "authorAddedResult";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private AuthorService service;

    /**
     * Class constructor
     */

    AuthorCommand(){
        this.service = new AuthorService();
    }

    /**
     * <p>A method for retrieving data from a request object and creating
     * a new instance of the Author class.</p>
     * @param  request request object in which data is received to add a new author
     * @return         the Router which redirects app to librarian menu page
     */

    Router addNewAuthor(HttpServletRequest request) {
        try {
            String name = request.getParameter(NAME_AUTHOR);
            String surname = request.getParameter(SURNAME_AUTHOR);
            String biography = request.getParameter(BIOGRAPHY_AUTHOR);
            Part photoPart = request.getPart(PHOTO_AUTHOR);
            Author.Builder newAuthor = Author.newBuilder()
                                .setName(name)
                                .setSurname(surname)
                                .setBiography(biography);
            if (photoPart != null) {
                InputStream photo = photoPart.getInputStream();
                newAuthor.setPhoto(photo);
            }
            Author author = newAuthor.build();
            if(service.addNewAuthor(author)){
                request.getSession().setAttribute(AUTHOR_ADDED_RESULT,SUCCESS);
            } else {
                request.getSession().setAttribute(AUTHOR_ADDED_RESULT,ERROR);
            }
            request.getSession().setAttribute(AUTHOR,author.getName() + SPACE + author.getSurname());
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LIBRARIAN_MENU));
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "IOException was occurred during getting the photo from the client");
        } catch (ServletException e) {
            LOGGER.log(Level.ERROR, "ServletException was occurred during getting the photo from the client");
        }
        return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LIBRARIAN_MENU));
    }

    /**
     * <p>Method for obtaining information about all the authors
     * available in the application.</p>
     * @param request
     * @return
     */

    Router viewAuthors(HttpServletRequest request){
        request.setAttribute(AUTHOR_LIST,service.viewAuthors());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(LIBRARIAN_MENU));
    }

}

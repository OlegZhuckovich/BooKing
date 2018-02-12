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

//Only for classes and interfaces ::::::: @author (required), @version (required) @param, @see , @since
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

class AuthorCommand extends AbstractCommand{

    private static final Logger LOGGER = LogManager.getLogger(AuthorCommand.class);
    private AuthorService service;

    /**
     * Class constructor that initializes the AuthorService variable
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

    Router addAuthor(HttpServletRequest request) {
        try {
            String authorName = request.getParameter(AUTHOR_NAME);
            String authorSurname = request.getParameter(AUTHOR_SURNAME);
            String authorBiography = request.getParameter(AUTHOR_BIOGRAPHY);
            Part authorPhotoPart = request.getPart(AUTHOR_PHOTO);
            Author.Builder newAuthor = Author.newBuilder()
                                .setName(authorName)
                                .setSurname(authorSurname)
                                .setBiography(authorBiography);
            if (authorPhotoPart != null) {
                InputStream authorPhoto = authorPhotoPart.getInputStream();
                newAuthor.setPhoto(authorPhoto);
            }
            Author author = newAuthor.build();
            if(service.addAuthor(author)){
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
     * @param request sends the list of authors to the client
     * @return        forwards app to author gallery page
     */

    Router viewAuthors(HttpServletRequest request){
        request.setAttribute(AUTHOR_LIST,service.viewAuthors());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(AUTHOR_GALLERY));
    }

}

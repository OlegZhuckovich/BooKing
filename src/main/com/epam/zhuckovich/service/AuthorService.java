package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.AuthorDAO;
import com.epam.zhuckovich.entity.Author;

import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>A class that checks the validity of information received from methods
 * of the AuthorCommand class and accesses the dao layer.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Author
 * @since       1.0
 */

public class AuthorService {

    private static final String NAME_SURNAME_REGEX = "[A-ZА-Я][a-zа-я]+-?[A-ZА-Я]?[a-zа-я]+?";
    private Pattern nameSurnameRegex;

    private AuthorDAO authorDAO;

    /**
     * Class constructor
     */

    public AuthorService() {
        this.authorDAO = AuthorDAO.getInstance();
        nameSurnameRegex = Pattern.compile(NAME_SURNAME_REGEX);
    }

    /**
     * <p>Checks the data to add a new author to match the regular expressions
     * and sends them to the dao layer for further processing</p>
     * @param newAuthor author that will be added to the database
     * @return          true if author was added
     */

    public boolean addAuthor(Author newAuthor) {
        if (newAuthor.getName() == null ||
                newAuthor.getSurname() == null ||
                newAuthor.getBiography() == null ||
                newAuthor.getPhoto() == null) {
            return false;
        }
        return nameSurnameRegex.matcher(newAuthor.getName()).matches() &&
                nameSurnameRegex.matcher(newAuthor.getSurname()).matches() &&
                authorDAO.addAuthor(newAuthor);
    }

    /**
     * <p>Turn to the dao layer to get a list of all authors of books</p>
     * @return the list of book authors
     */

    public List<Author> viewAuthors(){
        return authorDAO.executeQuery(statement -> authorDAO.findAllAuthors(statement),AuthorDAO.getFindAllAuthorsQuery());
    }
}

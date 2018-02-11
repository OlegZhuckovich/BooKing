package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.AuthorDAO;
import com.epam.zhuckovich.entity.Author;

import java.util.List;
import java.util.regex.Matcher;
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

    public AuthorService() {
        this.authorDAO = AuthorDAO.getInstance();
        nameSurnameRegex = Pattern.compile(NAME_SURNAME_REGEX);
    }

    public boolean addAuthor(Author newAuthor) {
        if (newAuthor.getName() == null || newAuthor.getSurname() == null || newAuthor.getBiography() == null || newAuthor.getPhoto() == null) {
            return false;
        }
        boolean nameMatch, surnameMatch;
        Matcher nameMatcher = nameSurnameRegex.matcher(newAuthor.getName());
        Matcher surnameMatcher = nameSurnameRegex.matcher(newAuthor.getSurname());
        nameMatch = nameMatcher.matches();
        surnameMatch = surnameMatcher.matches();
        return nameMatch && surnameMatch && authorDAO.addAuthor(newAuthor);
    }

    public List<Author> viewAuthors(){
        return authorDAO.executeQuery(statement -> authorDAO.findAllAuthors(statement),AuthorDAO.getFindAllAuthors());
    }
}

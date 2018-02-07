package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.AuthorDAO;
import com.epam.zhuckovich.entity.Author;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorService {

    private static final String NAME_SURNAME_REGEX = "[A-ZА-Я][a-zа-я]+-?[A-ZА-Я]?[a-zа-я]+?";

    private AuthorDAO authorDAO;

    public AuthorService() {
        this.authorDAO = AuthorDAO.getInstance();
    }

    public boolean addNewAuthor(Author newAuthor) {
        if(newAuthor.getName()==null || newAuthor.getSurname()==null || newAuthor.getBiography()==null){
            return false;
        }
        boolean nameMatch;
        boolean surnameMatch;
        Pattern nameSurnameRegex = Pattern.compile(NAME_SURNAME_REGEX);
        Matcher nameMatcher = nameSurnameRegex.matcher(newAuthor.getName());
        Matcher surnameMatcher = nameSurnameRegex.matcher(newAuthor.getSurname());
        nameMatch = nameMatcher.matches();
        surnameMatch = surnameMatcher.matches();
        return (nameMatch || surnameMatch) && authorDAO.addNewAuthor(newAuthor);
    }

    public List<Author> viewAuthors(){
        return authorDAO.executeQuery(statement -> authorDAO.findAllAuthors(statement),AuthorDAO.getFindAllAuthors());
    }
}

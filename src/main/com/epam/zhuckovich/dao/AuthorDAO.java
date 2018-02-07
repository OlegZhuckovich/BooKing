package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends AbstractDAO<Integer,Author>{

    private static AuthorDAO authorDAO;

    private static final String FIND_ALL_AUTHORS_QUERY = "SELECT name, surname, email, password, role FROM LibraryDatabase.member WHERE email = ? AND password = ?";
    private static final String ADD_NEW_AUTHOR_QUERY = "INSERT INTO LibraryDatabase.author (name, surname, biography, photo) VALUES (?,?,?,?)";
    private static final String FIND_AUTHORS_BY_NAME_SURNAME_QUERY = "SELECT authorID FROM LibraryDatabase.author WHERE name = ? AND surname = ?";

    private static final String FIND_AUTHORS_BY_BOOK_ID = "SELECT author.authorID, author.name, author.surname " +
                                                            "FROM author " +
                                                            "INNER JOIN book_author ON book_author.authorID = author.authorID " +
                                                            "INNER JOIN book ON book.bookID = book_author.bookID " +
                                                          "WHERE book.bookID = ?";
    private static final String FIND_ALL_AUTHORS = "SELECT authorID, name, surname, biography FROM author ";


    private static final String AUTHOR_ID = "authorID";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIOGRAPHY = "biography";

    private AuthorDAO(){}

    public static AuthorDAO getInstance() {
        if (authorDAO == null) {
            authorDAO = new AuthorDAO();
        }
        return authorDAO;
    }

    List<Author> findAuthorsByNameSurname(PreparedStatement statement, String name, String surname) {
        boolean isResultSetEmpty = true;
        try {
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet authorResultSet = statement.executeQuery();
            isResultSetEmpty = !authorResultSet.isBeforeFirst();
        } catch (SQLException e){

        }
        return isResultSetEmpty ? null : new ArrayList<>();
    }

    public boolean addNewAuthor(Author newAuthor) {
        return executeQuery(statement -> findAuthorsByNameSurname(statement, newAuthor.getName(), newAuthor.getSurname()), FIND_AUTHORS_BY_NAME_SURNAME_QUERY) == null
            && executeUpdate(ADD_NEW_AUTHOR_QUERY, newAuthor.getName(), newAuthor.getSurname(), newAuthor.getBiography(), newAuthor.getPhoto()) != 0;
    }

    List<Author> findAuthorsByBookID(PreparedStatement statement, int bookID){
        List<Author> authorList = new ArrayList<>();
        try{
            statement.setInt(1,bookID);
            ResultSet authorResultSet = statement.executeQuery();
            if(!authorResultSet.isBeforeFirst()){

            } else {
                while (authorResultSet.next()) {
                    authorList.add(Author.newBuilder()
                            .setId(authorResultSet.getInt(AUTHOR_ID))
                            .setName(authorResultSet.getString(NAME))
                            .setSurname(authorResultSet.getString(SURNAME))
                            .build());
                }
            }
        } catch(SQLException e){

        }
        return authorList;
    }

    public List<Author> findAllAuthors(PreparedStatement statement){
        List<Author> authorList = new ArrayList<>();
        try{
            ResultSet authorResultSet = statement.executeQuery();
            if(!authorResultSet.isBeforeFirst()){

            } else {
                while (authorResultSet.next()) {
                    authorList.add(Author.newBuilder()
                            .setId(authorResultSet.getInt(AUTHOR_ID))
                            .setName(authorResultSet.getString(NAME))
                            .setSurname(authorResultSet.getString(SURNAME))
                            .setBiography(authorResultSet.getString(BIOGRAPHY))
                            .build());
                }
            }
        } catch(SQLException e){

        }
        return authorList;
    }

    public static String getFindAllAuthorsQuery(){
        return FIND_ALL_AUTHORS_QUERY;
    }

    public static String getFindAuthorsByNameSurnameQuery(){
        return FIND_AUTHORS_BY_NAME_SURNAME_QUERY;
    }

    public static String getAddNewAuthorQuery(){
        return ADD_NEW_AUTHOR_QUERY;
    }

    public static String getFindAuthorsByBookId(){
        return FIND_AUTHORS_BY_BOOK_ID;
    }

    public static String getFindAllAuthors(){
        return FIND_ALL_AUTHORS;
    }

}

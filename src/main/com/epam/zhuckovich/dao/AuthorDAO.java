package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Author;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The class contains methods for adding extraction and processing
 * of data about authors from the database</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public class AuthorDAO extends AbstractDAO<Author>{

    private static final Logger LOGGER = LogManager.getLogger(AuthorDAO.class);

    private static AuthorDAO authorDAO;

    private static final String ADD_NEW_AUTHOR_QUERY = "INSERT INTO LibraryDatabase.author (name, surname, biography, photo) VALUES (?,?,?,?)";
    private static final String FIND_AUTHORS_BY_BOOK_ID_QUERY = "SELECT author.authorID, author.name, author.surname " +
                                                            "FROM author " +
                                                            "INNER JOIN book_author ON book_author.authorID = author.authorID " +
                                                            "INNER JOIN book ON book.bookID = book_author.bookID " +
                                                          "WHERE book.bookID = ?";
    private static final String FIND_ALL_AUTHORS_QUERY = "SELECT authorID, name, surname, biography FROM author ORDER BY surname, name";
    private static final String FIND_AUTHORS_BY_NAME_SURNAME_QUERY = "SELECT authorID FROM LibraryDatabase.author WHERE name = ? AND surname = ?";

    /**
     * Private constructor
     */

    private AuthorDAO(){}

    /**
     * <p>Method that returns the new AuthorDAO object if it is not
     * created in other case returns the AuthorDAO object</p>
     * @return the instance of AuthorDAO
     */

    public static AuthorDAO getInstance() {
        if (authorDAO == null) {
            authorDAO = new AuthorDAO();
        }
        return authorDAO;
    }

    /**
     * <p>The method finds authors by name and surname in the database</p>
     * @param statement preparedStatement
     * @param name      name of the author
     * @param surname   surname of the author
     * @return          list of authors
     */

    List<Author> findAuthorsByNameSurname(PreparedStatement statement, String name, String surname) {
        boolean isResultSetEmpty = true;
        try {
            statement.setString(1, name);
            statement.setString(2, surname);
            ResultSet authorResultSet = statement.executeQuery();
            isResultSetEmpty = !authorResultSet.isBeforeFirst();
        } catch (SQLException e){
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAuthorsByNameSurname operation");
        }
        return isResultSetEmpty ? null : new ArrayList<>();
    }

    /**
     * <p>The method adds a new author to the database</p>
     * @param newAuthor author that will be added
     * @return          the true if author was added to the database
     */

    public boolean addAuthor(Author newAuthor) {
        if(executeQuery(statement -> findAuthorsByNameSurname(statement, newAuthor.getName(), newAuthor.getSurname()), FIND_AUTHORS_BY_NAME_SURNAME_QUERY) == null){
            return executeUpdate(ADD_NEW_AUTHOR_QUERY, newAuthor.getName(), newAuthor.getSurname(), newAuthor.getBiography(), newAuthor.getPhoto()) != 0;
        }
        return false;
    }

    /**
     * <p>The method finds the authors of the specific book</p>
     * @param statement preparedStatement
     * @param bookID    id of the book
     * @return          list of authors of the specific book
     */

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
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAuthorsByBookID operation");
        }
        return authorList;
    }

    /**
     * <p>The method finds all authors who are located in the database</p>
     * @param statement preparedStatement
     * @return          list of authors
     */

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
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAllAuthors operation");
        }
        return authorList;
    }

    /**
     * <p>Returns the FIND_AUTHORS_BY_NAME_SURNAME_QUERY query</p>
     * @return    the FIND_AUTHORS_BY_NAME_SURNAME_QUERY query
     */

    public static String getFindAuthorsByNameSurnameQuery(){
        return FIND_AUTHORS_BY_NAME_SURNAME_QUERY;
    }

    /**
     * <p>Returns the ADD_NEW_AUTHOR_QUERY query</p>
     * @return    the ADD_NEW_AUTHOR_QUERY query
     */

    public static String getAddNewAuthorQuery(){
        return ADD_NEW_AUTHOR_QUERY;
    }

    /**
     * <p>Returns the FIND_AUTHORS_BY_BOOK_ID_QUERY query</p>
     * @return    the FIND_AUTHORS_BY_BOOK_ID_QUERY query
     */

    public static String getFindAuthorsByBookIdQuery(){
        return FIND_AUTHORS_BY_BOOK_ID_QUERY;
    }

    /**
     * <p>Returns the FIND_ALL_AUTHORS_QUERY query</p>
     * @return    the FIND_ALL_AUTHORS_QUERY query
     */

    public static String getFindAllAuthorsQuery(){
        return FIND_ALL_AUTHORS_QUERY;
    }

}

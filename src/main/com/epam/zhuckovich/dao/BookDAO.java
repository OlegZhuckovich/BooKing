package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;
import com.epam.zhuckovich.entity.Book;
import com.epam.zhuckovich.exception.SQLTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The class contains methods for adding extraction and processing
 * of data about books from the database</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public class BookDAO extends AbstractDAO<Book>{

    private static final Logger LOGGER = LogManager.getLogger(BookDAO.class);

    private static BookDAO bookDAO;

    private static final String ADD_BOOK_QUERY = "INSERT INTO LibraryDatabase.book (title,genre,publishing_house,year,pages,quantity) VALUES (?,?,?,?,?,?)";
    private static final String ADD_BOOK_AUTHOR_QUERY = "INSERT INTO book_author VALUES (?,?)";
    private static final String ADD_BOOK_CONTENT_QUERY = "INSERT INTO book_content VALUES (?,?)";
    private static final String DELETE_BOOK_QUERY = "UPDATE book SET quantity = 0 WHERE bookID = ?";
    private static final String EDIT_DELETE_AUTHORS_QUERY = "DELETE FROM book_author WHERE bookID = ?";
    private static final String EDIT_BOOK_QUERY = "UPDATE book SET title = ?, genre = ?, publishing_house = ?, year = ?, pages = ?, quantity = ? WHERE bookID = ?";
    private static final String EDIT_BOOK_CONTENT_QUERY = "UPDATE book_content SET book_content = ? WHERE bookID = ?";
    private static final String EDIT_ADD_NEW_AUTHORS_QUERY = "INSERT INTO book_author VALUES (?,?)";
    private static final String FIND_ALL_BOOKS_QUERY = "SELECT book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages, book.quantity " +
                                                        "FROM book ";
    private static final String FIND_BOOK_CONTENT_QUERY = "SELECT book_content FROM book_content WHERE bookID = ?";
    private static final String SEARCH_BOOK_BY_CRITERIA_QUERY = "SELECT DISTINCT book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages, book.quantity " +
                                                                    "FROM book " +
                                                                    "INNER JOIN book_author ON book_author.bookID=book.bookID " +
                                                                    "INNER JOIN author ON author.authorID = book_author.authorID " +
                                                                "WHERE ";

    private static final String SEARCH_BOOK_BY_CRITERIA_LIKE_STATEMENT = " LIKE ? ESCAPE '!' AND book.quantity != 0 AND book.bookID NOT IN " +
                                                                            "(SELECT bookID " +
                                                                                "FROM ordered_book " +
                                                                             "WHERE memberID = ? AND (return_date >= CURDATE() OR return_date IS NULL))";

    /**
     * Private constructor
     */

    private BookDAO(){}

    /**
     * <p>Method that returns the new BookDAO object if it is not
     * created in other case returns the BookDAO object</p>
     * @return the instance of BookDAO
     */

    public static BookDAO getInstance() {
        if (bookDAO == null) {
            bookDAO = new BookDAO();
        }
        return bookDAO;
    }

    /**
     * <p>The method that finds all books all books that are contained
     * in the database or searches books for parameters if parameters variable
     * length is not 0</p>
     * @param statement  preparedStatement
     * @param parameters parameters for searching
     * @return           the list of books
     */

    public List<Book> findAllBooks(PreparedStatement statement, String...parameters) {
        var bookList = new ArrayList<Book>();
        try {
            if(parameters.length != 0) {
                if (parameters[0].charAt(0) == '%') {
                    statement.setString(1, parameters[0]);
                    statement.setInt(2, Integer.parseInt(parameters[1]));
                } else {
                    statement.setInt(1, Integer.parseInt(parameters[0]));
                }
            }
            var bookResultSet = statement.executeQuery();
            if(bookResultSet.isBeforeFirst()){
                while(bookResultSet.next()){
                    var bookID = bookResultSet.getInt(BOOK_ID);
                    var authorDAO = AuthorDAO.getInstance();
                    var authors = authorDAO.executeQuery(preparedStatement -> authorDAO.findAuthorsByBookID(preparedStatement,bookID),AuthorDAO.getFindAuthorsByBookIdQuery());
                    var book = Book.newBuilder()
                            .setId(bookResultSet.getInt(BOOK_ID))
                            .setTitle(bookResultSet.getString(TITLE))
                            .setGenre(Book.Genre.valueOf(bookResultSet.getString(GENRE)))
                            .setPublishingHouse(bookResultSet.getString(PUBLISHING_HOUSE))
                            .setNumberInformation(new Book.Metadata(bookResultSet.getInt(YEAR),
                                    bookResultSet.getInt(PAGES),
                                    bookResultSet.getInt(QUANTITY)))
                            .setAuthors(authors)
                            .build();
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAllBooks operation");
        }
        return bookList;
    }

    /**
     * <p>The method that adds new book to the library</p>
     * @param parameters bookParameters
     * @return           bookID if the book was added, otherwise 0
     */

    public int addBook(Object...parameters){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        var bookID = 0;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(BookDAO.ADD_BOOK_QUERY,Statement.RETURN_GENERATED_KEYS);
            if(parameters.length != 0) {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }
            preparedStatement.executeUpdate();
            connection.commit();
            var bookIdResultSet = preparedStatement.getGeneratedKeys();
            if(bookIdResultSet.next()) {
                bookID = bookIdResultSet.getInt(1);
            }
        } catch(SQLTechnicalException e) {
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during addBook operation");
            connection.rollback();
        } catch(SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during addBook operation");
            connection.rollback();
        } finally {
            close(preparedStatement,connection);
        }
        return bookID;
    }

    /**
     * <p>Method that loads book content from the database</p>
     * @param bookID bookID parameter
     * @return       the contents of the book presented as a byte stream
     */

    public byte[] loadBook(int bookID){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        Blob bookFile;
        byte[] book = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(FIND_BOOK_CONTENT_QUERY);
            preparedStatement.setInt(1,bookID);
            var bookResultSet = preparedStatement.executeQuery();
            if(bookResultSet.next()){
                bookFile = bookResultSet.getBlob(BOOK_CONTENT);
                book = bookFile.getBytes(1, (int) bookFile.length());
            }
            connection.commit();
        } catch(SQLTechnicalException e) {
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during loadBook operation");
            connection.rollback();
        } catch(SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during loadBook operation");
            connection.rollback();
        } finally {
            close(preparedStatement,connection);
        }
        return book;
    }

    /**
     * <p>Returns the FIND_ALL_BOOKS_QUERY query</p>
     * @return    the FIND_ALL_BOOKS_QUERY query
     */

    public static String getFindAllBooksQuery(){
        return FIND_ALL_BOOKS_QUERY;
    }

    /**
     * <p>Returns the SEARCH_BOOK_BY_CRITERIA_QUERY query</p>
     * @return    the SEARCH_BOOK_BY_CRITERIA_QUERY query
     */

    public static String getSearchBookQueryByCriteria(){
        return SEARCH_BOOK_BY_CRITERIA_QUERY;
    }

    /**
     * <p>Returns the DELETE_BOOK_QUERY query</p>
     * @return    the DELETE_BOOK_QUERY query
     */

    public static String getDeleteBookQuery(){
        return DELETE_BOOK_QUERY;
    }

    public static String getEditDeleteAuthorsQuery(){
        return EDIT_DELETE_AUTHORS_QUERY;
    }

    public static String getEditBookQuery(){
        return EDIT_BOOK_QUERY;
    }

    public static String getEditBookContentQuery(){
        return EDIT_BOOK_CONTENT_QUERY;
    }

    public static String getEditAddNewAuthorsQuery(){
        return EDIT_ADD_NEW_AUTHORS_QUERY;
    }

    /**
     * <p>Returns the SEARCH_BOOK_BY_CRITERIA_LIKE_STATEMENT query</p>
     * @return    the SEARCH_BOOK_BY_CRITERIA_LIKE_STATEMENT query
     */

    public static String getSearchBookByCriteriaLikeStatement(){
        return SEARCH_BOOK_BY_CRITERIA_LIKE_STATEMENT;
    }

    /**
     * <p>Returns the ADD_BOOK_AUTHOR_QUERY query</p>
     * @return    the ADD_BOOK_AUTHOR_QUERY query
     */

    public static String getAddBookAuthorQuery(){
        return ADD_BOOK_AUTHOR_QUERY;
    }

    /**
     * <p>Returns the ADD_BOOK_CONTENT_QUERY query</p>
     * @return    the ADD_BOOK_CONTENT_QUERY query
     */

    public static String getAddBookContentQuery(){
        return ADD_BOOK_CONTENT_QUERY;
    }

}


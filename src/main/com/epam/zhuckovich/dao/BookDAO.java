package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;
import com.epam.zhuckovich.entity.Author;
import com.epam.zhuckovich.entity.Book;
import com.epam.zhuckovich.exception.SQLTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends AbstractDAO<Book>{

    private static final Logger LOGGER = LogManager.getLogger(BookDAO.class);

    private static BookDAO bookDAO;

    private static final String ADD_BOOK_QUERY = "INSERT INTO LibraryDatabase.book (title,genre,publishing_house,year,pages,quantity) VALUES (?,?,?,?,?,?)";
    private static final String ADD_BOOK_AUTHOR_QUERY = "INSERT INTO book_author VALUES (?,?)";
    private static final String ADD_BOOK_CONTENT_QUERY = "INSERT INTO book_content VALUES (?,?)";
    private static final String DELETE_BOOK_QUERY = "UPDATE book SET quantity = 0 WHERE bookID = ?";
    private static final String FIND_ALL_BOOKS_QUERY = "SELECT book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages, book.quantity " +
                                                        "FROM book ";

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

    public static BookDAO getInstance() {
        if (bookDAO == null) {
            bookDAO = new BookDAO();
        }
        return bookDAO;
    }

    public List<Book> findAllBooks(PreparedStatement statement, String...parameters) {
        List<Book> bookList = new ArrayList<>();
        try {
            if(parameters.length != 0) {
                if (parameters[0].charAt(0) == '%') {
                    statement.setString(1, parameters[0]);
                    statement.setInt(2, Integer.parseInt(parameters[1]));
                } else {
                    statement.setInt(1, Integer.parseInt(parameters[0]));
                }
            }
            ResultSet bookResultSet = statement.executeQuery();
            if(!bookResultSet.isBeforeFirst()){

            } else {
                while(bookResultSet.next()){
                    int bookID = bookResultSet.getInt(BOOK_ID);
                    AuthorDAO authorDAO = AuthorDAO.getInstance();
                    List<Author> bookAuthorList = authorDAO.executeQuery(preparedStatement -> authorDAO.findAuthorsByBookID(preparedStatement,bookID),AuthorDAO.getFindAuthorsByBookId());
                    Book book = Book.newBuilder()
                            .setId(bookResultSet.getInt(BOOK_ID))
                            .setTitle(bookResultSet.getString(TITLE))
                            .setGenre(Book.BookType.valueOf(bookResultSet.getString(GENRE)))
                            .setPublishingHouse(bookResultSet.getString(PUBLISHING_HOUSE))
                            .setNumberInformation(new Book.NumberInformation(bookResultSet.getInt(YEAR), bookResultSet.getInt(PAGES), bookResultSet.getInt(QUANTITY)))
                            .setAuthors(bookAuthorList)
                            .build();
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAllBooks operation");
        }
        return bookList;
    }

    public int addBook(Object...parameters){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        int bookID = 0;
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
            ResultSet bookIdResultSet = preparedStatement.getGeneratedKeys();
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

    public byte[] loadBook(int bookID, String query){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        Blob bookFile;
        byte[] book = null;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,bookID);
            ResultSet bookResultSet = preparedStatement.executeQuery();
            if(bookResultSet.next()){
                bookFile = bookResultSet.getBlob("book_content");
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


    public static String getFindAllBooksQuery(){
        return FIND_ALL_BOOKS_QUERY;
    }

    public static String getSearchBookQueryByCriteria(){
        return SEARCH_BOOK_BY_CRITERIA_QUERY;
    }

    public static String getDeleteBookQuery(){
        return DELETE_BOOK_QUERY;
    }

    public static String getSearchBookByCriteriaLikeStatement(){
        return SEARCH_BOOK_BY_CRITERIA_LIKE_STATEMENT;
    }

    public static String getAddBookQuery(){
        return ADD_BOOK_QUERY;
    }

    public static String getAddBookAuthorQuery(){
        return ADD_BOOK_AUTHOR_QUERY;
    }

    public static String getAddBookContentQuery(){
        return ADD_BOOK_CONTENT_QUERY;
    }

}


package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Entity;
import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;
import com.epam.zhuckovich.exception.SQLTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A class that contains common methods and variables that are used by
 * all dao classes</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public class AbstractDAO<T extends Entity> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class);

    static final String ADRESS_ID = "adressID";
    static final String AUTHOR_ID = "authorID";
    static final String AVATAR = "avatar";
    static final String BOOK_ID = "bookID";
    static final String BOOK_CONTENT = "book_content";
    static final String BIOGRAPHY = "biography";
    static final String CITY = "city";
    static final String EMAIL = "email";
    static final String GENRE = "genre";
    static final String HOUSE = "house";
    static final String MEMBER_ID = "memberID";
    static final String NAME = "name";
    static final String ORDER_DATE = "order_date";
    static final String ORDER_TYPE = "order_type";
    static final String PAGES = "pages";
    static final String PHOTO = "photo";
    static final String PUBLISHING_HOUSE = "publishing_house";
    static final String QUANTITY = "quantity";
    static final String REGISTRATION_DATE = "registration_date";
    static final String RETURN_DATE = "return_date";
    static final String ROLE = "role";
    static final String STREET = "street";
    static final String SURNAME = "surname";
    static final String TELEPHONE_NUMBER = "telephone_number";
    static final String TITLE = "title";
    static final String YEAR = "year";

    /**
     * <p>A method that executes a query to the database and returns
     * a list of entity objects</p>
     * @param actionDAO realization of ActionDAO interface
     * @param query     contains sql query
     * @return          list of entity objects
     */

    public List<T> executeQuery(ActionDAO<T> actionDAO, String query){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        List<T> entityList = new ArrayList<>();
        try{
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            entityList = actionDAO.executeQuery(preparedStatement);
        } catch(SQLTechnicalException e) {
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during executeQuery");
        } finally {
            close(preparedStatement,connection);
        }
        return entityList;
    }

    /**
     * <p>A method that connects to the database and performs removal requests
     * updating and adding data</p>
     * @param query      contains sql query
     * @param parameters sql query parameters
     * @return           the executeUpdate value
     */

    public int executeUpdate(String query, Object... parameters) {
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        int operationSuccess = 0;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            if(parameters.length != 0) {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }
            operationSuccess = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLTechnicalException e) {
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during executeUpdate");
            connection.rollback();
        } catch (SQLException exp) {
            exp.printStackTrace();
            LOGGER.log(Level.ERROR,"SQLException was occurred during executeUpdate");
        } finally {
            close(preparedStatement,connection);
        }
        return operationSuccess;
    }

    /**
     * <p>Closes the proxyConnection</p>
     * @param connection proxyConnection that will be closed
     */

    private void close(ProxyConnection connection) {
        if(connection!=null){
            try {
                connection.setAutoCommit(true);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLTechnicalException e) {
                LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during setAutoCommit operation");
            }
        }
    }

    /**
     * <p>Closes preparedStatement and call method for closing proxyConnection</p>
     * @param preparedStatement preparedStatement
     * @param connection        proxyConnection
     */

    void close(Statement preparedStatement, ProxyConnection connection){
        try{
            if(preparedStatement != null){
                preparedStatement.close();
                close(connection);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during closing the statement");
        }
    }

}

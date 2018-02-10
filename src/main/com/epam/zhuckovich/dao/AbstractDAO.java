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

public class AbstractDAO<T extends Entity> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class);

    public List<T> executeQuery(ActionDAO<T> actionDAO, String query){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        List<T> bookList = new ArrayList<>();
        try{
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(query);
            bookList = actionDAO.executeQuery(preparedStatement);
        } catch(SQLTechnicalException e) {
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during executeQuery");
        } finally {
            close(preparedStatement,connection);
        }
        return bookList;
    }

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

    void close(ProxyConnection connection) {
        if(connection!=null){
            try {
                connection.setAutoCommit(true);
                ConnectionPool.getInstance().releaseConnection(connection);
            } catch (SQLTechnicalException e) {
                LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during setAutoCommit operation");
            }
        }
    }

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

package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Entity;
import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AbstractDAO<K,T extends Entity> {

    public List<T> executeQuery(ActionDAO<T> actionDAO, String query){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        List<T> bookList = new ArrayList<>();
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(query);
            bookList = actionDAO.executeQuery(preparedStatement);
            connection.commit();
        } catch(SQLException e) {
            connection.rollback();
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
        } catch(SQLException e) {
            connection.rollback();
        } finally {
            close(preparedStatement,connection);
        }
        return operationSuccess;
    }

    void close(Statement preparedStatement, ProxyConnection connection){
        try{
            if(preparedStatement != null){
                preparedStatement.close();
                close(connection);
            }
        } catch (SQLException e) {
            //log
        }
    }

    void close(ProxyConnection connection) throws SQLException {
        if(connection!=null){
            connection.setAutoCommit(true);
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }
}

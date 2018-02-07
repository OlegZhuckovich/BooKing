package com.epam.zhuckovich.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>Proxy class that contains a single connection to the database
 * and overrides main operations with it.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Connection
 * @since       1.0
 */

public class ProxyConnection {

    private Connection connection;

    ProxyConnection(Connection connection){
        this.connection = connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public PreparedStatement prepareStatement(String sql, int generatedKeys) throws SQLException {
        return connection.prepareStatement(sql,generatedKeys);
    }

    public void releaseConnection() throws InterruptedException {
        ConnectionPool.getInstance().releaseConnection(this);
    }

    void closeConnection() throws SQLException {
        connection.close();
    }

    public void setAutoCommit(boolean flag) throws SQLException {
        connection.setAutoCommit(flag);
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

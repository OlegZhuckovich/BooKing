package com.epam.zhuckovich.connection;

import com.epam.zhuckovich.exception.SQLTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private static final Logger LOGGER = LogManager.getLogger(ProxyConnection.class);

    private Connection connection;

    ProxyConnection(Connection connection){
        this.connection = connection;
    }

    public Statement createStatement() throws SQLTechnicalException {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLTechnicalException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    public PreparedStatement prepareStatement(String sql, int generatedKeys) throws SQLTechnicalException {
        try {
            return connection.prepareStatement(sql,generatedKeys);
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    public void releaseConnection() {
        ConnectionPool.getInstance().releaseConnection(this);
    }

    void closeConnection() throws SQLTechnicalException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    public void setAutoCommit(boolean flag) throws SQLTechnicalException {
        try {
            connection.setAutoCommit(flag);
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    public void commit() throws SQLTechnicalException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during rollback operation");
        }

    }

}

package com.epam.zhuckovich.connection;

import com.epam.zhuckovich.exception.SQLTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    /**
     * <p>Class constructor</p>
     * @param connection connection to the database
     */

    ProxyConnection(Connection connection){
        this.connection = connection;
    }

    /**
     * <p>Create prepared statement on connection</p>
     * @param sql contains sql query
     * @return    preparedStatement that method created
     * @throws SQLTechnicalException if method cannot create preparedStatement
     */

    public PreparedStatement prepareStatement(String sql) throws SQLTechnicalException {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    /**
     * <p>Create prepared statement on connection with generatedKeys</p>
     * @param sql           contains sql query
     * @param generatedKeys contains generatedKeys statement
     * @return              preparedStatement that method created
     * @throws SQLTechnicalException if method cannot create preparedStatement
     */

    public PreparedStatement prepareStatement(String sql, int generatedKeys) throws SQLTechnicalException {
        try {
            return connection.prepareStatement(sql,generatedKeys);
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    /**
     * Close the connection to the database
     * @throws SQLTechnicalException if connection cannot be closed
     */

    void closeConnection() throws SQLTechnicalException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    /**
     * <p>Enables or disables autocommit, depending on the flag variable passed
     * to the method</p>
     * @param flag the variable that determines will enabled or disabled autocommit
     * @throws SQLTechnicalException if autocommit cannot be enabled of disabled
     */

    public void setAutoCommit(boolean flag) throws SQLTechnicalException {
        try {
            connection.setAutoCommit(flag);
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    /**
     * Commit changes made by sql query
     */

    public void commit() throws SQLTechnicalException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new SQLTechnicalException(e);
        }
    }

    /**
     * Rollback changes made by sql query
     */

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during rollback operation");
        }

    }

}

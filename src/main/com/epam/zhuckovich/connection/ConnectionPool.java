package com.epam.zhuckovich.connection;

import com.epam.zhuckovich.exception.SQLTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>Class that contains a connection pool for communicating the
 * program with the database</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         ProxyConnection
 * @since       1.0
 */

public final class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool connectionPool;
    private static final int POOL_SIZE = 20;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean connectionPoolCreated = new AtomicBoolean(false);
    private BlockingQueue<ProxyConnection> connectionQueue;
    private ResourceBundle databaseBundle;

    /**
     * Private class constructor
     */

    private ConnectionPool(){
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        databaseBundle = ResourceBundle.getBundle("database");
        Properties connectionProperties = new Properties();
        connectionProperties.put(databaseBundle.getString("user"), databaseBundle.getString("user.value"));
        connectionProperties.put(databaseBundle.getString("password"), databaseBundle.getString("password.value"));
        connectionProperties.put(databaseBundle.getString("characterEncoding"), databaseBundle.getString("characterEncoding.value"));
        connectionProperties.put(databaseBundle.getString("autoReconnect"), databaseBundle.getString("autoReconnect.value"));
        connectionProperties.put(databaseBundle.getString("useUnicode"), databaseBundle.getString("useUnicode.value"));
        connectionProperties.put(databaseBundle.getString("useSSL"), databaseBundle.getString("useSSL.value"));
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            for (int i = 0; i < POOL_SIZE; i++) {
                connectionQueue.put(makeConnection(connectionProperties));
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR,"InterruptedException was occurred during connection creation");
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during connection creation");
        }
    }

    /**
     * <p>Static method for getting the single instance of current class</p>
     * @return      the instance of ConnectionPool class
     */

    public static ConnectionPool getInstance() {
        if(!connectionPoolCreated.get()) {
            lock.lock();
            try {
                if (connectionPool == null) {
                    connectionPool = new ConnectionPool();
                    connectionPoolCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return connectionPool;
    }

    /**
     * <p>Method for obtaining a connection from pool.</p>
     * @return      the connection to the database from pool
     * @see ProxyConnection
     */

    public ProxyConnection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "InterruptedException was occurred during getting the connection");
        }
        return proxyConnection;
    }

    /**
     * <p>Method that returns the ProxyConnection to the connection pool.</p>
     * @param proxyConnection the connection that returns to the pool
     * @see ProxyConnection
     */

    public void releaseConnection(ProxyConnection proxyConnection) {
        try {
            connectionQueue.put(proxyConnection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "InterruptedException was occurred during releasing the connection");
        }
    }

    /**
     * <p>Method that makes connection to the database.</p>
     * @param connectionProperties contains properties for connecting to the database
     * @return                     the connection that was created in the method
     */

    private ProxyConnection makeConnection(Properties connectionProperties) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseBundle.getString("url"), connectionProperties);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQLException was occurred during making the connection");
        }
        return new ProxyConnection(connection);
    }

    /**
     * <p>A method that closes all connections in the pool and deregisters
     * the database connection driver</p>
     */

    public void closePool(){
        for(int i = 0; i < POOL_SIZE; i++){
            try {
                connectionQueue.take().closeConnection();
                DriverManager.deregisterDriver(new com.mysql.jdbc.Driver());
            } catch (SQLTechnicalException e) {
                LOGGER.log(Level.ERROR, "SQLTechnicalException was occurred during closing the pool");
            } catch (SQLException e){
                LOGGER.log(Level.ERROR, "SQLException was occurred during deregister the driver");
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "InterruptedException was occurred during closing the pool");
            }
        }
    }

    /**
     * <p>Method for blocking clone operation of the connection pool</p>
     * @return                            clone of the object if it is not null
     * @throws CloneNotSupportedException when the ConnectionPool object is not null
     */

    @Override
    public Object clone() throws CloneNotSupportedException {
        if(connectionPool != null){
            throw new CloneNotSupportedException();
        }
        return super.clone();
    }

}

package com.epam.zhuckovich.connection;

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

    private static ConnectionPool connectionPool;
    private static final int POOL_SIZE = 15;
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
                e.printStackTrace();
        } catch (SQLException e) {
                e.printStackTrace();
        }
        System.out.println("total size:" + connectionQueue.size());
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
            System.out.println("size before getting connection" + connectionQueue.size());
            proxyConnection = connectionQueue.take();
            System.out.println("size after getting connection" + connectionQueue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            System.out.println("size before releasing connection" + connectionQueue.size());
            connectionQueue.put(proxyConnection);
            System.out.println("size after releasing connection" + connectionQueue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Method that makes connection to the database.</p>
     * @param connectionProperties contains properties for connecting to the database
     * @retur                      the connection that was created in the method
     */

    private ProxyConnection makeConnection(Properties connectionProperties) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseBundle.getString("url"), connectionProperties);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ProxyConnection proxyConnection = new ProxyConnection(connection);
        return proxyConnection;
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
                System.out.println("close connection");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        if(connectionPool!=null){
            throw new CloneNotSupportedException();
        }
        return super.clone();
    }

}

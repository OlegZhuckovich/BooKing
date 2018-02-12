package com.epam.zhuckovich;

import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;
import com.epam.zhuckovich.dao.BookDAO;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static org.testng.Assert.assertNotNull;

public class ConnectionTest {

    private BookDAO bookDAO;
    private ProxyConnection connection;


    @BeforeClass
    public void setUp(){
        bookDAO = BookDAO.getInstance();
        connection = ConnectionPool.getInstance().getConnection();
    }

    @Test
    public void connectionNotNullTest(){
        assertNotNull(connection);
    }

    @Test
    public void daoNotNullTest(){
        assertNotNull(bookDAO);
    }

    @AfterClass
    public void closeConnection(){
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.getInstance().closePool();
    }

}

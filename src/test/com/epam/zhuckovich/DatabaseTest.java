package com.epam.zhuckovich;

import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.service.UserService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class DatabaseTest {

    private ProxyConnection connection;

    @BeforeClass
    public void init(){
        connection = ConnectionPool.getInstance().getConnection();
    }

    @Test
    public void databaseTest(){
        UserService userService = new UserService();
        User user = userService.findMemberById(1);
        assertNotNull(user);
    }

    @AfterClass
    public void reset(){
        ConnectionPool.getInstance().releaseConnection(connection);
        ConnectionPool.getInstance().closePool();
    }
}

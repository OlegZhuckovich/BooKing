package com.epam.zhuckovich;

import com.epam.zhuckovich.manager.PageManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ResourceTest {

    private String expectedJspPath;
    private String keyValue;

    @BeforeClass
    public void init(){
        expectedJspPath = "/jsp/login.jsp";
        keyValue = "loginPage";
    }

    @Test
    public void connectToResource(){
        String realJspPath = PageManager.getPage(keyValue);
        assertEquals(expectedJspPath,realJspPath);
    }

    @AfterClass
    public void reset(){

    }
}

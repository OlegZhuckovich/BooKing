package com.epam.zhuckovich;


import com.epam.zhuckovich.command.CommandFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

public class CommandFactoryTest {

    private static final String COMMAND_VALUE = "undefined_command";
    HttpServletRequest request;

    @BeforeClass
    public void init(){

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void commandTest(){
        CommandFactory.defineCommand(COMMAND_VALUE,request);
    }

    @AfterClass
    public void reset(){}
}

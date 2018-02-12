package com.epam.zhuckovich;


import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.UserType;
import com.epam.zhuckovich.service.UserService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;

import static org.testng.Assert.assertEquals;

public class ValidationTest {

    private static final String USER_TEST_NAME = "Oleg123";
    private static final String USER_TEST_SURNAME = "Zhuckovich456";
    private static final String USER_TEST_EMAIL = "email.com";
    private static final String USER_TEST_PASSWORD = "123";
    private static final String USER_TEST_PHOTO = "userPhoto";

    private User user;

    @BeforeClass
    public void init(){
        user = User.newBuilder()
                .setName(USER_TEST_NAME)
                .setSurname(USER_TEST_SURNAME)
                .setEmail(USER_TEST_EMAIL)
                .setPassword(USER_TEST_PASSWORD)
                .setPhoto(new ByteArrayInputStream( USER_TEST_PHOTO.getBytes() ))
                .setUserType(UserType.MEMBER)
                .build();
    }

    @Test
    public void validationTest(){
        UserService userService = new UserService();
        int expected = 0;
        int actual = userService.registerUser(user.getName(),user.getSurname(), user.getEmail(), user.getPassword(), user.getPhoto(), user.getUserType());
        assertEquals(expected,actual);
    }

    @AfterClass
    public void reset(){}
}

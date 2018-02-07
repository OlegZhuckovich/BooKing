package com.epam.zhuckovich.service;

import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.UserType;
import com.epam.zhuckovich.dao.UserDAO;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String ENCRYPTION_MECHANISM = "MD5";

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = UserDAO.getInstance();
    }

    public User checkUser(String email, String password){
        String finalPassword = encription(password);
        List<User> userList = userDAO.executeQuery(statement -> userDAO.findMemberByEmail(statement,email, finalPassword),UserDAO.getFindUserQuery());
        return userList.isEmpty() ? User.newBuilder().build() : userList.get(0);
    }

    public boolean insertUser(String name, String surname, String email, String password){
        String finalPassword = encription(password);
        User newUser = User.newBuilder()
                           .setName(name)
                           .setSurname(surname)
                           .setEmail(email)
                           .setPassword(finalPassword)
                           .setUserType(UserType.MEMBER)
                           .build();
        List<String> emailUsers = userDAO.findAll();
        for(String currentEmail:emailUsers){
            if(currentEmail.equals(email)){
                return false;
            }
        }
        return userDAO.executeUpdate(UserDAO.getAddUserQuery(), newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getPassword(), newUser.getUserType().toString()) != 0;
    }


    public boolean orderBook(){
        return false;
    }


    public List<User> findAllRemovableUsers(UserType userType){
        List<User> userList = new ArrayList<>();
        switch (userType) {
            case MEMBER:
                userList = userDAO.executeQuery(statement -> userDAO.findAllRemovableUsers(statement), UserDAO.getFindAllRemovableMembers());break;
            case LIBRARIAN:
                userList = userDAO.executeQuery(statement -> userDAO.findAllRemovableUsers(statement), UserDAO.getFindAllRemovableLibrarians());break;
        }
        return userList;
    }


    public boolean deleteUser(String userID){
        Integer numberUserID = Integer.parseInt(userID);
        return userDAO.executeUpdate(UserDAO.getDeleteUserQuery(), numberUserID) != 0;
    }

    public int deleteAccount(Integer userID, UserType userType){
        int operationSuccess = 0;
        switch(userType){
            case MEMBER:
                operationSuccess = userDAO.executeUpdate(UserDAO.getDeleteMemberAccountQuery(),userID);break;
            case LIBRARIAN:
                operationSuccess = userDAO.executeUpdate(UserDAO.getDeleteLibrarianAccountQuery(),userID);break;
        }
        return operationSuccess;
    }

    public boolean editAccount(InputStream photo, int userID){
        System.out.println("jglgksdgl");
        userDAO.updateAvatar(photo,userID);
        return true;
    }

    String encription(String realPassword){
        StringBuilder passwordStringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_MECHANISM);
            messageDigest.update(realPassword.getBytes());
            byte passwordByteData[] = messageDigest.digest();
            for (byte aPasswordByteData : passwordByteData) {
                passwordStringBuilder.append(Integer.toString((aPasswordByteData & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Digest(in hex format):: " + passwordStringBuilder.toString());
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return passwordStringBuilder.toString();
    }

    //other methods
}

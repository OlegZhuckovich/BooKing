package com.epam.zhuckovich.service;

import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.UserType;
import com.epam.zhuckovich.dao.UserDAO;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private static final String ENCRYPTION_MECHANISM = "MD5";
    private static final String NAME_SURNAME_REGEX = "[A-ZА-Я][a-zа-я]+-?[A-ZА-Я]?[a-zа-я]+?";
    private static final String EMAIL_REGEX = "[\\w\\.]{2,40}@[a-z]{2,10}\\.[a-z]{2,10}";
    private static final String PASSWORD_REGEX = "[\\w]{5,40}";


    private UserDAO userDAO;

    public UserService() {
        this.userDAO = UserDAO.getInstance();
    }

    public User checkUser(String email, String password){
        String finalPassword = encription(password);
        List<User> userList = userDAO.executeQuery(statement -> userDAO.findMemberByEmail(statement,email, finalPassword),UserDAO.getFindUserQuery());
        return userList.isEmpty() ? User.newBuilder().build() : userList.get(0);
    }

    public int registerUser(String name, String surname, String email, String password, InputStream photo){

        int operationResult = 0;

        if(name==null || surname==null || email==null || password==null || photo==null){
            return operationResult;
        }
        boolean nameMatch, surnameMatch, emailMatch, passwordMatch;
        Pattern nameSurnameRegex = Pattern.compile(NAME_SURNAME_REGEX);
        Pattern emailRegex = Pattern.compile(EMAIL_REGEX);
        Pattern passwordRegex = Pattern.compile(PASSWORD_REGEX);

        Matcher nameMatcher = nameSurnameRegex.matcher(name);
        Matcher surnameMatcher = nameSurnameRegex.matcher(surname);
        Matcher emailMatcher = emailRegex.matcher(email);
        Matcher passwordMatcher = passwordRegex.matcher(password);


        nameMatch = nameMatcher.matches();
        surnameMatch = surnameMatcher.matches();
        emailMatch = emailMatcher.matches();
        passwordMatch = passwordMatcher.matches();

        if(nameMatch && surnameMatch && emailMatch && passwordMatch){
            String finalPassword = encription(password);
            User newUser = User.newBuilder()
                    .setName(name)
                    .setSurname(surname)
                    .setEmail(email)
                    .setPassword(finalPassword)
                    .setPhoto(photo)
                    .setUserType(UserType.MEMBER)
                    .build();
            operationResult = userDAO.executeUpdate(UserDAO.getAddUserQuery(), newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getPassword(), newUser.getUserType().toString(), newUser.getPhoto());
        }
        return operationResult;
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

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
    private static final String CITY_STREET_REGEX = "[A-ZА-Я][a-zа-я]{2,40}";
    private static final String HOUSE_REGEX = "[\\d]{1,4}";
    private static final String TELEPHONE_REGEX = "[\\d]{7,12}";
    private Pattern nameSurnameRegex;
    private Pattern emailRegex;
    private Pattern passwordRegex;
    private Pattern cityStreetRegex;
    private Pattern houseRegex;
    private Pattern telephoneRegex;

    private UserDAO userDAO;

    public UserService() {
        nameSurnameRegex = Pattern.compile(NAME_SURNAME_REGEX);
        emailRegex = Pattern.compile(EMAIL_REGEX);
        passwordRegex = Pattern.compile(PASSWORD_REGEX);
        cityStreetRegex = Pattern.compile(CITY_STREET_REGEX);
        houseRegex = Pattern.compile(HOUSE_REGEX);
        telephoneRegex = Pattern.compile(TELEPHONE_REGEX);
        this.userDAO = UserDAO.getInstance();
    }

    public User checkUser(String email, String password){
        String finalPassword = encription(password);
        List<User> userList = userDAO.executeQuery(statement -> userDAO.findUser(statement,email, finalPassword),UserDAO.getFindUserQuery());
        return userList.isEmpty() ? User.newBuilder().build() : userList.get(0);
    }

    public User findMemberById(int userID){
        List<User> userList = userDAO.executeQuery(statement -> userDAO.findUser(statement,userID),UserDAO.getFindUserByIdQuery());
        return userList.isEmpty() ? User.newBuilder().build() : userList.get(0);
    }


    public int registerUser(String name, String surname, String email, String password, InputStream photo, UserType registrationType){
        int operationResult = 0;
        if(name==null || surname==null || email==null || password==null || photo==null){
            return operationResult;
        }
        boolean nameMatch, surnameMatch, emailMatch, passwordMatch;
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
            User.Builder newUserBuilder= User.newBuilder()
                    .setName(name)
                    .setSurname(surname)
                    .setEmail(email)
                    .setPassword(finalPassword)
                    .setPhoto(photo);
            if(registrationType == UserType.MEMBER){
                newUserBuilder.setUserType(UserType.MEMBER);
            } else {
                newUserBuilder.setUserType(UserType.LIBRARIAN);
            }
            User newUser = newUserBuilder.build();
            operationResult = userDAO.executeUpdate(UserDAO.getAddUserQuery(), newUser.getName(), newUser.getSurname(), newUser.getEmail(), newUser.getPassword(), newUser.getUserType().toString(), newUser.getPhoto());
        }
        return operationResult;
    }

    public int editAccountMainInformation(User editableUser){
        int operationResult = 0;
        boolean nameMatch, surnameMatch;
        Matcher nameMatcher = nameSurnameRegex.matcher(editableUser.getName());
        Matcher surnameMatcher = nameSurnameRegex.matcher(editableUser.getSurname());
        nameMatch = nameMatcher.matches();
        surnameMatch = surnameMatcher.matches();
        if(nameMatch && surnameMatch){
            if(userDAO.executeUpdate(UserDAO.getEditMainInformation(),editableUser.getName(),editableUser.getSurname(), editableUser.getId()) != 0){
                operationResult++;
            }
        }
        if(editableUser.getPassword() != null){
            if(userDAO.executeUpdate(UserDAO.getEditPassword(),encription(editableUser.getPassword()),editableUser.getId()) != 0){
                operationResult++;
            }
        }
        return operationResult;
    }

    public int addNewAddressToUser(int addressID,int userID){
        return userDAO.executeUpdate(UserDAO.getAddNewAddressToUserQuery(),addressID,userID);
    }

    public int updateAddress(User user, String city, String street, String house, String telephone, int updateType){
        boolean cityMatch, streetMatch, houseMatch, telephoneMatch;
        Matcher cityMatcher = cityStreetRegex.matcher(city);
        Matcher streetMatcher = cityStreetRegex.matcher(street);
        Matcher houseMatcher = houseRegex.matcher(house);
        Matcher telephoneMatcher = telephoneRegex.matcher(telephone);
        cityMatch = cityMatcher.matches();
        streetMatch = streetMatcher.matches();
        houseMatch = houseMatcher.matches();
        telephoneMatch = telephoneMatcher.matches();
        if(cityMatch && streetMatch && houseMatch && telephoneMatch){
            if(updateType == 0){
                return userDAO.addNewAddress(city, street,Integer.parseInt(house), Integer.parseInt(telephone));
            } else {
                return userDAO.executeUpdate(UserDAO.getUpdateAddressQuery(),city, street,Integer.parseInt(house), Integer.parseInt(telephone), user.getAddress().getId());
            }
        } else {
            return 0;
        }
    }

    public int updateAvatar(InputStream photo, int userID){
        return userDAO.updateAvatar(photo,userID);
    }


    public int deleteUserAddress(int addressID){
        return userDAO.executeUpdate(UserDAO.getDeleteAddressQuery(),addressID);
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


    String encription(String realPassword){
        StringBuilder passwordStringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_MECHANISM);
            messageDigest.update(realPassword.getBytes());
            byte passwordByteData[] = messageDigest.digest();
            for (byte aPasswordByteData : passwordByteData) {
                passwordStringBuilder.append(Integer.toString((aPasswordByteData & 0xff) + 0x100, 16).substring(1));
            }
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return passwordStringBuilder.toString();
    }

}

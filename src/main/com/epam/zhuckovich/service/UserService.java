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

/**
 * <p>A class that checks the validity of information received from methods
 * of the UserCommand class and accesses the dao layer.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         User
 * @since       1.0
 */

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

    /**
     *  Class constructor
     */

    public UserService() {
        nameSurnameRegex = Pattern.compile(NAME_SURNAME_REGEX);
        emailRegex = Pattern.compile(EMAIL_REGEX);
        passwordRegex = Pattern.compile(PASSWORD_REGEX);
        cityStreetRegex = Pattern.compile(CITY_STREET_REGEX);
        houseRegex = Pattern.compile(HOUSE_REGEX);
        telephoneRegex = Pattern.compile(TELEPHONE_REGEX);
        this.userDAO = UserDAO.getInstance();
    }

    /**
     * <p>Turns to the dao layer to search for a user in the database by email and password</p>
     * @param email    email received from the client
     * @param password password received from the client
     * @return         user if it was found
     */

    public User findUserByEmailPassword(String email, String password){
        String finalPassword = encryption(password);
        List<User> userList = userDAO.executeQuery(statement -> userDAO.findUser(statement,email, finalPassword),UserDAO.getFindUserQuery());
        return userList.isEmpty() ? User.newBuilder().build() : userList.get(0);
    }

    /**
     * <p>Turns to the dao layer to search for a user in the database by id</p>
     * @param userID userID parameter
     * @return       user if it was found
     */

    public User findMemberById(int userID){
        List<User> userList = userDAO.executeQuery(statement -> userDAO.findUser(statement,userID),UserDAO.getFindUserByIdQuery());
        return userList.isEmpty() ? User.newBuilder().build() : userList.get(0);
    }

    /**
     * <p>The method for registering a new reader or librarian in the application</p>
     * @param name             user name
     * @param surname          user surname
     * @param email            user email
     * @param password         user password
     * @param photo            user photo
     * @param registrationType can be MEMBER or LIBRARIAN
     * @return                 returns a number other than 0 if the user was successfully registered
     */

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
            String finalPassword = encryption(password);
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

    /**
     * <p>The method for editing basic account information (name, surname and password)</p>
     * @param editableUser user whose data is being edited
     * @return             returns a number other than 0 if the account was successfully edited
     */

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
            if(userDAO.executeUpdate(UserDAO.getEditPassword(),encryption(editableUser.getPassword()),editableUser.getId()) != 0){
                operationResult++;
            }
        }
        return operationResult;
    }

    /**
     * <p>Adds a new address to an existing user</p>
     * @param addressID addressID parameter
     * @param userID    userID parameter
     * @return          returns a number other than 0 if the address was successfully added to user
     */

    public int addNewAddressToUser(int addressID,int userID){
        return userDAO.executeUpdate(UserDAO.getAddNewAddressToUserQuery(),addressID,userID);
    }

    /**
     * <p>Updates information about user address</p>
     * @param user       user parameter
     * @param city       city parameter
     * @param street     street parameter
     * @param house      house parameter
     * @param telephone  telephone parameter
     * @param updateType type of update
     * @return           returns a number other than 0 if the address was successfully updated
     */

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

    /**
     * <p>Updates user avatar</p>
     * @param photo  new user photo
     * @param userID userID parameter
     * @return       returns a number other than 0 if the photo was successfully updated
     */

    public int updateAvatar(InputStream photo, int userID){
        return userDAO.updateAvatar(photo,userID);
    }

    /**
     * <p>Removes the current user address</p>
     * @param addressID addressID parameter
     * @return          returns a number other than 0 if the user address was successfully deleted
     */

    public int deleteUserAddress(int addressID){
        return userDAO.executeUpdate(UserDAO.getDeleteAddressQuery(),addressID);
    }


    /**
     * <p>Returns the list of all removable users</p>
     * @param userType type of user
     * @return         the list of all removable users
     */

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

    /**
     * <p>Delete user from application with userID parameter</p>
     * @param userID userID parameter
     * @return       true if user was successfully deleted
     */

    public boolean deleteUser(String userID){
        Integer numberUserID = Integer.parseInt(userID);
        return userDAO.executeUpdate(UserDAO.getDeleteUserQuery(), numberUserID) != 0;
    }

    /**
     * <p>The method for filing a request for the removal of
     * an account by the reader or librarian</p>
     * @param userID   userID parameter
     * @param userType type of user
     * @return         returns a number other than 0 if the application for account deletion was successfully sent
     */

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

    /**
     * <p>The method for encryption of the user's password</p>
     * @param realPassword user-entered password
     * @return             user-entered password after encryption
     */

    String encryption(String realPassword){
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
        System.out.println(passwordStringBuilder.toString());
        return passwordStringBuilder.toString();
    }

}

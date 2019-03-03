package com.epam.zhuckovich.service;

import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.dao.UserDAO;
import com.epam.zhuckovich.entity.User.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
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

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private static final String ENCRYPTION_MECHANISM = "MD5";
    private static final String NAME_SURNAME_REGEX = "[A-ZА-Я][a-zа-яё]+-?[A-ZА-Я]?[a-zа-яё]+?";
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
        var finalPassword = encryption(password);
        var users = userDAO.executeQuery(statement -> userDAO.findUser(statement,email, finalPassword),UserDAO.getFindUserQuery());
        return users.isEmpty() ? User.newBuilder().build() : users.get(0);
    }

    /**
     * <p>Turns to the dao layer to search for a user in the database by id</p>
     * @param userID userID parameter
     * @return       user if it was found
     */

    public User findMemberById(int userID){
        var users = userDAO.executeQuery(statement -> userDAO.findUser(statement,userID),UserDAO.getFindUserByIdQuery());
        return users.isEmpty() ? User.newBuilder().build() : users.get(0);
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

    public int registerUser(String name, String surname, String email, String password, InputStream photo, User.Role registrationType){
        int operationResult = 0;
        if(name==null || surname==null || email==null || password==null || photo==null){
            return operationResult;
        }
        if(nameSurnameRegex.matcher(name).matches() &&
                nameSurnameRegex.matcher(surname).matches() &&
                emailRegex.matcher(email).matches() &&
                passwordRegex.matcher(password).matches()){
            var finalPassword = encryption(password);
            var userBuilder = User.newBuilder()
                    .setName(name)
                    .setSurname(surname)
                    .setEmail(email)
                    .setPassword(finalPassword)
                    .setPhoto(photo);
            if(registrationType == Role.MEMBER){
                userBuilder .setUserType(Role.MEMBER);
            } else {
                userBuilder .setUserType(Role.LIBRARIAN);
            }
            var user = userBuilder .build();
            operationResult = userDAO.executeUpdate(UserDAO.getAddUserQuery(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getRole().toString(), user.getPhoto());
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
        if(nameSurnameRegex.matcher(editableUser.getName()).matches() &&
                nameSurnameRegex.matcher(editableUser.getSurname()).matches()){
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
        if(cityStreetRegex.matcher(city).matches() &&
                cityStreetRegex.matcher(street).matches() &&
                houseRegex.matcher(house).matches() &&
                telephoneRegex.matcher(telephone).matches()){
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
     * @param role type of user
     * @return         the list of all removable users
     */

    public List<User> findAllRemovableUsers(User.Role role){
        List<User> userList = new ArrayList<>();
        switch (role) {
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
        return userDAO.executeUpdate(UserDAO.getDeleteUserQuery(), Integer.parseInt(userID)) != 0;
    }

    /**
     * <p>The method for filing a request for the removal of
     * an account by the reader or librarian</p>
     * @param userID   userID parameter
     * @param role type of user
     * @return         returns a number other than 0 if the application for account deletion was successfully sent
     */

    public int deleteAccount(Integer userID, User.Role role){
        int operationSuccess = 0;
        switch(role){
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
            var messageDigest = MessageDigest.getInstance(ENCRYPTION_MECHANISM);
            messageDigest.update(realPassword.getBytes());
            byte passwordByteData[] = messageDigest.digest();
            for (byte aPasswordByteData : passwordByteData) {
                passwordStringBuilder.append(Integer.toString((aPasswordByteData & 0xff) + 0x100, 16).substring(1));
            }
        } catch(NoSuchAlgorithmException e){
           LOGGER.log(Level.ERROR,"NoSuchAlgorithmException occurred during encryption operation");
        }
        return passwordStringBuilder.toString();
    }

}

package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Address;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.User.Role;
import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;
import com.epam.zhuckovich.exception.SQLTechnicalException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The class contains methods for adding extraction and processing
 * of data about users from the database</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public final class UserDAO extends AbstractDAO<User> {

    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);

    private static UserDAO userDAO;

    private static final String FIND_USER_QUERY = "SELECT member.memberID, member.name, member.surname, member.email, member.role, member.registration_date, adress.adressID, adress.city, adress.street, adress.house, adress.telephone_number " +
                                                        "FROM member " +
                                                        "LEFT JOIN adress ON adress.adressID=member.adressID " +
                                                  "WHERE email = ? AND password = ?";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT member.memberID, member.name, member.surname, member.email, member.role, member.registration_date, adress.adressID, adress.city, adress.street, adress.house, adress.telephone_number " +
                                                            "FROM member " +
                                                            "LEFT JOIN adress ON adress.adressID=member.adressID " +
                                                        "WHERE memberID = ?";
    private static final String ADD_USER_QUERY = "INSERT INTO LibraryDatabase.member (name, surname, email, password, role, registration_date, avatar) VALUES (?,?,?,?,?, CURDATE(),?)";
    private static final String FIND_ALL_REMOVABLE_LIBRARIANS = "SELECT member.memberID, member.name, member.surname, member.email, member.registration_date FROM member WHERE role='librarian' AND delete_status = '1'";
    private static final String FIND_ALL_REMOVABLE_MEMBERS = "SELECT member.memberID, member.name, member.surname, member.email, member.registration_date FROM member WHERE role='member' AND delete_status = '1'";
    private static final String DELETE_USER_QUERY = "DELETE FROM member WHERE memberID = ?";
    private static final String DELETE_LIBRARIAN_ACCOUNT_QUERY = "UPDATE member SET delete_status = 1 WHERE memberID = ?";
    private static final String DELETE_MEMBER_ACCOUNT_QUERY = "UPDATE member SET delete_status = 1 WHERE memberID = ?";
    private static final String EDIT_ACCOUNT_QUERY = "UPDATE member SET avatar = ? WHERE memberID = ? ";
    private static final String EDIT_MAIN_INFORMATION = "UPDATE member SET name = ?, surname = ? WHERE memberID = ?";
    private static final String EDIT_PASSWORD = "UPDATE member SET password = ? WHERE memberID = ?";
    private static final String DELETE_ADDRESS_QUERY = "DELETE FROM adress WHERE adressID = ?";
    private static final String ADD_NEW_ADDRESS_QUERY = "INSERT INTO adress (city, street, house, telephone_number) VALUES (?,?,?,?)";
    private static final String ADD_NEW_ADDRESS_TO_USER_QUERY = "UPDATE member SET adressID = ? WHERE memberID = ?";
    private static final String UPDATE_ADDRESS_QUERY = "UPDATE adress SET city = ?, street = ?, house = ?, telephone_number=? WHERE adressID = ?";
    private static final String FIND_USER_AVATAR = "SELECT avatar FROM member WHERE memberID = ?";
    private static final String FIND_AUTHOR_PHOTO_QUERY = "SELECT photo FROM author WHERE authorID = ?";

    /**
     * Private constructor
     */

    private UserDAO(){}

    /**
     * <p>Method that returns the new UserDAO object if it is not
     * created in other case returns the UserDAO object</p>
     * @return the instance of UserDAO
     */

    public static UserDAO getInstance() {
        if (userDAO == null) {
                userDAO = new UserDAO();
        }
        return userDAO;
    }


    /**
     * <p>The method finds user in the database by different parameters</p>
     * @param statement  preparedStatement
     * @param parameters parameters for searching
     * @return           list contains user
     */

    public List<User> findUser(PreparedStatement statement, Object... parameters) {
        var userList = new ArrayList<User>();
        try{
            if(parameters.length != 0){
                for(int i=0; i<parameters.length; i++){
                    statement.setObject(i+1,parameters[i]);
                }
            }
            var userResultSet = statement.executeQuery();
            if(!userResultSet.isBeforeFirst()){
                return userList;
            } else {
                userResultSet.next();
                var addressID = userResultSet.getInt(ADRESS_ID);
                Address userAddress = null;
                if (!userResultSet.wasNull()) {
                    userAddress = Address.newBuilder()
                            .setId(addressID)
                            .setCity(userResultSet.getString(CITY))
                            .setStreet(userResultSet.getString(STREET))
                            .setHouse(userResultSet.getInt(HOUSE))
                            .setTelephoneNumber(userResultSet.getInt(TELEPHONE_NUMBER))
                            .build();
                }
                userList.add(User.newBuilder()
                            .setId(userResultSet.getInt(MEMBER_ID))
                            .setName(userResultSet.getString(NAME))
                            .setSurname(userResultSet.getString(SURNAME))
                            .setEmail(userResultSet.getString(EMAIL))
                            .setUserType(Role.valueOf(userResultSet.getString(ROLE).toUpperCase()))
                            .setRegistrationDate(userResultSet.getDate(REGISTRATION_DATE))
                            .setAddress(userAddress)
                            .build());
                if(userList.get(0).getRole() == Role.ADMINISTRATOR || userList.get(0).getRole() == Role.LIBRARIAN){
                    OrderDAO.getInstance().executeUpdate(OrderDAO.getCheckOldOrdersQuery());
                }
            }
        } catch(SQLException e){
            LOGGER.log(Level.ERROR,"SQLException was occurred during findMemberByEmail operation");
        }
        return userList;
    }

    /**
     * <p>Finds in the database all users who requested the removal of the account</p>
     * @param statement preparedStatement
     * @return          list of user who requested the removal of the account
     */

    public List<User> findAllRemovableUsers(PreparedStatement statement){
        var userList = new ArrayList<User>();
        try{
            var userResultSet = statement.executeQuery();
            if(userResultSet != null) {
                if (!userResultSet.isBeforeFirst()) {
                    return userList;
                } else {
                    while (userResultSet.next()) {
                        var currentLibrarian = User.newBuilder()
                                .setId(userResultSet.getInt(MEMBER_ID))
                                .setName(userResultSet.getString(NAME))
                                .setSurname(userResultSet.getString(SURNAME))
                                .setEmail(userResultSet.getString(EMAIL))
                                .setRegistrationDate(userResultSet.getDate(REGISTRATION_DATE))
                                .build();
                        userList.add(currentLibrarian);
                    }
                }
            }
        } catch(SQLException e){
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAllRemovableUsers operation");
        }
        return userList;
    }

    /**
     * <p>Add new address to the database</p>
     * @param parameters contains such address parameters as city, street, house and telephone number
     * @return           a number other than 0 if the addition was successful
     */

    public int addNewAddress(Object...parameters){
        ProxyConnection connection = null;
        PreparedStatement preparedStatement = null;
        var addressID = 0;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(ADD_NEW_ADDRESS_QUERY,Statement.RETURN_GENERATED_KEYS);
            if(parameters.length != 0) {
                for (int i = 0; i < parameters.length; i++) {
                    preparedStatement.setObject(i + 1, parameters[i]);
                }
            }
            preparedStatement.executeUpdate();
            connection.commit();
            var bookIdResultSet = preparedStatement.getGeneratedKeys();
            if(bookIdResultSet.next()) {
                addressID = bookIdResultSet.getInt(1);
            }
        } catch(SQLTechnicalException e) {
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during addNewAddress operation");
            connection.rollback();
        } catch(SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during addNewAddress operation");
            connection.rollback();
        } finally {
            close(preparedStatement,connection);
        }
        return addressID;
    }

    /**
     * <p>The method that updates the user avatar</p>
     * @param photo  contains user photo
     * @param userID id of the user
     * @return       a number other than 0 if the updating was successful
     */

    public int updateAvatar(InputStream photo, int userID){
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        var operationResult = 0;
        try{
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(EDIT_ACCOUNT_QUERY);
            statement.setBinaryStream(1,photo);
            statement.setInt(2,userID);
            operationResult = statement.executeUpdate();
            connection.commit();
        } catch (SQLTechnicalException e){
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during updateAvatar operation");
            connection.rollback();
        } catch (SQLException e){
            LOGGER.log(Level.ERROR,"SQLException was occurred during updateAvatar operation");
            connection.rollback();
        } finally {
            close(statement,connection);
        }
        return operationResult;
    }

    /**
     * <p>Loads user avatar from the database</p>
     * @param imageName parameter for image loading
     * @return          the user avatar presented as a byte stream
     */


    public byte[] loadImage(String imageName, int loadType) {
        byte[] content = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            if(loadType == 0){
                statement = connection.prepareStatement(FIND_AUTHOR_PHOTO_QUERY);
            } else {
                statement = connection.prepareStatement(FIND_USER_AVATAR);
            }
            statement.setString(1, imageName);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if(loadType == 0){
                    content = resultSet.getBytes(PHOTO);
                } else {
                    content = resultSet.getBytes(AVATAR);
                }
            }
        } catch (SQLTechnicalException e) {
            LOGGER.log(Level.ERROR,"SQLTechnicalException was occurred during updateAvatar operation");
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during updateAvatar operation");
            connection.rollback();
        } finally {
            close(statement,connection);
        }
        return content;
    }

    /**
     * <p>Returns the FIND_USER_QUERY query</p>
     * @return    the FIND_USER_QUERY query
     */

    public static String getFindUserQuery(){
        return FIND_USER_QUERY;
    }

    /**
     * <p>Returns the FIND_ALL_REMOVABLE_MEMBERS query</p>
     * @return    the FIND_ALL_REMOVABLE_MEMBERS query
     */

    public static String getFindAllRemovableMembers() {
        return FIND_ALL_REMOVABLE_MEMBERS;
    }

    /**
     * <p>Returns the FIND_ALL_REMOVABLE_LIBRARIANS query</p>
     * @return    the FIND_ALL_REMOVABLE_LIBRARIANS query
     */

    public static String getFindAllRemovableLibrarians() {
        return FIND_ALL_REMOVABLE_LIBRARIANS;
    }

    /**
     * <p>Returns the ADD_USER_QUERY query</p>
     * @return    the ADD_USER_QUERY query
     */

    public static String getAddUserQuery(){
        return ADD_USER_QUERY;
    }

    /**
     * <p>Returns the DELETE_USER_QUERY query</p>
     * @return    the DELETE_USER_QUERY query
     */

    public static String getDeleteUserQuery(){
        return DELETE_USER_QUERY;
    }

    /**
     * <p>Returns the DELETE_MEMBER_ACCOUNT_QUERY query</p>
     * @return    the DELETE_MEMBER_ACCOUNT_QUERY query
     */

    public static String getDeleteMemberAccountQuery(){
        return DELETE_MEMBER_ACCOUNT_QUERY;
    }

    /**
     * <p>Returns the DELETE_LIBRARIAN_ACCOUNT_QUERY query</p>
     * @return    the DELETE_LIBRARIAN_ACCOUNT_QUERY query
     */

    public static String getDeleteLibrarianAccountQuery(){
        return DELETE_LIBRARIAN_ACCOUNT_QUERY;
    }

    /**
     * <p>Returns the EDIT_MAIN_INFORMATION query</p>
     * @return    the EDIT_MAIN_INFORMATION query
     */

    public static String getEditMainInformation() {
        return EDIT_MAIN_INFORMATION;
    }

    /**
     * <p>Returns the EDIT_PASSWORD query</p>
     * @return    the EDIT_PASSWORD query
     */

    public static String getEditPassword(){
        return EDIT_PASSWORD;
    }

    /**
     * <p>Returns the DELETE_ADDRESS_QUERY query</p>
     * @return    the DELETE_ADDRESS_QUERY query
     */

    public static String getDeleteAddressQuery(){
        return DELETE_ADDRESS_QUERY;
    }

    /**
     * <p>Returns the ADD_NEW_ADDRESS_TO_USER_QUERY query</p>
     * @return    the ADD_NEW_ADDRESS_TO_USER_QUERY query
     */

    public static String getAddNewAddressToUserQuery(){
        return ADD_NEW_ADDRESS_TO_USER_QUERY;
    }

    /**
     * <p>Returns the UPDATE_ADDRESS_QUERY query</p>
     * @return    the UPDATE_ADDRESS_QUERY query
     */

    public static String getUpdateAddressQuery(){
        return UPDATE_ADDRESS_QUERY;
    }

    /**
     * <p>Returns the FIND_USER_BY_ID_QUERY query</p>
     * @return    the FIND_USER_BY_ID_QUERY query
     */

    public static String getFindUserByIdQuery(){
        return FIND_USER_BY_ID_QUERY;
    }
}

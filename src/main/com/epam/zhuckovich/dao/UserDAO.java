package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Address;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.UserType;
import com.epam.zhuckovich.connection.ConnectionPool;
import com.epam.zhuckovich.connection.ProxyConnection;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class UserDAO extends AbstractDAO<Integer,User> {

    private static UserDAO userDAO;

    private static final String FIND_ALL_USERS_QUERY = "SELECT name, surname, email, password, role FROM LibraryDatabase.member";
    private static final String FIND_USER_QUERY = "SELECT member.memberID, member.name, member.surname, member.role, member.registration_date, adress.adressID, adress.city, adress.street, adress.house, adress.telephone_number " +
                                                        "FROM member " +
                                                        "LEFT JOIN adress ON adress.adressID=member.adressID " +
                                                  "WHERE email = ? AND password = ?";
    private static final String ADD_USER_QUERY = "INSERT INTO LibraryDatabase.member (name, surname, email, password, role, registration_date) VALUES (?,?,?,?,?,CURDATE())";
    private static final String FIND_ALL_MEMBERS = "SELECT member.memberID, member.name, member.email, member.registration_date, member.books_ordered, adress.city, adress.street, adress.house, adress.telephone_number " +
                                                        "FROM member " +
                                                        "LEFT JOIN adress ON adress.adressID = member.adressID";
    private static final String FIND_ALL_REMOVABLE_LIBRARIANS = "SELECT member.memberID, member.name, member.surname, member.email, member.registration_date FROM member WHERE role='librarian' AND delete_status = '1'";
    private static final String FIND_ALL_REMOVABLE_MEMBERS = "SELECT member.memberID, member.name, member.surname, member.email, member.registration_date FROM member WHERE role='member' AND delete_status = '1'";
    private static final String DELETE_USER_QUERY = "DELETE FROM member WHERE memberID = ?";
    private static final String DELETE_LIBRARIAN_ACCOUNT_QUERY = "UPDATE member SET delete_status = 1 WHERE memberID = ?";
    private static final String DELETE_MEMBER_ACCOUNT_QUERY = "UPDATE member SET delete_status = 1 WHERE memberID = ? AND books_ordered = 0";
    private static final String EDIT_ACCOUNT_QUERY = "UPDATE member SET avatar = ? WHERE memberID = ? ";

    private static final String ID = "memberID";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String REGISTRATION_DATE = "registration_date";
    private static final String ROLE = "role";

    private static final String ADRESS_ID = "adressID";
    private static final String CITY = "city";
    private static final String STREET = "street";
    private static final String HOUSE = "house";
    private static final String TELEPHONE_NUMBER = "telephone_number";


    private UserDAO(){}

    public static UserDAO getInstance() {
        if (userDAO == null) {
                userDAO = new UserDAO();
        }
        return userDAO;
    }

    public List findAll(){
        ProxyConnection cn = null;
        Statement st = null;
        List<String> emailList = new ArrayList<>();
        try{
            cn = ConnectionPool.getInstance().getConnection();
            cn.setAutoCommit(false);
            st = cn.createStatement();
            ResultSet userResultSet = st.executeQuery(FIND_ALL_USERS_QUERY);
            while(userResultSet.next()){
                emailList.add(userResultSet.getString("email"));
            }
            cn.commit();
        } catch(SQLException e){
            cn.rollback();
        } finally {
            close(st,cn);
        }
        return emailList;
    }

    public List<User> findMemberByEmail(PreparedStatement statement, String email, String password){
        List<User> userList = new ArrayList<>();
        try{
            statement.setString(1,email);
            statement.setString(2,password);
            ResultSet userResultSet = statement.executeQuery();
            if(!userResultSet.isBeforeFirst()){
                return userList;
            } else {
                userResultSet.next();
                int addressID = userResultSet.getInt(ADRESS_ID);
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
                            .setId(userResultSet.getInt(ID))
                            .setName(userResultSet.getString(NAME))
                            .setSurname(userResultSet.getString(SURNAME))
                            .setEmail(email)
                            .setUserType(UserType.valueOf(userResultSet.getString(ROLE).toUpperCase()))
                            .setAddress(userAddress)
                            .build());
                if(userList.get(0).getUserType() == UserType.ADMINISTRATOR){
                    OrderDAO.getInstance().executeUpdate(OrderDAO.getCheckOldOrdersQuery());
                }
            }
        } catch(SQLException e){

        }
        return userList;
    }

    public List<User> findAllRemovableUsers(PreparedStatement statement){
        List<User> userList = new ArrayList<>();
        try{
            ResultSet userResultSet = statement.executeQuery();
            if(userResultSet != null) {
                if (!userResultSet.isBeforeFirst()) {
                    return userList;
                } else {
                    while (userResultSet.next()) {
                        User currentLibrarian = User.newBuilder()
                                .setId(userResultSet.getInt(ID))
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

        }
        return userList;
    }

    public void updateAvatar(InputStream photo, int userID){
        System.out.println("Результат:");
        ProxyConnection cn = null;
        PreparedStatement st = null;
        try{
            cn = ConnectionPool.getInstance().getConnection();
            cn.setAutoCommit(false);
            st = cn.prepareStatement(EDIT_ACCOUNT_QUERY);
            st.setBinaryStream(1,photo);
            st.setInt(2,userID);
            int operation = st.executeUpdate();
            cn.commit();
        } catch(SQLException e){
            cn.rollback();
        } finally {
            close(st,cn);
        }
    }

    //загрузка изображения

    public byte[] loadImage(String imageName) {
        byte[] content = null;
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement("SELECT avatar FROM member WHERE memberID = ?");
            statement.setString(1, imageName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                content = resultSet.getBytes("avatar");
            }
        } catch(SQLException e){
            connection.rollback();
        } finally {
            close(statement,connection);
        }
        return content;
    }


    public static String getFindAllUsersQuery(){
        return FIND_ALL_USERS_QUERY;
    }

    public static String getFindUserQuery(){
        return FIND_USER_QUERY;
    }

    public static String getFindAllRemovableMembers() {
        return FIND_ALL_REMOVABLE_MEMBERS;
    }

    public static String getFindAllRemovableLibrarians() {
        return FIND_ALL_REMOVABLE_LIBRARIANS;
    }

    public static String getAddUserQuery(){
        return ADD_USER_QUERY;
    }

    public static String getDeleteUserQuery(){
        return DELETE_USER_QUERY;
    }

    public static String getDeleteMemberAccountQuery(){
        return DELETE_MEMBER_ACCOUNT_QUERY;
    }

    public static String getDeleteLibrarianAccountQuery(){
        return DELETE_LIBRARIAN_ACCOUNT_QUERY;
    }

    public static String getEditAccountQuery(){
        return EDIT_ACCOUNT_QUERY;
    }

}

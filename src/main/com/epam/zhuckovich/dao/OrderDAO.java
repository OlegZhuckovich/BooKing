package com.epam.zhuckovich.dao;

import com.epam.zhuckovich.entity.Address;
import com.epam.zhuckovich.entity.Book;
import com.epam.zhuckovich.entity.Order;
import com.epam.zhuckovich.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>The class contains methods for adding extraction and processing
 * of data about orders from the database</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

public class OrderDAO extends AbstractDAO<Order>{

    private static final Logger LOGGER = LogManager.getLogger(OrderDAO.class);

    private static OrderDAO orderDAO;

    private static final String CHECK_OLD_ORDERS_QUERY = "DELETE FROM ordered_book WHERE order_date < CURDATE() AND return_date IS NULL";
    private static final String DECREMENT_BOOK_QUANTITY_QUERY = "UPDATE book SET quantity = quantity - 1 WHERE bookID = ?";
    private static final String ISSUE_READING_ROOM_QUERY = "UPDATE ordered_book SET return_date = CURDATE() WHERE memberID = ? AND bookID = ?";
    private static final String ISSUE_SUBSCRIPTION_QUERY = "UPDATE ordered_book SET return_date = DATE (DATE_ADD(NOW(), INTERVAL 30 DAY)) WHERE memberID = ? AND bookID = ?";
    private static final String MEMBER_ORDERS_QUERY = "SELECT book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages, ordered_book.order_date, ordered_book.return_date, ordered_book.order_type " +
                                                            "FROM book " +
                                                            "INNER JOIN ordered_book ON ordered_book.bookID = book.bookID " +
                                                      "WHERE memberID = ? AND return_date IS NOT NULL AND (return_date = CURDATE() AND order_type = 'reading_room') OR (return_date > CURDATE() AND order_type = 'subscription')";
    private static final String ORDER_BOOK_QUERY = "INSERT INTO ordered_book (memberID, bookID, order_date, order_type) VALUES (?,?,CURDATE(),?)";
    private static final String READING_ROOM_ORDER_QUERY = "SELECT member.memberID, member.name, member.surname,member.email, book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages " +
                                                                "FROM member " +
                                                                "INNER JOIN ordered_book ON ordered_book.memberID = member.memberID " +
                                                                "INNER JOIN book ON book.bookID = ordered_book.bookID " +
                                                            "WHERE ordered_book.return_date IS NULL AND ordered_book.order_type = 'reading_room'";
    private static final String RETURN_BOOK_QUERY = "UPDATE ordered_book SET return_date = CURDATE() WHERE memberID = ? AND bookID = ? AND return_date > CURDATE()";
    private static final String SUBSCRIPTION_ORDER_QUERY = "SELECT member.memberID, member.name, member.surname,member.email, adress.city, adress.street, adress.house, adress.telephone_number, book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages " +
                                                                "FROM member " +
                                                                "INNER JOIN ordered_book ON ordered_book.memberID = member.memberID " +
                                                                "INNER JOIN adress ON adress.adressID = member.adressID " +
                                                                "INNER JOIN book ON book.bookID = ordered_book.bookID " +
                                                            "WHERE ordered_book.return_date IS NULL AND ordered_book.order_type = 'subscription'";

    private static final String UPDATE_BOOK_QUANTITY = "UPDATE book SET quantity = quantity + 1 WHERE bookID = ?";

    /**
     * Private constructor
     */

    private OrderDAO(){}

    /**
     * <p>Method that returns the new OrderDAO object if it is not
     * created in other case returns the OrderDAO object</p>
     * @return the instance of OrderDAO
     */

    public static OrderDAO getInstance() {
        if (orderDAO == null) {
            orderDAO = new OrderDAO();
        }
        return orderDAO;
    }

    /**
     * <p>Finds all reading room orders in the database</p>
     * @param statement preparedStatement
     * @return          list of the reading room orders
     */

    public List<Order> findAllReadingRoomOrders(PreparedStatement statement){
        var readingRoomOrderList = new ArrayList<Order>();
        try{
            var readingRoomOrderResultSet = statement.executeQuery();
                if (!readingRoomOrderResultSet.isBeforeFirst()) {
                    return readingRoomOrderList;
                } else {
                    while (readingRoomOrderResultSet.next()) {
                        readingRoomOrderList.add(
                                Order.newBuilder()
                                        .setMember(
                                                User.newBuilder()
                                                        .setId(readingRoomOrderResultSet.getInt(MEMBER_ID))
                                                        .setName(readingRoomOrderResultSet.getString(NAME))
                                                        .setSurname(readingRoomOrderResultSet.getString(SURNAME))
                                                        .setEmail(readingRoomOrderResultSet.getString(EMAIL))
                                                        .build())
                                        .setBook(
                                                Book.newBuilder()
                                                        .setId(readingRoomOrderResultSet.getInt(BOOK_ID))
                                                        .setTitle(readingRoomOrderResultSet.getString(TITLE))
                                                        .setGenre(Book.Genre.valueOf(readingRoomOrderResultSet.getString(GENRE)))
                                                        .setPublishingHouse(readingRoomOrderResultSet.getString(PUBLISHING_HOUSE))
                                                        .setNumberInformation(new Book.Metadata(readingRoomOrderResultSet.getInt(YEAR),readingRoomOrderResultSet.getInt(PAGES)))
                                                        .build())
                                        .build()
                        );
                    }
                }
        } catch(SQLException e){
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAllReadingRoomOrders operation");
        }
        return readingRoomOrderList;
    }

    /**
     * <p>Finds all subscription orders in the database</p>
     * @param statement preparedStatement
     * @return          list of the subscription orders
     */

    public List<Order> findAllSubscriptionOrders(PreparedStatement statement){
        var subscriptionOrderList = new ArrayList<Order>();
        try{
            var subscriptionOrderResultSet = statement.executeQuery();
            if (!subscriptionOrderResultSet.isBeforeFirst()) {
                return subscriptionOrderList;
            } else {
                while (subscriptionOrderResultSet.next()) {
                    subscriptionOrderList.add(
                            Order.newBuilder()
                                    .setMember(
                                            User.newBuilder()
                                                    .setId(subscriptionOrderResultSet.getInt(MEMBER_ID))
                                                    .setName(subscriptionOrderResultSet.getString(NAME))
                                                    .setSurname(subscriptionOrderResultSet.getString(SURNAME))
                                                    .setEmail(subscriptionOrderResultSet.getString(EMAIL))
                                                    .setAddress(Address.newBuilder()
                                                                .setCity(subscriptionOrderResultSet.getString(CITY))
                                                                .setStreet(subscriptionOrderResultSet.getString(STREET))
                                                                .setHouse(subscriptionOrderResultSet.getInt(HOUSE))
                                                                .setTelephoneNumber(subscriptionOrderResultSet.getInt(TELEPHONE_NUMBER))
                                                                .build())
                                                    .build())
                                    .setBook(
                                            Book.newBuilder()
                                                    .setId(subscriptionOrderResultSet.getInt(BOOK_ID))
                                                    .setTitle(subscriptionOrderResultSet.getString(TITLE))
                                                    .setGenre(Book.Genre.valueOf(subscriptionOrderResultSet.getString(GENRE)))
                                                    .setPublishingHouse(subscriptionOrderResultSet.getString(PUBLISHING_HOUSE))
                                                    .setNumberInformation(new Book.Metadata(subscriptionOrderResultSet.getInt(YEAR),subscriptionOrderResultSet.getInt(PAGES)))
                                                    .build())
                                    .build()
                    );
                }
            }
        } catch(SQLException e) {
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAllSubscriptionOrders operation");
        }
        return subscriptionOrderList;
    }

    /**
     * <p>Finds in the database all books ordered by the reader with userID</p>
     * @param statement preparedStatement
     * @param userID    userID parameter
     * @return          list of books ordered by the reader with userID parameter
     */

    public List<Order> findAllMemberOrders(PreparedStatement statement, int userID){
        var memberOrderList = new ArrayList<Order>();
        try {
            statement.setInt(1, userID);
            var memberOrderResultSet = statement.executeQuery();
            if (!memberOrderResultSet.isBeforeFirst()) {
                return memberOrderList;
            } else {
                while (memberOrderResultSet.next()) {
                    memberOrderList.add(
                            Order.newBuilder()
                                    .setBook(
                                            Book.newBuilder()
                                                    .setId(memberOrderResultSet.getInt(BOOK_ID))
                                                    .setTitle(memberOrderResultSet.getString(TITLE))
                                                    .setGenre(Book.Genre.valueOf(memberOrderResultSet.getString(GENRE)))
                                                    .setPublishingHouse(memberOrderResultSet.getString(PUBLISHING_HOUSE))
                                                    .setNumberInformation(new Book.Metadata(memberOrderResultSet.getInt(YEAR), memberOrderResultSet.getInt(PAGES)))
                                                    .build())
                                    .setOrderDate(memberOrderResultSet.getDate(ORDER_DATE))
                                    .setReturnDate(memberOrderResultSet.getDate(RETURN_DATE))
                                    .setOrderType(Order.OrderType.valueOf(memberOrderResultSet.getString(ORDER_TYPE).toUpperCase()))
                                    .build()
                    );
                }
            }
        } catch(SQLException e){
            LOGGER.log(Level.ERROR,"SQLException was occurred during findAllMemberOrders operation");
        }
        return memberOrderList;
    }

    /**
     * <p>Returns the MEMBER_ORDERS_QUERY query</p>
     * @return    the MEMBER_ORDERS_QUERY query
     */

    public static String getMemberOrdersQuery() {
        return MEMBER_ORDERS_QUERY;
    }

    /**
     * <p>Returns the READING_ROOM_ORDER_QUERY query</p>
     * @return    the READING_ROOM_ORDER_QUERY query
     */

    public static String getReadingRoomOrderQuery(){
        return READING_ROOM_ORDER_QUERY;
    }

    /**
     * <p>Returns the SUBSCRIPTION_ORDER_QUERY query</p>
     * @return    the SUBSCRIPTION_ORDER_QUERY query
     */

    public static String getSubscriptionOrderQuery(){
        return SUBSCRIPTION_ORDER_QUERY;
    }

    /**
     * <p>Returns the ORDER_BOOK_QUERY query</p>
     * @return    the ORDER_BOOK_QUERY query
     */

    public static String getOrderBookQuery(){
        return ORDER_BOOK_QUERY;
    }

    /**
     * <p>Returns the ISSUE_READING_ROOM_QUERY query</p>
     * @return    the ISSUE_READING_ROOM_QUERY query
     */

    public static String getIssueReadingRoomQuery(){
        return ISSUE_READING_ROOM_QUERY;
    }

    /**
     * <p>Returns the ISSUE_SUBSCRIPTION_QUERY query</p>
     * @return    the ISSUE_SUBSCRIPTION_QUERY query
     */

    public static String getIssueSubscriptionQuery(){
        return ISSUE_SUBSCRIPTION_QUERY;
    }

    /**
     * <p>Returns the DECREMENT_BOOK_QUANTITY_QUERY query</p>
     * @return    the DECREMENT_BOOK_QUANTITY_QUERY query
     */

    public static String getDecrementBookQuantityQuery() {
        return DECREMENT_BOOK_QUANTITY_QUERY;
    }

    /**
     * <p>Returns the CHECK_OLD_ORDERS_QUERY query</p>
     * @return    the CHECK_OLD_ORDERS_QUERY query
     */

    public static String getCheckOldOrdersQuery(){
        return CHECK_OLD_ORDERS_QUERY;
    }

    /**
     * <p>Returns the RETURN_BOOK_QUERY query</p>
     * @return    the RETURN_BOOK_QUERY query
     */

    public static String getReturnBookQuery(){
        return RETURN_BOOK_QUERY;
    }

    /**
     * <p>Returns the UPDATE_BOOK_QUANTITY query</p>
     * @return    the UPDATE_BOOK_QUANTITY query
     */

    public static String getUpdateBookQuantity(){
        return UPDATE_BOOK_QUANTITY;
    }
}

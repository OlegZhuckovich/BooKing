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
 * This is class for user orders
 */

public class OrderDAO extends AbstractDAO<Order>{

    private static final Logger LOGGER = LogManager.getLogger(OrderDAO.class);

    private static OrderDAO orderDAO;
    private static final String READING_ROOM_ORDER_QUERY = "SELECT member.memberID, member.name, member.surname,member.email, book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages " +
                                                                "FROM member " +
                                                                "INNER JOIN ordered_book ON ordered_book.memberID = member.memberID " +
                                                                "INNER JOIN book ON book.bookID = ordered_book.bookID " +
                                                            "WHERE ordered_book.return_date IS NULL AND ordered_book.order_type = 'reading_room'";
    private static final String SUBSCRIPTION_ORDER_QUERY = "SELECT member.memberID, member.name, member.surname,member.email, adress.city, adress.street, adress.house, adress.telephone_number, book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages " +
                                                                "FROM member " +
                                                                "INNER JOIN ordered_book ON ordered_book.memberID = member.memberID " +
                                                                "INNER JOIN adress ON adress.adressID = member.adressID " +
                                                                "INNER JOIN book ON book.bookID = ordered_book.bookID " +
                                                            "WHERE ordered_book.return_date IS NULL AND ordered_book.order_type = 'subscription'";
    private static final String MEMBER_ORDERS_QUERY = "SELECT book.bookID, book.title, book.genre, book.publishing_house, book.year, book.pages, ordered_book.order_date, ordered_book.return_date, ordered_book.order_type " +
                                                           "FROM book " +
                                                           "INNER JOIN ordered_book ON ordered_book.bookID = book.bookID " +
                                                      "WHERE memberID = ? AND return_date IS NOT NULL AND (return_date = CURDATE() AND order_type = 'reading_room') OR (return_date > CURDATE() AND order_type = 'subscription')";

    private static final String ORDER_BOOK_QUERY = "INSERT INTO ordered_book (memberID, bookID, order_date, order_type) VALUES (?,?,CURDATE(),?)";
    private static final String ISSUE_READING_ROOM_QUERY = "UPDATE ordered_book SET return_date = CURDATE() WHERE memberID = ? AND bookID = ?";
    private static final String ISSUE_SUBSCRIPTION_QUERY = "UPDATE ordered_book SET return_date = DATE (DATE_ADD(NOW(), INTERVAL 30 DAY)) WHERE memberID = ? AND bookID = ?";
    private static final String DECREMENT_BOOK_QUANTITY_QUERY = "UPDATE book SET quantity = quantity - 1 WHERE bookID = ?";
    private static final String CHECK_OLD_ORDERS_QUERY = "DELETE FROM ordered_book WHERE order_date < CURDATE() AND return_date IS NULL";

    private static final String RETURN_BOOK_QUERY = "UPDATE ordered_book SET return_date = CURDATE() WHERE memberID = ? AND bookID = ? AND return_date > CURDATE()";
    private static final String UPDATE_BOOK_QUANTITY = "UPDATE book SET quantity = quantity + 1 WHERE bookID = ?";

    private static final String MEMBER_ID = "memberID";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String EMAIL = "email";
    private static final String BOOK_ID = "bookID";
    private static final String TITLE = "title";
    private static final String GENRE = "genre";
    private static final String PUBLISHING_HOUSE = "publishing_house";
    private static final String YEAR = "year";
    private static final String PAGES = "pages";
    private static final String CITY = "city";
    private static final String STREET = "street";
    private static final String HOUSE = "house";
    private static final String TELEPHONE_NUMBER = "telephone_number";
    private static final String ORDER_DATE = "order_date";
    private static final String RETURN_DATE = "return_date";
    private static final String ORDER_TYPE = "order_type";

    private OrderDAO(){}

    public static OrderDAO getInstance() {
        if (orderDAO == null) {
            orderDAO = new OrderDAO();
        }
        return orderDAO;
    }

    public void checkOldReadingRoomOrders(){

    }

    public List<Order> findAllReadingRoomOrders(PreparedStatement statement){
        List<Order> readingRoomOrderList = new ArrayList<>();
        try{
            ResultSet readingRoomOrderResultSet = statement.executeQuery();
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
                                                        .setGenre(Book.BookType.valueOf(readingRoomOrderResultSet.getString(GENRE)))
                                                        .setPublishingHouse(readingRoomOrderResultSet.getString(PUBLISHING_HOUSE))
                                                        .setNumberInformation(new Book.NumberInformation(readingRoomOrderResultSet.getInt(YEAR),readingRoomOrderResultSet.getInt(PAGES)))
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

    public List<Order> findAllSubscriptionOrders(PreparedStatement statement){
        List<Order> subscriptionOrderList = new ArrayList<>();
        try{
            ResultSet subscriptionOrderResultSet = statement.executeQuery();
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
                                                    .setGenre(Book.BookType.valueOf(subscriptionOrderResultSet.getString(GENRE)))
                                                    .setPublishingHouse(subscriptionOrderResultSet.getString(PUBLISHING_HOUSE))
                                                    .setNumberInformation(new Book.NumberInformation(subscriptionOrderResultSet.getInt(YEAR),subscriptionOrderResultSet.getInt(PAGES)))
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


    public List<Order> findAllMemberOrders(PreparedStatement statement, int userID){
        List<Order> memberOrderList = new ArrayList<>();
        try {
            statement.setInt(1, userID);
            ResultSet memberOrderResultSet = statement.executeQuery();
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
                                                    .setGenre(Book.BookType.valueOf(memberOrderResultSet.getString(GENRE)))
                                                    .setPublishingHouse(memberOrderResultSet.getString(PUBLISHING_HOUSE))
                                                    .setNumberInformation(new Book.NumberInformation(memberOrderResultSet.getInt(YEAR), memberOrderResultSet.getInt(PAGES)))
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

    public static String getMemberOrdersQuery() {
        return MEMBER_ORDERS_QUERY;
    }

    public static String getReadingRoomOrderQuery(){
        return READING_ROOM_ORDER_QUERY;
    }

    public static String getSubscriptionOrderQuery(){
        return SUBSCRIPTION_ORDER_QUERY;
    }

    public static String getOrderBookQuery(){
        return ORDER_BOOK_QUERY;
    }

    public static String getIssueReadingRoomQuery(){
        return ISSUE_READING_ROOM_QUERY;
    }

    public static String getIssueSubscriptionQuery(){
        return ISSUE_SUBSCRIPTION_QUERY;
    }

    public static String getDecrementBookQuantityQuery() {
        return DECREMENT_BOOK_QUANTITY_QUERY;
    }

    public static String getCheckOldOrdersQuery(){
        return CHECK_OLD_ORDERS_QUERY;
    }

    public static String getReturnBookQuery(){
        return RETURN_BOOK_QUERY;
    }

    public static String getUpdateBookQuantity(){
        return UPDATE_BOOK_QUANTITY;
    }
}

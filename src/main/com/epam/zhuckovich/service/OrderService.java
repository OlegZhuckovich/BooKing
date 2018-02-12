package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.OrderDAO;
import com.epam.zhuckovich.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A class that checks the validity of information received from methods
 * of the OrderCommand class and accesses the dao layer.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Order
 * @since       1.0
 */

public class OrderService {

    private OrderDAO orderDAO;

    /**
     * Class constructor
     */

    public OrderService() {
        this.orderDAO = OrderDAO.getInstance();
    }

    /**
     * <p>Turns to the dao layer to find the member's orders
     * and returns them as a list</p>
     * @param userID userID parameter
     * @return       the list of member orders
     */

    public List<Order> findAllMemberOrders(int userID){
        return orderDAO.executeQuery(statement -> orderDAO.findAllMemberOrders(statement,userID),OrderDAO.getMemberOrdersQuery());
    }

    /**
     * <p>Turns to the dao layer to find all reading room
     * orders and returns them as a list</p>
     * @return the list of reading room orders
     */

    public List<Order> findAllReadingRoomOrders(){
        return orderDAO.executeQuery(statement -> orderDAO.findAllReadingRoomOrders(statement),OrderDAO.getReadingRoomOrderQuery());
    }

    /**
     * <p>Turns to the dao layer to find all subscription
     * orders and returns them as a list</p>
     * @return the list of subscription orders
     */

    public List<Order> findAllSubscriptionOrders(){
        return orderDAO.executeQuery(statement -> orderDAO.findAllSubscriptionOrders(statement),OrderDAO.getSubscriptionOrderQuery());
    }

    /**
     * <p>Method for ordering a book</p>
     * @param userID    userID parameter
     * @param bookID    bookID parameter
     * @param orderType type of the order
     * @return          a number other than 0 if the book was successfully ordered
     */

    public int orderBook(int userID, int bookID, String orderType){
        return orderDAO.executeUpdate(OrderDAO.getOrderBookQuery(), userID, bookID, orderType);
    }

    /**
     * <p>The method for issuing a book to the reading room with the bookID parameter
     * to the reader with the memberID parameter</p>
     * @param memberID memberID parameter
     * @param bookID   bookID parameter
     * @return         the list of reading room orders
     */

    public List<Order> issueBookInReadingRoom(String memberID, String bookID) {
        int numberMemberID = Integer.parseInt(memberID);
        int numberBookID = Integer.parseInt(bookID);
        List<Order> readingRoomOrderList = new ArrayList<>();
        if(orderDAO.executeUpdate(OrderDAO.getIssueReadingRoomQuery(), numberMemberID, numberBookID)!=0 && orderDAO.executeUpdate(OrderDAO.getDecrementBookQuantityQuery(), numberBookID)!=0){
            readingRoomOrderList = findAllReadingRoomOrders();
        }
        return readingRoomOrderList;
    }

    /**
     * <p>The method for issuing a book on subscription with the bookID parameter
     * to the reader with the memberID parameter</p>
     * @param memberID memberID parameter
     * @param bookID   bookID parameter
     * @return         the list of subscription orders
     */

    public List<Order> issueBookOnSubscription(String memberID, String bookID){
        int numberMemberID = Integer.parseInt(memberID);
        int numberBookID = Integer.parseInt(bookID);
        List<Order> subscriptionOrderList = new ArrayList<>();
        if(orderDAO.executeUpdate(OrderDAO.getIssueSubscriptionQuery(), numberMemberID, numberBookID) != 0 && orderDAO.executeUpdate(OrderDAO.getDecrementBookQuantityQuery(), numberBookID) != 0){
            subscriptionOrderList = findAllSubscriptionOrders();
        }
        return subscriptionOrderList;
    }

    /**
     * <p>The method for returning the book back to the library</p>
     * @param memberID memberID parameter
     * @param bookID   bookID parameter
     * @return         a number other than 0 if the book was successfully returned to the library
     */

    public int returnBook(int memberID, int bookID){
        int operationResult = 0;
        if(orderDAO.executeUpdate(OrderDAO.getReturnBookQuery(),memberID,bookID)!=0){
            operationResult++;
        }
        if(orderDAO.executeUpdate(OrderDAO.getUpdateBookQuantity(), bookID)!=0){
            operationResult++;
        }
        return operationResult;
    }

}

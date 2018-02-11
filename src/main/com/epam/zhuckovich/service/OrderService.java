package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.OrderDAO;
import com.epam.zhuckovich.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = OrderDAO.getInstance();
    }

    public List<Order> findAllReadingRoomOrders(){
        return orderDAO.executeQuery(statement -> orderDAO.findAllReadingRoomOrders(statement),OrderDAO.getReadingRoomOrderQuery());
    }

    public List<Order> findAllSubscriptionOrders(){
        return orderDAO.executeQuery(statement -> orderDAO.findAllSubscriptionOrders(statement),OrderDAO.getSubscriptionOrderQuery());
    }

    public List<Order> findAllMemberOrders(int userID){
        return orderDAO.executeQuery(statement -> orderDAO.findAllMemberOrders(statement,userID),OrderDAO.getMemberOrdersQuery());
    }

    public int orderBook(int userID, int bookID, String orderType){
        return orderDAO.executeUpdate(OrderDAO.getOrderBookQuery(), userID, bookID, orderType);
    }

    public List<Order> issueBookInReadingRoom(String memberID, String bookID) {
        int numberMemberID = Integer.parseInt(memberID);
        int numberBookID = Integer.parseInt(bookID);
        List<Order> readingRoomOrderList = new ArrayList<>();
        if(orderDAO.executeUpdate(OrderDAO.getIssueReadingRoomQuery(), numberMemberID, numberBookID)!=0 && orderDAO.executeUpdate(OrderDAO.getDecrementBookQuantityQuery(), numberBookID)!=0){
            readingRoomOrderList = findAllReadingRoomOrders();
        }
        return readingRoomOrderList;
    }

    public List<Order> issueBookOnSubscription(String memberID, String bookID){
        int numberMemberID = Integer.parseInt(memberID);
        int numberBookID = Integer.parseInt(bookID);
        List<Order> subscriptionOrderList = new ArrayList<>();
        if(orderDAO.executeUpdate(OrderDAO.getIssueSubscriptionQuery(), numberMemberID, numberBookID) != 0 && orderDAO.executeUpdate(OrderDAO.getDecrementBookQuantityQuery(), numberBookID) != 0){
            subscriptionOrderList = findAllSubscriptionOrders();
        }
        return subscriptionOrderList;
    }

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

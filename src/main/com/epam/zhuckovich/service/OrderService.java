package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.OrderDAO;
import com.epam.zhuckovich.entity.Order;

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

    public boolean orderBook(int userID, int bookID, String orderType){
        return orderDAO.executeUpdate(OrderDAO.getOrderBookQuery(), userID, bookID, orderType) != 0;
    }

    public boolean issueBookInReadingRoom(String memberID, String bookID) {
        int numberMemberID = Integer.parseInt(memberID);
        int numberBookID = Integer.parseInt(bookID);
        return orderDAO.executeUpdate(OrderDAO.getIssueReadingRoomQuery(), numberMemberID, numberBookID) != 0 && orderDAO.executeUpdate(OrderDAO.getDecrementBookQuantityQuery(), numberBookID) != 0;
    }

    public boolean issueBookOnSubscription(String memberID, String bookID){
        int numberMemberID = Integer.parseInt(memberID);
        int numberBookID = Integer.parseInt(bookID);
        return orderDAO.executeUpdate(OrderDAO.getSubscriptionOrderQuery(), numberMemberID, numberBookID) != 0 && orderDAO.executeUpdate(OrderDAO.getDecrementBookQuantityQuery(), numberBookID) != 0;
    }


}

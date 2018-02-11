package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.entity.Order;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.manager.PageManager;
import com.epam.zhuckovich.service.OrderService;
import com.sun.org.apache.bcel.internal.generic.RET;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

class OrderCommand extends AbstractCommand{

    private OrderService service;

    OrderCommand(){
        this.service = new OrderService();
    }

    Router issueBookInReadingRoom(HttpServletRequest request){
        String memberID = request.getParameter(MEMBER_ID_PARAMETER);
        String bookID = request.getParameter(BOOK_ID);
        if(service.issueBookInReadingRoom(memberID,bookID).isEmpty()){
            request.getSession().setAttribute(EMPTY_READING_ROOM_DELIVERY,SUCCESS);
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LIBRARIAN_MENU_PAGE));
        } else {
            return openReadingRoomMenu(request);
        }
    }

    Router issueBookOnSubscription(HttpServletRequest request){
        String memberID = request.getParameter(MEMBER_ID_PARAMETER);
        String bookID = request.getParameter(BOOK_ID);
        if(service.issueBookOnSubscription(memberID,bookID).isEmpty()){
            request.getSession().setAttribute(EMPTY_SUBSCRIPTION_DELIVERY,SUCCESS);
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LIBRARIAN_MENU_PAGE));
        } else {
            return openSubscriptionMenu(request);
        }
    }

    Router openReadingRoomMenu(HttpServletRequest request){
        List<Order> readingRoomList = service.findAllReadingRoomOrders();
        if(readingRoomList.isEmpty()){
            request.getSession().setAttribute(EMPTY_READING_ROOM_DELIVERY,SUCCESS);
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LIBRARIAN_MENU_PAGE));
        } else {
            request.setAttribute(READING_ROOM_ORDER_LIST_PARAMETER,readingRoomList);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(READING_ROOM_BOOK_DELIVERY_PAGE));
        }
    }

    Router openSubscriptionMenu(HttpServletRequest request){
        List<Order> subscriptionList = service.findAllSubscriptionOrders();
        if(subscriptionList.isEmpty()){
            request.getSession().setAttribute(EMPTY_SUBSCRIPTION_DELIVERY,SUCCESS);
            return new Router(Router.RouterType.REDIRECT,PageManager.getPage(LIBRARIAN_MENU_PAGE));
        } else {
            request.setAttribute(SUBSCRIPTION_ORDER_LIST_PARAMETER,subscriptionList);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(SUBSCRIPTION_BOOK_DELIVERY_PAGE));
        }
    }

    Router orderBook(HttpServletRequest request) {
        String stringOrderType = request.getParameter(ACTION);
        User user = (User)request.getSession().getAttribute(USER);
        String stringBookID = request.getParameter(BOOK_ID);
        int userID = user.getId();
        int bookID = Integer.parseInt(stringBookID);
        Order.OrderType orderType = Order.OrderType.valueOf(stringOrderType.toUpperCase());
        int orderBookResult = 0;
        switch (orderType){
            case READING_ROOM:
                orderBookResult = service.orderBook(userID,bookID, stringOrderType); break;
            case SUBSCRIPTION:
                orderBookResult = service.orderBook(userID,bookID, stringOrderType); break;
        }
        if(orderBookResult == 0){
            request.getSession().setAttribute(ORDER_RESULT,ERROR);
        } else {
            request.getSession().setAttribute(ORDER_RESULT,SUCCESS);
        }
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ORDER_BOOK_PAGE));
    }

    Router returnBook(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER);
        String bookID = request.getParameter(BOOK_ID);
        int memberID = user.getId();
        if(service.returnBook(memberID,Integer.parseInt(bookID))!=0){
            request.getSession().setAttribute(RETURN_OPERATION_RESULT,SUCCESS);
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(VIEW_ORDERED_BOOKS_PAGE));
        } else {
            request.getSession().setAttribute(RETURN_OPERATION_RESULT,ERROR);
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(VIEW_ORDERED_BOOKS_PAGE));
        }
    }

    Router viewOrderedBooks(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER);
        List<Order> orderList = service.findAllMemberOrders(user.getId());
        if(orderList.isEmpty()){
            request.setAttribute(ORDER_OPERATION_RESULT, SUCCESS);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(MEMBER_MENU_PAGE));
        } else {
            request.setAttribute(ORDER_LIST_PARAMETER,service.findAllMemberOrders(user.getId()));
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(VIEW_ORDERED_BOOKS_PAGE));
        }
    }

}

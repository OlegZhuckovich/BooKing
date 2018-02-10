package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.entity.Order;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.manager.PageManager;
import com.epam.zhuckovich.service.OrderService;

import javax.servlet.http.HttpServletRequest;

class OrderCommand {

    private static final String READING_ROOM_ORDER_LIST_PARAMETER = "readingRoomOrderList";
    private static final String READING_ROOM_BOOK_DELIVERY_PAGE = "readingRoomBookDelivery";
    private static final String SUBSCRIPTION_BOOK_DELIVERY_PAGE = "subscriptionBookDelivery";
    private static final String SUBSCRIPTION_ORDER_LIST_PARAMETER = "subscriptionOrderList";
    private static final String ORDER_LIST_PARAMETER = "orderList";
    private static final String VIEW_ORDERED_BOOKS_PAGE = "viewOrderedBooks";

    private static final String ORDER_BOOK_PAGE = "orderBook";
    private static final String ACTION = "action";

    private static final String USER_ATTRIBUTE = "user";
    private static final String BOOK_ID_PARAMETER = "bookID";
    private static final String MEMBER_ID_PARAMETER = "memberID";

    private static final String ORDER_RESULT = "orderResult";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private OrderService service;

    OrderCommand(){
        this.service = new OrderService();
    }

    Router openReadingRoomMenu(HttpServletRequest request){
        request.setAttribute(READING_ROOM_ORDER_LIST_PARAMETER,service.findAllReadingRoomOrders());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(READING_ROOM_BOOK_DELIVERY_PAGE));
    }

    Router openSubscriptionMenu(HttpServletRequest request){
        request.setAttribute(SUBSCRIPTION_ORDER_LIST_PARAMETER,service.findAllSubscriptionOrders());
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(SUBSCRIPTION_BOOK_DELIVERY_PAGE));
    }

    Router viewOrderedBooks(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE);
        request.setAttribute(ORDER_LIST_PARAMETER,service.findAllMemberOrders(user.getId()));
        return new Router(Router.RouterType.FORWARD, PageManager.getPage(VIEW_ORDERED_BOOKS_PAGE));
    }

    Router orderBook(HttpServletRequest request) {
        String stringOrderType = request.getParameter(ACTION);
        User user = (User)request.getSession().getAttribute(USER_ATTRIBUTE);
        String stringBookID = request.getParameter(BOOK_ID_PARAMETER);
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

    Router issueBookInReadingRoom(HttpServletRequest request){
        String memberID = request.getParameter(MEMBER_ID_PARAMETER);
        String bookID = request.getParameter(BOOK_ID_PARAMETER);
        service.issueBookInReadingRoom(memberID,bookID);
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(READING_ROOM_BOOK_DELIVERY_PAGE));
    }

    Router issueBookOnSubscription(HttpServletRequest request){
        String memberID = request.getParameter(MEMBER_ID_PARAMETER);
        String bookID = request.getParameter(BOOK_ID_PARAMETER);
        service.issueBookOnSubscription(memberID,bookID);
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(SUBSCRIPTION_BOOK_DELIVERY_PAGE));
    }


}

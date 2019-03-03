package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;
import com.epam.zhuckovich.entity.Order;
import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.manager.PageManager;
import com.epam.zhuckovich.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>A class that contains methods for processing user orders in the
 * library.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Order
 * @since       1.0
 */

class OrderCommand extends AbstractCommand{

    private OrderService service;

    /**
     * Class constructor
     */

    OrderCommand(){
        this.service = new OrderService();
    }

    /**
     * <p>A method in which the issuance of a particular book to a particular
     * reader in the reading room by a librarian.</p>
     * @param request sends memberID and bookID to the server
     * @return        the router that redirects to the librarian menu page if all books
     *                in the reading room are issued, otherwise call openReadingRoomMenu method.
     */

    Router issueBookInReadingRoom(HttpServletRequest request){
        if(service.issueBookInReadingRoom(request.getParameter(MEMBER_ID), request.getParameter(BOOK_ID)).isEmpty()){
            request.getSession().setAttribute(EMPTY_READING_ROOM_DELIVERY,SUCCESS);
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LIBRARIAN_MENU_PAGE));
        } else {
            return openOrderMenu(request, Order.OrderType.READING_ROOM);
        }
    }

    /**
     * <p>A method in which the issuance of a particular book to a particular
     * reader on subscription by a librarian.</p>
     * @param request sends memberID and bookID to the server
     * @return        the router that redirects to the librarian menu page if all books
     *                on subscription are issued, otherwise call openSubscriptionMenu method.
     */

    Router issueBookOnSubscription(HttpServletRequest request){
        if(service.issueBookOnSubscription(request.getParameter(MEMBER_ID), request.getParameter(BOOK_ID)).isEmpty()){
            request.getSession().setAttribute(EMPTY_SUBSCRIPTION_DELIVERY, SUCCESS);
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LIBRARIAN_MENU_PAGE));
        } else {
            return openOrderMenu(request, Order.OrderType.SUBSCRIPTION);
        }
    }

    /**
     * <p>Finds all book orders</p>
     * @param request used to send a list of orders  to client
     * @return        router that redirects user to the librarian menu page if the
     *                order list is empty, otherwise forwards user to the book
     *                delivery page
     */

    Router openOrderMenu(HttpServletRequest request, Order.OrderType orderType){
        List<Order> orderList;
        if(orderType == Order.OrderType.READING_ROOM){
            orderList = service.findAllReadingRoomOrders();
        } else {
            orderList = service.findAllSubscriptionOrders();
        }
        if(orderList.isEmpty()){
            if(orderType == Order.OrderType.READING_ROOM){
                request.getSession().setAttribute(EMPTY_READING_ROOM_DELIVERY, SUCCESS);
            } else {
                request.getSession().setAttribute(EMPTY_SUBSCRIPTION_DELIVERY, SUCCESS);
            }
            return new Router(Router.RouterType.REDIRECT, PageManager.getPage(LIBRARIAN_MENU_PAGE));
        } else {
            if(orderType == Order.OrderType.READING_ROOM){
                request.setAttribute(READING_ROOM_ORDER_LIST, orderList);
                return new Router(Router.RouterType.FORWARD, PageManager.getPage(READING_ROOM_BOOK_DELIVERY_PAGE));
            } else {
                request.setAttribute(SUBSCRIPTION_ORDER_LIST, orderList);
                return new Router(Router.RouterType.FORWARD, PageManager.getPage(SUBSCRIPTION_BOOK_DELIVERY_PAGE));
            }
        }
    }

    /**
     * <p>A method used to order books in the reading room or on a subscription by readers</p>
     * @param request sends information about the user, book and the type of order to the server
     * @return        router that redirects user to the order book page
     */

    Router orderBook(HttpServletRequest request) {
        var user = (User)request.getSession().getAttribute(USER);
        if(service.orderBook(user.getId(), Integer.parseInt(request.getParameter(BOOK_ID)),
                request.getParameter(ACTION)) == 0){
            request.getSession().setAttribute(ORDER_RESULT,ERROR);
        } else {
            request.getSession().setAttribute(ORDER_RESULT,SUCCESS);
        }
        return new Router(Router.RouterType.REDIRECT,PageManager.getPage(ORDER_BOOK_PAGE));
    }

    /**
     * <p>A method that is used to return books by readers back to the library</p>
     * @param request sends information about the user and book to the server
     * @return        router that redirects user to the view ordered books page
     */

    Router returnBook(HttpServletRequest request){
        var user = (User) request.getSession().getAttribute(USER);
        var bookID = request.getParameter(BOOK_ID);
        if(service.returnBook(user.getId(), Integer.parseInt(bookID))!= 0){
            request.getSession().setAttribute(RETURN_OPERATION_RESULT, SUCCESS);
        } else {
            request.getSession().setAttribute(RETURN_OPERATION_RESULT, ERROR);
        }
        return new Router(Router.RouterType.REDIRECT, PageManager.getPage(VIEW_ORDERED_BOOKS_PAGE));
    }

    /**
     * <p>A method used to view ordered books in the library</p>
     * @param request sends information about the user the server
     * @return        router that forwards user to the member menu page if
     *                the user order list is empty, otherwise forwards to the
     *                view ordered books page
     */

    Router viewOrderedBooks(HttpServletRequest request){
        var user = (User) request.getSession().getAttribute(USER);
        var orders = service.findAllMemberOrders(user.getId());
        if(orders.isEmpty()){
            request.setAttribute(ORDER_OPERATION_RESULT, SUCCESS);
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(MEMBER_MENU_PAGE));
        } else {
            request.setAttribute(ORDER_LIST, service.findAllMemberOrders(user.getId()));
            return new Router(Router.RouterType.FORWARD, PageManager.getPage(VIEW_ORDERED_BOOKS_PAGE));
        }
    }

}

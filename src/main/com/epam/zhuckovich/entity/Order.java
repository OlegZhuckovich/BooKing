package com.epam.zhuckovich.entity;

import java.sql.Date;

/**
 * <p>Class that contains information about book order.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Entity
 * @since       1.0
 */

public class Order extends Entity {

    /**
     * Enumeration of book order types
     */

    public enum OrderType {
        READING_ROOM, SUBSCRIPTION
    }

    private User user;
    private Book book;
    private Date orderDate;
    private Date returnDate;
    private OrderType orderType;

    /**
     * Private constructor
     */

    private Order(){}

    /**
     * <p>Returns name of user.</p>
     * @return name of user
     */

    public User getUser() {
        return user;
    }

    /**
     * <p>Returns the ordered book.</p>
     * @return the ordered book
     */

    public Book getBook() {
        return book;
    }

    /**
     * <p>Returns the order date.</p>
     * @return the order date
     */

    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * <p>Returns the return date.</p>
     * @return the return date
     */

    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * <p>Returns the order type.</p>
     * @return the order type
     */

    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * <p>Returns the builder to construct order</p>
     * @return the order builder
     */

    public static Builder newBuilder() {
        return new Order().new Builder();
    }

    /**
     * Inner class to construct order object
     */

    public class Builder {

        /**
         * Private constructor
         */

        private Builder() {}

        /**
         * <p>Sets the id of the order</p>
         * @param orderId number value of order id
         * @return the builder to construct order object
         */

        public Builder setId(int orderId) {
            Order.this.id = orderId;
            return this;
        }

        /**
         * <p>Sets the user of the order</p>
         * @param user user which order book
         * @return the builder to construct order object
         */

        public Builder setMember(User user) {
            Order.this.user = user;
            return this;
        }

        /**
         * <p>Sets the book of the order</p>
         * @param book books which user ordered
         * @return the builder to construct order object
         */

        public Builder setBook(Book book) {
            Order.this.book = book;
            return this;
        }

        /**
         * <p>Sets the orderDate of the order</p>
         * @param orderDate orderDate of the order
         * @return the builder to construct order object
         */

        public Builder setOrderDate(Date orderDate) {
            Order.this.orderDate = orderDate;
            return this;
        }

        /**
         * <p>Sets the returnDate of the order</p>
         * @param returnDate returnDate of the order
         * @return the builder to construct order object
         */

        public Builder setReturnDate(Date returnDate) {
            Order.this.returnDate = returnDate;
            return this;
        }

        /**
         * <p>Sets the orderType of the order</p>
         * @param orderType type of the order
         * @return the builder to construct order object
         */

        public Builder setOrderType(OrderType orderType){
            Order.this.orderType = orderType;
            return this;
        }

        /**
         * <p>Build the order object</p>
         * @return the order object
         */

        public Order build() {
            return Order.this;
        }

    }

    @Override
    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        if(this == object){
            return true;
        }
        if(getClass() != object.getClass()){
            return false;
        }
        Order order = (Order) object;
        if(id != order.id){
            return false;
        }
        if(user == null){
            if(order.user != null){
                return false;
            }
        } else if(!user.equals(order.user)){
            return false;
        }
        if(book == null){
            if(order.book != null){
                return false;
            }
        } else if(!book.equals(order.book)){
            return false;
        }
        if(orderDate == null){
            if(order.orderDate != null){
                return false;
            }
        } else if(!orderDate.equals(order.orderDate)){
            return false;
        }
        if(returnDate == null){
            if(order.returnDate != null){
                return false;
            }
        } else if(!returnDate.equals(order.returnDate)){
            return false;
        }
        if(orderType == null){
            if(order.orderType != null){
                return false;
            }
        } else if(orderType != order.orderType){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        return 31*id + ((user == null) ? 0 : user.hashCode()) + ((book == null) ? 0 : book.hashCode()) +
                ((orderDate == null) ? 0 : orderDate.hashCode()) + ((returnDate == null) ? 0 : returnDate.hashCode()) +
                ((orderType == null) ? 0 : orderType.hashCode());
    }

    @Override
    public String toString(){
        return "OrderID: " + id + "\n" +
                "User: " + user.toString() + "\n" +
                "Book: " + book.toString() + "\n" +
                "OrderDate: " + orderDate + "\n" +
                "ReturnDate: " + returnDate + "\n" +
                "OrderType: " + orderType + "\n";
    }

}

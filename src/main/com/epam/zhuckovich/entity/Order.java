package com.epam.zhuckovich.entity;

import java.sql.Date;

public class Order extends Entity{

    public enum OrderType{
        READING_ROOM, SUBSCRIPTION
    }

    private User user;
    private Book book;
    private Date orderDate;
    private Date returnDate;
    private OrderType orderType;

    private Order(){}

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public static Builder newBuilder() {
        return new Order().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder setId(int orderId) {
            Order.this.id = orderId;
            return this;
        }

        public Builder setMember(User user) {
            Order.this.user = user;
            return this;
        }

        public Builder setBook(Book book) {
            Order.this.book = book;
            return this;
        }

        public Builder setOrderDate(Date orderDate) {
            Order.this.orderDate = orderDate;
            return this;
        }

        public Builder setReturnDate(Date returnDate) {
            Order.this.returnDate = returnDate;
            return this;
        }

        public Builder setOrderType(OrderType orderType){
            Order.this.orderType = orderType;
            return this;
        }

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

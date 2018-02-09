package com.epam.zhuckovich.command;

/**
 * Enum which contains different action for different realeasation of Command functional interface
 */

public enum CommandType {
    LOGIN, REGISTER, SEARCH, ADD_AUTHOR, VIEW_AUTHORS, ADD_BOOK, ADD_BOOK_MENU,
    ORDER_BOOK, EDIT_ACCOUNT, READING_ROOM_MENU, READING_ROOM_ISSUE, SUBSCRIPTION_ISSUE,
    SUBSCRIPTION_MENU, DELETE_ACCOUNT, DELETE_BOOK_MENU, DELETE_BOOK, DELETE_USER,
    DELETE_LIBRARIAN_MENU, DELETE_MEMBER_MENU, LOGOUT, VIEW_ORDERED_BOOKS
}

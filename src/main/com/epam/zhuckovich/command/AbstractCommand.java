package com.epam.zhuckovich.command;

/**
 * <p>Class in which variables are located used by other classes of commands to
 * extract parameters from request object.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @since       1.0
 */

abstract class AbstractCommand {

    /**
     * AuthorCommand final variables
     */

    static final String AUTHOR = "author";
    static final String AUTHOR_ADDED_RESULT = "authorAddedResult";
    static final String AUTHOR_BIOGRAPHY = "authorBiography";
    static final String AUTHOR_GALLERY = "authorGallery";
    static final String AUTHOR_PHOTO = "authorPhoto";
    static final String AUTHOR_NAME = "authorName";
    static final String AUTHOR_SURNAME = "authorSurname";
    static final String LIBRARIAN_MENU = "librarianMenu";

    /**
     * BookCommand final variables
     */

    static final String ADD_BOOK_PAGE = "addBook";
    static final String ADMINISTRATOR_MENU = "administratorMenu";
    static final String BOOK = "book";
    static final String BOOK_ADDED_RESULT = "bookAddedResult";
    static final String BOOK_LIST = "bookList";
    static final String BOOK_AUTHOR = "bookAuthor";
    static final String BOOK_CONTENT = "bookContent";
    static final String BOOK_GENRE = "bookGenre";
    static final String BOOK_PAGES = "bookPages";
    static final String BOOK_PUBLISHING_HOUSE = "bookPublishingHouse";
    static final String BOOK_QUANTITY = "bookQuantity";
    static final String BOOK_TITLE = "bookTitle";
    static final String BOOK_YEAR = "bookYear";
    static final String CRITERIA = "criteriaSelect";
    static final String DELETE_BOOK_PAGE = "deleteBook";
    static final String EDIT_BOOK_PAGE = "editBook";
    static final String EDIT_CURRENT_BOOK_PAGE = "editCurrentBook";
    static final String EMPTY_LIST = "emptyList";
    static final String GENRE_LIST = "genreList";
    static final String SEARCH_RESULT = "searchResult";
    static final String SEARCH_VALUE = "searchField";

    //   OrderCommand final variables

    static final String ACTION = "action";
    static final String EMPTY_READING_ROOM_DELIVERY = "emptyReadingRoomDelivery";
    static final String EMPTY_SUBSCRIPTION_DELIVERY = "emptySubscriptionDelivery";
    static final String MEMBER_ID = "memberID";
    static final String ORDER_LIST = "orderList";
    static final String ORDER_OPERATION_RESULT = "orderOperationResult";
    static final String ORDER_RESULT = "orderResult";
    static final String READING_ROOM_ORDER_LIST = "readingRoomOrderList";
    static final String READING_ROOM_BOOK_DELIVERY_PAGE = "readingRoomBookDelivery";
    static final String RETURN_OPERATION_RESULT = "returnOperationResult";
    static final String SUBSCRIPTION_BOOK_DELIVERY_PAGE = "subscriptionBookDelivery";
    static final String SUBSCRIPTION_ORDER_LIST = "subscriptionOrderList";
    static final String VIEW_ORDERED_BOOKS_PAGE = "viewOrderedBooks";

    /**
     * UserCommand final variables
     */

    static final String ADMINISTRATOR_MENU_PAGE = "administratorMenu";
    static final String AVATAR_USER = "avatarUser";
    static final String CITY_USER = "cityUser";
    static final String DELETE_ACCOUNT = "deleteAccount";
    static final String DELETE_LIBRARIAN_PAGE = "deleteLibrarian";
    static final String DELETE_MEMBER_PAGE = "deleteMember";
    static final String EDIT_ACCOUNT_PAGE = "editAccount";
    static final String EMAIL_USER = "emailUser";
    static final String EMPTY_DELETE_LIBRARIAN = "emptyDeleteLibrarian";
    static final String EMPTY_DELETE_MEMBER = "emptyDeleteMember";
    static final String ERROR_LOGIN_MESSAGE = "loginError";
    static final String HOUSE_USER = "houseUser";
    static final String LOGIN_PAGE = "loginPage";
    static final String LIBRARIAN_LIST = "librarianList";
    static final String LIBRARIAN_PAGE = "deleteLibrarian";
    static final String MEMBER_LIST = "memberList";
    static final String NAME_USER = "nameUser";
    static final String OPERATION_SUCCESS = "operationSuccess";
    static final String PAGE = "page";
    static final String PASSWORD_USER = "passwordUser";
    static final String REGISTRATION_PAGE = "registrationPage";
    static final String REGISTRATION_RESULT = "registrationResult";
    static final String REPEAT_PASSWORD_USER = "repeatPasswordUser";
    static final String STREET_USER = "streetUser";
    static final String SURNAME_USER = "surnameUser";
    static final String TELEPHONE_USER = "telephoneUser";
    static final String USER_ID = "userID";
    static final String USER_REGISTRATION = "userRegistration";

    /**
     * Common variables for several commands
     */

    static final String AUTHOR_LIST = "authorList";
    static final String BOOK_ID = "bookID";
    static final String LIBRARIAN_MENU_PAGE = "librarianMenu";
    static final String MEMBER_MENU_PAGE = "memberMenu";
    static final String ORDER_BOOK_PAGE = "orderBook";
    static final String USER = "user";
    static final String SUCCESS = "success";
    static final String ERROR = "error";
    static final String SPACE = " ";

}

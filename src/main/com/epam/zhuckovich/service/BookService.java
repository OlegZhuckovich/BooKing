package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.AuthorDAO;
import com.epam.zhuckovich.dao.BookDAO;
import com.epam.zhuckovich.entity.Author;
import com.epam.zhuckovich.entity.Book;

import java.io.InputStream;
import java.util.List;

/**
 * <p>A class that checks the validity of information received from methods
 * of the BookCommand class and accesses the dao layer.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Book
 * @since       1.0
 */

public class BookService {

    private BookDAO bookDAO;

    /**
     *  Class constructor
     */

    public BookService() {
        this.bookDAO = BookDAO.getInstance();
    }

    /**
     * <p>Checks the data of the new book for compliance with regular expressions and
     * allowed intervals and, if successful, sends information to the dao layer for
     * further processing</p>
     * @param book book that will be added
     * @return     true if book was added to the database
     */

    public boolean addBook(Book book){
        if(book.getTitle()==null || book.getPublishingHouse()==null || book.getNumberInformation()==null || book.getGenre()==null || book.getAuthors()==null || book.getAuthors().isEmpty()){
            return false;
        }
        var quantity = book.getNumberInformation().getQuantity();
        var year = book.getNumberInformation().getYear();
        var pages = book.getNumberInformation().getPages();
        if(quantity < 1 || quantity > 100 || year < 1500 || year > 2019 || pages < 1 || pages > 10000){
            return false;
        }
        var bookID = bookDAO.addBook(book.getTitle(),String.valueOf(book.getGenre()),book.getPublishingHouse(),book.getNumberInformation().getYear(), book.getNumberInformation().getPages(), book.getNumberInformation().getQuantity());
        if(bookID == 0){
            return false;
        } else {
            for(Author authors: book.getAuthors()){
                var operationSuccess = bookDAO.executeUpdate(BookDAO.getAddBookAuthorQuery(),authors.getId(),bookID);
                if(operationSuccess == 0){
                    return false;
                }
            }
            if(book.getBookContent()!=null) {
                int operationSuccess = bookDAO.executeUpdate(BookDAO.getAddBookContentQuery(), bookID, book.getBookContent());
                if(operationSuccess == 0){
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Converts the bookID parameter to int type to delete a book
     * from the database</p>
     * @param bookID bookID parameter
     * @return       true if the operation was completed successfully
     */

    public boolean deleteBook(String bookID){
        return bookDAO.executeUpdate(BookDAO.getDeleteBookQuery(), Integer.parseInt(bookID)) != 0;
    }

    public int editCurrentBook(int bookID, List<Integer> authorIds, String bookTitle, String bookPublishingHouse, Book.Metadata bookMetadata, Book.Genre bookGenre, InputStream... bookContent){
        var year = bookMetadata.getYear();
        var pages = bookMetadata.getPages();
        var quantity = bookMetadata.getQuantity();
        if(bookID < 1 || quantity < 1 || quantity > 100 || year < 1500 || year > 2019 || pages < 1 || pages > 10000 || authorIds.isEmpty() || bookTitle == null || bookPublishingHouse == null || bookGenre == null){
            return 0;
        } else {
            if(bookDAO.executeUpdate(BookDAO.getEditDeleteAuthorsQuery(),bookID)!=0) {
                authorIds.forEach(authorId -> bookDAO.executeUpdate(BookDAO.getEditAddNewAuthorsQuery(), authorId, bookID));
                if(bookContent.length != 0){
                    bookDAO.executeUpdate(BookDAO.getEditBookContentQuery(), bookContent, bookID);
                }
                bookDAO.executeUpdate(BookDAO.getEditBookQuery(),bookTitle,String.valueOf(bookGenre),bookPublishingHouse,year, pages, quantity, bookID);
            }
            return 0;
        }
    }

    /**
     * <p>Returns the list of all authors</p>
     * @return the list of authors
     */

    public List<Author> findAllAuthors(){
        var authorDAO = AuthorDAO.getInstance();
        return authorDAO.executeQuery(authorDAO::findAllAuthors,AuthorDAO.getFindAllAuthorsQuery());
    }

    /**
     * <p>Returns the list of all books</p>
     * @return the list of books
     */

    public List<Book> findAllBooks(){
        return bookDAO.executeQuery(statement -> bookDAO.findAllBooks(statement), BookDAO.getFindAllBooksQuery());
    }

    /**
     * <p>Returns the list of books that were found as a result of the
     * search by criterion </p>
     * @param searchValue    value for search
     * @param searchCriteria criteria for search
     * @param userID         userID parameter
     * @return               the list of books that were found in the search result
     */

    public List<Book> findBooksByCriteria(String searchValue, String searchCriteria, String userID){
        if(searchValue == null || searchCriteria == null){
            return null;
        } else {
            return bookDAO.executeQuery(statement -> bookDAO.findAllBooks(statement,"%"+searchValue+"%", userID),BookDAO.getSearchBookQueryByCriteria()+searchCriteria+BookDAO.getSearchBookByCriteriaLikeStatement());
        }
    }

}

package com.epam.zhuckovich.service;

import com.epam.zhuckovich.dao.AuthorDAO;
import com.epam.zhuckovich.dao.BookDAO;
import com.epam.zhuckovich.entity.Author;
import com.epam.zhuckovich.entity.Book;

import java.util.List;

public class BookService {

    private BookDAO bookDAO;

    public BookService() {
        this.bookDAO = BookDAO.getInstance();
    }

    public List<Book> findAllBooks(){
        return bookDAO.executeQuery(statement -> bookDAO.findAllBooks(statement),BookDAO.getFindAllBooksQuery());
    }

    public List<Book> findBooksByCriteria(String searchValue, String searchCriteria, String userID){
        if(searchValue == null || searchCriteria == null){
            return null;
        } else {
            return bookDAO.executeQuery(statement -> bookDAO.findAllBooks(statement,"%"+searchValue+"%", userID),BookDAO.getSearchBookQueryByCriteria()+searchCriteria+BookDAO.getLikeStatement());
        }
    }

    public boolean deleteBook(String bookID){
        Integer integerBookID = Integer.parseInt(bookID);
        return bookDAO.executeUpdate(BookDAO.getDeleteBookQuery(), integerBookID) != 0;
    }

    public List<Author> findAllAuthors(){
        AuthorDAO authorDAO = AuthorDAO.getInstance();
        return authorDAO.executeQuery(authorDAO::findAllAuthors,AuthorDAO.getFindAllAuthors());
    }

    public boolean addBook(Book book){
        if(book.getTitle()==null || book.getPublishingHouse()==null || book.getNumberInformation()==null || book.getGenre()==null || book.getAuthors()==null || book.getAuthors().isEmpty()){
              return false;
        }
        int quantity = book.getNumberInformation().getQuantity();
        int year = book.getNumberInformation().getYear();
        int pages = book.getNumberInformation().getPages();
        if(quantity < 1 || quantity > 100 || year < 1500 || year > 2018 || pages < 1 || pages > 10000){
            return false;
        }
        int bookID = bookDAO.addBook(book.getTitle(),String.valueOf(book.getGenre()),book.getPublishingHouse(),book.getNumberInformation().getYear(), book.getNumberInformation().getPages(), book.getNumberInformation().getQuantity());
        if(bookID == 0){
           return false;
        } else {
            for(Author bookAuthor:book.getAuthors()){
                int operationSuccess = bookDAO.executeUpdate(BookDAO.getAddBookAuthorQuery(),bookAuthor.getId(),bookID);
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


}

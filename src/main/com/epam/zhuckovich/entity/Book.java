package com.epam.zhuckovich.entity;

import java.io.InputStream;
import java.util.List;

/**
 * <p>Class that contains information about books.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Entity
 * @since       1.0
 */

public class Book extends Entity {

    public enum BookType {
        Роман, Научная, Фантастика, Стихи, Поэма, Реализм
    }

    private String title;
    private BookType genre;
    private String publishingHouse;
    private NumberInformation bookNumberInformation;
    private List<Author> bookAuthors;
    private InputStream bookContent;

    /**
     * Private class constructor
     */

    private Book(){}




    public static class NumberInformation{

        private int year;
        private int pages;
        private int quantity;

        public NumberInformation(){}

        public NumberInformation(int year, int pages){
            this.year=year;
            this.pages=pages;
        }

        public NumberInformation(int year, int pages, int quantity){
            this.year = year;
            this.pages = pages;
            this.quantity = quantity;
        }

        public void setYear(int year){
            this.year = year;
        }

        public void setPages(int pages){
            this.pages = pages;
        }

        public void setQuantity(int quantity){
            this.quantity = quantity;
        }

        public int getYear(){
            return year;
        }

        public int getPages(){
            return pages;
        }

        public int getQuantity(){
            return quantity;
        }
    }

    public String getTitle(){
        return title;
    }

    public BookType getGenre(){
        return genre;
    }

    public String getPublishingHouse(){
        return publishingHouse;
    }

    public NumberInformation getNumberInformation(){
        return bookNumberInformation;
    }

    public List<Author> getAuthors(){
        return bookAuthors;
    }

    public InputStream getBookContent(){
        return bookContent;
    }

    public static Builder newBuilder() {
        return new Book().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder setId(int bookId) {
            Book.this.id = bookId;
            return this;
        }

        public Builder setTitle(String title) {
            Book.this.title = title;
            return this;
        }

        public Builder setGenre(BookType genre) {
            Book.this.genre = genre;
            return this;
        }

        public Builder setPublishingHouse(String publishingHouse) {
            Book.this.publishingHouse = publishingHouse;
            return this;
        }

        public Builder setNumberInformation(NumberInformation bookNumberInformation) {
            Book.this.bookNumberInformation = bookNumberInformation;
            return this;
        }

        public Builder setAuthors(List<Author> bookAuthors) {
            Book.this.bookAuthors = bookAuthors;
            return this;
        }

        public Builder setBookContent(InputStream bookContent){
            Book.this.bookContent = bookContent;
            return this;
        }

        public Book build() {
            return Book.this;
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
        Book book = (Book) object;
        if(id != book.id){
            return false;
        }
        if(title == null){
            if(book.title != null){
                return false;
            }
        } else if(!title.equals(book.title)){
            return false;
        }
        if(genre == null){
            if(book.genre != null){
                return false;
            }
        } else if(genre != book.genre){
            return false;
        }
        if(publishingHouse == null){
            if(book.publishingHouse != null){
                return false;
            }
        } else if(!publishingHouse.equals(book.publishingHouse)){
            return false;
        }
        if(bookNumberInformation.year != book.bookNumberInformation.year){
            return false;
        }
        if(bookNumberInformation.pages != book.bookNumberInformation.pages){
            return false;
        }
        if(bookNumberInformation.quantity != book.bookNumberInformation.quantity){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int authorHashCode = 0;
        for(Author author:bookAuthors){
            authorHashCode += author.hashCode();
        }
        return 31*id + ((title == null) ? 0 : title.hashCode()) + ((genre == null) ? 0 : genre.hashCode()) +
                ((publishingHouse == null) ? 0 : publishingHouse.hashCode()) + bookNumberInformation.year +
                bookNumberInformation.pages + bookNumberInformation.quantity + authorHashCode;
    }

    @Override
    public String toString(){
        String authors = "";
        for(Author author:bookAuthors){
            authors += author.toString();
        }
        return "BookID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Genre: " + genre + "\n" +
                "PublishingHouse: " + publishingHouse + "\n" +
                "Year: " + bookNumberInformation.year + "\n" +
                "Pages: " + bookNumberInformation.pages + "\n" +
                "Quantity: " + bookNumberInformation.quantity + "\n" +
                "Authors: " + authors + "\n";
    }

}

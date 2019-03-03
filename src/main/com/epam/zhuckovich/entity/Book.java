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

    /**
     * Enumeration of different book genres
     */

    public enum Genre {
        Античная, Историческая, Классика, Комедия, Комикс, Научная, Поэзия, Поэма,
        Приключения, Реализм, Религиозная, Роман, Сатира, Стихи, Справочник, Фантастика
    }

    private String title;
    private Genre genre;
    private String publishingHouse;
    private Metadata bookMetadata;
    private List<Author> bookAuthors;
    private InputStream bookContent;

    /**
     * Private class constructor
     */

    private Book(){}

    /**
     * Inner static class for book number information
     */

    public static class Metadata {

        private int year;
        private int pages;
        private int quantity;

        public Metadata(){}

        /**
         * Constructor with two parameters
         * @param year  year of book publication
         * @param pages quantity of pages
         */

        public Metadata(int year, int pages){
            this.year=year;
            this.pages=pages;
        }

        /**
         * Constructor with three parameters
         * @param year     year of book publication
         * @param pages    quantity of pages
         * @param quantity number of copies of a book
         */

        public Metadata(int year, int pages, int quantity){
            this.year = year;
            this.pages = pages;
            this.quantity = quantity;
        }

        /**
         * <p>Sets the year of book publication</p>
         * @param year year of book publication
         */

        public void setYear(int year){
            this.year = year;
        }

        /**
         * <p>Sets the quantity of book pages</p>
         * @param pages quantity of book pages
         */

        public void setPages(int pages){
            this.pages = pages;
        }

        /**
         * <p>Sets the quantity of book copies</p>
         * @param quantity number of copies of a book
         */

        public void setQuantity(int quantity){
            this.quantity = quantity;
        }

        /**
         * <p>Return the year of book publication</p>
         * @return the year of book publication
         */

        public int getYear(){
            return year;
        }

        /**
         * <p>Return the quantity of book pages</p>
         * @return the quantity of book pages
         */

        public int getPages(){
            return pages;
        }

        /**
         * <p>Return the quantity of book copies</p>
         * @return the quantity of book copies
         */

        public int getQuantity(){
            return quantity;
        }
    }

    /**
     * <p>Return the book title</p>
     * @return the book title
     */

    public String getTitle(){
        return title;
    }

    /**
     * <p>Return the book genre</p>
     * @return the book genre
     */

    public Genre getGenre(){
        return genre;
    }

    /**
     * <p>Return the publishing house</p>
     * @return the publishing house
     */

    public String getPublishingHouse(){
        return publishingHouse;
    }

    /**
     * <p>Return the number information about book</p>
     * @return the number information about book
     */

    public Metadata getNumberInformation(){
        return bookMetadata;
    }

    /**
     * <p>Return the list of book authors</p>
     * @return the list of book authors
     */

    public List<Author> getAuthors(){
        return bookAuthors;
    }

    /**
     * <p>Return the book content</p>
     * @return the book content
     */

    public InputStream getBookContent(){
        return bookContent;
    }

    /**
     * <p>Returns the builder to construct book</p>
     * @return the book builder
     */

    public static Builder newBuilder() {
        return new Book().new Builder();
    }

    /**
     * Inner class to construct book object
     */

    public class Builder {

        /**
         * Private constructor
         */

        private Builder() {}

        /**
         * <p>Sets the id of the book</p>
         * @param bookId number value of book id
         * @return the builder to construct book object
         */

        public Builder setId(int bookId) {
            Book.this.id = bookId;
            return this;
        }

        /**
         * <p>Sets the title of the book</p>
         * @param title book title
         * @return the builder to construct book object
         */

        public Builder setTitle(String title) {
            Book.this.title = title;
            return this;
        }

        /**
         * <p>Sets the genre of the book</p>
         * @param genre book genre
         * @return the builder to construct book object
         */

        public Builder setGenre(Genre genre) {
            Book.this.genre = genre;
            return this;
        }

        /**
         * <p>Sets the publishingHouse of the book</p>
         * @param publishingHouse book publishing house
         * @return the builder to construct book object
         */

        public Builder setPublishingHouse(String publishingHouse) {
            Book.this.publishingHouse = publishingHouse;
            return this;
        }

        /**
         * <p>Sets the book number information</p>
         * @param bookMetadata number information about book
         * @return the builder to construct book object
         */

        public Builder setNumberInformation(Metadata bookMetadata) {
            Book.this.bookMetadata = bookMetadata;
            return this;
        }

        /**
         * <p>Sets the authors of the book</p>
         * @param bookAuthors authors of the book
         * @return the builder to construct book object
         */

        public Builder setAuthors(List<Author> bookAuthors) {
            Book.this.bookAuthors = bookAuthors;
            return this;
        }

        /**
         * <p>Sets the bookContent of the book</p>
         * @param bookContent the contents of the book
         * @return the builder to construct book object
         */

        public Builder setBookContent(InputStream bookContent){
            Book.this.bookContent = bookContent;
            return this;
        }

        /**
         * <p>Build the book object</p>
         * @return the book object
         */

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
        if(bookMetadata.year != book.bookMetadata.year){
            return false;
        }
        if(bookMetadata.pages != book.bookMetadata.pages){
            return false;
        }
        if(bookMetadata.quantity != book.bookMetadata.quantity){
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
                ((publishingHouse == null) ? 0 : publishingHouse.hashCode()) + bookMetadata.year +
                bookMetadata.pages + bookMetadata.quantity + authorHashCode;
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
                "Year: " + bookMetadata.year + "\n" +
                "Pages: " + bookMetadata.pages + "\n" +
                "Quantity: " + bookMetadata.quantity + "\n" +
                "Authors: " + authors + "\n";
    }

}

package com.epam.zhuckovich.entity;

import java.io.InputStream;

/**
 * <p>Class that contains information about book author.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Entity
 * @since       1.0
 */

public class Author extends Entity{
    private String name;
    private String surname;
    private String biography;
    private InputStream photo;

    /**
     * Private constructor
     */

    public Author(){}

    /**
     * <p>Returns name of author.</p>
     * @return name of author
     */

    public String getName(){
        return name;
    }

    /**
     * <p>Returns surname of author.</p>
     * @return surname of author
     */

    public String getSurname(){
        return this.surname;
    }

    /**
     * <p>Returns information about author's biography.</p>
     * @return information about author's biography
     */

    public String getBiography(){ return biography; }

    /**
     * <p>Returns the photo of author.</p>
     * @return the photo of author
     */

    public InputStream getPhoto(){
        return photo;
    }

    /**
     * <p>Returns the builder to construct author</p>
     * @return the author builder
     */

    public static Builder newBuilder() {
        return new Author().new Builder();
    }

    /**
     * Inner class to construct author object
     */

    public class Builder {

        private Builder() {}

        /**
         * <p>Sets the id of the author</p>
         * @param authorId number value of author id
         * @return the builder to construct author object
         */

        public Builder setId(int authorId) {
            Author.this.id = authorId;
            return this;
        }

        /**
         * <p>Sets the name of the author</p>
         * @param name name of author
         * @return the builder to construct author object
         */

        public Builder setName(String name) {
            Author.this.name = name;
            return this;
        }

        /**
         * <p>Sets the surname of the author</p>
         * @param surname surname of author
         * @return the builder to construct author object
         */

        public Builder setSurname(String surname) {
            Author.this.surname = surname;
            return this;
        }

        /**
         * <p>Sets the biography of the author</p>
         * @param biography biography of the author
         * @return the builder to construct author object
         */

        public Builder setBiography(String biography) {
            Author.this.biography = biography;
            return this;
        }

        /**
         * <p>Sets the photo of the author</p>
         * @param photo photo of the author
         * @return the builder to construct author object
         */

        public Builder setPhoto(InputStream photo) {
            Author.this.photo = photo;
            return this;
        }

        /**
         * <p>Build the author object</p>
         * @return the author object
         */

        public Author build() {
            return Author.this;
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
        Author author = (Author) object;
        if(id != author.id){
            return false;
        }
        if(name == null){
            if(author.name != null){
                return false;
            }
        } else if(!name.equals(author.name)){
            return false;
        }
        if(surname == null){
            if(author.surname != null){
                return false;
            }
        } else if(!surname.equals(author.surname)){
            return false;
        }
        if(biography == null){
            if(author.biography != null){
                return false;
            }
        } else if(!biography.equals(author.biography)){
            return false;
        }
        if(photo == null){
            if(author.photo != null){
                return false;
            }
        } else if(!photo.equals(author.photo)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        return 31*id + ((name == null) ? 0 : name.hashCode()) + ((surname == null) ? 0 : surname.hashCode()) +
                ((biography == null) ? 0 : biography.hashCode()) + ((photo == null) ? 0 : photo.hashCode());
    }

    @Override
    public String toString(){
        return "AuthorID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Surname: " + surname + "\n" +
                "Biography: " + biography + "\n";
    }

}

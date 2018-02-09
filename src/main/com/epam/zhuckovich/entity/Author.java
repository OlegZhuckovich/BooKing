package com.epam.zhuckovich.entity;

import java.io.InputStream;

public class Author extends Entity{
    private String name;
    private String surname;
    private String biography;
    private InputStream photo;

    public Author(){}

    public String getName(){
        return name;
    }

    public String getSurname(){
        return this.surname;
    }

    public String getBiography(){ return biography; }

    public InputStream getPhoto(){
        return photo;
    }

    public static Builder newBuilder() {
        return new Author().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(int authorId) {
            Author.this.id = authorId;
            return this;
        }

        public Builder setName(String name) {
            Author.this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            Author.this.surname = surname;
            return this;
        }

        public Builder setBiography(String biography) {
            Author.this.biography = biography;
            return this;
        }

        public Builder setPhoto(InputStream photo) {
            Author.this.photo = photo;
            return this;
        }

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

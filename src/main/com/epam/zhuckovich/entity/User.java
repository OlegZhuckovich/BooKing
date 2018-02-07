package com.epam.zhuckovich.entity;

import java.io.InputStream;
import java.sql.Date;

public class User extends Entity{

    private String name;
    private String surname;
    private String email;
    private String password;
    private UserType userType;
    private Date registrationDate;
    private InputStream photo;
    private int booksOrdered;
    private Address address;

    private User(){}

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public UserType getUserType(){
        return userType;
    }

    public Date getRegistrationDate(){
        return registrationDate;
    }

    public InputStream getPhoto(){
        return photo;
    }

    public int getBooksOrdered() {
        return booksOrdered;
    }

    public Address getAddress() {
        return address;
    }

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public class Builder {

        private Builder() {}

        public Builder setId(int userId) {
            User.this.id = userId;
            return this;
        }

        public Builder setName(String name) {
            User.this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            User.this.surname = surname;
            return this;
        }

        public Builder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        public Builder setUserType(UserType userType) {
            User.this.userType = userType;
            return this;
        }

        public Builder setRegistrationDate(Date registrationDate){
            User.this.registrationDate = registrationDate;
            return this;
        }

        public Builder setPhoto(InputStream photo){
            User.this.photo = photo;
            return this;
        }

        public Builder setBooksOrdered(Integer booksOrdered){
            User.this.booksOrdered = booksOrdered;
            return this;
        }

        public Builder setAddress(Address address){
            User.this.address = address;
            return this;
        }

        public User build() {
            return User.this;
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
        User user = (User) object;
        if(id != user.id){
            return false;
        }
        if(name == null){
            if(user.name != null){
                return false;
            }
        } else if(!name.equals(user.name)){
            return false;
        }
        if(surname == null){
            if(user.surname != null){
                return false;
            }
        } else if(!surname.equals(user.surname)){
            return false;
        }
        if(email == null){
            if(user.email != null){
                return false;
            }
        } else if(!email.equals(user.email)){
            return false;
        }
        if(password == null){
            if(user.password != null){
                return false;
            }
        } else if(!password.equals(user.password)){
            return false;
        }
        if(userType == null){
            if(user.userType != null){
                return false;
            }
        } else if(userType != user.userType){
            return false;
        }
        if(registrationDate == null){
            if(user.registrationDate != null){
                return false;
            }
        } else if(!registrationDate.equals(user.registrationDate)){
            return false;
        }
        if(photo == null){
            if(user.photo != null){
                return false;
            }
        } else if(!photo.equals(user.photo)){
            return false;
        }
        if(booksOrdered != user.booksOrdered){
            return false;
        }
        if(address == null){
            if(user.address != null){
                return false;
            }
        } else if(!address.equals(user.address)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        return 31*id + ((name == null) ? 0 : name.hashCode()) + ((surname == null) ? 0 : surname.hashCode()) +
                ((email == null) ? 0 : email.hashCode()) + ((password == null) ? 0 : password.hashCode()) +
                ((userType == null) ? 0 : userType.hashCode()) + ((registrationDate == null) ? 0 : registrationDate.hashCode()) +
                ((photo == null) ? 0 : photo.hashCode()) + booksOrdered + ((address == null) ? 0 : address.hashCode());
    }

    @Override
    public String toString(){
        return "UserID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Surname: " + surname + "\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n" +
                "UserType: " + userType + "\n" +
                "RegistrationDate: " + registrationDate + "\n" +
                "BooksOrdered: " + booksOrdered + "\n" +
                "Address: " + address.toString() + "\n";
    }
}

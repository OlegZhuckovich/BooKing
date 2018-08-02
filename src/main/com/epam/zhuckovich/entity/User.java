package com.epam.zhuckovich.entity;

import java.io.InputStream;
import java.sql.Date;

/**
 * <p>Class that contains information about user.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Entity
 * @since       1.0
 */

public class User extends Entity{

    private String name;
    private String surname;
    private String email;
    private String password;
    private UserType userType;
    private Date registrationDate;
    private InputStream photo;
    private Address address;

    /**
     * Private constructor
     */

    private User(){}

    /**
     * <p>Returns name of user.</p>
     * @return name of user
     */

    public String getName(){
        return name;
    }

    /**
     * <p>Returns surname of user.</p>
     * @return surname of user
     */

    public String getSurname(){
        return surname;
    }

    /**
     * <p>Returns email of user.</p>
     * @return email of user
     */

    public String getEmail(){
        return email;
    }

    /**
     * <p>Returns password of user.</p>
     * @return password of user
     */

    public String getPassword(){
        return password;
    }

    /**
     * <p>Returns type of user.</p>
     * @return type of user
     */

    public UserType getUserType(){
        return userType;
    }

    /**
     * <p>Returns user registration date.</p>
     * @return user registration date
     */

    public Date getRegistrationDate(){
        return registrationDate;
    }

    /**
     * <p>Returns photo of user.</p>
     * @return photo of user
     */

    public InputStream getPhoto(){
        return photo;
    }

    /**
     * <p>Returns user address.</p>
     * @return user address
     */

    public Address getAddress() {
        return address;
    }

    /**
     * <p>Returns the builder to construct user</p>
     * @return the user builder
     */


    public static Builder newBuilder() {
        return new User().new Builder();
    }

    /**
     * Inner class to construct user object
     */

    public class Builder {

        /**
         * Private constructor
         */

        private Builder() {}

        /**
         * <p>Sets the id of the user</p>
         * @param userId number value of author id
         * @return the builder to construct user object
         */

        public Builder setId(int userId) {
            User.this.id = userId;
            return this;
        }

        /**
         * <p>Sets the name of the user</p>
         * @param name name of user
         * @return the builder to construct user object
         */

        public Builder setName(String name) {
            User.this.name = name;
            return this;
        }

        /**
         * <p>Sets the surname of the user</p>
         * @param surname surname of user
         * @return the builder to construct user object
         */

        public Builder setSurname(String surname) {
            User.this.surname = surname;
            return this;
        }

        /**
         * <p>Sets the email of the user</p>
         * @param email email of user
         * @return the builder to construct user object
         */

        public Builder setEmail(String email) {
            User.this.email = email;
            return this;
        }

        /**
         * <p>Sets the password of the user</p>
         * @param password email of user
         * @return the builder to construct user object
         */

        public Builder setPassword(String password) {
            User.this.password = password;
            return this;
        }

        /**
         * <p>Sets the type of the user</p>
         * @param userType type of user
         * @return the builder to construct user object
         */

        public Builder setUserType(UserType userType) {
            User.this.userType = userType;
            return this;
        }

        /**
         * <p>Sets the user registrationDate</p>
         * @param registrationDate registrationDate of user
         * @return the builder to construct user object
         */

        public Builder setRegistrationDate(Date registrationDate){
            User.this.registrationDate = registrationDate;
            return this;
        }

        /**
         * <p>Sets the photo of the user</p>
         * @param photo photo of user
         * @return the builder to construct user object
         */

        public Builder setPhoto(InputStream photo){
            User.this.photo = photo;
            return this;
        }

        /**
         * <p>Sets the address of the user</p>
         * @param address address of user
         * @return the builder to construct user object
         */

        public Builder setAddress(Address address){
            User.this.address = address;
            return this;
        }

        /**
         * <p>Build the user object</p>
         * @return the user object
         */

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
                ((photo == null) ? 0 : photo.hashCode()) + ((address == null) ? 0 : address.hashCode());
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
                "Address: " + address.toString() + "\n";
    }
}

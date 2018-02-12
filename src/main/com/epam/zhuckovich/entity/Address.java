package com.epam.zhuckovich.entity;

/**
 * <p>Class that contains information about the address of program user.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         Entity
 * @since       1.0
 */

public class Address extends Entity {
    private String city;
    private String street;
    private int house;
    private int telephoneNumber;

    /**
     * Private constructor
     */

    private Address() {}

    /**
     * <p>Returns name of user city.</p>
     * @return name of user city
     */

    public String getCity() {
        return city;
    }

    /**
     * <p>Returns name of user street.</p>
     * @return name of user street
     */

    public String getStreet() {
        return street;
    }

    /**
     * <p>Returns number of user house.</p>
     * @return number of user house
     */

    public int getHouse() {
        return house;
    }

    /**
     * <p>Returns user telephone number</p>
     * @return value of user telephone number
     */

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    /**
     * <p>Returns the builder to construct address</p>
     * @return the address builder
     */

    public static Builder newBuilder() {
        return new Address().new Builder();
    }

    /**
     * Inner class to construct address object
     */

    public class Builder {

        /**
         * Private constructor
         */

        private Builder() {}

        /**
         * <p>Sets the id of the address</p>
         * @param addressId number value of address id
         * @return the builder to construct address object
         */

        public Builder setId(int addressId) {
            Address.this.id = addressId;
            return this;
        }

        /**
         * <p>Sets the city of the address</p>
         * @param city city of address
         * @return the builder to construct address object
         */

        public Builder setCity(String city) {
            Address.this.city = city;
            return this;
        }

        /**
         * <p>Sets the street of the address</p>
         * @param street street of address
         * @return the builder to construct address object
         */

        public Builder setStreet(String street) {
            Address.this.street = street;
            return this;
        }

        /**
         * <p>Sets the house of the address</p>
         * @param house house of address
         * @return the builder to construct address object
         */

        public Builder setHouse(int house) {
            Address.this.house = house;
            return this;
        }

        /**
         * <p>Sets the user telephone number</p>
         * @param telephoneNumber user telephone number
         * @return the builder to construct address object
         */

        public Builder setTelephoneNumber(int telephoneNumber) {
            Address.this.telephoneNumber = telephoneNumber;
            return this;
        }

        /**
         * <p>Build the address object</p>
         * @return the address object
         */

        public Address build() {
            return Address.this;
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
        Address address = (Address) object;
        if(id != address.id){
            return false;
        }
        if(city == null){
            if(address.city != null){
                return false;
            }
        } else if(!city.equals(address.city)){
            return false;
        }
        if(street == null){
            if(address.street != null){
                return false;
            }
        } else if(!street.equals(address.street)){
            return false;
        }
        if(house != address.house){
            return false;
        }
        return telephoneNumber == address.telephoneNumber;
    }

    @Override
    public int hashCode(){
        return 31*id + ((city == null) ? 0 : city.hashCode()) + ((street == null) ? 0 : street.hashCode()) + house + telephoneNumber;
    }

    @Override
    public String toString(){
        return "AddressID: " + id + "\n" +
                "City: " + city + "\n" +
                "Street: " + street + "\n" +
                "House: " + house + "\n" +
                "TelephoneNumber: " + telephoneNumber + "\n";
    }

}

package com.sogeti.smartshelf.model;

import java.util.Iterator;
import java.util.List;
/**
 *
 * @author fabdin
 */
public class User {
    String firstName;
    String lastName;
    String username;
    String address;
    String city;
    String State;
    String country;
    int zipCode;
    Household household;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
    

    @Override
    public String toString() {

          String str =  "{ id: "+username+",firstName: \"" + firstName + "\",lastName: \"" + lastName + "\",username: \"" + username 
                  + "\", address: \"" + address + "\", city: " + city + ", state: " + State + ", country: " + country 
                  + ", zipCode: " + zipCode + ", household" + household.toString() + " }";
          return str;
    
    }
    
}

package com.sogeti.smartshelf.model;

/**
 *
 * @author fabdin
 */
public class User {
    String firstName;
    String lastName;
    String username;

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

    @Override
    public String toString() {

          return "{ id: "+username+",firstName: " + firstName + ",lastName: " + lastName + ",username: " + username +"}";
    }
    
}

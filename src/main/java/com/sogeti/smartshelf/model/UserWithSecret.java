package com.sogeti.smartshelf.model;

/**
 *
 * @author fabdin
 */
public class UserWithSecret extends User{
    
    
    String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {

          String str =  "{ \"firstName\": " + firstName + "\",lastName: \"" +lastName + "\",username: \"" + username 
                  + "\", address\": \"" + address + "\", city: " + city + ", state: " + State + ", country: " + country 
                  + ", zipCode: " + zipCode + ", household" + household.toString() + " }";
          return str;
    
    }
    
    
}

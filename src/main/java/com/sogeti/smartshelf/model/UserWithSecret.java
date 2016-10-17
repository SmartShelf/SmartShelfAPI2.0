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
    
    
    
}

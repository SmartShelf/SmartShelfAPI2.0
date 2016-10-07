package com.sogeti.smartshelf.model;

import java.util.List;

/**
 *
 * @author fabdin
 */
public class UserDoc  extends Doc{
    

    User user;    
    List<Shelf> shelfs;
//
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Shelf> getShelfs() {
        return shelfs;
    }

    public void setShelfs(List<Shelf> shelfs) {
        this.shelfs = shelfs;
    }
     
}

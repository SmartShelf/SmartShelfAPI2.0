package com.sogeti.smartshelf.model;

import java.util.List;

/**
 *
 * @author fabdin
 */
public class UserDoc  extends Doc{
    

    UserWithSecret user;    
    List<Shelf> shelfs;

    public UserWithSecret getUser() {
        return user;
    }

    public void setUser(UserWithSecret user) {
        this.user = user;
    }

    public List<Shelf> getShelfs() {
        return shelfs;
    }

    public void setShelfs(List<Shelf> shelfs) {
        this.shelfs = shelfs;
    }
     
}

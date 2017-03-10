package com.sogeti.smartshelf.model;

import java.util.Iterator;
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
    
    @Override
    public String toString() {

          String str =  "{ \"_id\": \""+this._id+"\",\"_rev\": \"" + this._rev + "\", \"user: " + user.toString() + ", shelfs: [";
          boolean isFirst = true;
          for(Iterator<Shelf> s = shelfs.iterator(); s.hasNext(); ) {
            if (!isFirst)
            {
                str = str + ", ";
            }
            else
            {
                isFirst = false;
            }
                
            Shelf sc = s.next();
            str = str + sc.toString();
            
          }
          str = str + "] } ";
          return str;
    }
     
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sogeti.smartshelf.model;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author johnmart2k
 */
public class Household {
    int size;
    List<Member> members;
    
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
    

    @Override
    public String toString() {

          String hhStr =  "{ members: [";
          boolean isFirst = true;
          for(Iterator<Member> m = members.iterator(); m.hasNext(); ) {
            if (!isFirst)
            {
                hhStr = hhStr + ", ";
            }
            else
            {
                isFirst = false;
            }
                
            Member mem = m.next();
            hhStr = hhStr + mem.toString();
            
          }
          hhStr = hhStr + "] }";
          return hhStr;
    }
}

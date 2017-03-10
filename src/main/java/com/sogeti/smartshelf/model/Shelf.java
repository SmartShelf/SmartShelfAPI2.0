package com.sogeti.smartshelf.model;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author fabdin
 */
public class Shelf {
    String id;
    String name;
    List<Scale> scales;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Scale> getScales() {
        return scales;
    }

    public void setScales(List<Scale> scales) {
        this.scales = scales;
    }
    @Override
    public String toString() {

          String hhStr =  "{ id: " + id + ", name: " + name + " scales: [";
          boolean isFirst = true;
          for(Iterator<Scale> s = scales.iterator(); s.hasNext(); ) {
            if (!isFirst)
            {
                hhStr = hhStr + ", ";
            }
            else
            {
                isFirst = false;
            }
                
            Scale sc = s.next();
            hhStr = hhStr + sc.toString();
            
          }
          hhStr = hhStr + "] }";
          return hhStr;
    }
    
    
}

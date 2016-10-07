package com.sogeti.smartshelf.model;

import java.util.List;

/**
 *
 * @author fabdin
 */
public class ItemsDoc extends Doc{
    
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    
    
}

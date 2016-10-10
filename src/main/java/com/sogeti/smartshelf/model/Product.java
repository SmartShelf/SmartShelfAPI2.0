package com.sogeti.smartshelf.model;

/**
 *
 * @author fabdin
 */
public class Product {
     
    Integer weight;
    String name;
    String barcode;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    
}

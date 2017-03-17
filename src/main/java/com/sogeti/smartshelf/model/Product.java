package com.sogeti.smartshelf.model;

/**
 *
 * @author fabdin
 */
public class Product {
    
    String id;
    String name;
    Integer weight;
    Integer packageWeight;
    
    String barcode;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(Integer packageWeight) {
        this.packageWeight = packageWeight;
    }

}

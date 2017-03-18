package com.sogeti.smartshelf.model;

/**
 *
 * @author fabdin
 */
public class Product {
    
    String id;
    String name;
    Double weight;
    Double packageWeight;
    Double avgDailyUse;
    String barcode;
    String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
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

    public Double getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(Double packageWeight) {
        this.packageWeight = packageWeight;
    }
    
    public Double getAvgDailyUse() {
    	return avgDailyUse;
    }
    
    public void setAvgDailyUse(Double avgDailyUse) {
		this.avgDailyUse = avgDailyUse;
	}
}

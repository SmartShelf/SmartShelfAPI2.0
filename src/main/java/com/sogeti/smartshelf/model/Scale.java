package com.sogeti.smartshelf.model;

import java.util.Iterator;

/**
 *
 * @author fabdin
 */
public class Scale {

    String id;
    Double weight;
    String productId;
    Integer persentage;
    String registerDate;
    String updateDate;
    String estimatedDate;
    Integer useDays;

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getPersentage() {
        return persentage;
    }

    public void setPersentage(Integer persentage) {
        this.persentage = persentage;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(String estimatedDate) {
        this.estimatedDate = estimatedDate;
    }
    
    public Integer getUseDays() {
    	return useDays;
    }
    
    public void setUseDays(Integer useDays) {
    	this.useDays = useDays;
    }
    
    @Override
    public String toString() {

          return "{ id: "+id+",weight: " + weight + ",productId: " +productId+ 
                  ",persentage: " +persentage+ ",productId: " +registerDate +
                  ",updateDate: " +updateDate+",updateDate: " +estimatedDate+
                  ",useDays: " + useDays + "}";
    }
}

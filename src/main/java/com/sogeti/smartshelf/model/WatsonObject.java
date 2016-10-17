package com.sogeti.smartshelf.model;

/**
 *
 * @author fabdin
 */
public class WatsonObject {
    
    User user;
    Product product;
    String weight;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}

package com.sogeti.smartshelf.model;

import java.util.List;

/**
 *
 * @author fabdin
 */
public class ProductsDoc extends Doc{
    
    List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    
}

package com.sogeti.smartshelf;

import com.sogeti.smartshelf.model.Product;

/**
 *
 * @author fabdin
 */
public class MathUtils {
    
    
    public static Integer getPersentage(Product product, Integer currentWeight){
        
        Integer weightWithoutPackaging = product.getWeight() - product.getPackageWeight();
        Integer currentWeightWithoutPackaging = currentWeight - product.getPackageWeight();
        
        Integer persentage = (currentWeightWithoutPackaging/weightWithoutPackaging)*100;
        
        return persentage;
    }
    
}

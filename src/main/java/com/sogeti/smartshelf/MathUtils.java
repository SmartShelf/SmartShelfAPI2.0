package com.sogeti.smartshelf;

import com.sogeti.smartshelf.model.Product;

/**
 *
 * @author fabdin
 */
public class MathUtils {
    
    
    public static Integer getPersentage(Product product, Integer currentWeight){
        
        if(product == null) return 0;
        Integer weightWithoutPackaging = product.getWeight() - product.getPackageWeight();
        Integer currentWeightWithoutPackaging = currentWeight - product.getPackageWeight();
        
        Integer persentage = (currentWeightWithoutPackaging/weightWithoutPackaging)*100;
        
        return persentage;
    }
    
    public static Integer normalizeWeight(String pressure, String name){
        
        Double d=new Double(pressure);
        
        
        if(d>500000){
            d=0.00;
        }
        
        Double result = 0.0;
        
        switch (name) {
            case "scale1":
                result=d*3.48;
                
                break;
            case "scale2":
                result=d*3.82;
                break;
        }
        
         

        return result.intValue();
    }
    
}

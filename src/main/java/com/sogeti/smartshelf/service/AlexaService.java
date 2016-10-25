package com.sogeti.smartshelf.service;

import com.sogeti.smartshelf.model.Message;
import com.sogeti.smartshelf.model.Product;
import com.sogeti.smartshelf.model.Scale;
import com.sogeti.smartshelf.model.Shelf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author fabdin
 */
@Service
public class AlexaService {

    @Autowired
    DataService dataService;

    public Message getStatus(Shelf shelf) {        
        String message = "";

        if (shelf.getScales().size() > 0) {
            
//            message = "Your SmartShelf " + ((shelf.getName() == null)? "":shelf.getName()) + " is tracking " + shelf.getScales().size() + " products now";
            for (Scale s : shelf.getScales()) {
                Product p=dataService.getProduct(s.getProductId());
                String prodName=""; 
                if(p!=null) prodName = p.getName();
                message += "The "+prodName+" is 0 percent ";
                
                if(shelf.getScales().indexOf(s) < shelf.getScales().size()-1){
                    message += "and ";
                }
            }
            
        } else {
            message = "There are no products registered at this point, use the mobile application and register your products";
            
        }

        return new Message(message);
    }

}

package com.sogeti.smartshelf.service;

import com.sogeti.smartshelf.model.Message;
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
                message += "the "+dataService.getProduct(s.getProductId()).getName()+" is "+" percent ";
                
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

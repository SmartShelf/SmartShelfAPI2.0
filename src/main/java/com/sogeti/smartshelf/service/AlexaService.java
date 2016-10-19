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
        String message = "No Message";

        if (shelf.getScales().size() > 0) {
            
            message = "Your SmartShelf " + ((shelf.getName() == null)? "":shelf.getName()) + " is tracking " + shelf.getScales().size() + " products now<break time='1s'>";
            for (Scale s : shelf.getScales()) {
                message += "The "+dataService.getProduct(s.getProductId()).getName()+" is "+s.getWeight()+" pounds <break time='1s'/>";
                
            }
        } else {
            message = "There are no products registered at this point, use the mobile application and register your products";
        }

        return new Message(message);
    }

}

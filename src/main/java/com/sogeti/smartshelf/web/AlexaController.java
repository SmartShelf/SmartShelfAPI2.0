package com.sogeti.smartshelf.web;

import com.sogeti.smartshelf.model.Message;
import com.sogeti.smartshelf.model.UserDoc;
import com.sogeti.smartshelf.service.AlexaService;
import com.sogeti.smartshelf.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fabdin
 */
@RestController
@RequestMapping("alexa")
@Api(value = "Alexa Service", produces = "application/json")
public class AlexaController {
    
    
    @Autowired
    DataService dataService;
    
    @Autowired
    AlexaService alexaService;
    
    
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ApiOperation(value = "Return the summary result for Alexa to say", produces = "application/json")
    public ResponseEntity getShelfStatus(){
        

        UserDoc user = dataService.getUser();
        if(user==null){
            user=dataService.findUser("fadiabdeen");
        }
        
        Message message = alexaService.getStatus(user.getShelfs().get(0));
        
        return new ResponseEntity(message,HttpStatus.OK);
        
    }
    
}

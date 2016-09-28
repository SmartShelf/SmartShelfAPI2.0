package com.sogeti.smartshelf.web;

import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author fabdin
 */
@Controller
@RequestMapping("main")
public class MainController {
    
    
    
        @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity isAuthenticated(HttpSession session) {
        
        
        System.out.println("login");
        if(true){
            return new ResponseEntity(HttpStatus.OK);
        }else { 
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    
}

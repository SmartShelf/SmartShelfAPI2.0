package com.sogeti.smartshelf.web;

import com.sogeti.smartshelf.model.Scale;
import com.sogeti.smartshelf.model.User;

import com.sogeti.smartshelf.model.Product;
import com.sogeti.smartshelf.service.DataService;
import java.net.MalformedURLException;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fabdin
 */
@RestController
@RequestMapping("main")
@Api(value = "Main Service", description = "", produces = "application/json")
public class MainController {
    
    @Autowired
    DataService test;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
   @ApiOperation(value = "login", produces = "application/json")
    public ResponseEntity isAuthenticated(HttpSession session) {
        
        test.getUser("");
        

        System.out.println("login");
        if (true) {
            return new ResponseEntity("Welcome to Sogeti IoT SmartShelf",HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    
        @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResponseEntity getUserInfo(HttpSession session) throws MalformedURLException {
        
        
        
        test.testConnection();
        

        System.out.println("UserInfo");
        
        User user=new User();
        user.setFirstName("Sogeti Guy");
        user.setUsername("abdc");
        
        return new ResponseEntity(user, HttpStatus.IM_USED);
    }
    
    
        @RequestMapping(value = "/scaleInfo", method = RequestMethod.GET)
    public ResponseEntity getScaleInfo(HttpSession session) throws MalformedURLException {
        

        System.out.println("getScaleInfo");
        
        Scale s=new Scale();
        Product p = new Product();
        p.setName("Dog Food");
        p.setFullWeight(50);
        
        s.setProduct(p);
        s.setWeight(25);
        return new ResponseEntity(s, HttpStatus.OK);
    }
        
}

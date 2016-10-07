package com.sogeti.smartshelf.web;

import com.sogeti.smartshelf.model.Scale;
import com.sogeti.smartshelf.model.User;

import com.sogeti.smartshelf.model.Item;
import com.sogeti.smartshelf.model.UserDoc;
import com.sogeti.smartshelf.service.DataService;
import java.net.MalformedURLException;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
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
    DataService dataService;
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
   @ApiOperation(value = "Login service that accepts a username and password and return the user info", produces = "application/json")
    public ResponseEntity login(
            HttpSession session,
            @ApiParam(name="username", value="Username") @RequestParam(value="username", required=true ) String username,
            @ApiParam(name="password", value="Password") @RequestParam(value="password", required=true ) String password) {
        
        UserDoc user = dataService.findUser(username);
        
        if(user.getUser().getPassword().equals(password)){
            return new ResponseEntity(user.getUser(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        
        
    }
    
    //shelfs list
    
    //shelf update
    
    //shelf get
    
    //scale update
   
    //item lookup
    
    //item list
    
    //scale update
    
    
}

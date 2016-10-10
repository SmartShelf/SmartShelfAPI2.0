package com.sogeti.smartshelf.web;

import com.sogeti.smartshelf.model.UserDoc;
import com.sogeti.smartshelf.service.DataService;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import javax.ws.rs.PathParam;
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
            return new ResponseEntity("Unauthorized",HttpStatus.UNAUTHORIZED);
        }
        
        
    }
    
    //shelfs list
    
    @RequestMapping(value = "/shelfs", method = RequestMethod.GET)
    @ApiOperation(value = "List of shelfs", produces = "application/json")
    public ResponseEntity shelfs() {
        
        return new ResponseEntity(HttpStatus.OK);
    }    
    
    //shelf add
    
    @RequestMapping(value = "/shelf", method = RequestMethod.PUT)
    @ApiOperation(value = "Add new shelf", produces = "application/json")
    public ResponseEntity addShelf() {
        
        return new ResponseEntity(HttpStatus.OK);
    }    
    
    //shelf update
    
    @RequestMapping(value = "/shelf", method = RequestMethod.POST)
    @ApiOperation(value = "Update shelf", produces = "application/json")
    public ResponseEntity updateShelf() {
        
        return new ResponseEntity(HttpStatus.OK);
    }    
    
    //shelf get
    @RequestMapping(value = "/shelf/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get Shelf", produces = "application/json")
    public ResponseEntity getShelf(@ApiParam(name="id") @PathParam(value="id") String id) {
        
        return new ResponseEntity(HttpStatus.OK);
    }  
    
    //product lookup
    
    @RequestMapping(value = "/productLookup", method = RequestMethod.GET)
    @ApiOperation(value = "Search for products", produces = "application/json")
    public ResponseEntity getProducts(@ApiParam(name="searchString") @PathParam(value="searchString") String searchString) {
        
        return new ResponseEntity(HttpStatus.OK);
    } 
    
    //product list
    
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ApiOperation(value = "List of products", produces = "application/json")
    public ResponseEntity getProducts() {
        
        return new ResponseEntity(HttpStatus.OK);
    } 
    
    //scale update
    
     @RequestMapping(value = "/scale", method = RequestMethod.PUT)
    @ApiOperation(value = "Update Scale from device", produces = "application/json")
    public ResponseEntity updateScale() {
        
        //need to figure out the scale id/macId
        
        return new ResponseEntity(HttpStatus.OK);
    } 
    
}

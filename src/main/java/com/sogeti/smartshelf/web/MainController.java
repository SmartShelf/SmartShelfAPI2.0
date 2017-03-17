package com.sogeti.smartshelf.web;

import com.sogeti.smartshelf.MathUtils;
import com.sogeti.smartshelf.model.*;
import com.sogeti.smartshelf.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import javax.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fabdin
 */
@RestController
@RequestMapping("main")
@Api(value = "Main Service", produces = "application/json")
public class MainController {

    @Autowired
    DataService dataService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiOperation(value = "Login service that accepts a username and password and return the user info", produces = "application/json")
    public ResponseEntity login(
            HttpSession session,
            @ApiParam(name = "username", value = "Username") @RequestParam(value = "username", required = true) String username,
            @ApiParam(name = "password", value = "Password") @RequestParam(value = "password", required = true) String password) {

        UserDoc user = dataService.findUser(username);
//        if (user.getUser() != null)
//        {
//            if (user.getUser().getHousehold() != null)
//            {
//                System.out.println(user.getUser().getHousehold().getMembers().get(0).getAge());
//            }
//        }
        if (user != null && user.getUser().getPassword().equals(password)) {
           // System.out.println(user.toString());
            return new ResponseEntity((User) user.getUser(), HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

    }
    
    @RequestMapping(value = "/loginGetDoc", method = RequestMethod.GET)
    @ApiOperation(value = "Login service that accepts a username and password and return the user info", produces = "application/json")
    public ResponseEntity loginGetDoc(
            HttpSession session,
            @ApiParam(name = "username", value = "Username") @RequestParam(value = "username", required = true) String username,
            @ApiParam(name = "password", value = "Password") @RequestParam(value = "password", required = true) String password) {

        UserDoc user = dataService.findUser(username);

        if (user != null && user.getUser().getPassword().equals(password)) {
            System.out.println(user.toString());
            return new ResponseEntity((UserDoc) user, HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

    }

    //shelfs list
    @RequestMapping(value = "/shelfs", method = RequestMethod.GET)
    @ApiOperation(value = "List of shelfs", produces = "application/json")
    public ResponseEntity shelfs(@RequestParam(value = "username", required = false) String username) {
        
        System.out.println("Shelfs .... ");

        List<Shelf> shelfs = dataService.getShelfs();

        return new ResponseEntity(shelfs, HttpStatus.OK);
    }
    
    //shelves list
    @RequestMapping(value = "/shelves", method = RequestMethod.GET)
    @ApiOperation(value = "List of shelves", produces = "application/json")
    public ResponseEntity shelves(@RequestParam(value = "username", required = false) String username) {
        
        System.out.println("Shelves .... ");

        List<Shelf> shelfs = dataService.getShelves();

        return new ResponseEntity(shelfs, HttpStatus.OK);
    }

    //user add
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    @ApiOperation(value = "Add new user", produces = "application/json")
    public ResponseEntity addUser(@RequestBody User user, @RequestParam(value = "username", required = false) String username) {
        
        
        Scale s1 = new Scale();
        s1.setId("1");
        Scale s2 = new Scale();
        s2.setId("2");
        
        List<Scale> scales = new ArrayList();
        scales.add(s1);
        scales.add(s2);
        
        //shelf.setScales(scales);
        
//        dataService.addShelf(shelf);
        
        
        return null;
        //return new ResponseEntity(HttpStatus.OK);
    }
    
    //shelf add
    @RequestMapping(value = "/shelf", method = RequestMethod.PUT)
    @ApiOperation(value = "Add new shelf", produces = "application/json")
    public ResponseEntity addShelf(@RequestBody Shelf shelf, @RequestParam(value = "username", required = false) String username) {

        Scale s1 = new Scale();
        s1.setId("1");
        Scale s2 = new Scale();
        s2.setId("2");
        
        List<Scale> scales = new ArrayList();
        scales.add(s1);
        scales.add(s2);
        
        shelf.setScales(scales);
        
        dataService.addShelf(shelf);
        
        

        return new ResponseEntity(HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/shelf/{shelfId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Update shelf", produces = "application/json")
    public ResponseEntity deleteShelf(
            @RequestParam(value = "username", required = false) String username,
            @ApiParam(name = "shelfId", required = true) @PathVariable(value = "shelfId") String shelfId
    ) {

        dataService.deleteShelf(shelfId);

        return new ResponseEntity(HttpStatus.OK);
    }
    

    //shelf update
    @RequestMapping(value = "/shelf", method = RequestMethod.POST)
    @ApiOperation(value = "Update shelf", produces = "application/json")
    public ResponseEntity updateShelf(@RequestBody Shelf shelf, @RequestParam(value = "username", required = false) String username) {

        dataService.updateShelf(shelf);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    
    //shelf update
    @RequestMapping(value = "UpdateShelf/{shelfId}/{shelfName}", method = RequestMethod.POST)
    @ApiOperation(value = "Update shelf", produces = "application/json")
    public ResponseEntity updateShelfSimple(HttpSession session,
            @ApiParam(name = "shelfId", value = "shelfId") @RequestParam(value = "shelfId", required = true) String shelfId,
            @ApiParam(name = "shelfName", value = "shelfName") @RequestParam(value = "shelfName", required = true) String shelfName) {
        Shelf shelf = dataService.getShelf(shelfId);
        shelf.setName(shelfName);
        
        dataService.updateShelf(shelf);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "product/register/shelf/{shelfId}/scale/{scaleId}/product/{productId}", method = RequestMethod.POST)
    @ApiOperation(value = "Register new product on a scale", produces = "application/json")
    public ResponseEntity registerProduct(
            @RequestParam(value = "username", required = false) String username,
            @ApiParam(name = "shelfId", required = true) @PathVariable(value = "shelfId") String shelfId,
            @ApiParam(name = "scaleId", required = true) @PathVariable(value = "scaleId") String scaleId,
            @ApiParam(name = "productId", required = true) @PathVariable(value = "productId") String productId) {

        Shelf shelf = dataService.getShelf(shelfId);

        for (Scale scale : shelf.getScales()) {
            if (scale.getId().equals(scaleId)) {

                dataService.updateProduct(scale, productId);

            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "product/unregister/shelf/{shelfId}/scale/{scaleId}", method = RequestMethod.POST)
    @ApiOperation(value = "Unregister a product from a scale and archive the data", produces = "application/json")
    public ResponseEntity unRegisterProduct(
            @RequestParam(value = "username", required = false) String username,
            @ApiParam(name = "shelfId", required = true) @PathVariable(value = "shelfId") String shelfId,
            @ApiParam(name = "scaleId", required = true) @PathVariable(value = "scaleId") String scaleId) {

        Shelf shelf = dataService.getShelf(shelfId);

        for (Scale scale : shelf.getScales()) {
            if (scale.getId().equals(scaleId)) {

                dataService.clearScale(scale);

            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    //shelf get
    @RequestMapping(value = "/shelf/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get Shelf", produces = "application/json")
    public ResponseEntity getShelf(
            @ApiParam(name = "id", required = true) @PathVariable(value = "id") String id,
            @RequestParam(value = "username", required = false) String username) {

        Shelf shelf = dataService.getShelf(id);

        dataService.getScales(shelf.getId());
        return new ResponseEntity(shelf, HttpStatus.OK);
    }

    //product lookup
    @RequestMapping(value = "/productLookup", method = RequestMethod.GET)
    @ApiOperation(value = "Search for products", produces = "application/json")
    public ResponseEntity getProducts(@ApiParam(name = "searchString") @PathParam(value = "searchString") String searchString) {

        List<Product> products = dataService.findProduct(searchString);
        return new ResponseEntity(products, HttpStatus.OK);
    }

    //product list
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ApiOperation(value = "List of products", produces = "application/json")
    public ResponseEntity getProducts() {

        List<Product> products = dataService.getProducts();

        return new ResponseEntity(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/scale", method = RequestMethod.POST)
    @ApiOperation(value = "Update Scale", produces = "application/json")
    public ResponseEntity updateScale(
            @RequestParam(value = "username", required = false) String username,
            @RequestBody Scale scale
    ) {

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/weightsUpdate/{deviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "Update Weights", produces = "application/json")
    public ResponseEntity updateWeights(
            @PathVariable(value = "deviceId") String deviceId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "scale1") String scale1,
            @RequestParam(value = "scale2") String scale2,
            @RequestBody(required = false) Map<String,String> scales
    ) {
        
        
        System.out.println("DevicdID : " + deviceId);
        
        Integer s1=MathUtils.normalizeWeight(scale1, "scale1");
        Integer s2=MathUtils.normalizeWeight(scale2, "scale2");
        
        System.out.println("scale1 "+s1);
        System.out.println("scale2 "+s2);   
        
       // dataService.updateWeights(deviceId,"1",s1);
       // dataService.updateWeights(deviceId,"2",s2);
        dataService.updateShelfWeights_AddNew(deviceId, "1", s1, "2", s2);
//        for(String key:scales.keySet()){
//            System.out.println(key+" : "+scales.get(key));
//        }
        

        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/weightsUpdateAddNew/{deviceId}", method = RequestMethod.POST)
    @ApiOperation(value = "Update Weights Add New", produces = "application/json")
    public ResponseEntity updateWeightsAddNew(
            @PathVariable(value = "deviceId") String deviceId,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "scale1") String scale1,
            @RequestParam(value = "scale2") String scale2,
            @RequestBody(required = false) Map<String,String> scales
    ) {
        
        
        System.out.println("DevicdID : " + deviceId);
        
        Integer s1=MathUtils.normalizeWeight(scale1, "scale1");
        Integer s2=MathUtils.normalizeWeight(scale2, "scale2");
        
        System.out.println("scale1 "+s1);
        System.out.println("scale2 "+s2);   
        
        //dataService.updateWeights(deviceId,"1",s1);
        //dataService.updateWeights(deviceId,"2",s2);
        dataService.updateShelfWeights_AddNew(deviceId, "1", s1, "2", s2);
//        for(String key:scales.keySet()){
//            System.out.println(key+" : "+scales.get(key));
//        }
        

        return new ResponseEntity(HttpStatus.OK);
    }

}

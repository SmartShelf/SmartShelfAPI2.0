package com.sogeti.smartshelf.web;

import com.cloudant.client.api.model.Response;
import com.sogeti.smartshelf.model.Product;
import com.sogeti.smartshelf.model.Scale;
import com.sogeti.smartshelf.model.Shelf;
import com.sogeti.smartshelf.model.User;
import com.sogeti.smartshelf.model.UserDoc;
import com.sogeti.smartshelf.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

        if (user != null && user.getUser().getPassword().equals(password)) {
            return new ResponseEntity((User) user.getUser(), HttpStatus.OK);
        } else {
            return new ResponseEntity("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

    }

    //shelfs list
    @RequestMapping(value = "/shelfs", method = RequestMethod.GET)
    @ApiOperation(value = "List of shelfs", produces = "application/json")
    public ResponseEntity shelfs(@RequestParam(value = "username", required = false) String username) {

        List<Shelf> shelfs = dataService.getShelfs();

        return new ResponseEntity(shelfs, HttpStatus.OK);
    }

    //shelf add
    @RequestMapping(value = "/shelf", method = RequestMethod.PUT)
    @ApiOperation(value = "Add new shelf", produces = "application/json")
    public ResponseEntity addShelf(@RequestBody Shelf shelf, @RequestParam(value = "username", required = false) String username) {

        Response result = dataService.addShelf(shelf);

        return new ResponseEntity(HttpStatus.valueOf(result.getStatusCode()));
    }

    //shelf update
    @RequestMapping(value = "/shelf", method = RequestMethod.POST)
    @ApiOperation(value = "Update shelf", produces = "application/json")
    public ResponseEntity updateShelf(@RequestBody Shelf shelf, @RequestParam(value = "username", required = false) String username) {

        Response result = dataService.updateShelf(shelf);

        return new ResponseEntity(HttpStatus.valueOf(result.getStatusCode()));
    }

    @RequestMapping(value = "product/register/shelf/{shelfId}/scale/{scaleId}/product/{productId}", method = RequestMethod.POST)
    @ApiOperation(value = "Register new product on a scale", produces = "application/json")
    public ResponseEntity registerProduct(
            @RequestBody Scale scale, 
            @RequestParam(value = "username", required = false) String username,
            @ApiParam(name = "shelfId") @PathVariable(value = "shelfId") String shelfId,
            @ApiParam(name = "scaleId") @PathVariable(value = "scaleId") String scaleId,
            @ApiParam(name = "productId") @PathVariable(value = "productId") String productId) {

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "product/unregister/shelf/{shelfId}/scale/{scaleId}", method = RequestMethod.POST)
    @ApiOperation(value = "Unregister a product from a scale and archive the data", produces = "application/json")
    public ResponseEntity unRegisterProduct(
            @RequestBody Scale scale, 
            @RequestParam(value = "username", required = false) String username,
            @ApiParam(name = "shelfId") @PathVariable(value = "shelfId") String shelfId,
            @ApiParam(name = "scaleId") @PathVariable(value = "scaleId") String scaleId) {

        return new ResponseEntity(HttpStatus.OK);
    }

    //shelf get
    @RequestMapping(value = "/shelf/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get Shelf", produces = "application/json")
    public ResponseEntity getShelf(
            @ApiParam(name = "id") @PathVariable(value = "id") String id, 
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
            @RequestBody Map<String,String> scales) {

        return new ResponseEntity(HttpStatus.OK);
    }

}

package com.sogeti.smartshelf.service;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.org.lightcouch.CouchDbException;
import com.sogeti.smartshelf.model.Product;
import com.sogeti.smartshelf.model.ProductsDoc;
import com.sogeti.smartshelf.model.Scale;
import com.sogeti.smartshelf.model.Shelf;
import com.sogeti.smartshelf.model.UserDoc;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author fabdin
 */
@Service
public class DataService {

    private CloudantClient client;
    private Database db;
    private UserDoc user;

    public DataService() {

        client = ClientBuilder.account("b3a3013d-c4b6-43be-bd87-5b066e097bc5-bluemix")
                .username("reachatherstakinevileful")
                .password("f6b728296b6c0086f2b32cae2a7e135a123de84d")
                .build();

        db = client.database("smart_shelf", false);
    }

    public UserDoc findUser(String username) {
        String docName = "6401de6cf1a89da5026c546a1ef1a092";
        try {
            if (db.contains(docName)) {
                user = db.find(UserDoc.class, docName);
            }
        } catch (CouchDbException ex) {
            System.out.println("Cant reach DB");
        }

        return user;
    }
    

    public Response updateUser(UserDoc user) {
        Response response = db.update(user);
        System.out.println(response.getId());
        System.out.println(response.getStatusCode());

        return response;
    }

    public List<Shelf> getShelfs() {

        return user.getShelfs();

    }

    public List<Scale> getScales(String shelfId) {

        for (Shelf s : user.getShelfs()) {
            if (s.getId().equals(shelfId)) {
                return s.getScales();
            }
        }

        return null;

    }

    public List<Product> getProducts() {

        ProductsDoc prodDoc;

        String docName = "products";
        try {
            if (db.contains(docName)) {
                prodDoc = db.find(ProductsDoc.class, docName);
                return prodDoc.getProducts();
            }
        } catch (CouchDbException ex) {
            System.out.println("Cant reach DB");
        }

        return null;
    }

    public Shelf getShelf(String id) {
        for (Shelf s : user.getShelfs()) {
            if (s.getId().equals(id)) {
                return s;
            }
        }

        return null;
    }

    public List<Product> findProduct(String searchString) {
        List<Product> result = new ArrayList<>();

        for (Product p : getProducts()) {

            if (p.getName().contains(searchString)) {
                result.add(p);
            }
        }

        return result;

    }

    public Response addShelf(Shelf shelf) {

        getShelfs().add(shelf);
        
        
        return updateUser(user);

    }

    public Response updateShelf(Shelf shelf) {
        
        for(Shelf f:getShelfs()){
            if(f.getId().equals(shelf.getId())){
                shelf=f;
                return updateUser(user);
            }
        }

        return null;
    }
}

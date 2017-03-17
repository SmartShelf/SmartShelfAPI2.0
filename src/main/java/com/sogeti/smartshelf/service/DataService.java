package com.sogeti.smartshelf.service;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.org.lightcouch.CouchDbException;
import com.sogeti.smartshelf.MathUtils;
import com.sogeti.smartshelf.model.Product;
import com.sogeti.smartshelf.model.ProductsDoc;
import com.sogeti.smartshelf.model.Scale;
import com.sogeti.smartshelf.model.Shelf;
import com.sogeti.smartshelf.model.UserDoc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        
        try {
            user = db.findByIndex("\"selector\": { \"user.username\": \"" + username + "\"} " 
                    , UserDoc.class).get(0);
            String u = user.toString();
            System.out.println(u);
          
        } catch (CouchDbException ex) {
            System.out.println("Cant reach DB");
        }

        return user;
    }

    public UserDoc getUser() {

        user=findUser("demouser");
            
        
        return user;
    }

    public Response updateUser(UserDoc user) {
        
        try
        {
       // user.setRev(null);
        //String json = user.toString();
       // JsonParser parser = new JsonParser();
       // JsonObject docJson = parser.parse(user.toString()).getAsJsonObject();
        //user.setId(java.util.UUID.randomUUID().toString());
        //String revPre = (Integer.parseInt(user.getRev().substring(0, 4)) + 1) + "-";
        String rev = "123-" + java.util.UUID.randomUUID().toString();
        user.setRev(rev);
        Response response = db.update(user);
       // Response response = db.save(user);

        return response;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
    public Response updateUser_AddNew() {
        
        try
        {
          
         
         
       // user.setRev(null);
        //String json = user.toString();
       // JsonParser parser = new JsonParser();
       // JsonObject docJson = parser.parse(user.toString()).getAsJsonObject();
        user.setId(java.util.UUID.randomUUID().toString());
        //String revPre = (Integer.parseInt(user.getRev().substring(0, 4)) + 1) + "-";
        user.setRev(null);
        //Response response = db.update(user);
        Response response = db.save(user);

        return response;
        }
        catch (Exception ex)
        {
                return null;
        }
    }

    public List<Shelf> getShelfs() {

        return getUser().getShelfs();

    }
    
    public List<Shelf> getShelves() {

        return getUser().getShelfs();

    }

    public List<Scale> getScales(String shelfId) {

        for (Shelf s : getUser().getShelfs()) {
            if (s.getId().equals(shelfId)) {

                if(s.getScales()!=null){
                    for (Scale sc : s.getScales()) {
                        populatePersentageInScale(sc);
                    }
                }

                return s.getScales();

            }
        }

        return null;

    }

    public void populatePersentageInScale(Scale s) {

        if (s.getProductId() != null) {
            Product p = getProduct(s.getProductId());

            if(s.getWeight()!=null){
                s.setPersentage(MathUtils.getPersentage(p, s.getWeight()));
            }
        }
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

    public Product getProduct(String productId) {

        for (Product p : getProducts()) {

            if (p.getId().equals(productId)) {
                return p;
            }
        }

        return null;
    }

    public Shelf getShelf(String id) {
        for (Shelf s : getUser().getShelfs()) {
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

//    public void addUser(User user) {
//
//        db.add(user);
//        getShelfs().add(user);
//
//         updateUser();
//
//    }
    
    public void addShelf(Shelf shelf) {
        
        Response response = db.remove(user);
        getShelfs().add(shelf);

         updateUser_AddNew();

    }

    public void updateShelf(Shelf shelf) {
        List<Shelf> lstShelves = new ArrayList<>();
        for (Shelf f : getShelfs()) {
            if (f.getId().equals(shelf.getId())) {
                f = shelf;
                
                
            }
            lstShelves.add(f);
        }
        user.setShelfs(lstShelves);
        Response response = db.remove(user);
        updateUser_AddNew();
    }

    public void clearScale(Scale scale) {
        scale.setProductId("");
        scale.setRegisterDate("");
        scale.setEstimatedDate("");
        scale.setUpdateDate("");
        scale.setPersentage(0);
        Response response = db.remove(user);
        updateUser_AddNew();
        
    }

    public void updateProduct(Scale scale, String productId) {

        scale.setProductId(productId);
        Response response = db.remove(user);
        updateUser_AddNew();

    }
    
    private void updateUser(){
         updateUser(user);
         findUser(null);
    }

    public void deleteShelf(String shelfId) {
     
        List<Shelf> shelfs= new ArrayList<>();
        
        for(Shelf s: getShelfs()){
            if(s.getId()!=null && !s.getId().equals(shelfId)){
                shelfs.add(s);
            }
        }
        
        user.setShelfs(shelfs);
        updateUser();
    }

    public void updateWeights(String deviceId, String scaleId, Integer scaleValue) {
        
        getUser();
        Response response = db.remove(user);
        
        for(Shelf shelf:user.getShelfs()){
            if(shelf.getId().equals(deviceId)){
                
                for(Scale scale: shelf.getScales()){
                    if(scale.getId().equals(scaleId)){
                        scale.setWeight(scaleValue);
                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        Calendar cal = Calendar.getInstance();

                        scale.setRegisterDate(dateFormat.format(cal));
                        updateUser_AddNew();
                        break;
                    }
                }
                
            }
        }
    }
    public void updateShelfWeights(String deviceId, String scaleId1, Integer scaleValue1, String scaleId2, Integer scaleValue2) {
        
        getUser();
        Response response = db.remove(user);
        
        for(Shelf shelf:user.getShelfs()){
            if(shelf.getId().equals(deviceId)){
                
                for(Scale scale: shelf.getScales()){
                    if(scale.getId().equals(scaleId1)){
                        scale.setWeight(scaleValue1);
                        
                        
                    }
                    if(scale.getId().equals(scaleId2)){
                        scale.setWeight(scaleValue2);
                                                
                    }
                }
                updateUser_AddNew();
                
            }
        }
    }
    public void updateShelfWeights_AddNew(String deviceId, String scaleId1, Integer scaleValue1, String scaleId2, Integer scaleValue2) {
        
        getUser();
        Response response = db.remove(user);
        for(Shelf shelf:user.getShelfs()){
            if(shelf.getId().equals(deviceId)){
                
                for(Scale scale: shelf.getScales()){
                    if(scale.getId().equals(scaleId1)){
                        scale.setWeight(scaleValue1);
                        
                        
                    }
                    if(scale.getId().equals(scaleId2)){
                        scale.setWeight(scaleValue2);
                                                
                    }
                }
                updateUser_AddNew();
                
            }
        }
    }
    
}

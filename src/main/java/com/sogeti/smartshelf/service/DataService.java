package com.sogeti.smartshelf.service;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.DbInfo;
import com.cloudant.client.api.model.Index;
import com.cloudant.client.api.model.IndexField;
import com.cloudant.client.api.model.Shard;
import com.sogeti.smartshelf.model.User;
import java.util.Iterator;
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

    public DataService() {
        
        client = ClientBuilder.account("b3a3013d-c4b6-43be-bd87-5b066e097bc5-bluemix")
                .username("b3a3013d-c4b6-43be-bd87-5b066e097bc5-bluemix")
                .password("859631ce8760d0353a0d7b7db107ef45ed6249367f214480a02d314c96326fa8")
              .build();
        
        db = client.database("smart_shelf", false);
    }
    

    public void testConnection() {
        // Create a new CloudantClient instance for account endpoint example.cloudant.com
        CloudantClient client = ClientBuilder.account("b3a3013d-c4b6-43be-bd87-5b066e097bc5-bluemix")
                .username("b3a3013d-c4b6-43be-bd87-5b066e097bc5-bluemix")
                .password("859631ce8760d0353a0d7b7db107ef45ed6249367f214480a02d314c96326fa8")
              .build();
 
 
 
 // Show the server version
System.out.println("Server Version: " + client.serverVersion());

// Get a List of all the databases this Cloudant account
List<String> databases = client.getAllDbs();
System.out.println("All my databases : ");
for ( String db : databases ) {
    System.out.println(db);
}

// Working with data

// Delete a database we created previously.
client.deleteDB("example_db");

// Create a new database.
client.createDB("example_db");

// Get a Database instance to interact with, but don't create it if it doesn't already exist
Database db = client.database("example_db", false);



// Create an ExampleDocument and save it in the database
User user=new User();
user.setFirstName("Fadi");
user.setLastName("Abdin");
user.setUsername("fadiabdeen");
user.setPassword("123456");

db.save(user);
System.out.println("You have inserted the document");

// Get an ExampleDocument out of the database and deserialize the JSON into a Java type
User doc = db.find(User.class,user.getUsername());
System.out.println(doc);

    }
    
    // A Java type that can be serialized to JSON
//public class ExampleDocument {
//  private String _id = "example_id";
//  private String _rev = null;
//  private boolean isExample;
//
//  public ExampleDocument(boolean isExample) {
//    this.isExample = isExample;
//  }
//
//  public String toString() {
//    return "{ id: " + _id + ",\nrev: " + _rev + ",\nisExample: " + isExample + "\n}";
//  }
//}

    public void getUser(String string) {
        
        String docId = "users";
        
        //Check if users document exist
        if(db.contains(docId)){
            System.out.println("DBUri : "+db.getDBUri());
            
            //Information about the document
            Shard docInfo = db.getShard(docId);
            System.out.println("Range : "+ docInfo.getRange());
            
            //More info
            DbInfo dbInfo2= db.info();
            
            List<Index> list=db.listIndices();
            
            for(Index i: list){
                System.out.println("Index name: "+i.getName());
                
               Iterator<IndexField> it=i.getFields();
               
               while(it.hasNext()){
                   IndexField inF=it.next();
                   System.out.println("indexField: "+inF.getName());
               }
                
            }
            
        }

        
    }
    
}

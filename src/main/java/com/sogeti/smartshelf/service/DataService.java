package com.sogeti.smartshelf.service;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 * @author fabdin
 */
public class DataService {

    public void testConnection() throws MalformedURLException {

//         Note: for Cloudant Local or Apache CouchDB use:
 CloudantClient client = ClientBuilder.url(new URL("https://abdin.cloudant.com"))
              .username("heingtherenglichmedisslo")
              .password("3ac349477c2912e2abf529aed616b6b9968f54f0")
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
db.save(new ExampleDocument(true));
System.out.println("You have inserted the document");

// Get an ExampleDocument out of the database and deserialize the JSON into a Java type
ExampleDocument doc = db.find(ExampleDocument.class,"example_id");
System.out.println(doc);

    }
    
    // A Java type that can be serialized to JSON
public class ExampleDocument {
  private String _id = "example_id";
  private String _rev = null;
  private boolean isExample;

  public ExampleDocument(boolean isExample) {
    this.isExample = isExample;
  }

  public String toString() {
    return "{ id: " + _id + ",\nrev: " + _rev + ",\nisExample: " + isExample + "\n}";
  }
}
    
}

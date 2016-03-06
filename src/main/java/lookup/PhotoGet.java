package lookup;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.logging.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.taskqueue.Queue.*;
import com.google.appengine.api.datastore.Query.GeoRegion.*;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.memcache.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public enum PhotoGet {
  instance;
  
  private Map<String, BlobKey> contentProvider = new HashMap<>();
  
  private PhotoGet() {
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    //ImagesService imagesService = ImagesServiceFactory.getImagesService();
    Filter publicFilter = new FilterPredicate("public",
              FilterOperator.EQUAL,
              "true");

    // have something that sends the function over to this thing
    
    GeoPt center = new GeoPt(37.7913156f,-122.3926051f);
    double radius = 5000;
    Filter f = new StContainsFilter("location", new Circle(center, radius));

    Filter compFilter = CompositeFilterOperator.and(f,publicFilter);


    Query q = new Query("Photo").setFilter(compFilter);
    PreparedQuery pq = datastore.prepare(q);

    for (Entity result : pq.asIterable()) {
      BlobKey blobKey = new BlobKey(result.getProperty("blobkey").toString());
      contentProvider.put(result.getProperty("title").toString(), blobKey);
    }

  }

  public Map<String, BlobKey> getPhoto(){
    return contentProvider;
  }
  
} 

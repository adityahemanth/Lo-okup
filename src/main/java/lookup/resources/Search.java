package lookup.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.GeoRegion.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.*;
import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.images.*;
import com.google.appengine.api.memcache.*;
import java.util.logging.*;

import lookup.models.*;

@Path("/latlng")
public class Search {
	
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      UserService userService = UserServiceFactory.getUserService();
      MemcacheService syncCache = null;


	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public ArrayList<Photo> getphoto(@QueryParam("radius") double radius,
		  @QueryParam("lat") float lat, @QueryParam("lng") float lng)
		{
		
            if(syncCache == null) {
                  syncCache = MemcacheServiceFactory.getMemcacheService();
                  syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
            }


		ArrayList<Photo> photolist = new ArrayList<>();
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
            GeoPt center = new GeoPt(lat,lng);
            com.google.appengine.api.datastore.Query.Filter f = new StContainsFilter("location", new Circle(center, radius));

            // this keeps track of caching. 
            // caching everything within 360 feet on
            // search location.
            
            int key11 = (int) lat * 1000;
            int key21 = (int) lng * 1000;

            String finalKey = key11 + "|" + key21 + "|" + radius; 

            try {
                  photolist = (ArrayList<Photo>) syncCache.get(finalKey);

                  if(photolist == null){

                        photolist = new ArrayList<>();
                        Query q = new Query("Photo").setFilter(f);
                        PreparedQuery pq = datastore.prepare(q);
                        Photo photo;

                        for (Entity result : pq.asIterable()) {

                              try{
                                    BlobKey blobKey = new BlobKey(result.getProperty("blobkey").toString());
                                    GeoPt loc = (GeoPt) result.getProperty("location");
                                    photo = new Photo();
                                    photo.setURL(imagesService.getServingUrl(blobKey));
                                    photo.setLocation(loc);
                                    photo.setOwner((String) result.getProperty("owner"));
                                    photo.setTitle((String) result.getProperty("title"));
                                    photo.setDescription((String) result.getProperty("description"));
                                    photolist.add(photo);
                              } catch (Exception e){
                                    e.printStackTrace();
                              }     
                        }

                        syncCache.put(finalKey, photolist);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

		return photolist;	
	}

}

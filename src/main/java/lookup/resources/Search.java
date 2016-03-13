package lookup.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


import lookup.models.*;

@Path("/latlng")
public class Search {
	
DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
UserService userService = UserServiceFactory.getUserService();


	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public ArrayList<Photo> getActivity(@QueryParam("radius") double radius,
		  @QueryParam("lat") float lat, @QueryParam("lng") float lng)
		{
		
			ArrayList<Photo> photolist = new ArrayList<>();
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
            GeoPt center = new GeoPt(lat,lng);
            Filter f = new StContainsFilter("location", new Circle(center, radius));

            Query q = new Query("Photo").setFilter(f);
            PreparedQuery pq = datastore.prepare(q);
            Photo photo;

            for (Entity result : pq.asIterable()) {
            	BlobKey blobKey = new BlobKey(result.getProperty("blobkey").toString());
            	photo = new Photo();
            	photo.setURL(imagesService.getServingUrl(blobKey));
            	photo.setOwner((String) result.getProperty("owner"));
            	photo.setTitle((String) result.getProperty("title"));
            	photo.setDescription((String) result.getProperty("description"));
            	photolist.add(photo);
            }

		return photolist;	
	}


	@POST
	@Consumes("application/json")
	public void createActivity( Photo photo) {
		
	}


}

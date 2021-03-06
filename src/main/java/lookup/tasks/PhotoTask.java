package lookup.tasks;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;
import com.google.appengine.api.taskqueue.*;
import com.google.appengine.api.users.*;

import lookup.models.*;


// this saves the User data that is passed to this class.

public class PhotoTask { 

	private static DatastoreService datastore;
	private static MemcacheService syncCache;
	private static final String ENTITY = "Photo";
	
	public PhotoTask() {

		// instantiating the datastore and memcache objects
		datastore = DatastoreServiceFactory.getDatastoreService();
		syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

	}

	// stores the User data passed in to data store. 
	// Memcache storage is not necessary.
	public void addPhoto(lookup.models.Photo photo) {

		Key placeKey = KeyFactory.createKey("User", photo.getPlace());

		Entity newPhoto = new Entity(ENTITY, placeKey);
		newPhoto.setProperty("owner", photo.getOwner());
		newPhoto.setProperty("title", photo.getTitle());
		newPhoto.setProperty("place", photo.getPlace());
		newPhoto.setProperty("description", photo.getDescription());
		datastore.put(newPhoto);
		syncCache.put(photo.getOwner(), newPhoto);

	}


}
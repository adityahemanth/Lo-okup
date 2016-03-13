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

public class PlaceTask { 

	private static DatastoreService datastore;
	private static MemcacheService syncCache;
	private static final String ENTITY = "Place";
	
	public PlaceTask() {

		// instantiating the datastore and memcache objects
		datastore = DatastoreServiceFactory.getDatastoreService();
		syncCache = MemcacheServiceFactory.getMemcacheService();
		syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

	}

	// stores the User data passed in to data store. 
	// Memcache storage is not necessary.
	public void addPlace(lookup.models.Place place) {

		Entity newPlace = new Entity(ENTITY, place.getTitle());
		newPlace.setProperty("title", place.getTitle());
		newPlace.setProperty("location", place.getLocation());
		newPlace.setProperty("description", place.getDescription());
		datastore.put(newPlace);
		syncCache.put(place.getTitle(),newPlace);
	}


}
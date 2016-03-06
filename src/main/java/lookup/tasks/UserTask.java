package lookup.tasks;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.taskqueue.*;
import com.google.appengine.api.users.*;

import lookup.models.*;


// this saves the User data that is passed to this class.

public class UserTask { 

	private static DatastoreService datastore;
	private static final String ENTITY = "User";
	
	public UserTask() {

		// instantiating the datastore object
		datastore = DatastoreServiceFactory.getDatastoreService();

	}

	// stores the User data passed in to data store. 
	// Memcache storage is not necessary.
	public void addUser(lookup.models.User user) {

		Entity newUser = new Entity(ENTITY, user.getEmail());
		newUser.setProperty("username", user.getUsername());
		newUser.setProperty("fullname", user.getFullname());
		newUser.setProperty("email", user.getEmail());
		newUser.setProperty("description", user.getDescription());
		newUser.setProperty("dob", user.getDOB());
		newUser.setProperty("dateCreated", user.getDateCreated());
		datastore.put(newUser);

	}


}
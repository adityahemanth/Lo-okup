package lookup;

import java.io.IOException;
import java.util.*;
import java.io.*;
import java.util.logging.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.taskqueue.Queue.*;
import com.google.appengine.api.datastore.Query.GeoRegion.*;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.memcache.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


public class Upload extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    UserService userService = UserServiceFactory.getUserService();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        User user = userService.getCurrentUser();
        if (user == null)
	    	res.sendRedirect("/login.jsp"); 

	    else {

	        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
	        List<BlobKey> blobKeys = blobs.get("photo");
	        String owner = (String) user.toString();
	        String title = (String) req.getParameter("title");
	        String description = (String) req.getParameter("description");
	        String pub = req.getParameter("public");
	        float lat = Float.parseFloat(req.getParameter("lat"));
	        float lng = Float.parseFloat(req.getParameter("lng"));
	        GeoPt location = new GeoPt(lat,lng);

	        if (blobKeys == null || blobKeys.isEmpty()) {
	            res.sendRedirect("/");
	        } else {

	        	Key photokey = KeyFactory.createKey("Photo", title);
	        	Entity photo = new Entity("Photo",photokey);
	        	String blobkey = (String) blobKeys.get(0).getKeyString();
	        	photo.setProperty("blobkey", blobkey);
	        	photo.setProperty("public",pub);
	        	photo.setProperty("description",description);
	        	photo.setProperty("location", location);
	        	photo.setProperty("title",title);
	        	photo.setProperty("owner",user.toString());

	        	// have to make it a cron job
	        	Date date = new Date();
	        	photo.setProperty("uploaded",date);
	        	datastore.put(photo);

	        	//puting it in datastore and memcache
				res.sendRedirect("/index.jsp");

	            //res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
	        }
    	}

    }
}


// create a view image servlet
// put that logic. 
// return the result.
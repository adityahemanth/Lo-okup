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
import com.google.appengine.api.taskqueue.TaskOptions.Method;
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

	        String blobkey = (String) blobKeys.get(0).getKeyString();
	        String owner = (String) user.toString();
	        String title = (String) req.getParameter("title");
	        String description = (String) req.getParameter("description");
	        String pub = req.getParameter("public");
	        String lat = req.getParameter("lat");
	        String lng = req.getParameter("lng");


	      
        	//puting it in datastore and memcache
			com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getDefaultQueue();
   			queue.add(TaskOptions.Builder.withUrl("/rest/upload").param("blob", blobkey).param("user",owner)
   				.param("title",title).param("description",description).param("public",pub).param("lat",lat).param("lng",lng).method(Method.POST));

            //res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
            res.sendRedirect("/index.jsp");
    	}

    }
}


// create a view image servlet
// put that logic. 
// return the result.
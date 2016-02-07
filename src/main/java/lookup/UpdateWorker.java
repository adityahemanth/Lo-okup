package lookup;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;

public class UpdateWorker extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String fullname  = request.getParameter("fullname");
        String description = request.getParameter("description");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	  	syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

        Entity store = new Entity("User",username);
        store.setProperty("fullname", fullname);
        store.setProperty("description", description);

		Date date = new Date();
		store.setProperty("date",date);
		datastore.put(store);

		//adding entity to memcache
		syncCache.put(username,store);
    }
}
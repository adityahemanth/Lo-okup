package cs263w16;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;

public class Worker extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyname = request.getParameter("keyname");
        String value  = request.getParameter("value");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	  	syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

        Entity store = new Entity("TaskData",keyname);
        store.setProperty("value", value);

		Date date = new Date();
		store.setProperty("date",date);
		datastore.put(store);

		//adding entity to memcache
		syncCache.put(keyname,store);
    }
}
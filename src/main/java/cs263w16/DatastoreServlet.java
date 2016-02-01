package cs263w16;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;

@SuppressWarnings("serial")
public class DatastoreServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

      String paramKeyName = req.getParameter("keyname");
      String paramValue   = req.getParameter("value");

      // datastore object and memcache
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	  MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	  syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

      //printwriter
	  PrintWriter writer = resp.getWriter();
	  writer.println("<h1> Welcome to Lo-okup ! </h1>");


		if(paramKeyName == null && paramValue == null){

			writer.println("<h3>  Entities in Datastore </h3>");
			Query q = new Query("TaskData");
			PreparedQuery pq = datastore.prepare(q);

			writer.println("<ol>");
			for (Entity result : pq.asIterable()) {
				String keyname = result.getKey().getName();
				String value   = (String) result.getProperty("value");
				Date date   = (Date) result.getProperty("date");
				writer.println("<li> <pre> <b> Key: </b>" + keyname + "  <b>     Value: </b> " + value + " <b>     Date: " +date+ " </pre> </li>");
			}
			writer.println("</ol>");

		}

		else if (paramKeyName != null && paramValue == null){


            Key keystore = KeyFactory.createKey("TaskData", paramKeyName);
            Entity keyname, memval;

			try {
			   	keyname = datastore.get(keystore);

			    String value = (String) keyname.getProperty("value");
				writer.println("<p> <b> Key: </b> " + paramKeyName + " <b> Value: </b>" + value + " </p>");

				memval = (Entity) syncCache.get(paramKeyName);
				if(memval != null) {
					writer.println("Data found in BOTH");
				}

				else {
					syncCache.put(paramKeyName, keyname);
					writer.println("Data found in DATASTORE");
				}

			} catch (EntityNotFoundException e) {
				writer.println("<p> found in NEITHER. </p>");

			}


		}

		else if (paramKeyName != null && paramValue != null) {

			Entity store = new Entity("TaskData",paramKeyName);
			store.setProperty("value", paramValue);

			Date date = new Date();
			store.setProperty("date",date);
			datastore.put(store);
			syncCache.put(paramKeyName,store);

			writer.println("<p> Stored " + paramKeyName + " and "+ paramValue+" in Datastore and Memcache </p>");
		}

		else {
			writer.println("<p> Invalid Request </p>");
		}

  }


}
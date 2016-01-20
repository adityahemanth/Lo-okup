package main.lookup;

import javax.servlet.*;
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

      // datastore object
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

      //printwriter
	  PrintWriter writer = resp.getWriter();
	  writer.println("<h1> Welcome to Lo-okup ! </h1>");

		if(paramKeyName == null && paramValue == null){

			Query q = new Query("TaskData");
			PreparedQuery pq = datastore.prepare(q);

			for (Entity result : pq.asIterable()) {
				String keyname = result.getKey().toString();
				keyname = keyname.substring(10,keyname.length()-2);
				String value   = (String) result.getProperty("value");
				writer.println("<p> <b> Key: </b>" + keyname + "  <b> Value: </b> " + value + "</p>");

			}

		}

		else if (paramKeyName != null && paramValue == null){


            Key keystore = KeyFactory.createKey("TaskData", paramKeyName);
            Entity keyname;

			try {
			   	keyname = datastore.get(keystore);

			    String value = (String) keyname.getProperty("value");
				writer.println("<p> <b> Key: </b> " + paramKeyName + " <b> Value: </b>" + value + " </p>");

			} catch (EntityNotFoundException e) {
				writer.println("<p> Sorry, didn't find what you are looking for.</p>");
			}


		}

		else if (paramKeyName != null && paramValue != null){

			Entity store = new Entity("TaskData",paramKeyName);
			store.setProperty("value", paramValue);

			Date date = new Date();
			store.setProperty("date",date);
			datastore.put(store);

			writer.println("<p> Stored " + paramKeyName + " and "+ paramValue+" in Datastore </p>");
		}

		else {
			writer.println("<p> Invalid Request </p>");
		}

  }


}
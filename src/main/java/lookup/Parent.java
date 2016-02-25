package lookup;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class Parent extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

            PrintWriter writer = response.getWriter();
            writer.println("<body>");
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        	String parent = request.getParameter("parent");
        	String child = request.getParameter("child");

        	if( parent != "" && child != "") {

            Key parentkey = KeyFactory.createKey("Parent", parent);

            Entity baby = new Entity("Child", child,  parentkey);
            baby.setProperty("name", child);
            baby.setProperty("age", 23);
            datastore.put(baby);

            Query q = new Query("Child")
                .setAncestor(parentkey);

            PreparedQuery pq = datastore.prepare(q);

			writer.println("<ol>");
			for (Entity result : pq.asIterable()) {
				String keyname = result.getKey().getName();
				String name   = (String) result.getProperty("name");
				long age   = (long) result.getProperty("age");
				writer.println("<li> <pre> <b> Key: </b>" + keyname + "  <b>     Name: </b> " + name + " <b>     Age: " +age+ " </pre> </li>");
			}
			writer.println("</ol>");
            }

            writer.println("</body>");

    }
}

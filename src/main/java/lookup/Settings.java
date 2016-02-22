package lookup;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Settings extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
     /* Reads the form parameters, stores in datastore and creates a task queue to process them */
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    
	    /* Redirect to cover page to prevent unauthorized access */
	    if (user == null)
	    	resp.sendRedirect("/coverindex.jsp");

	    String username = req.getParameter("username");
	    String first_name = req.getParameter("first_name");
	    String last_name = req.getParameter("last_name");
	    
	   /* Debug Statements */
	    System.out.println("In Worker - " + "first_name " + first_name + " last_name " + last_name 
	    + " username " + username +  " user" + user.toString());
	    
	  
	    /* Start a worker Thread and return back a home page */
	    
	    Queue queue = QueueFactory.getDefaultQueue();
	    queue.add(TaskOptions.Builder.withUrl("/worker").param("username", username).param("first_name",first_name)
	    							 .param("last_name", last_name).param("email",user.toString()));

      
	   resp.sendRedirect("/index.jsp");
	  }

}
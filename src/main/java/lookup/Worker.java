package lookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

/* Uses Urlfetch to fetch Json from a currency exchange site and triggers SMS notifications
 * if conditions are met. Twilio Api is used to send SMS. This also runs as a cron job.
 */
public class Worker extends HttpServlet {
	
	public static final String ACCOUNT_SID = "AC5dd34f2b8a80e573c9c636ea4900b48a";
    public static final String AUTH_TOKEN = "0a5940ce7a8585d733170a4aab1620a6";
    public static final String Twilio_Num = "+18664514765";
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	
        String username = request.getParameter("username");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String user = request.getParameter("email");
        Date date = new Date();

          /* convert user to string */
        Key userkey = KeyFactory.createKey("User", user);
        Entity  new_user = new Entity(userkey);
        new_user.setProperty("username", username);
        new_user.setProperty("date", date);
        if(!first_name.equals(""))
            new_user.setProperty("first_name", first_name);
        if(!last_name.equals(""))
            new_user.setProperty("last_name", last_name);
        
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(new_user);
    	
    }
	
		
	/* @return: nothing @param: request, response. - GET requests for cron job */	
	protected void doGET(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	
    }		
	
	
}
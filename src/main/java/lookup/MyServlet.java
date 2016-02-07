package lookup;

import java.io.IOException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

        String thisURL = req.getRequestURI();
        PrintWriter writer = resp.getWriter();

        resp.setContentType("text/html");
        if (req.getUserPrincipal() != null) {
            writer.println("<p>Hello, " +
                                     req.getUserPrincipal().getName() +
                                     "!  You can <a href=\"" +
                                     userService.createLogoutURL(thisURL) +
                                     "\">sign out</a>.</p>");

            String name = req.getUserPrincipal().getName();
            writer.println("Email:  " +name);

            //checking whether user exists in the database
            Key keystore = KeyFactory.createKey("User", name);
            Entity user;

            try {
                user = datastore.get(keystore);
                writer.println(" <p> Welcome </p>" + (String) user.getProperty("username"));

            } 

            catch (EntityNotFoundException e) {
                writer.println(" <p> Welcome, your profile update </p>");      
            }

        } else {
            writer.println("<p>Please <a href=\"" +
                                     userService.createLoginURL(thisURL) +
                                     "\">sign in</a>.</p>");
        }
    }
}
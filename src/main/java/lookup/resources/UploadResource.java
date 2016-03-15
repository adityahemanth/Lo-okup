package lookup.resources;

import java.util.*;
import java.io.IOException;


import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.servlet.http.*;
import java.net.URISyntaxException;


import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.GeoRegion.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.images.*;

import java.io.*;
import java.util.logging.*;



import lookup.models.*;

@Path("/upload")
public class UploadResource {

      private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
      private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


      @POST
      @Consumes({MediaType.MULTIPART_FORM_DATA,MediaType.APPLICATION_FORM_URLENCODED})
      public void newPlace(@Context HttpServletRequest req,
      @Context HttpServletResponse res) throws IOException, URISyntaxException{

        String blobkey = (String) req.getParameter("blob");
        String owner = (String) req.getParameter("user");
        String title = (String) req.getParameter("title");
        String description = (String) req.getParameter("description");
        String pub = req.getParameter("public");

        float lat = Float.parseFloat(req.getParameter("lat"));
        float lng = Float.parseFloat(req.getParameter("lng"));
        GeoPt location = new GeoPt(lat,lng);



            Key photokey = KeyFactory.createKey("Photo", title);
            Entity photo = new Entity("Photo",photokey);
            photo.setProperty("blobkey", blobkey);
            photo.setProperty("public",pub);
            photo.setProperty("description",description);
            photo.setProperty("location", location);
            photo.setProperty("title",title);
            photo.setProperty("owner",owner);

            Date date = new Date();
            photo.setProperty("uploaded",date);
            datastore.put(photo);

            System.out.println("\n\n\n\n Added to store \n\n");

  }

}

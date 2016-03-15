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
import com.google.appengine.api.memcache.*;
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
      @Consumes(MediaType.MULTIPART_FORM_DATA)
      public Response newPlace(@Context HttpServletRequest req,
      @Context HttpServletResponse res) throws IOException, URISyntaxException{


       MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
       syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));

       Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
       List<BlobKey> blobKeys = blobs.get("photo");

        String owner = (String) req.getParameter("user");
        String title = (String) req.getParameter("title");
        String description = (String) req.getParameter("description");
        String pub = req.getParameter("public");
        float lat = Float.parseFloat(req.getParameter("lat"));
        float lng = Float.parseFloat(req.getParameter("lng"));
        GeoPt location = new GeoPt(lat,lng);

        if (blobKeys == null || blobKeys.isEmpty()) {
            res.getWriter().println("Unsuccessful Upload!<br>Try again!");
        } else {

            Key photokey = KeyFactory.createKey("Photo", title);
            Entity photo = new Entity("Photo",photokey);
            String blobkey = (String) blobKeys.get(0).getKeyString();
            photo.setProperty("blobkey", blobkey);
            photo.setProperty("public",pub);
            photo.setProperty("description",description);
            photo.setProperty("location", location);
            photo.setProperty("title",title);
            photo.setProperty("owner",owner);

            // have to make it a cron job
            Date date = new Date();
            photo.setProperty("uploaded",date);
            datastore.put(photo);
            syncCache.put(photo, title);

            //puting it in datastore and memcache
            res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
        }

        return res;
  }

}

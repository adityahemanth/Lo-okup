package cs263w16;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.appengine.api.datastore.*;

//Map this class to /ds route
@Path("/ds")
public class DatastoreResource {
  // Allows to insert contextual objects into the class,
  // e.g. ServletContext, Request, Response, UriInfo
  @Context
  UriInfo uriInfo;
  @Context
  Request request;

  // datastore
  private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  // Return the list of entities to the user in the browser
  @GET
  @Produces(MediaType.TEXT_XML)
  public List<TaskData> getEntitiesBrowser() {
    //datastore dump -- only do this if there are a small # of entities

    List<TaskData> list = new ArrayList<TaskData>();

    Query q = new Query("TaskData");
    PreparedQuery pq = datastore.prepare(q);

    for (Entity result : pq.asIterable()) {

        String keyname = result.getKey().getName();
        String value = (String) result.getProperty("value");
        Date date = (Date) result.getProperty("date");

        TaskData task = new TaskData(keyname, value, date);
        list.add(task);
      }

    return list;
  }

  // Return the list of entities to applications
  @GET
  @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
  public List<TaskData> getEntities() {
    //datastore dump -- only do this if there are a small # of entities
    List<TaskData> list = new ArrayList<TaskData>();

    Query q = new Query("TaskData");
    PreparedQuery pq = datastore.prepare(q);

    for (Entity result : pq.asIterable()) {

        String keyname = result.getKey().getName();
        String value = (String) result.getProperty("value");
        Date date = (Date) result.getProperty("date");

        TaskData task = new TaskData(keyname, value, date);
        list.add(task);
      }

    return list;
  }

  //Add a new entity to the datastore
  @POST
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void newTaskData(@FormParam("keyname") String keyname,
      @FormParam("value") String value,
      @Context HttpServletResponse servletResponse) throws IOException {

    Date date = new Date();
    System.out.println("Posting new TaskData: " +keyname+" val: "+value+" ts: "+date);
    Entity newEntity = new Entity("TaskData",keyname);
    newEntity.setProperty("value",value);
    newEntity.setProperty("date",date);
    datastore.put(newEntity);

    servletResponse.sendRedirect("../done.html");
  }

  //The @PathParam annotation says that keyname can be inserted as parameter after this class's route /ds
  @Path("{keyname}")
  public TaskDataResource getEntity(@PathParam("keyname") String keyname) {
    System.out.println("GETting TaskData for " +keyname);
    return new TaskDataResource(uriInfo, request, keyname);
  }
}
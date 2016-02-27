<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>   
<%@ page import="com.google.appengine.api.blobstore.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.images.*" %>
<%@ page import="com.google.appengine.api.datastore.Query.*" %>
<%@ page import="com.google.appengine.api.datastore.Query.Filter" %>

<!DOCTYPE html>
<html lang="en">
  <head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Lo-okup</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="justified-nav.css" rel="stylesheet">

   </head>

  <body>

    <div class="container">

      <div class="masthead">
      
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();

    Key userKey = KeyFactory.createKey("User", user.toString());
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    try {
      Entity currentUser = datastore.get(userKey);
      System.out.println("User exists \n\n\n\n");
    } catch (Exception e) {
      response.sendRedirect("/reg.jsp");  
    }

%>


      
         
        <div role="navigation">
          <ul class="nav nav-justified">
            <li class="active"><a href="#"> <%= user.toString() %> </a></li>
            <li><a href="/main.jsp">Upload</a></li>
            <li><a href="#"></a></li>
            <li><a href="#">Profile</a></li>
            <li><a href="<%= userService.createLogoutURL("/login.jsp") %>">Sign out</a></li>
          </ul>
        </div>
      
     
     </div>

      <!-- Example row of columns -->


      <div class= "container">

          <%

            ImagesService imagesService = ImagesServiceFactory.getImagesService();

            Filter publicFilter = new FilterPredicate("public",
                      FilterOperator.EQUAL,
                      "true");

            Filter ownership = new FilterPredicate("owner",FilterOperator.EQUAL,user.toString());

            Filter compFilter = CompositeFilterOperator.or(ownership, publicFilter);


            Query q = new Query("Photo").setFilter(compFilter);
            PreparedQuery pq = datastore.prepare(q);

            for (Entity result : pq.asIterable()) {
            BlobKey blobKey = new BlobKey(result.getProperty("blobkey").toString());

              %>

              <img src="<%= imagesService.getServingUrl(blobKey) %>" >

              <%
            }

          %>



      </div>

      <!-- Site footer -->
      <footer class="footer">
        <p>&copy; Lookup 2016 </p>
      </footer>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
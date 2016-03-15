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
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.datastore.GeoPt" %>
<%@ page import="com.google.appengine.api.datastore.Query.GeoRegion.*" %>
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
    <link href="css/ghpages-materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="css/dropify.min.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="css/styles.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="http://fonts.googleapis.com/css?family=Inconsolata" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- Custome CSS-->    
    <!-- <link href="css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection"> -->

   </head>

  <body class="blue-grey lighten-5">


  <%

    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user == null) {
      response.sendRedirect("/login.jsp");
    }

    else {

      pageContext.setAttribute("user", user);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

      Entity currentUser = null;

      try {
      Key userKey = KeyFactory.createKey("User", user.toString());
      currentUser = datastore.get(userKey);
      } catch(Exception e) {
        e.printStackTrace();
      }
      

    if(currentUser == null){
      response.sendRedirect("/register.jsp"); 
      System.out.println("\n\n\n\n Redirecting \n\n\n\n");
    }


%>



   <div class="navbar-fixed"> 
    <nav>
    <div class="nav-wrapper">
       <a href="/index.jsp"><img src="images/nav.png" alt="" style="margin-left: 5em;width: 115px;"></a>
      <a href="#" data-activates="mobile-demo" class="button-collapse"><i class="material-icons">menu</i></a>
      <ul class="right hide-on-med-and-down">
        <li><a href="<%= userService.createLogoutURL("/login.jsp") %>" style="margin-right:5em;">Logout</a></li>
      </ul>
      <ul class="side-nav" id="mobile-demo">
        <li><a href="<%= userService.createLogoutURL("/login.jsp") %>" style="margin-right:5em;">Logout</a></li>
      </ul>
    </div>
  </nav>
  </div>

      <!-- Example row of columns -->


   <div class= "container" style="padding-top:50px;" >


    <div class="card z-depth-1" style = "padding:20px">

    <div class="row">
     <div class="input-field col s6">
      <i class="material-icons prefix">search</i>
      <input id="search" type="text" class="validate">
    </div>
    <div class="input-field col s3">
      <i class="material-icons prefix">straighten</i>
      <input id="radius" type="number" placeholder="5" min="5" max="50" class="validate">
      <label for="radius">Search Radius (Kms) </label>
    </div>

     <div class="col s3" style="padding-top:15px; padding-left:100px;"> <a class="waves-effect waves-light btn" onclick="dosearch()">Search</a></div>
    </div>
    <div id="map" style="width:100%; height:450px;"></div>
    </div>

    <div class="separator"></div>

    <div id="log"> </div>

        <div  class="row">
          <div id="photo-container" class="col l12"  style="min-height:600px">

          <div class="row">
          <div class="col s2 offset-s5" style="padding-bottom:200px;">
          <div class="align-center preloader-wrapper big active">
            <div class="spinner-layer spinner-blue">
              <div class="circle-clipper left">
                <div class="circle"></div>
              </div>
              <div class="gap-patch">
                <div class="circle"></div>
              </div>
              <div class="circle-clipper right">
                <div class="circle"></div>
              </div>
            </div>

            <div class="spinner-layer spinner-red">
              <div class="circle-clipper left">
                <div class="circle"></div>
              </div>
              <div class="gap-patch">
                <div class="circle"></div>
              </div>
              <div class="circle-clipper right">
                <div class="circle"></div>
              </div>
            </div>

            <div class="spinner-layer spinner-yellow">
              <div class="circle-clipper left">
                <div class="circle"></div>
              </div>
              <div class="gap-patch">
                <div class="circle"></div>
              </div>
              <div class="circle-clipper right">
                <div class="circle"></div>
              </div>
            </div>

            <div class="spinner-layer spinner-green">
              <div class="circle-clipper left">
                <div class="circle"></div>
              </div>
              <div class="gap-patch">
                <div class="circle"></div>
              </div>
              <div class="circle-clipper right">
                <div class="circle"></div>
              </div>
            </div>
          </div>
        </div>
        </div>

      </div>
    </div>

      <div class="fixed-action-btn horizontal click-to-toggle" style="bottom: 5em; right: 8em;">
        <a class="btn-floating btn-large red" href="/upload.jsp">
          <i class="material-icons">add</i>
        </a>
      </div>


    <!-- Site footer -->
    <footer class="page-footer">
        <div class="container">
          <div class="row">
            <div class="col l6 s12">
              <h5 class="white-text">Lookup</h5>
              <p class="grey-text text-lighten-4">You can use rows and columns here to organize your footer content.</p>
            </div>
            <div class="col l4 offset-l2 s12">
              <h5 class="white-text">Links</h5>
              <ul>
                <li><a class="grey-text text-lighten-3" href="#!">Link 1</a></li>
                <li><a class="grey-text text-lighten-3" href="#!">Link 2</a></li>
                <li><a class="grey-text text-lighten-3" href="#!">Link 3</a></li>
                <li><a class="grey-text text-lighten-3" href="#!">Link 4</a></li>
              </ul>
            </div>
          </div>
        </div>
        <div class="footer-copyright">
          <div class="container">
          © 2016 Lookup
          <a class="grey-text text-lighten-4 right" href="#!">More Links</a>
          </div>
        </div>
      </footer>


  <div class="hide row" id="column-template">
    <div class="col l4" style="padding: 20px">

      <div class="card" style="overflow: hidden;">
        <div class="card-image waves-effect waves-block waves-light">
          <div style="overflow: hidden; height:400px;
                          width: 100%;
                          background-image: url('{url_to_replace}');
                          background-size: cover;
                          background-position: center;">
          </div>
        </div>
        <div class="card-content">
          <span class="card-title activator grey-text text-darken-4" style="text-transform: capitalize">{title_to_replace}<i class="material-icons right">more_vert</i></span>
          </div>
          <div class="card-action" style="padding-top: 20px;padding-bottom: 20px;">
          <div class="chip">
            <p style="margin-top:-1px;">{author_to_replace}<p>
            <i class="material-icons">account_circle</i>
          </div>
        </div>
        <div class="card-reveal" style="display: none; transform: translateY(0px);">
          <span class="card-title grey-text text-darken-4"><p id="title2">{title_to_replace}</p><i class="material-icons right">close</i></span>
          <p id="description">{desp_to_replace}</p>
        </div>
      </div>

      </div>
    </div>


    <!-- template for no content -->
    <div class="hide">
    <div class="row" id="empty-template">
    <div class="col l12" style="padding: 20px">

      <h2 class="amber-text"> Sorry, there are no images around you! </h2>
      <h4 class="grey-text"> You can upload a few </h4>

      </div>
    </div>
    </div>

     <!-- template for header -->
    <div class="hide">
    <div class="row" id="header-template">
    <div class="col l12" style="padding: 20px">

      <h4 class="pink-text"> Places around you! </h4>
      <h6 class="grey-text text-darken-4"> You too can upload a few. </h6>

      </div>
    </div>
    </div>



  <!-- Scripts -->
  <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="js/materialize.js"></script>
  <script type="text/javascript" src="js/dropify.min.js"></script>
  <script type="text/javascript" src="js/map.js"></script>

  <script>
    $(document).ready(function(){
    $('.modal-trigger').leanModal();
    $('.dropify').dropify();
    $('.materialboxed').materialbox();
  });
  </script>

 <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDIQSkRfPT5yc5A8i27PjWnqwKqnUgBzqE&sensor=true&libraries=places&callback=initAutocomplete" async defer></script>

  <%

   }

  %>



  </body>
</html>

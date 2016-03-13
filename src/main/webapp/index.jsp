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


   <div class= "container" style="margin-top: 6em">

    <div id="map" class="card z-depth-2 hide" style="width:100%; height:500px;"></div>

    <nav class="search-bar">
    <div class="nav-wrapper">
      <form>
        <div class="input-field">
          <input id="search" type="search" value="" required>
          <label for="search"><i class="material-icons">search</i></label>
        </div>
      </form>
    </div>
    </nav>

        <div  class="row">
          <div id="photo-container" class="col l12">
           
      </div>
    </div>

      <div class="fixed-action-btn horizontal click-to-toggle" style="bottom: 5em; right: 8em;">
        <a class="btn-floating btn-large red">
          <i class="material-icons">add</i>
        </a>
        <ul>
          <li><a class="btn-floating btn-large red"><i class="material-icons">insert_chart</i></a></li>
          <li><a class="btn-floating btn-large yellow darken-1"><i class="material-icons">format_quote</i></a></li>
          <li><a class="btn-floating btn-large green"  href="/upload.jsp"><i class="material-icons">publish</i></a></li>
        </ul>
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



  <!-- Upload Modal Structure -->

  <div id="upload" class="modal">
    <div class="modal-content">

    <% 
      BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    %>
    <div class = "container">
        <form id="upload-form" action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">

            <div class="row margin">
              <div class="input-field col s12">
                <input type="text" name="title" id="title" class="form-control input-sm">
                <label for="title" class="center-align">Title</label>
              </div>
            </div>

            <input type="radio" name="public" id="public" value="true" checked>
            <label for="public">Public</label>
            
            <input type="radio" name="public" id="private" value="false">
            <label for="private">Private</label>

             <div class="row section">
              <div class="col s12 m4 l3">
                 <p>Upload Image</p>
              </div>
              <div class="col s12 m8 l9">
                  <input type="file" name="photo" id="input-file-now" class="dropify" data-default-file="" />
              </div>
            </div>

        </form>

    </div>

    <div class="modal-footer">
     <button class="btn waves-effect waves-light" onclick="document.getElementById('upload-form').submit()" type="submit" name="action">Submit
      <i class="material-icons right">send</i>
    </button>
    </div>
  </div>


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

          <div class="card-action" style="padding-top: 20px;padding-bottom: 20px;margin-bottom: -20px;">
          <a href="#"><i class="material-icons" style="padding-right: 20px;">favorite_border</i></a>
          <a href="#"><i class="material-icons">rate_review</i></a>
          </div>
        </div>
        <div class="card-reveal" style="display: none; transform: translateY(0px);">
          <span class="card-title grey-text text-darken-4"><p id="title2">{title_to_replace}</p><i class="material-icons right">close</i></span>
          <p id="description">{desp_to_replace}</p>
        </div>
      </div>

      </div>
    </div>



  <!-- Scripts -->
  <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="js/materialize.js"></script>
  <script type="text/javascript" src="js/dropify.min.js"></script>
  <script type="text/javascript" src="js/maps.js"></script>

  <script>
    $(document).ready(function(){
    $('.modal-trigger').leanModal();
    $('.dropify').dropify();
    $('.materialboxed').materialbox();
  });
  </script>

  <script>
      // Note: This example requires that you consent to location sharing when
      // prompted by your browser. If you see the error "The Geolocation service
      // failed.", it means you probably did not give permission for the browser to
      // locate you.

      function setAddress(address){
        $("#search").val(address);
      }

      function initMap() {
        var marker = null;
        var position;
        var geocoder = new google.maps.Geocoder;
        var map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 33.397, lng: -110.644},
          zoom: 6
        });
        var infoWindow = new google.maps.InfoWindow({map: map});
        google.maps.event.addListener(map, 'click', function(event) {
          //call function to create marker
              position = event.latLng;
              //delete the old marker
              if (marker) { marker.setMap(null); }
              //create a new marker
              marker = new google.maps.Marker({ position: position, map: map});
          });

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };

            map.setCenter(pos);
            geocoder.geocode({'location': pos}, function(results, status) {
               if (status === google.maps.GeocoderStatus.OK) {
                  setAddress(results[1].formatted_address);
                  $.get( "http://localhost:8080/rest/latlng", {radius : 5000, lat : pos.lat, lng : pos.lng}, function( data ) {
                         var count = data.length - 1; 

                         for(;count >= 0; count--){
                            
                          var url = data[count].url;
                          var desp = data[count].description;
                          var title = data[count].title;
                          var $template = $("#column-template").html(); // html as string
                          $template = $template.replace("{title_to_replace}",title);
                          $template = $template.replace("{title_to_replace}",title);
                          $template = $template.replace("{desp_to_replace}",desp);
                          $template = $template.replace("{url_to_replace}",url);
                          $("#title1").text(data[count].title);
                          $("#photo-container").append($template);

                         }
                  });
               }
            });


          }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
          });
        } else {
          // Browser doesn't support Geolocation
          handleLocationError(false, infoWindow, map.getCenter());
        }
      }

      function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
                              'Error: The Geolocation service failed.' :
                              'Error: Your browser doesn\'t support geolocation.');
      }

    </script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDIQSkRfPT5yc5A8i27PjWnqwKqnUgBzqE&callback=initMap" async defer></script>


  <%

   }

  %>



  </body>
</html>

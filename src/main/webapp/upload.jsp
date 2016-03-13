<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.EntityNotFoundException" %> 
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>



<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html class="light-green">
    <head>
        <title> Upload | Lo-okup</title>
    </head>
    <body>

    <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="css/dropify.min.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="css/styles.css" type="text/css" rel="stylesheet" media="screen,projection">
    <link href="http://fonts.googleapis.com/css?family=Inconsolata" rel="stylesheet" type="text/css">
    <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

<div class="container">
<div class="row margin">
    <div class = " col s12 z-depth-4 card-panel" style="padding-top: 30px; padding-bottom: 30px; padding-right:60px; padding-left:60px;">
    <%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    %>

        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
           <div class="row margin">
              <div class="input-field col s12">
                <input type="text" name="title" id="title" class="form-control input-sm">
                <label for="title" class="center-align">Title</label>
              </div>
            </div>

            <div class="row margin">
              <div class="input-field col s12">
                <input type="text" name="description" id="description" class="form-control input-sm">
                <label for="description" class="center-align">Description</label>
              </div>
            </div>

            <div class="row">
            <input type="radio" name="public" id="public" value="true" checked>
            <label for="public">Public</label>
            
            <input type="radio" name="public" id="private" value="false">
            <label for="private">Private</label>
            </div>
            
            <div class="row">

              <label for="map" > Location </label>
              <div class="input-field col s12">
                <div class="map" id="map" style="width:100%; height:300px;"> </div>
              </div>
            </div>

        <input class="hide validate" id="lat" name="lat"></input>
        <input class="hide validate" id="lng" name="lng"></input>
        <div class="row">
            <input type="file" class="dropify" name="photo">
        </div>

        <div class="row">
            <button class="btn waves-effect waves-light col sm6 offset-sm3" onclick="validate()" type="submit" name="action">Submit
              <i class="material-icons right">send</i>
            </button>
        </div>
        </form>

    </div>
    </div>
    </div>

  <!-- ================================================
    Scripts
    ================================================ -->

  <!-- jQuery Library -->
  <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
  <!--materialize js-->
  <script type="text/javascript" src="js/materialize.js"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDIQSkRfPT5yc5A8i27PjWnqwKqnUgBzqE&callback=initMap" async defer></script>
    <!-- Scripts -->
  <script type="text/javascript" src="js/dropify.min.js"></script>

  <script>
    $(document).ready(function(){
        $('.dropify').dropify();
  });
  </script>

    <script>
      // Note: This example requires that you consent to location sharing when
      // prompted by your browser. If you see the error "The Geolocation service
      // failed.", it means you probably did not give permission for the browser to
      // locate you.

      function validateForm() {
        var lat = $("#lat");
        var lng = $("#lng");
        if ((lat != null || lat != "") && (lng != null || lng != "")) {
            document.getElementById('upload-form').submit();
        }
      }


      function initMap() {
        var marker = null;
        var position;
        var geocoder = new google.maps.Geocoder;
        var map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 33.397, lng: -110.644},
          zoom: 12
        });
        google.maps.event.addListener(map, 'click', function(event) {
          //call function to create marker
              position = event.latLng;
              //delete the old marker
              if (marker) { marker.setMap(null); }
              marker = new google.maps.Marker({ position: position, map: map});
              $("#lat").val(position.lat);
              $("#lng").val(position.lng);

          });

        // Try HTML5 geolocation.
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var pos = {
              lat: position.coords.latitude,
              lng: position.coords.longitude
            };

            map.setCenter(pos);
            $("#lat").val(pos.lat);
            $("#lng").val(pos.lng);

          }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
          });
        } else {
          // Browser doesn't support Geolocation
          handleLocationError(false, infoWindow, map.getCenter());
        }
      }

      function handleLocationError(browserHasGeolocation, infoWindow, pos) {

      }

    </script>

  <!--prism-->


</body>

</html>
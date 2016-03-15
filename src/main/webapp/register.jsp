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

<!DOCTYPE html>
<html lang="en">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="msapplication-tap-highlight" content="no">
  <meta name="description" content="Materialize is a Material Design Admin Template,It's modern, responsive and based on Material Design by Google. ">
  <meta name="keywords" content="materialize, admin template, dashboard template, flat admin template, responsive admin template,">
  <title>Login | Lo-okup</title>

  <!-- Favicons-->
  <link rel="icon" href="images/favicon/favicon-32x32.png" sizes="32x32">
  <!-- Favicons-->
  <link rel="apple-touch-icon-precomposed" href="images/favicon/apple-touch-icon-152x152.png">
  <!-- For iPhone -->
  <meta name="msapplication-TileColor" content="#00bcd4">
  <meta name="msapplication-TileImage" content="images/favicon/mstile-144x144.png">
  <!-- For Windows Phone -->


  <!-- CORE CSS-->
  
  <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="css/style.css" type="text/css" rel="stylesheet" media="screen,projection">

   <link href="css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">
   <link href="http://fonts.googleapis.com/css?family=Inconsolata" rel="stylesheet" type="text/css">
   <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>

<body class="orange">

<%

  UserService userService = UserServiceFactory.getUserService();
  User user = userService.getCurrentUser();

  if (user != null) {

    pageContext.setAttribute("user", user);
    Key userKey = KeyFactory.createKey("User", user.toString());
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity currentUser;

    try {

      currentUser = datastore.get(userKey);
      response.sendRedirect("/index.jsp");    

    } catch (Exception e) {
        // User doesn't exist!
        
    }

   
  } else {
    response.sendRedirect("/login.jsp");
  }

%>

<%
  if (user != null) {
%>


  <div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
      <form id="reg-form" class="login-form" role="form" action = "/settings">
        <div class="row">
          <div class="input-field col s12 center">
            <img src="images/logo.png" alt="" style="width:300px; height:300px;" class="circle responsive-img valign profile-image-login">
            <p class="center login-form-text">Discover & Share  </p>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
             <input type="text" name="username" id="username" class="form-control input-sm">
            <label for="username" class="center-align">Username</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="first_name" name="first_name" type="text">
            <label for="password">First Name</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="last_name" name="last_name" type="text">
            <label for="password">Last Name</label>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <a href="#" onclick="document.getElementById('reg-form').submit()" class="btn waves-effect waves-light col s12">Register</a>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s6 m6 l6">
              <p class="margin right-align medium-small"><a href="page-forgot-password.html">Forgot password ?</a></p>
          </div>          
        </div>

      </form>
    </div>
  </div>



  <!-- ================================================
    Scripts
    ================================================ -->

  <!-- jQuery Library -->
  <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
  <!--materialize js-->
  <script type="text/javascript" src="js/materialize.js"></script>
  <!--prism-->

<%
  }
%>

</body>

</html>
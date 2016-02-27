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
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.EntityNotFoundException" %>    
<!DOCTYPE html>
<html>
<head>
    
    <title>Registration Form</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
 <!--    <link rel="stylesheet" type="text/css" href="WEB-INF/css/font-awesome.min.css" />
-->
    <script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>

<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);

   
} else {
	
	String url = "/login.jsp";
    response.sendRedirect(url);
    }
	
    Key userKey = KeyFactory.createKey("User", user.toString());
	 
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Entity currentUser;
	
	try {
	    currentUser = datastore.get(userKey);
	    response.sendRedirect("/index.jsp");	
	} catch (Exception e) {
	    // User doesn't exist!
		
	}

%>

<%
	if (user != null) {
%>
<div class="container">

<div class="page-header">
    <h3 class="text-muted"> Welcome to Lo-okup! </h3> 
</div>

<!-- Registration Form - START -->
 <div class="container" id="container1">
        <div class="row centered-form">
            <div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
                <div class="panel panel-default">
                   <div class="panel-heading">
                        <h3 class="panel-title text-center">Please fill out the below details</h3>
                    </div>
                  <div class="panel-body">
                        <form role="form" action = "/settings">
                            <div class="form-group">
                                <input type="text" name="first_name" id="first_name" class="form-control input-sm" placeholder="First Name">
                            </div>

                            <div class="form-group">
                                <input type="text" name="last_name" id="last_name" class="form-control input-sm" placeholder="Last Name">
                            </div>

                            <div class="form-group">
                                <input type="tel" name="username" id="username" class="form-control input-sm" placeholder="username">
                            </div>
                                              
                             <input type="submit" value="Register" id="submit" name="submit" class="btn btn-info btn-block">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<style>
#container1 {
    background-color: lighten(@gray-base, 13.5%);
}

.centered-form {
    margin-top: 120px;
    margin-bottom: 120px;
}

.centered-form .panel {
    background: rgba(255, 255, 255, 0.3);
    box-shadow: rgba(0, 0, 0, 0.2) 0px 3px 3px;
}
</style>
<!-- Registration Form - END -->

</div>

<%
	}
%>
</body>
</html>
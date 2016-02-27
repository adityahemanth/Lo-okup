<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Test</title>
    </head>
    <body>

    <div class = "container">
    <%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    %>

        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
        <ul>
            <li><input type="text" name="title"></li>
            <li><input type="radio" name="public" value="true" checked> Public</li>
            <li><input type="radio" name="public" value="false"> Private</li>
            <li><input type="file" name="photo"></li>
            <li><input type="submit" value="Submit"></li>
        </ul>
        </form>

    </div>
    </body>
</html>

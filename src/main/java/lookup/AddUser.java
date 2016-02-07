package lookup;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class AddUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uname = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String description = request.getParameter("description");

        System.out.println("Username: " + uname);
        System.out.println("Full name: " + fullname);
        System.out.println("Description: " + description);

        // Add the task to the default queue.
        Queue queue = QueueFactory.getDefaultQueue();
        queue.add(TaskOptions.Builder.withUrl("/updateworker").param("username", uname).param("fullname",fullname).param("description", description));

        response.sendRedirect("/myservlet");
    }
}

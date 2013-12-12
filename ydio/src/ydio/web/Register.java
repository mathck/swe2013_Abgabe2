package ydio.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ydio.UserManagement;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserManagement um;
	private RequestDispatcher JSPUserpage = null;
	
    public Register() {
        super();
    }
    
    public void init() throws ServletException
	{

		JSPUserpage = getServletContext().getRequestDispatcher("/userpage.jsp");

	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String eMail = request.getParameter("email");
    	String fullName = request.getParameter("fullname");
        
        
        
        response.setContentType("text/html");
        //um.registerYdiot(username, password, fullName, eMail, 'm', new Date(), "desc");
       
        JSPUserpage.forward(request, response);           

    }
}

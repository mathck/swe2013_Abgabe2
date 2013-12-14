package ydio.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ydio.UserManagement;

public class Register{
	private static final long serialVersionUID = 1L;
	private UserManagement um;

	
    
    
    public static void aufrufRegister(HttpServletRequest request, HttpServletResponse response, HttpSession session, RequestDispatcher JSPRegister) throws ServletException, IOException
   	{
   		try{
   			
   			JSPRegister.include(request, response);
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   	}
}

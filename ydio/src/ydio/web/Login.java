package ydio.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 */
public class Login{

	/**
	 *
	 *
	 * @param request Http Servlet Request
	 * @param response Http Servlet Response 
	 * @param session Http session
	 * 
	 */
	public static void aufrufLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, RequestDispatcher JSPLogin) throws ServletException, IOException
	{
		try{
			
			JSPLogin.include(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

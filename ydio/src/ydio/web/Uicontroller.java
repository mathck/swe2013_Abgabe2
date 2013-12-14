package ydio.web;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ydio.UserManagement;
import ydio.dao.DAO;

/**
 * 
 */
public class Uicontroller extends HttpServlet implements SingleThreadModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private RequestDispatcher JSPRegister = null;
	private RequestDispatcher JSPUserpage = null;
	private RequestDispatcher JSPLogin = null;

	
	public void init() throws ServletException
	{

		JSPRegister = getServletContext().getRequestDispatcher("/register.jsp");
		JSPUserpage = getServletContext().getRequestDispatcher("/userpage.jsp");
		JSPLogin = getServletContext().getRequestDispatcher("/login.jsp");
	}
	
	/**
	 *
	 * Abfangen von Get-Anfragen
	 *
	 * @param request Http Servlet Request
	 * @param response Http Servlet Response
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
			doRequest(request, response);
	}
	
	/**
	 *
	 * Abfangen von Post-Anfragen
	 *
	 * @param request Http Servlet Request
	 * @param response Http Servlet Response
	 * 
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException
	{
		
		
		doRequest(request, response);

	}

	/**
	 *
	 * Verarbeitung des Request, egal ob durch Post oder Get eingegangen
	 *
	 * @param request Http Servlet Request
	 * @param response Http Servlet Response
	 * 
	 */
	private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession(false);
		
		String gewuenschteSeite = request.getParameter("gewuenschteSeite");
			
		if (session == null)
		{
			session = request.getSession(true);
			session.setAttribute("name", "Testuser");
			session.setAttribute("status", "Session erstellt");
			
		}
		
		if( gewuenschteSeite == null){
			Register.aufrufRegister(request, response, session,  JSPRegister);
		} else {
			switch(gewuenschteSeite){
					case "register":
						Register.aufrufRegister(request, response, session,  JSPRegister);
						break;
					case "userpage":
						Userpage.aufrufUserpage(request, response, session, JSPUserpage);
						break;
					case "login":
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
			}
		}
		
		
		
	}
}

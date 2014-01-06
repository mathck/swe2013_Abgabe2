package ydio.web;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ydio.UserManagement;
import ydio.dao.DAO;
import ydio.user.AbstractUser;

/**
 * 
 */
public class Uicontroller extends HttpServlet implements SingleThreadModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UserManagement um = new UserManagement();
	AbstractUser user;

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
			try {
				doRequest(request, response);
			} catch (ParseException e) {
				e.printStackTrace();
			}
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
		
		
		try {
			doRequest(request, response);
		} catch (ParseException e) {

			e.printStackTrace();
		}

	}

	/**
	 *
	 * Verarbeitung des Request, egal ob durch Post oder Get eingegangen
	 *
	 * @param request Http Servlet Request
	 * @param response Http Servlet Response
	 * @throws ParseException 
	 * 
	 */
	private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException
	{

		HttpSession session = request.getSession(false);
		
		String gewuenschteSeite = request.getParameter("gewuenschteSeite");
			
		if (session == null)
		{
			session = request.getSession(true);
			session.setAttribute("username", "guest");
			session.setAttribute("status", "no login");
			
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
					case "verifylogin":
						um.login(request.getParameter("password"),request.getParameter("username"));
						
						if(um.getSession() == null){
							session.setAttribute("error", "Benutzername und Password stimmen nicht überein");
							Login.aufrufLogin(request, response, session, JSPLogin);
						}
						else{
							session.setAttribute("username", um.getSession().getUsername());
							session.setAttribute("fullname", um.getSession().getFullName());
							session.setAttribute("email", um.getSession().getEMail());
							session.setAttribute("date", um.getSession().getBirthday());
							session.setAttribute("status", "logged in");
							
							Userpage.aufrufUserpage(request, response, session, JSPUserpage);
						}
						break;
					case "completeRegistration":
						String requestDate = request.getParameter("date");
						Date date = new SimpleDateFormat("yyyy-MM-dd").parse(requestDate);
						um.registerYdiot(request.getParameter("username"), request.getParameter("password"),request.getParameter("fullName"),
										request.getParameter("email"), request.getParameter("sex").charAt(0), date, request.getParameter("desc"));
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					
					case "logout":
						if(session.getAttribute("status").equals("logged in")){
							um.logout();
							session.setAttribute("status", "no login");
							Login.aufrufLogin(request, response, session, JSPLogin);
							break;
						}
						session.setAttribute("error", "not logged in");
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
			}
		}
		
		
		
	}
}

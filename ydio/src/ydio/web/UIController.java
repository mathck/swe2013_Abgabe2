package ydio.web;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ydio.Beitrag;
import ydio.UserManagement;
import ydio.dao.DatabaseAccess;
import ydio.user.AbstractUser;

/**
 * 
 */
public class UIController extends HttpServlet implements SingleThreadModel {
	
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
						if(session.getAttribute("status").equals("logged in")){
							Userpage.aufrufUserpage(request, response, session, JSPUserpage);
							break;
						}
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					case "login":
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					case "verifylogin":
						um.login(request.getParameter("password"),request.getParameter("username"));
						
						if(um.getSession() == null){
							request.setAttribute("error", "Benutzername und Password stimmen nicht �berein");
							Login.aufrufLogin(request, response, session, JSPLogin);
						}
						else{
							session.setAttribute("um", um);
							session.setAttribute("status", "logged in");
							Userpage.aufrufUserpage(request, response, session, JSPUserpage);
						}
						break;
					case "completeRegistration":
						String requestDate = request.getParameter("date");
						Date date = new SimpleDateFormat("yyyy-MM-dd").parse(requestDate);
						um.registerYdiot(request.getParameter("username"), request.getParameter("password"),request.getParameter("fullname"),
										request.getParameter("email"), request.getParameter("sex").charAt(0), date, request.getParameter("desc"));
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					
					case "logout":
						session.setAttribute("um", null);
						session.setAttribute("status", "no login");
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					case "addBeitrag":
						if(session.getAttribute("status").equals("logged in")){
							String temp = (String) request.getParameter("content");
							um = (UserManagement) session.getAttribute("um");
							Beitrag bt = new Beitrag(um.getSession().getUsername(), um.getSession().getUsername());
							bt.setContent(temp);
							um.addBeitrag(bt);
							Userpage.aufrufUserpage(request, response, session, JSPUserpage);
							break;
						}
						request.setAttribute("error", "No Login");
						Userpage.aufrufUserpage(request, response, session, JSPUserpage);
						break;
			}
		}
		
		
		
	}
}

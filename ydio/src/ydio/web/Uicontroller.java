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
import ydio.web.Index;

/**
 * 
 */
public class Uicontroller extends HttpServlet implements SingleThreadModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	private RequestDispatcher JSPIndex = null;
	
	
	private UserManagement um;
	
	public void init() throws ServletException
	{
		// Header und Footer hier nur exemplarisch als extra Seiten, da somit der
		// immergleiche Content nicht jedesmal neu geschrieben werden muss

		JSPIndex = getServletContext().getRequestDispatcher("/Index.jsp");

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
		try{
			doRequest(request, response);
		}catch(Exception e){
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
			// neue Session erstellen und zuweisen
			session = request.getSession(true);
			session.setAttribute("name", "Testuser");
			session.setAttribute("status", "Session erstellt");
			
		}
		
		
		if ((gewuenschteSeite == null) || (gewuenschteSeite.equals("index")))
		{
			Index.aufrufIndex(request, response, session,  JSPIndex);
		}
		
	}
}

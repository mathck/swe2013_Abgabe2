package ydio.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 */
public class Index {

	/**
	 *
	 *
	 * @param request Http Servlet Request
	 * @param response Http Servlet Response 
	 * @param session Http session
	 * @param JSPHeader Request Dispatcher
	 * @param JSPIndex Request Dispatcher
	 * @param JSPFooter Request Dispatcher
	 * 
	 */
	public static void aufrufIndex(HttpServletRequest request, HttpServletResponse response, HttpSession session, RequestDispatcher JSPIndex) throws ServletException, IOException
	{
		try{
			/*
			 * Hier könnt ihr z.B. Parameter aus request abfangen, auswerten und dem entsprechend
			 * reagieren
			 * Das könnte theoretisch auch innerhalb vom Uicontroller.java stattfinden. 
			 * Dies macht jedoch wenig Sinn, da die Datei sehr groß und unübersichtlich
			 * werden würde.
			 */		
			JSPIndex.include(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

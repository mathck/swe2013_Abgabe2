package ydio.web;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import ydio.exceptions.InvalidDateInputException;
import ydio.exceptions.InvalidEmailInputException;
import ydio.exceptions.InvalidNameInputException;
import ydio.user.AbstractUser;
import ydio.user.Ydiot;

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
	private RequestDispatcher JSPSearch = null;
	private RequestDispatcher JSPEditProfile = null;

	
	public void init() throws ServletException
	{

		JSPRegister = getServletContext().getRequestDispatcher("/register.jsp");
		JSPUserpage = getServletContext().getRequestDispatcher("/userpage.jsp");
		JSPLogin = getServletContext().getRequestDispatcher("/login.jsp");
		JSPSearch = getServletContext().getRequestDispatcher("/search.jsp");
		JSPEditProfile = getServletContext().getRequestDispatcher("/editProfile.jsp");
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
							if(request.getParameter("target") == null || request.getParameter("target").equals(um.getSession().getUsername())){
								um.setTarget(um.getSession());
								Userpage.aufrufUserpage(request, response, session, JSPUserpage);
								break;
							}
							
							if(request.getParameter("target") != null){
								um.setTarget(um.getUserByUsername(request.getParameter("target")));
								Userpage.aufrufUserpage(request, response, session, JSPUserpage);
								break;
							}
							
						}
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					case "login":
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					case "verifylogin":
						um.login(request.getParameter("password"),request.getParameter("username"));
						
						if(um.getSession() == null){
							request.setAttribute("error", "Benutzername und Password stimmen nicht ï¿½berein");
							Login.aufrufLogin(request, response, session, JSPLogin);
						}
						else{
							session.setAttribute("um", um);
							session.setAttribute("status", "logged in");
							um.setTarget(um.getSession());
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
							String content = (String) request.getParameter("content");
							um = (UserManagement) session.getAttribute("um");
							Beitrag bt = new Beitrag(um.getSession().getUsername(), um.getTarget().getUsername());
							bt.setContent(content);
							um.addBeitrag(bt);
							Userpage.aufrufUserpage(request, response, session, JSPUserpage);
							break;
						}
						request.setAttribute("error", "No Login");
						Userpage.aufrufUserpage(request, response, session, JSPUserpage);
						break;
						
					case "search":
						if(session.getAttribute("status").equals("logged in")){
							String s = request.getParameter("searchstring");
							request.setAttribute("searchstring", s);
							Search.aufrufSearch(request, response, session, JSPSearch);
							break;
						}
						
						request.setAttribute("error", "Dafür müssen sie eingeloggt sein");
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
						
					case "addFriend":
						Ydiot user = (Ydiot) um.getSession();
						user.addFriend(um.getTarget().getUsername());
						um.updateUser(user);
						Userpage.aufrufUserpage(request, response, session, JSPUserpage);
						break;
						
					case "editProfile":
						if(request.getParameter("action").equals("open")){
							EditProfile.aufrufEditProfile(request, response, session, JSPEditProfile);
							break;
						}
						if(request.getParameter("action").equals("process")){
							Ydiot usertoedit = (Ydiot) um.getSession();
							
							try{
								if(!request.getParameter("fullname").equals("")){
									usertoedit.setFullName(request.getParameter("fullname"));
								}
							}
							catch(InvalidNameInputException INIE){
								request.setAttribute("error", "Invalid Fullname");
							}
							
							try{
								if(!request.getParameter("email").equals("")){
									usertoedit.setEMail(request.getParameter("email"));
								}
							}
							catch(InvalidEmailInputException IEIE){
								request.setAttribute("error", "Invalid Email");
							}
							
							try{
								if(!request.getParameter("date").equals("")){
									String datetoedit = request.getParameter("date");
									Date parseddate = new SimpleDateFormat("yyyy-MM-dd").parse(datetoedit);
									usertoedit.setBirthday(parseddate);
								}
							}
							catch(InvalidDateInputException IDIE){
								request.setAttribute("error", "Invalid Date");
							}
							
							um.updateUser(usertoedit);
						}
						
						Userpage.aufrufUserpage(request, response, session, JSPUserpage);
						break;
						
					case "rateBeitrag":
						if(request.getParameter("action").equals("like")){
							List<Beitrag> beitraglist = um.getBeitragListByUsername(um.getTarget().getUsername());
							long ID = Integer.parseInt(request.getParameter("id"));
							
							for(int i = 0; i < beitraglist.size(); i++){
								if(beitraglist.get(i).getID() == ID){
									beitraglist.get(i).addLike(um.getSession().getUsername());
									um.updateBeitrag(beitraglist.get(i));
									Userpage.aufrufUserpage(request, response, session, JSPUserpage);
									break;
								}
							}
						}
						else if(request.getParameter("action").equals("dislike")){
							List<Beitrag> beitraglist = um.getBeitragListByUsername(um.getTarget().getUsername());
							long ID = Integer.parseInt(request.getParameter("id"));
							
							for(int i = 0; i < beitraglist.size(); i++){
								if(beitraglist.get(i).getID() == ID){
									beitraglist.get(i).addDislike(um.getSession().getUsername());
									um.updateBeitrag(beitraglist.get(i));
									Userpage.aufrufUserpage(request, response, session, JSPUserpage);
									break;
								}
							}
						}
						
						break;
						
					case "reportBeitrag":
						List<Beitrag> beitraglist = um.getBeitragListByUsername(um.getTarget().getUsername());
						long ID = Integer.parseInt(request.getParameter("id"));
						
						for(int i = 0; i < beitraglist.size(); i++){
							if(beitraglist.get(i).getID() == ID){
								if(!beitraglist.get(i).addReport(um.getSession().getUsername())){
									request.setAttribute("error", "Sie haben diesen Beitrag schon gemeldet!");
								}
								um.updateBeitrag(beitraglist.get(i));
								Userpage.aufrufUserpage(request, response, session, JSPUserpage);
								break;
							}
						}
			}
		}
		
		
		
	}
}

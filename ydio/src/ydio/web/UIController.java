package ydio.web;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import ydio.exceptions.InvalidPasswordInputException;
import ydio.exceptions.InvalidSexInputException;
import ydio.exceptions.InvalidUsernameInputException;
import ydio.exceptions.NoUsernameLikeThisException;
import ydio.user.AbstractUser;
import ydio.user.Administrator;
import ydio.user.Forscher;
import ydio.user.Moderator;
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
	private RequestDispatcher JSPScientistPage = null;
	private RequestDispatcher JSPAdminPage = null;
	
	public void init() throws ServletException
	{

		JSPRegister = getServletContext().getRequestDispatcher("/register.jsp");
		JSPUserpage = getServletContext().getRequestDispatcher("/userpage.jsp");
		JSPLogin = getServletContext().getRequestDispatcher("/login.jsp");
		JSPSearch = getServletContext().getRequestDispatcher("/search.jsp");
		JSPEditProfile = getServletContext().getRequestDispatcher("/editProfile.jsp");
		JSPScientistPage = getServletContext().getRequestDispatcher("/scientist.jsp");
		JSPAdminPage = getServletContext().getRequestDispatcher("/admin.jsp");
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
								um.setTarget(um.getUserByUsername(um.getSession().getUsername()));
								AbstractUser userclass = um.getTarget();
				                if(userclass instanceof Ydiot)
				                  Userpage.aufrufUserpage(request, response, session, JSPUserpage);
				                else if(userclass instanceof Forscher)
				                  Userpage.aufrufUserpage(request, response, session, JSPScientistPage);
				                else if(userclass instanceof Administrator || userclass instanceof Moderator)
				                  Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
								break;
							}
							
							if(request.getParameter("target") != null){
								um.setTarget(um.getUserByUsername(request.getParameter("target")));
								AbstractUser userclass = um.getTarget();
				                if(userclass instanceof Ydiot)
				                  Userpage.aufrufUserpage(request, response, session, JSPUserpage);
				                else if(userclass instanceof Forscher)
				                  Userpage.aufrufUserpage(request, response, session, JSPScientistPage);
				                else if(userclass instanceof Administrator || userclass instanceof Moderator)
				                  Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
								break;
							}
							
						}
						
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					case "login":
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					case "verifylogin":
						try {
							um.login(request.getParameter("password"),request.getParameter("username"));
						} catch (NoUsernameLikeThisException e) {
							request.setAttribute("error", "Benutzername nicht gefunden");
							Login.aufrufLogin(request, response, session, JSPLogin);
							break;
						}
						
						if(um.getSession() == null){
							request.setAttribute("error", "Benutzername und Password stimmen nicht überein");
							Login.aufrufLogin(request, response, session, JSPLogin);
						}
						else{
							session.setAttribute("um", um);
							session.setAttribute("status", "logged in");
							um.setTarget(um.getSession());
							AbstractUser userclass = um.getTarget();
				              if(userclass instanceof Ydiot){
				            	//Falls der benutzer gesperrt ist wird ihm der Login verweigert
				            	Ydiot maybelockeduser = (Ydiot) um.getSession();
				            	if(maybelockeduser.isLocked()){
				            		request.setAttribute("error", "Sie wurden aufgrund unpassender Beiträge geperrt!");
				            		um.logout();
				            		session.setAttribute("status", "no login");
				            		Login.aufrufLogin(request, response, session, JSPLogin);
				            	}
				                Userpage.aufrufUserpage(request, response, session, JSPUserpage);
				              }
				              else if(userclass instanceof Forscher)
				                Userpage.aufrufUserpage(request, response, session, JSPScientistPage);
				              else if(userclass instanceof Administrator || userclass instanceof Moderator)
				                  Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
						}
						break;
					case "completeRegistration":
						if(request.getParameter("username").equals("") || request.getParameter("password").equals("") || request.getParameter("fullname").equals("")||
								request.getParameter("email").equals("")|| request.getParameter("sex").equals("") || request.getParameter("desc").equals("")){
							
							request.setAttribute("error", "All fields are requiered!!");
							Register.aufrufRegister(request, response, session,  JSPRegister);
							break;
						}
						String requestDate = request.getParameter("date");
						Date date = new SimpleDateFormat("yyyy-MM-dd").parse(requestDate);
						
						try{
							new Ydiot(request.getParameter("username"), request.getParameter("password"),request.getParameter("fullname"),
									request.getParameter("email"), request.getParameter("sex").charAt(0), date, request.getParameter("desc"), new ArrayList<String>());
						}
						catch(InvalidDateInputException e){
							request.setAttribute("error", "The entered Date is not valid");
							Register.aufrufRegister(request, response, session,  JSPRegister);
							break;
						}
						catch(InvalidEmailInputException e){
							request.setAttribute("error", "The entered Email is not valid");
							Register.aufrufRegister(request, response, session,  JSPRegister);
							break;
						}
						catch(InvalidNameInputException e){
							request.setAttribute("error", "Fullnames must consist of at least two names!");
							Register.aufrufRegister(request, response, session,  JSPRegister);
							break;
						}
						catch(InvalidPasswordInputException e){
							request.setAttribute("error", "Passwords must consist of at least 6 characters!");
							Register.aufrufRegister(request, response, session,  JSPRegister);
							break;
						}
						catch(InvalidSexInputException e){}
						catch(InvalidUsernameInputException e){
							request.setAttribute("error", "Your Username cannot consist spaces!");
							Register.aufrufRegister(request, response, session,  JSPRegister);
							break;
						}
						
						um.registerYdiot(request.getParameter("username"), request.getParameter("password"),request.getParameter("fullname"),
										request.getParameter("email"), request.getParameter("sex").charAt(0), date, request.getParameter("desc"));
						Login.aufrufLogin(request, response, session, JSPLogin);
						break;
					
					case "logout":
						session.setAttribute("um", null);
						um.logout();
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
						break;
					
					case "handleReport":
						Beitrag beitrag = null;
						if(request.getParameter("deleteID") != null)
						{
							long beitragID = Long.parseLong(request.getParameter("deleteID"));
							beitrag = um.getBeitrag(beitragID);
							um.removeBeitrag(beitrag);
						}
						
						if(request.getParameter("lockUser") != null)
						{
							Ydiot lockuser = (Ydiot)um.getUserByUsername(request.getParameter("lockUser"));
							Calendar c = Calendar.getInstance(); 
							Date lockdate = new Date();
							c.setTime(lockdate); 
							c.add(Calendar.DATE, 1);
							lockdate = c.getTime();
							lockuser.setLocked(lockdate);
							um.updateUser(lockuser);
						}
						
						if(request.getParameter("notBad") != null)
						{
							long beitragID = Long.parseLong(request.getParameter("notBad"));
							beitrag = um.getBeitrag(beitragID);
							beitrag.setReportList(null);
							um.updateBeitrag(beitrag);
						}
						Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
						break;
						
						
					case "registerAdmin":
						if(request.getParameter("username").equals("") || request.getParameter("password").equals("") || request.getParameter("fullname").equals("")||
								request.getParameter("email").equals("")|| request.getParameter("sex").equals("") || request.getParameter("desc").equals("")){
							
							request.setAttribute("error", "All fields are required!!");
							Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
							break;
						}
						String registeradmindate = request.getParameter("date");
						Date admindate = new SimpleDateFormat("yyyy-MM-dd").parse(registeradmindate);
						um.registerAdministrator(request.getParameter("username"), request.getParameter("password"), 
								request.getParameter("fullname"), request.getParameter("email"), request.getParameter("sex").charAt(0), admindate);
						Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
						
						break;
						
					case "registerModerator":
						
						
						break;
            
					case "lockUserByDate":
						if(request.getParameter("username").equals("") || request.getParameter("lockdate").equals(""))
						{
							request.setAttribute("error", "All fields are required!");
							Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
						}
						String date_s = request.getParameter("lockdate");
						Date lockdate = new SimpleDateFormat("yyyy-MM-dd").parse(date_s);
						Ydiot lockuser = (Ydiot)um.getUserByUsername(request.getParameter("username"));

						lockuser.setLocked(lockdate);
						Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
						um.updateUser(lockuser);
						break;
            
					case "unlockUser":
						if(request.getParameter("username").equals("")) 
						{
							request.setAttribute("error", "All fields are required!");
							Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
						}
						lockuser = (Ydiot)um.getUserByUsername(request.getParameter("username"));

						lockuser.setLocked(new Date(0));
						um.updateUser(lockuser);
						Userpage.aufrufUserpage(request, response, session, JSPAdminPage);
						break; 
			}
		}
		
		
		
	}
}

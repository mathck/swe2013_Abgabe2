package ydio;

import ydio.user.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ydio.dao.*;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:13
 */
public class UserManagement {

	private AbstractUser session;
	private DAO dao;

	public UserManagement() {
		try {
			dao = new SQL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Fuegt einen neuen Beitrag hinzu
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Ydiot
	 * @param beitrag
	 */
	public void addBeitrag(Beitrag beitrag) {
		if(session instanceof Ydiot){
			try {
				dao.addBeitrag(beitrag);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * set strings null if no change, set char ' ' if no change
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Ydiot, Moderator
	 */
	public void editSignedOnUserProfile(String password, String fullName, String eMail, char sex, Date birthday) {
		if(session instanceof Ydiot || session instanceof Moderator) {
			try {
				session.setPassword(password);
				session.setFullName(fullName);
				session.setEMail(eMail);
				if(sex != ' ')
					session.setSex(sex);
				session.setBirthday(birthday);
			} catch(Exception e) {
				// catch all exceptions TODO
			}
			
		}
	}

	/**
	 * IMPLEMENT THIS METHODE IN PROTOTYPE 2
	 * @param data_type
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Forscher
	 */
	public String getScientistData(data_type dataType) {
		if(session instanceof Forscher){
			String out = "";
			
			switch(dataType) {
			
			case ydiotCount:
				// TODO
				break;
				
			case likeDislikeReportStats:
				// TODO
				break;
				
			case avgContentLength:
				// TODO
				break;
				
			case maleVsFemale:
				// TODO
				break;
				
			case avgAge:
				// TODO
				break;
				
			case avgPasswordLength:
				// TODO
				break;
				
			case wordCount_FuckStupidIdiotGayNoob:
				// TODO
				break;
				
			default:
				out = "wrong data_type in getScientistData(data_type dataType) in UserManagement!";
				throw new NotImplementedException();
				//break;
			}
		
			return out;
		}
		return null;
	}

	/**
	 * simple login
	 * 
	 * @param password
	 * @param username
	 * Keine Zugriffs�berpr�fung
	 */
	public void login(String password, String username) {
		try {
			if (username == null || password == null) throw new IOException ("Username or Password is null");
			this.session = dao.getUserByUsername(username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!session.getPassword().equals(password))
			this.session = null;
	}
	

	public void logout() {
		this.session = null;
	}

	/**
	 * create new admin
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Administrator
	 */
	public void registerAdministrator(String username, String password, String fullName, String eMail, char sex, Date birthday) {
		if(session instanceof Administrator){
			try {
				dao.addUser(new Administrator(username, password, fullName, eMail, sex, birthday));
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
	}

	/**
	 * create new forscher
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Administrator
	 */
	public void registerForscher(String username, String password, String fullName, String eMail, char sex, Date birthday) {
		if(session instanceof Administrator){
			try {
				dao.addUser(new Forscher(username, password, fullName, eMail, sex, birthday));
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
	}

	/**
	 * create Moderator
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Administrator
	 */
	public void registerModerator(String username, String password, String fullName, String eMail, char sex, Date birthday) {
		if(session instanceof Administrator){
			try {
				dao.addUser(new Moderator(username, password, fullName, eMail, sex, birthday));
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
	}

	/**
	 * create new Ydiot
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 * @param description
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Ydiot, Moderator
	 */
	public void registerYdiot(String username, String password, String fullName, String eMail, char sex, Date birthday, String description) {
	//if(session instanceof Ydiot || session instanceof Moderator){
			List<String> fL = new ArrayList<String>();
			try {
				dao.addUser(new Ydiot(username, password, fullName, eMail, sex, birthday, description, fL));
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
	//}
	}
	
	/**
	 * this method is very bad, it goes through all Beitraege and return a List of all created by username
	 * deeper changes are needed to improve performance
	 * @param username
	 * @return list of all Beitraege created username
	 */
	public List<Beitrag> getBeitragListByUsername(String username) {
		try {
			List<Beitrag> list = dao.getBeitragList();
			List<Beitrag> newList = new ArrayList<Beitrag>();

			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getCreator().equals(username)) {
					newList.add(list.get(i));
				}
			}
			
			return newList;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * get all beitraege of current session
	 * @return
	 */
	public List<Beitrag> getSessionBeitragList() {
		return getBeitragListByUsername(session.getUsername());
	}

	/**
	 * remove Beitrag
	 * @param beitrag
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Moderator
	 */
	public void removeBeitrag(Beitrag beitrag) {
		if(session instanceof Moderator){
			try {
				dao.removeBeitrag(beitrag);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * remove User
	 * @param user
	 * Zugriffs�berpr�fung
	 * Beschr�nkt auf: Administrator
	 */
	public void removeUser(AbstractUser user) {
		if(session instanceof Administrator){
			try {
				dao.removeUser(user);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public AbstractUser getSession() {
		return session;
	}
}
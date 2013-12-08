package ydio;

import ydio.user.*;

import java.sql.SQLException;
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Fuegt einen neuen Beitrag hinzu
	 * @param beitrag
	 */
	public void addBeitrag(Beitrag beitrag) {
		try {
			dao.addBeitrag(beitrag);
		} catch (SQLException e) {
			throw new NotImplementedException();
		}
	}

	/**
	 * set strings null if no change, set char ' ' if no change
	 */
	public void editSignedOnUserProfile(String password, String fullName, String eMail, char sex, Date birthday) {
		session.setPassword(password);
		session.setFullName(fullName);
		session.setEMail(eMail);
		if(sex != ' ')
			session.setSex(sex);
		session.setBirthday(birthday);
	}

	/**
	 * IMPLEMENT THIS METHODE IN PROTOTYPE 2
	 * @param data_type
	 */
	public String getScientistData(data_type dataType) {
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

	/**
	 * simple login
	 * @param password
	 * @param username
	 */
	public void login(String password, String username) {
		try {
			this.session = dao.getUserByUsername(username);
		} catch (SQLException e) {
			throw new NotImplementedException();
		}
		
		if(!session.getPassword().equals(password)) {
			this.session = null;
		} else {
			throw new NotImplementedException();
		}
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
	 */
	public void registerAdministrator(String username, String password, String fullName, String eMail, char sex, Date birthday) {
		try {
			dao.addUser(new Administrator(username, password, fullName, eMail, sex, birthday));
		} catch (SQLException e) {
			throw new NotImplementedException();
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
	 */
	public void registerForscher(String username, String password, String fullName, String eMail, char sex, Date birthday) {
		try {
			dao.addUser(new Forscher(username, password, fullName, eMail, sex, birthday));
		} catch (SQLException e) {
			throw new NotImplementedException();
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
	 */
	public void registerModerator(String username, String password, String fullName, String eMail, char sex, Date birthday) {
		try {
			dao.addUser(new Moderator(username, password, fullName, eMail, sex, birthday));
		} catch (SQLException e) {
			throw new NotImplementedException();
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
	 */
	public void registerYidiot(String username, String password, String fullName, String eMail, char sex, Date birthday, String description) {
		List<Ydiot> fL = new ArrayList<Ydiot>();
		try {
			dao.addUser(new Ydiot(username, password, fullName, eMail, sex, birthday, description, fL));
		} catch (SQLException e) {
			throw new NotImplementedException();
		}
	}

	/**
	 * remove Beitrag
	 * @param beitrag
	 */
	public void removeBeitrag(Beitrag beitrag) {
		try {
			dao.removeBeitrag(beitrag);
		} catch (SQLException e) {
			throw new NotImplementedException();
		}
	}

	/**
	 * remove User
	 * @param user
	 */
	public void removeUser(AbstractUser user) {
		dao.removeUser(user);
	}

	public String viewAdminWall() {
		return "<HTML></HTML>";
	}

	public String viewMoeratorWall() {
		return "<HTML></HTML>";
	}

	public String viewScientistWall() {
		return "<HTML></HTML>";
	}

	public String viewSignedOnUserWall() {
		return "<HTML></HTML>";
	}

	public String viewUserWall(AbstractUser user) {
		return "<HTML></HTML>";
	}
	
	public AbstractUser getSession() {
		return session;
	}
}
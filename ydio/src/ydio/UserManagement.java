package ydio;

import ydio.user.*;
import java.util.Date;
import ydio.dao.*;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:13
 */
public class UserManagement {

	private AbstractUser session;

	public UserManagement(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param beitrag
	 */
	public void addBeitrag(Beitrag beitrag){

	}

	/**
	 * 
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 */
	public void editSignedOnUserProfile(String password, String fullName, String eMail, char sex, Date birthday){

	}

	/**
	 * 
	 * @param data_type
	 */
	public String getScientistData(data_type dataType){
		return "";
	}

	/**
	 * 
	 * @param password
	 * @param username
	 */
	public void login(String password, String username){

	}

	public void logout(){

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 */
	public void registerAdministrator(String username, String password, String fullName, String eMail, char sex, Date birthday){

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 */
	public void registerForscher(String username, String password, String fullName, String eMail, char sex, Date birthday){

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 */
	public void registerModerator(String username, String password, String fullName, String eMail, char sex, Date birthday){

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param fullName
	 * @param eMail
	 * @param sex
	 * @param birthday
	 * @param description
	 */
	public void registerYidiot(String username, String password, String fullName, String eMail, char sex, Date birthday, String description){

	}

	/**
	 * 
	 * @param beitrag
	 */
	public void removeBeitrag(Beitrag beitrag){

	}

	/**
	 * 
	 * @param user
	 */
	public void removeUser(AbstractUser user){

	}

	public String viewAdminWall(){
		return "";
	}

	public String viewMoeratorWall(){
		return "";
	}

	public String viewScientistWall(){
		return "";
	}

	public String viewSignedOnUserWall(){
		return "";
	}

	/**
	 * 
	 * @param user
	 */
	public String viewUserWall(AbstractUser user){
		return "";
	}

}
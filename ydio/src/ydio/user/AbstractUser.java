package ydio.user;

import java.util.Date;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:05
 */
public abstract class AbstractUser {

	private Date birthday;
	private String eMail;
	private String fullName;
	private String password;
	private char sex;
	private String username;

	public AbstractUser(){

	}

	public void finalize() throws Throwable {

	}

	public Date getBirthday(){
		return null;
	}

	public String getEMail(){
		return "";
	}

	public String getFullName(){
		return "";
	}

	public String getPassword(){
		return "";
	}

	public char getSex(){
		return 0;
	}

	public String getUsername(){
		return "";
	}

	/**
	 * 
	 * @param date
	 */
	public void setBirthday(Date date){

	}

	/**
	 * 
	 * @param email
	 */
	public void setEMail(String email){

	}

	/**
	 * 
	 * @param fullName
	 */
	public void setFullName(String fullName){

	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password){

	}

	/**
	 * 
	 * @param sex
	 */
	public void setSex(char sex){

	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username){

	}

}
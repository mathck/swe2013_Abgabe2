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

	public AbstractUser(String username, String password, String fullName, String email, char sex, Date date) {
		setBirthday(date);
		setEMail(email);
		setFullName(fullName);
		setSex(sex);
		setPassword(password);
		setUsername(username);
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getEMail() {
		return eMail;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPassword() {
		return password;
	}

	public char getSex() {
		return sex;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param date
	 */
	public void setBirthday(Date date) {
		this.birthday = date;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEMail(String email) {
		this.eMail = email;
	}

	/**
	 * 
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @param sex
	 */
	public void setSex(char sex) {
		this.sex = sex;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
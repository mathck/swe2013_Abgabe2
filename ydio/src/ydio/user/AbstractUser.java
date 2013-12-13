package ydio.user;

import java.util.Date;

import ydio.exceptions.*;

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

	public AbstractUser(String username, String password, String fullName, String email, char sex, Date date) throws InvalidEmailInputException, InvalidNameInputException, InvalidSexInputException, InvalidPasswordInputException, InvalidUsernameInputException, InvalidDateInputException {
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

	public void setBirthday(Date date) throws InvalidDateInputException {
		Date now = new Date();
		if(date.after(now))
			throw new InvalidDateInputException();
		
		this.birthday = date;
	}

	public void setEMail(String email) throws InvalidEmailInputException {
		if(email.contains("@"))
			this.eMail = email;
		else
			throw new InvalidEmailInputException();
	}

	public void setFullName(String fullName) throws InvalidNameInputException {
		if(fullName.contains(" "))
			this.fullName = fullName;
		else
			throw new InvalidNameInputException();
	}

	public void setPassword(String password) throws InvalidPasswordInputException {
		if(!(password.length() < 6))
			this.password = password;
		else
			throw new InvalidPasswordInputException();
	}

	public void setSex(char sex) throws InvalidSexInputException {
		if(sex == 'm' || sex == 'f')
			this.sex = sex;
		else
			throw new InvalidSexInputException();
	}

	public void setUsername(String username) throws InvalidUsernameInputException {
		if(!username.contains(" "))
			this.username = username;
		else
			throw new InvalidUsernameInputException();
	}
}
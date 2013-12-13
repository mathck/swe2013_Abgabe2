package ydio.user;

import java.util.Date;
import java.util.List;

import ydio.exceptions.InvalidDateInputException;
import ydio.exceptions.InvalidEmailInputException;
import ydio.exceptions.InvalidNameInputException;
import ydio.exceptions.InvalidPasswordInputException;
import ydio.exceptions.InvalidSexInputException;
import ydio.exceptions.InvalidUsernameInputException;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:07
 */
public class Ydiot extends AbstractUser {

	private String description;
	private List<String> friendList;
	private Date lockedUntil;

	public Ydiot(String username, String password, String fullName, String email, char sex, Date date, String desc, List<String> fL) throws InvalidEmailInputException, InvalidNameInputException, InvalidSexInputException, InvalidPasswordInputException, InvalidUsernameInputException, InvalidDateInputException {
		super(username, password, fullName, email, sex, date);
		this.description = desc;
		this.friendList = fL;
	}
	
	/**
	 * compares Date.Now and Date.lockedUntil and determins if the User is locked
	 * @return true: is Locked, false: is not Locked
	 */
	public boolean isLocked() {
		if(lockedUntil.compareTo(new Date()) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Lock a User, only the Moderator and Admin
	 * @param date_until set a Date
	 */
	public void setLocked(Date date_until) {
		lockedUntil = date_until;
	}
	public Date getLocked() {
		return lockedUntil;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<String> friendList) {
		this.friendList = friendList;
	}
}
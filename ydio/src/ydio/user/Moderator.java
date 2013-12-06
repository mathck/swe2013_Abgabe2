package ydio.user;

import java.util.Date;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:09
 */
public class Moderator extends AbstractUser {

	public Moderator(String username, String password, String fullName, String email, char sex, Date date) {
		super(username, password, fullName, email, sex, date);
	}
}
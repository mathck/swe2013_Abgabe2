package ydio.user;

import java.util.Date;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:08
 */
public class Administrator extends Moderator {

	public Administrator(String username, String password, String fullName, String email, char sex, Date date) {
		super(username, password, fullName, email, sex, date);
	}
}
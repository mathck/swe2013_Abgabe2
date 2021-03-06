package ydio.user;

import java.util.Date;

import ydio.exceptions.InvalidDateInputException;
import ydio.exceptions.InvalidEmailInputException;
import ydio.exceptions.InvalidNameInputException;
import ydio.exceptions.InvalidPasswordInputException;
import ydio.exceptions.InvalidSexInputException;
import ydio.exceptions.InvalidUsernameInputException;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:08
 */
public class Administrator extends Moderator {

	public Administrator(String username, String password, String fullName, String email, char sex, Date date) throws InvalidEmailInputException, InvalidNameInputException, InvalidSexInputException, InvalidPasswordInputException, InvalidUsernameInputException, InvalidDateInputException {
		super(username, password, fullName, email, sex, date);
	}
}
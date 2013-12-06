package ydio.user;

import java.util.Date;
import java.util.List;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:07
 */
public class Ydiot extends AbstractUser {

	private String description;
	private List<Ydiot> friendList;
	private Date lockedUntil;

	public Ydiot(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public boolean isLocked(){
		return false;
	}

	/**
	 * 
	 * @param date_until
	 */
	public void setLocked(Date date_until){

	}

}
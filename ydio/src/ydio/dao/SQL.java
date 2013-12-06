package ydio.dao;

import java.util.List;

import ydio.*;
import ydio.user.*;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:12
 */
public class SQL implements DAO {

	private static final String jdbcDriver = null;
	private static final String password = null;
	private static final String path = null;
	private static final String username = null;

	public SQL(){
		

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
	 * @param user
	 */

	public void addUser(AbstractUser user){

	}

	public List<AbstractUser> getAllUsers(){
		return null;
	}

	public List<Beitrag> getBeitragList(){
		return null;
	}

	/**
	 * 
	 * @param data_type
	 */
	public String[][] getScientistData(data_type dataType){
		return null;
	}

	/**
	 * 
	 * @param username
	 */
	public AbstractUser getUserByUsername(String username){
		return null;
	}

	public List<Ydiot> getUserList(){
		return null;
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

	/**
	 * 
	 * @param searchstring
	 */
	public List<Ydiot> search(String searchstring){
		return null;
	}

	/**
	 * 
	 * @param beitrag
	 */
	public void updateBeitrag(Beitrag beitrag){

	}

	/**
	 * 
	 * @param user
	 */
	public void updateUser(AbstractUser user){

	}

}
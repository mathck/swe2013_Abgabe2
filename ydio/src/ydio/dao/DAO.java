package ydio.dao;

import java.util.List;

import ydio.Beitrag;
import ydio.UserManagement;
import ydio.user.*;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:11
 */
public interface DAO {

//fggf
	/**
	 * 
	 * @param beitrag
	 */
	public void addBeitrag(Beitrag beitrag);

	/**
	 * 
	 * @param user
	 */
	public void addUser(AbstractUser user);

	public List<AbstractUser> getAllUsers();

	public List<Beitrag> getBeitragList();

	/**
	 * 
	 * @param data_type
	 */
	public String[][] getScientistData(data_type dataType);

	/**
	 * 
	 * @param username
	 */
	public AbstractUser getUserByUsername(String username);

	public List<Ydiot> getUserList();

	/**
	 * 
	 * @param beitrag
	 */
	public void removeBeitrag(Beitrag beitrag);

	/**
	 * 
	 * @param user
	 */
	public void removeUser(AbstractUser user);

	/**
	 * 
	 * @param searchstring
	 */
	public List<Ydiot> search(String searchstring);

	/**
	 * 
	 * @param beitrag
	 */
	public void updateBeitrag(Beitrag beitrag);

	/**
	 * 
	 * @param user
	 */
	public void updateUser(AbstractUser user);

}
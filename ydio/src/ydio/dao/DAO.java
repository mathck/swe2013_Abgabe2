package ydio.dao;

import java.sql.SQLException;
import java.util.List;

import ydio.Beitrag;
import ydio.data_type;
import ydio.user.*;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:11
 */
public interface DAO {

	/**
	 * 
	 * @param beitrag
	 * @throws SQLException 
	 */
	public void addBeitrag(Beitrag beitrag) throws SQLException;

	/**
	 * 
	 * @param user
	 * @throws SQLException 
	 */
	public void addUser(AbstractUser user) throws SQLException;

	public List<AbstractUser> getAllUsers() throws SQLException;

	public List<Beitrag> getBeitragList() throws SQLException;

	/**
	 * 
	 * @param data_type
	 */
	public String[][] getScientistData(data_type dataType);

	/**
	 * 
	 * @param username
	 * @throws SQLException 
	 */
	public AbstractUser getUserByUsername(String username) throws SQLException;

	public List<Ydiot> getUserList() throws SQLException;

	/**
	 * 
	 * @param beitrag
	 * @throws SQLException 
	 */
	public void removeBeitrag(Beitrag beitrag) throws SQLException;

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
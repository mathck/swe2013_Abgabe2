package ydio.dao;

import java.io.IOException;
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
	 * @throws IOException 
	 */
	public void addBeitrag(Beitrag beitrag) throws IOException;

	/**
	 * 
	 * @param user
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void addUser(AbstractUser user) throws IOException;

	public List<AbstractUser> getAllUsers() throws IOException;

	public List<Beitrag> getBeitragList() throws IOException;

	/**
	 * 
	 * @param data_type
	 * @throws IOException 
	 */
	public String[][] getScientistData(data_type dataType) throws IOException;

	/**
	 * 
	 * @param username
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public AbstractUser getUserByUsername(String username) throws IOException;

	public List<Ydiot> getUserList() throws IOException;

	/**
	 * 
	 * @param beitrag
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void removeBeitrag(Beitrag beitrag) throws IOException;

	/**
	 * 
	 * @param user
	 * @throws IOException 
	 */
	public void removeUser(AbstractUser user) throws IOException;

	/**
	 * 
	 * @param searchstring
	 * @throws IOException 
	 */
	public List<Ydiot> search(String searchstring) throws IOException;

	/**
	 * 
	 * @param beitrag
	 * @throws IOException 
	 */
	public void updateBeitrag(Beitrag beitrag) throws IOException;

	/**
	 * 
	 * @param user
	 * @throws IOException 
	 */
	public void updateUser(AbstractUser user) throws IOException;

}
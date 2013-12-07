package ydio.dao;

import java.sql.*;
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
	private static final String password = "mypassword";
	private static final String path = "mysql://localhost/ydio";
	private static final String username = "myuser";

	private Connection connection;
	private Statement statement;
	private ResultSet result;
	
	public SQL() throws SQLException {
		String url 
			= "jdbc:" + path
			+ "?user=" + username
			+ "&password=" + password;
		connection = DriverManager.getConnection(url);
		statement = connection.createStatement();
		result = null;
	}

	/**
	 * 
	 * @param beitrag
	 */

	public void addBeitrag(Beitrag beitrag) throws SQLException {
		result = statement.executeQuery("select id from id where type=beitrag");
		long id = result.getLong(0);
		beitrag.setID(id);
		result = statement.executeQuery("update id set id="+(id+1)+" where type=beitrag;");
		String statementString = 
			"insert into beitrag (creator, content, date, ID, recep) values ('" +
			beitrag.getCreator().getUsername() + "', '" +
			beitrag.getContent() + "', '" +
			beitrag.getDate() + "', '" +
			beitrag.getID() + "', '" +
			beitrag.getRecep().getUsername() + "');";				;
		result = statement.executeQuery(statementString);
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
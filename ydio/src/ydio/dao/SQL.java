package ydio.dao;

import java.sql.*;
import java.util.ArrayList;
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
	 * @throws SQLException 
	 */

	public void addUser(AbstractUser user) throws SQLException {
		String columns = "username, email, fullname, password, sex, birthday";
		String values = "'"+
			user.getUsername()+"', '"+
			user.getEMail()+"', '"+
			user.getFullName()+"', '"+
			user.getPassword()+"', '"+
			user.getSex()+"', '"+
			user.getBirthday()+"'";
		if (user instanceof Ydiot) {
			columns = 
				"ydiot ("+columns+", description, lockeduntil)";
			values = 
				values+", '"+
				((Ydiot) user).getDescription()+"', '"+
				null+"'";
		} else if (user instanceof Administrator) {
			columns = "administrator ("+columns+")";
		} else if (user instanceof Moderator) {
			columns = "moderator ("+columns+")";
		} else if (user instanceof Forscher) {
			columns = "forscher ("+columns+")";
		}
		result = statement.executeQuery("insert into"+columns+" values ("+values+");");
	}

	public List<AbstractUser> getAllUsers() throws SQLException{
		List<AbstractUser> list = new ArrayList<AbstractUser> ();
		result = statement.executeQuery("select * from ydiot");
		while (result.next()) {
			list.add(createYdiot(result));
		}
		result = statement.executeQuery("select * from administrator");
		while (result.next()) {
			list.add(createAdministrator(result));
		}
		result = statement.executeQuery("select * from moderator");
		while (result.next()) {
			list.add(createModerator(result));
		}
		result = statement.executeQuery("select * from forscher");
		while (result.next()) {
			list.add(createForscher(result));
		}
		return list;
	}

	public List<Beitrag> getBeitragList() throws SQLException{
		result = statement.executeQuery("select * from beitrag");
		List<Beitrag> list = new ArrayList<Beitrag> ();
		while (result.next()) {
			list.add(createBeitrag(result));
		}
		
		return list;
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
	 * @throws SQLException 
	 */
	public AbstractUser getUserByUsername(String username) throws SQLException{
		result = statement.executeQuery("select * from ydiot where username='"+username+"'");
		if (result.first()) {
			return createYdiot(result);
		}
	}

	public List<Ydiot> getUserList() throws SQLException{
		result = statement.executeQuery("select * from ydiot");
		List<Ydiot> list = new ArrayList<Ydiot> ();
		while (result.next()) list.add(createYdiot(result));
		return list;
	}

	/**
	 * 
	 * @param beitrag
	 * @throws SQLException 
	 */
	public void removeBeitrag(Beitrag beitrag) throws SQLException{
		result = statement.executeQuery("delete from beitrag where id="+beitrag.getID());
	}

	/**
	 * 
	 * @param user
	 * @throws SQLException 
	 */
	public void removeUser(AbstractUser user) throws SQLException{
		String table = null;
		if (user instanceof Administrator) {
			table = "administrator";
		} else if (user instanceof Moderator) {
			table = "moderator";
		} else if (user instanceof Forscher) {
			table = "forscher";
		} else if (user instanceof Ydiot) {
			table = "ydiot";
		}
		result = statement.executeQuery("delet from "+table+" where username='"+user.getUsername()+"'");
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
	private Ydiot createYdiot (ResultSet result) {
		Ydiot user = new Ydiot (
				result.getString("username"), 
				result.getString("password"), 
				result.getString("fullname"), 
				result.getString("email"), 
				result.getString("sex").charAt(0), 
				result.getDate("birthday"), 
				result.getString("description"), 
				null);
			ResultSet friendResult = statement.executeQuery("select * from friendlist where user1='"+result.getString("username")+"';");
			List<String> friendList = new ArrayList<String> ();
			while (result.next()) {
				friendList.add(result.getString("user2"));
			}
			user.setFriendList(friendList);
		return user;
	}
	private Administrator createAdministrator (ResultSet result) throws SQLException {
		Administrator user = new Administrator (
				result.getString("username"), 
				result.getString("password"), 
				result.getString("fullname"), 
				result.getString("email"), 
				result.getString("sex").charAt(0), 
				result.getDate("birthday"));
		return user;
	}
	
	private Moderator createModerator (ResultSet result) throws SQLException {
		Moderator user = new Moderator (
				result.getString("username"), 
				result.getString("password"), 
				result.getString("fullname"), 
				result.getString("email"), 
				result.getString("sex").charAt(0), 
				result.getDate("birthday"));
		return user;
	}
	private Forscher createForscher (ResultSet result) throws SQLException {
		Forscher user = new Forscher (
				result.getString("username"), 
				result.getString("password"), 
				result.getString("fullname"), 
				result.getString("email"), 
				result.getString("sex").charAt(0), 
				result.getDate("birthday"));
		return user;
	}
	private Beitrag createBeitrag (ResultSet result) throws SQLException {
		Beitrag beitrag = new Beitrag (
			result.getString("creator"), 
			result.getString("recipient"), 
			result.getString("content"), 
			null,
			result.getInt("id"), 
			null, 
			null, 
			null, 
			result.getDate("date"));
			// TODO zugehörige Listen laden
		return beitrag;
	}
}
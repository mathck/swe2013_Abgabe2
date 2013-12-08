package ydio.dao;

import java.io.IOException;
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
	private static final String password = "whatever";
	private static final String path = "mysql://localhost/ydio";
	private static final String username = "ydio_java";

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

	public void addBeitrag(Beitrag beitrag) throws IOException {
		try {
			statement = connection.createStatement();
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
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}			
		}
	}

	/**
	 * 
	 * @param user
	 * @throws IOException 
	 */

	public void addUser(AbstractUser user) throws IOException {
		try {
			statement = connection.createStatement();
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
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	public List<AbstractUser> getAllUsers() throws IOException{
		try {
			statement = connection.createStatement();
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
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}
		}
	}

	public List<Beitrag> getBeitragList() throws IOException{	
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from beitrag");
			List<Beitrag> list = new ArrayList<Beitrag> ();
			while (result.next()) {
				list.add(createBeitrag(result));
			}
			return list;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param data_type
	 * @throws IOException 
	 */
	public String[][] getScientistData(data_type dataType) throws IOException{
		try {
			// TODO Forscherzugriff einrichten
			throw new SQLException ();
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param username
	 * @throws IOException 
	 */
	public AbstractUser getUserByUsername(String username) throws IOException{
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from ydiot where username='"+username+"'");
			if (result.first()) {
				return createYdiot(result);
			}
			return null;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}
		}
	}

	public List<Ydiot> getUserList() throws IOException{
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from ydiot");
			List<Ydiot> list = new ArrayList<Ydiot> ();
			while (result.next()) list.add(createYdiot(result));
			return list;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param beitrag
	 * @throws IOException 
	 */
	public void removeBeitrag(Beitrag beitrag) throws IOException{
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("delete from beitrag where id="+beitrag.getID());
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param user
	 * @throws IOException 
	 */
	public void removeUser(AbstractUser user) throws IOException{
		try {
			statement = connection.createStatement();
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
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param searchstring
	 * @throws IOException 
	 */
	public List<Ydiot> search(String searchstring) throws IOException{
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select * from ydiot where username like '"+searchstring+"'");
			List<Ydiot> list = new ArrayList<Ydiot> ();
			while (result.next()) {
				list.add(createYdiot(result));
			}
			result = statement.executeQuery("select * from ydiot where fullname like '"+searchstring+"'");
			while (result.next()) {
				Ydiot temp = createYdiot(result);
				if (!list.contains(temp))
					list.add(temp);
			}
			return list;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param beitrag
	 * @throws IOException 
	 */
	public void updateBeitrag(Beitrag beitrag) throws IOException{
		try {
			statement = connection.createStatement();
			String query = "update beitrag set "+
				"creator='"+beitrag.getCreator().getUsername()+"', "+
				"recipiant='"+beitrag.getRecep().getUsername()+"', "+
				"content='"+beitrag.getContent()+"', "+
				"date='"+beitrag.getDate()+"'"+
				"where id="+beitrag.getID();
			result = statement.executeQuery(query);
			for (int i = 0; i < beitrag.getLikes().size(); i++) {
				// TODO Listen absprechen und dann anpassen
			}
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param user
	 * @throws IOException 
	 */
	public void updateUser(AbstractUser user) throws IOException{
		try {
			//TODO updateUser implementieren
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
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
			//TODO friendList abklären
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
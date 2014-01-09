package ydio.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import ydio.exceptions.YdioException;
import ydio.user.*;

public class MySQL /*implements DatabaseAccess*/ {
	DataSource source;
	Connection connection;
	/*Constructor for Database Connection*/
	public MySQL () throws Exception {
		InitialContext context;
		context = new InitialContext();
		source = (DataSource)context.lookup("java:comp/env/jdbc/ydio");
		if (source == null) throw new IOException ("Data Source not found");
		connection = null;
	}
	/*UserData Access*/
	public List<AbstractUser> getAllUsers () throws IOException {
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = source.getConnection();
			String query = "select * from users";
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			List<AbstractUser> user = new ArrayList<AbstractUser> ();
			while (result.next()) {
				switch (result.getString("type")) {
				case "ydiot":
					user.add(createYdiot(result));
					break;
				case "forscher":
					user.add(createForscher(result));
					break;
				case "moderator":
					user.add(createModerator(result));
					break;
				case "administrator":
					user.add(createAdministrator(result));
					break;
				default:
					throw new IOException ("User Type not recognized.");
				}
			}
			return user;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}
		}
		
	}
	/*create Methoden*/
	Ydiot createYdiot (ResultSet result) throws IOException {
		Ydiot user = null;
		PreparedStatement friendStatement = null;
		ResultSet friendResult = null;
		try {
			user = new Ydiot (
				result.getString("username"), 
				result.getString("password"), 
				result.getString("fullname"), 
				result.getString("email"), 
				result.getString("sex").charAt(0), 
				new Date(result.getDate("birthday").getTime()), 
				result.getString("description"),
				new ArrayList <String> ());
			if (result.getDate("lockeduntil") != null) 
				user.setLocked(new Date(result.getDate("lockeduntil").getTime()));
			friendStatement = connection.prepareStatement("select * from friendlist where user1=?");
			friendStatement.setString(1, result.getString("username"));
			friendResult = friendStatement.executeQuery();
			List<String> friendList = new ArrayList<String> ();
			while (friendResult.next()) {
				friendList.add(friendResult.getString("user2"));
			}
			user.setFriendList(friendList);
			return user;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} catch (YdioException e) {
			// wrong input was saved in database, impossible
		} finally {
			try {
				if (friendResult != null) friendResult.close();
				if (friendStatement != null) friendStatement.close();
			} catch (SQLException e) {}	
		}
		return user;
	}
	Administrator createAdministrator (ResultSet result) throws SQLException {
		Administrator user = null;
		try {
			user = new Administrator (
					result.getString("username"), 
					result.getString("password"), 
					result.getString("fullname"), 
					result.getString("email"), 
					result.getString("sex").charAt(0), 
					new Date(result.getDate("birthday").getTime()));
		} catch (YdioException e) {
			// wrong input was saved in database, impossible
		}
		return user;
	}
	Moderator createModerator (ResultSet result) throws SQLException {
		Moderator user = null;
		try {
			user = new Moderator (
					result.getString("username"), 
					result.getString("password"), 
					result.getString("fullname"), 
					result.getString("email"), 
					result.getString("sex").charAt(0), 
					new Date(result.getDate("birthday").getTime()));
		} catch (YdioException e) {
			// wrong input was saved in database, impossible
		}
		return user;
	}
	Forscher createForscher (ResultSet result) throws SQLException {
		Forscher user = null;
		try {
			user = new Forscher (
					result.getString("username"), 
					result.getString("password"), 
					result.getString("fullname"), 
					result.getString("email"), 
					result.getString("sex").charAt(0), 
					new Date(result.getDate("birthday").getTime()));
		} catch (YdioException e) {
			// wrong input was saved in database, impossible
		}
		return user;
	}
}

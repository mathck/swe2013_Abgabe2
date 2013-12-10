package ydio.dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ydio.*;
import ydio.user.*;

/**
 * Die Klasse SQL implementiert DAO. 
 * Diese Klasse erstellt eine Verbindung zum MySQL Server entsprechend den Benutzerdaten und dem Pfad, der angegeben wurde.
 * @author Benjamin Ilg
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
	
	/**
	 * Creation of SQL Element.
	 * This is the object that the application will work with.
	 * @throws SQLException
	 */
	public SQL() throws IOException {
		try {
			String url 
				= "jdbc:" + path
				+ "?user=" + username
				+ "&password=" + password;
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			result = null;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		}
	}

	/**
	 * Speichert eine Repräsentation des Beitrag Objektes in der MySQL Datenbank.
	 * @param beitrag Das zu speichernde Objekt für die MySQL Datenbank.
	 */
	public void addBeitrag(Beitrag beitrag) throws IOException {
		try {
			statement = connection.createStatement();
			result = statement.executeQuery("select id from id where type=beitrag");
			long id = result.getLong(0);
			beitrag.setID(id);
			result = statement.executeQuery("update id set id="+(id+1)+" where type=beitrag;");
			String statementString = 
				"insert into beitrag (creator, content, creation_date, ID, recipient) values ('" +
				beitrag.getCreator() + "', '" +
				beitrag.getContent() + "', '" +
				beitrag.getDate() + "', '" +
				beitrag.getID() + "', '" +
				beitrag.getRecep() + "');";				;
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
	 * Speichert eine Repräsentation des Benutzers in der Datenbank.
	 * @param user Benutzerobjekt. Es wird die leere Freundesliste nicht übernommen (da keine Daten vorhanden).
	 * @throws IOException Wirft eine Exception, wenn ein Fehler bei der SQL Abfrage vorkommt für Handling im auszuführenden Code.
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
	/**
	 * Gibt alle Benutzer aus der Datenbank zurück, fragt einfach alle Tabellen ydiot, administrator, moderator und forscher ab.
	 * @return List Enthält alle user Elemente aus der Datenbank.
	 * @exception IOException Gibt Fehlermeldung der Datenbank zurück.
	 */
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
	/**
	 * Gibt eine Liste aller Beiträge aus der Datenbank zurück.
	 * @return List<Beitrag> Von SQL Struktur zu Objektstruktur konvertierte Liste der Beiträge
	 * @exception Wird geworfen, wenn bei der Datenbankabfrage Fehler auftreten.
	 */
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
	 * Gibt jeweils eine Tabelle zurück mit statistisch verwertbaren Daten
	 * @param data_type Durch data_type wird definiert welche Tabellen erstellt und zurückgegeben werden
	 * @throws IOException Wird geworfen wenn die Datenbankabfrage Fehler erzeugt.
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
	 * Gibt einen Benutzer mit genau dem Benutzernamen zurück, der angegeben wurde.
	 * @param username String, der im Objekt AbstractUser als Identifikator verwendet wird.
	 * @throws IOException Wird geworfen, wenn Fehler bei der MySQL Abfrage auftreten.
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
	
	/**
	 * Erzeugt eine Liste aller in der MySQL Datenbank gespeicherten Ydioten.
	 * @return List<Ydiot> Liste aller Benutzer in der Datenbank.
	 * @exception Fehlermeldung wird zurückgegeben wenn bei der Datenbankabfrage Fehler auftreten.
	 */
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
	 * Entfernt den spezifizierten Benutzer aus der Tabelle, die mit der user Klasse korrespondiert.
	 * @param user zu löschender Benutzer (es wird in der Abfrage nur der username verwendet, da er die id darstellt)
	 * @throws IOException Wird geworfen, wenn Fehler bei der Ausführung am MySQL Server auftreten.
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
	 * Sucht im Feld username und fullname nach dem angegebenen searchstring
	 * @param searchstring beliebige eingabedaten
	 * @throws IOException Wird zurückgegeben, falls Fehler bei der Ausführung am MySQL Server auftreten.
	 * @return List<Ydiot> Gibt eine Liste aller Benutzer zurück, die bei Abgleich mit SQL LIKE gefunden wurden.
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
	 * Aktualisierung eines Beitrag OBjektes. Hier wird auch die List mit Likes, Dislikes, etc gepflegt.
	 * Diese sind in der MySQL Datenbank über eigene Tabellen realisiert.
	 * @param beitrag In der Datenbank zu aktualisierender Beitrag
	 * @throws IOException Wird ausgegeben, wenn Fehler bei der Ausführung am MySQL Server auftreten.
	 */
	public void updateBeitrag(Beitrag beitrag) throws IOException{
		try {
			statement = connection.createStatement();
			String query = "update beitrag set "+
				"creator='"+beitrag.getCreator()+"', "+
				"recipiant='"+beitrag.getRecep()+"', "+
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
	 * Aktualisiert die Benutzerdaten (vollständig entsprechend dem Objekt)
	 * Hier wird auch die friendList gepflegt, welche über eine eigene Tabelle in der MySQL Datenbank realisiert ist.
	 * @param user Benutzer, der aktualisiert werden soll.
	 * @throws IOException Wird ausgegeben, wenn Fehler bei der Ausführung auf dem MySQL Server auftreten.
	 */
	public void updateUser(AbstractUser user) throws IOException{
		try {
			//TODO updateUser implementieren
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
	
	private Ydiot createYdiot (ResultSet result) throws IOException {
		try {
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
			while (friendResult.next()) {
				friendList.add(friendResult.getString("user2"));
			}
			user.setFriendList(friendList);
			return user;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
			} catch (SQLException e) {}	
		}
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
	private Beitrag createBeitrag (ResultSet result) throws IOException {
		ResultSet tempResult = null;
		try {
			List<String> likeList = new ArrayList<String> ();
			List<String> dislikeList = new ArrayList<String> ();
			List<String> reportList = new ArrayList<String> ();
			List<String> readList = new ArrayList<String> ();
			tempResult = statement.executeQuery("select * from stats where id="+result.getLong("id"));
			while (tempResult.next()) {
				switch (tempResult.getString("type")) {
				case "like":
					likeList.add(tempResult.getString("username"));
					break;
				case "dislike":
					dislikeList.add(tempResult.getString("username"));
					break;
				case "report":
					reportList.add(tempResult.getString("username"));
					break;
				case "read":
					reportList.add(tempResult.getString("username"));
					break;
				default:
					throw new IOException ("type not defined, cannot build stats of beitrag");	
				}
			}
			Beitrag beitrag = new Beitrag (
				result.getString("creator"), 
				result.getString("recipient"), 
				result.getString("content"), 
				dislikeList,
				result.getInt("id"), 
				likeList, 
				readList, 
				reportList, 
				result.getDate("creation_date"));
			return beitrag;
		} catch (Throwable e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				tempResult.close();
			} catch (SQLException e) {}
		}
		
	}
}
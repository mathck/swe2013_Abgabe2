package ydio.dao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import ydio.*;
import ydio.exceptions.YdioException;
import ydio.user.*;

/**
 * Die Klasse SQL implementiert DAO. 
 * Diese Klasse erstellt eine Verbindung zum MySQL Server entsprechend den Benutzerdaten und dem Pfad, der angegeben wurde.
 * @author Benjamin Ilg
 * @version 1.0
 * @created 04-Dez-2013 20:51:12
 */
public class MySQL implements DatabaseAccess {
	
	private DataSource source;
	private Connection connection;
	
	/**
	 * Erstellt eine Instanz von SQL und l�dt die Resource f�r den MySQL Zugriff f�r die sp�teren Queries.
	 * Getestet und funktioniert.
	 * @throws SQLException
	 */
	public MySQL() throws IOException {
		try {
			connection = null;
			InitialContext context = new InitialContext();
			source = (DataSource)context.lookup("java:comp/env/jdbc/ydio");
			if (source == null) throw new IOException ("Data Source not found");
		} catch (NamingException e) {
			throw new IOException (e.getMessage());
		}
		
	}

	/**
	 * Speichert eine Repräsentation des Beitrag Objektes in der MySQL Datenbank.
	 * Getestet und funktioniert.
	 * @param beitrag Das zu speichernde Objekt für die MySQL Datenbank.
	 */
	public void addBeitrag(Beitrag beitrag) throws IOException {
		PreparedStatement addStatement = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = source.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery("select id from id where type='beitrag'");
			
			if (!result.next()) throw new IOException ("No ID specified in database.");
			long id = result.getLong("id");
			beitrag.setID(id);
			statement.executeUpdate("update id set id='"+(id+1)+"' where type='beitrag'");
			addStatement = connection.prepareStatement(
					"insert into beitrag (creator, recipient, content, creation_date, ID) "
					+ "values (?, ?, ?, ?, ?)");
			addStatement.setString(1, beitrag.getCreator());
			addStatement.setString(2, beitrag.getRecep());
			addStatement.setString(3, beitrag.getContent());
			addStatement.setDate(4, new java.sql.Date (beitrag.getDate().getTime()));
			addStatement.setInt(5, (int) beitrag.getID());
			addStatement.executeUpdate();
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (addStatement != null) addStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}			
		}
	}

	/**
	 * Speichert eine Repräsentation des Benutzers in der Datenbank.
	 * @param user Benutzerobjekt. Es wird die leere Freundesliste nicht übernommen (da keine Daten vorhanden).
	 * @throws IOException Wirft eine Exception, wenn ein Fehler bei der SQL Abfrage vorkommt für Handling im auszuführenden Code.
	 */
	public void addUser(AbstractUser user) throws IOException {
		// Originalversion
		/* ResultSet result = null;
		Statement statement = null;
		try {
			connection = source.getConnection();
			statement = connection.createStatement();
			if (getUserByUsername(user.getUsername()) != null) return;
			String columns = "username, email, fullname, password, sex, birthday";
			String values = "'"+
				user.getUsername()+"', '"+
				user.getEMail()+"', '"+
				user.getFullName()+"', '"+
				user.getPassword()+"', '"+
				user.getSex()+"', '"+
				new java.sql.Date (user.getBirthday().getTime())+"'"; 
			if (user instanceof Ydiot) {
				columns = 
					"ydiot ("+columns+", description, lockeduntil)";
				values = 
					values+", '"+
					((Ydiot) user).getDescription()+"', "+
					"null";
			} else if (user instanceof Administrator) {
				columns = "administrator ("+columns+")";
			} else if (user instanceof Moderator) {
				columns = "moderator ("+columns+")";
			} else if (user instanceof Forscher) {
				columns = "forscher ("+columns+")";
			}
			statement.executeUpdate("insert into"+columns+" values ("+values+")");
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		} */
		// Version, die table user verwendet
		PreparedStatement add = null;
		try {
			String sql = "insert into user (username, password, fullname, email, sex, birthday, type, description) values (?, ?, ?, ?, ?, ?, ?, ?)";
			if (!(user instanceof Ydiot)) {
				
			}
			connection = source.getConnection();
			add = connection.prepareStatement(sql);
			add.setString(1, user.getUsername());
			add.setString(2, user.getPassword());
			add.setString(3, user.getFullName());
			add.setString(4, user.getEMail());
			add.setString(5, ""+user.getSex());
			add.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
			if (user instanceof Ydiot) {
				add.setString(7, "ydiot");
				add.setString(8, ((Ydiot) user).getDescription());
			} else {
				sql.replace(", ?)", ")");
				if (user instanceof Administrator) {
					add.setString(7, "administrator");
				} else if (user instanceof Moderator) {
					add.setString(7, "moderator");
				} else if (user instanceof Forscher) {
					add.setString(7, "forscher");
				}
			}
			add.executeUpdate();
		} catch (Throwable e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (add != null) add.close();
				if (connection != null) connection.close();
			} catch (Throwable e) {}
		} 
	}
	/**
	 * Gibt alle Benutzer aus der Datenbank zurück, fragt einfach alle Tabellen ydiot, administrator, moderator und forscher ab.
	 * @return List Enthält alle user Elemente aus der Datenbank.
	 * @exception IOException Gibt Fehlermeldung der Datenbank zurück.
	 */
	public List<AbstractUser> getAllUsers() throws IOException {
		/* ResultSet result = null;
		Statement statement = null;
		try {
			connection = source.getConnection();
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
				if (connection != null) connection.close();
			} catch (SQLException e) {}
		} */
		ResultSet result = null;
		Statement statement = null;
		List<AbstractUser> list = new ArrayList<AbstractUser> ();
		try {
			connection = source.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery("select * from user");
			while (result.next()) {
				switch (result.getString("type")) {
				case "ydiot":
					list.add(createYdiot(result));
					break;
				case "administrator":
					list.add(createAdministrator(result));
					break;
				case "moderator":
					list.add(createModerator(result));
					break;
				case "forscher":
					list.add(createForscher(result));
				}
			}
			return list;
		} catch (Throwable e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (Throwable e) {}
		}
	}
	/**
	 * Gibt eine Liste aller Beiträge aus der Datenbank zurück.
	 * @return List<Beitrag> Von SQL Struktur zu Objektstruktur konvertierte Liste der Beiträge
	 * @exception Wird geworfen, wenn bei der Datenbankabfrage Fehler auftreten.
	 */
	public List<Beitrag> getBeitragList(String username) throws IOException{
		ResultSet result = null;
		PreparedStatement get = null;
		try {
			connection = source.getConnection();
			get = connection.prepareStatement("select * from beitrag where username=?");
			get.setString(1, username);
			result = get.executeQuery();
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
				if (get != null) get.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * Gibt jeweils eine Tabelle zurück mit statistisch verwertbaren Daten
	 * @param data_type Durch data_type wird definiert welche Tabellen erstellt und zurückgegeben werden
	 * @throws IOException Wird geworfen wenn die Datenbankabfrage Fehler erzeugt.
	 */
	public String[][] getScientistData(data_type dataType) throws IOException{
		ResultSet result = null;
		Statement statement = null;
		try {
			// TODO Forscherzugriff einrichten
			throw new SQLException ();
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

	/**
	 * Gibt einen Benutzer mit genau dem Benutzernamen zurück, der angegeben wurde.
	 * @param username String, der im Objekt AbstractUser als Identifikator verwendet wird.
	 * @throws IOException Wird geworfen, wenn Fehler bei der MySQL Abfrage auftreten.
	 */
	public AbstractUser getUserByUsername(String username) throws IOException{
		/* ResultSet result = null;
		PreparedStatement get = null;
		try {
			AbstractUser user = null;
			connection = source.getConnection();
			statement = connection.createStatement();
			result = statement.executeQuery("select * from ydiot where username='"+username+"'");
			if (result.next()) {
				user = createYdiot(result);
			}
			result = statement.executeQuery("select * from administrator where username='"+username+"'");
			if (result.next()) {
				user = createAdministrator(result);
			}
			result = statement.executeQuery("select * from moderator where username='"+username+"'");
			if (result.next()) {
				user = createModerator(result);
			}
			result = statement.executeQuery("select * from forscher where username='"+username+"'");
			if (result.next()) {
				user = createForscher(result);
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
		} */
		ResultSet result = null;
		PreparedStatement get = null;
		try {
			connection = source.getConnection();
			get = connection.prepareStatement("select * from user where username=?");
			get.setString(1, username);
			result = get.executeQuery();
			if (result.next()) {
				switch (result.getString("type")) {
				case "ydiot":
					return createYdiot(result);
				case "adminstrator":
					return createAdministrator(result);
				case "moderator":
					return createModerator(result);
				case "forscher":
					return createForscher(result);
				default:
					throw new IOException ("Unknown user type");
				}
			}
			return null;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (get != null) get.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}
		}
	}
	
	/**
	 * Erzeugt eine Liste aller in der MySQL Datenbank gespeicherten Ydioten.
	 * @return List<Ydiot> Liste aller Benutzer in der Datenbank.
	 * @exception Fehlermeldung wird zurückgegeben wenn bei der Datenbankabfrage Fehler auftreten.
	 */
	public List<Ydiot> getUserList() throws IOException{
		ResultSet result = null;
		Statement statement = null;
		try {
			connection = source.getConnection();
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
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * 
	 * @param beitrag
	 * @throws IOException 
	 */
	public void removeBeitrag(Beitrag beitrag) throws IOException{
		PreparedStatement delete = null;
		try {
			connection = source.getConnection();
			delete = connection.prepareStatement("delete form beitrag where id=?");
			delete.setLong(1, beitrag.getID());
			delete.executeUpdate();
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (delete != null) delete.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * Entfernt den spezifizierten Benutzer aus der Tabelle, die mit der user Klasse korrespondiert.
	 * @param user zu löschender Benutzer (es wird in der Abfrage nur der username verwendet, da er die id darstellt)
	 * @throws IOException Wird geworfen, wenn Fehler bei der Ausführung am MySQL Server auftreten.
	 */
	public void removeUser(AbstractUser user) throws IOException{
		/*Statement statement = null;
		try {
			connection = source.getConnection();
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
			statement.executeUpdate("delet from "+table+" where username='"+user.getUsername()+"'");
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		}*/
		PreparedStatement delete = null;
		try {
			connection = source.getConnection();
			delete = connection.prepareStatement("delete from user where username=?");
			delete.setString(1, user.getUsername());
			delete.executeUpdate();
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (delete != null) delete.close();
				if (connection != null) connection.close();
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
		/* PreparedStatement statement = null;
		ResultSet result = null;
		try {
			connection = source.getConnection();
			statement = connection.prepareStatement("select * from ydiot where username like ?");
			result = statement.executeQuery();
			List<Ydiot> list = new ArrayList<Ydiot> ();
			while (result.next()) {
				list.add(createYdiot(result));
			}
			statement.close();
			statement = connection.prepareStatement("select * from ydiot where fullname like ?");
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
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		} */
		PreparedStatement search = null;
		ResultSet result = null;
		try {
			List<Ydiot> list = new ArrayList<Ydiot> ();
			connection = source.getConnection();
			search = connection.prepareStatement("select * from user where (username like ? or fullname like ?) and type=?");
			search.setString(1, searchstring);
			search.setString(2, searchstring);
			search.setString(3, "ydiot");
			result = search.executeQuery();
			while (result.next()) {
				list.add(createYdiot(result));
			}
			return list;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (search != null) search.close();
				if (connection != null) connection.close();
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
		PreparedStatement update = null;
		ResultSet result = null;
		Statement statement = null;
		try {
			connection = source.getConnection();
			statement = connection.createStatement();
			update = connection.prepareStatement("update beitrag set creator=?, recipient=?, content=?, date=? where id=?");
			/*String query = "update beitrag set "+
				"creator='"+beitrag.getCreator()+"', "+
				"recipient='"+beitrag.getRecep()+"', "+
				"content='"+beitrag.getContent()+"', "+
				"date='"+new java.sql.Date(beitrag.getDate().getTime())+"'"+
				"where id="+beitrag.getID();*/
			update.setString(1, beitrag.getCreator());
			update.setString(2, beitrag.getRecep());
			update.setString(3, beitrag.getContent());
			update.setDate(4, new java.sql.Date (beitrag.getDate().getTime()));
			update.setLong(5, beitrag.getID());
			update.executeUpdate();
			result = statement.executeQuery("select * from stats where id="+beitrag.getID());
			List<String> compare = new ArrayList<String> ();
			List<String> compareRead = new ArrayList<String> ();
			List<String> temp = new ArrayList<String> ();
			while (result.next()) {
				if (result.getString("type").equals("read")) compare.add(result.getString("username"));
				else compareRead.add(result.getString("username"));
			}
			temp.addAll(beitrag.getLikes());
			temp.removeAll(compare);
			for (int i = 0; i < temp.size(); i++) {
				statement.executeUpdate("insert into stats (id, username, type) values ("+
					beitrag.getID()+", '"+
					temp.get(i)+"', 'like')");
			}
			temp.clear();
			temp.addAll(beitrag.getDislikes());
			temp.removeAll(compare);
			for (int i = 0; i < temp.size(); i++) {
				statement.executeUpdate("insert into stats (id, username, type) values ("+
					beitrag.getID()+", '"+
					temp.get(i)+"', 'dislike')");
			}
			temp.clear();
			temp.addAll(beitrag.getReportList());
			temp.removeAll(compare);
			for (int i = 0; i < temp.size(); i++) {
				statement.executeUpdate("insert into stats (id, username, type) values ("+
					beitrag.getID()+", '"+
					temp.get(i)+"', 'report')");
			}
			temp.clear();
			temp.addAll(beitrag.getReadList());
			temp.removeAll(compareRead);
			for (int i = 0; i < temp.size(); i++) {
				statement.executeUpdate("insert into stats (id, username, type) values ("+
					beitrag.getID()+", '"+
					temp.get(i)+"', 'read')");
			}
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (update != null) update.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		}
	}

	/**
	 * Aktualisiert die Benutzerdaten (vollständig entsprechend dem Objekt)
	 * Hier wird auch die friendList gepflegt, welche über eine eigene Tabelle in der MySQL Datenbank realisiert ist.
	 * Bei der im user Objekt gegebenen friendList wird davon ausgegangen, dass diese die aktuellste Version ist, d.h.
	 * hat es mehr Einträge als die MySQL Datenbank, dann werden Einträge in der DB hinzugefügt, hat es weniger Ein-
	 * träge, dann werden Einträge in der DB gelöscht.
	 * @param user Benutzer, der aktualisiert werden soll.
	 * @throws IOException Wird ausgegeben, wenn Fehler bei der Ausführung auf dem MySQL Server auftreten.
	 */
	public void updateUser(AbstractUser user) throws IOException{
		/* try {
			connection = source.getConnection();
			statement = connection.createStatement();
			String table = null;
			String setColumns = 
				"password='"+user.getPassword()+"', "
				+ "fullname='"+user.getFullName()+"', "
				+ "email='"+user.getEMail()+"', "
				+ "birthday="+new java.sql.Date(user.getBirthday().getTime())+", "
				+ "sex='"+user.getSex()+"' "
				+ "where username='"+user.getUsername()+"'";
			if (user instanceof Ydiot) {
				table = "ydiot";
				java.sql.Date lockedUntil = null;
				if (((Ydiot) user).getLocked() != null) lockedUntil = new java.sql.Date(((Ydiot) user).getLocked().getTime());
				setColumns = setColumns
					+ "lockeduntil="+lockedUntil+", "
					+ "description='"+((Ydiot) user).getDescription()+"'";
				result = statement.executeQuery("select * from friendlist where user1='"+user.getUsername()+"'");
				List<String> temp = ((Ydiot) user).getFriendList();
				List<String> oldList = new ArrayList<String> ();
				while (result.next()) {
					oldList.add(result.getString("user2"));
				}
				if (temp.containsAll(oldList)) {
					temp.removeAll(oldList);
					for (int i = 0; i < temp.size(); i++) {
						statement.executeUpdate("insert into friendlist (user1, user2) values ('"
							+ user.getUsername()+"', '"
							+ temp.get(i)+"')");
					}
				} else {
					oldList.removeAll(temp);
					for (int i = 0; i < oldList.size(); i++) {
						statement.executeQuery("delete from friendlist where "
							+ "user1='"+user.getUsername()+"' and "
							+ "user2='"+oldList.get(i)+"'");
					}
				}
			} else if (user instanceof Administrator) {
				table = "administrator";
			} else if (user instanceof Moderator) {
				table = "moderator";
			} else if (user instanceof Forscher) {
				table = "forscher";
			}
			result = statement.executeQuery("update "+table+" set "+setColumns);
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (statement != null) statement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		} */
		PreparedStatement update = null;
		ResultSet result = null;
		try {
			connection = source.getConnection();
			update = connection.prepareStatement("update user password=?, email=?, fullname=?, sex=?, birthday=?, description=?, lockeduntil=? where username=?");
			update.setString(1, user.getPassword());
			update.setString(2, user.getEMail());
			update.setString(3, user.getFullName());
			update.setString(4, ""+user.getSex());
			update.setDate(5, new java.sql.Date (user.getBirthday().getTime()));
			if (user instanceof Ydiot) {
				update.setString(6, ((Ydiot) user).getDescription());
				update.setDate(7, new java.sql.Date(((Ydiot) user).getLocked().getTime()));
			}
			else {
				update.setNull(6, java.sql.Types.VARCHAR);
				update.setNull(7, java.sql.Types.DATE);
			}
			update.setString(8, user.getUsername());
			update.executeUpdate();
			if (user instanceof Ydiot) {
				update.close();
				update = connection.prepareStatement("select * from friendlist where user1=?");
				update.setString(1, user.getUsername());
				result = update.executeQuery();
				List <String> compare = ((Ydiot) user).getFriendList();
				String temp = null;
				while (result.next()) {
					temp = result.getString("user2");
					if (compare.contains(temp)) compare.remove(temp);
					else {
						update.close();
						update = connection.prepareStatement("delete from friendlist where user1=? and user2=?");
						update.setString(1, user.getUsername());
						update.setString(2, temp);
						update.executeUpdate();
					}
				}
				for (int i = 0; i < compare.size(); i++) {
					update.close();
					update = connection.prepareStatement("insert into friendlist (user1, user2) values (?, ?)");
					update.setString(1, user.getUsername());
					update.setString(2, compare.get(i));
					update.executeUpdate();
				}
			}
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (result != null) result.close();
				if (update != null) update.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {}	
		}
	}
	
	private Ydiot createYdiot (ResultSet result) throws IOException {
		Ydiot user = null;
		Statement friendStatement = null;
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
			friendStatement = connection.createStatement();
			friendResult = friendStatement.executeQuery("select * from friendlist where user1='"+result.getString("username")+"'");
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
	private Administrator createAdministrator (ResultSet result) throws SQLException {
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
	private Moderator createModerator (ResultSet result) throws SQLException {
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
	private Forscher createForscher (ResultSet result) throws SQLException {
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
	private Beitrag createBeitrag (ResultSet result) throws IOException {
		ResultSet tempResult = null;
		Statement tempStatement = null;
		try {
			List<String> likeList = new ArrayList<String> ();
			List<String> dislikeList = new ArrayList<String> ();
			List<String> reportList = new ArrayList<String> ();
			List<String> readList = new ArrayList<String> ();
			tempStatement = connection.createStatement();
			tempResult = tempStatement.executeQuery("select * from stats where id="+result.getLong("id"));
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
				result.getLong("id"), 
				likeList, 
				readList, 
				reportList, 
				new Date(result.getDate("creation_date").getTime()));
			return beitrag;
		} catch (Throwable e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (tempResult != null) tempResult.close();
				if (tempStatement != null) tempStatement.close();
			} catch (SQLException e) {}
		}
		
	}
}
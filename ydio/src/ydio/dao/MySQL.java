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

	private enum Action {
		INSERT, UPDATE, DELETE, SELECT;
	}
	private enum Stats {
		LIKE, DISLIKE, REPORT, READ;
	}
	private enum UserType {
		YDIOT, ADMINISTRATOR, MODERATOR, FORSCHER; 
	}
	
	private DataSource source;
	private Connection connection;
	
	/**
	 * Erstellt eine Instanz von SQL und l�dt die Resource f�r den MySQL Zugriff f�r die sp�teren Queries.
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
	public Beitrag getBeitrag (long id) throws IOException {
		PreparedStatement get = null;
		ResultSet result = null;
		try {
			connection = source.getConnection();
			get = connection.prepareStatement("select * from beitrag where id=?");
			get.setLong(1, id);
			result = get.executeQuery();
			if (result.next()) return createBeitrag(result);
			else return null;
		} catch (SQLException e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (get != null) get.close();
				if (result != null) result.close();
				if (connection != null) connection.close();
			} catch (Throwable e) {}
		}
	}
	/**
	 * Speichert eine Repräsentation des Benutzers in der Datenbank.
	 * @param user Benutzerobjekt. Es wird die leere Freundesliste nicht übernommen (da keine Daten vorhanden).
	 * @throws IOException Wirft eine Exception, wenn ein Fehler bei der SQL Abfrage vorkommt für Handling im auszuführenden Code.
	 */
	public void addUser(AbstractUser user) throws IOException {
		PreparedStatement add = null;
		PreparedStatement test = null;
		ResultSet testResult = null;
		try {
			String sql = "insert into user (username, password, fullname, email, sex, birthday, type, description, lockeduntil) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			connection = source.getConnection();
			test = connection.prepareStatement("select * from user where username=?");
			test.setString(1, user.getUsername());
			testResult = test.executeQuery();
			if (testResult.next()) throw new IOException ("User already exists in database.");
			add = connection.prepareStatement(sql);
			add.setString(1, user.getUsername());
			add.setString(2, user.getPassword());
			add.setString(3, user.getFullName());
			add.setString(4, user.getEMail());
			add.setString(5, ""+user.getSex());
			add.setDate(6, new java.sql.Date(user.getBirthday().getTime()));
			add.setDate(9, new java.sql.Date (0));
			if (user instanceof Ydiot) {
				add.setString(7, "ydiot");
				add.setString(8, ((Ydiot) user).getDescription());
			} else {
				add.setString(8, "");
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
				if (testResult != null) testResult.close();
				if (test != null) test.close();
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
	public List<Beitrag> getBeitragList(boolean reported) throws IOException {
		ResultSet result = null;
		PreparedStatement get = null;
		try {
			connection = source.getConnection();
			get = connection.prepareStatement("select * from beitrag");
			result = get.executeQuery();
			List<Beitrag> list = new ArrayList<Beitrag> ();
			while (result.next()) {
				list.add(createBeitrag(result));
			}
			
			if (reported) {
				List<Beitrag> temp = new ArrayList<Beitrag> ();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getReportList().size() > 0) {
						temp.add(list.get(i));
					}
				}
				list = temp;
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
	public List<Beitrag> getBeitragList(String username) throws IOException{
		ResultSet result = null;
		PreparedStatement get = null;
		try {
			connection = source.getConnection();
			get = connection.prepareStatement("select * from beitrag where recipient=?");
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
	public List<String[]> getScientistData(String typeString) throws IOException{
		ResultSet result = null;
		Statement statement = null;
		data_type dataType = null;
		if (typeString != null) {
			switch (typeString) {
			case "ydiot":
				dataType = data_type.YDIOT_STATS;
				break;
			case "beitrag":
				dataType = data_type.BEITRAG_STATS;
				break;
			default:
				throw new IOException ("DataType not known");
			}
		} else 
			throw new IOException ("Null String cannot be used for creating stats.");
		try {
			List<String[]> list = new ArrayList<String[]> ();
			connection = source.getConnection();
			statement = connection.createStatement();
			String sql = null;
			String[] temp = null;
			switch (dataType) {
			case BEITRAG_STATS:
				sql = "select * from beitrag";
				result = statement.executeQuery(sql);
				while (result.next()) {
					Beitrag beitrag = createBeitrag(result);
					temp = new String [7];
					temp [0] = beitrag.getDate().toString();
					temp [1] = beitrag.getCreator().equals(beitrag.getRecep()) + "";
					temp [2] = beitrag.getContent();
					temp [3] = beitrag.getLikes().size() + "";
					temp [4] = beitrag.getDislikes().size() + "";
					temp [5] = beitrag.getReportList().size() + "";
					temp [6] = beitrag.getReadList().size() + "";
					list.add(temp);
				}
				break;
			case YDIOT_STATS:
				List<Ydiot> listYdiot = new ArrayList<Ydiot>();
				List<Beitrag> listBeitrag = new ArrayList<Beitrag>();
				sql = "select * from user where type='ydiot'";
				result = statement.executeQuery(sql);
				while (result.next()) listYdiot.add(createYdiot(result));
				sql = "select * from beitrag";
				result = statement.executeQuery(sql);
				while (result.next()) listBeitrag.add(createBeitrag(result));
				String user = null;
				Beitrag beitrag = null;
				for (int i = 0; i < listYdiot.size(); i++) {
					int create_beitrag = 0, create_read = 0, create_like = 0, create_dislike = 0, create_report = 0;
					int give_read = 0, give_like = 0, give_dislike = 0, give_report = 0;
					user = listYdiot.get(i).getUsername();
					for (int j = 0; j < listBeitrag.size(); j++) {
						beitrag = listBeitrag.get(j);
						if (listBeitrag.get(j).getCreator().equals(user)) {
							create_beitrag++;
							create_read += beitrag.getReadList().size();
							create_like += beitrag.getCountLikes();
							create_dislike += beitrag.getCountDislike();
							create_report += beitrag.getCountReports();
						}
						if (beitrag.getRead(user)) give_read++;
						if (beitrag.getLike(user)) give_like++;
						if (beitrag.getDislike(user)) give_dislike++;
						if (beitrag.getReport(user)) give_report++;
					}
					temp = new String[11];
					temp[0] = listYdiot.get(i).getBirthday().toString();
					temp[1] = listYdiot.get(i).getSex() + "";
					temp[2] = create_beitrag + "";
					temp[3] = create_read + "";
					temp[4] = create_like + "";
					temp[5] = create_dislike + "";
					temp[6] = create_report + "";
					temp[7] = give_read + "";
					temp[8] = give_like + "";
					temp[9] = give_dislike + "";
					temp[10] = give_report + "";
					list.add(temp);
				}
				break;
			default:
				throw new IOException ("data_type not known");
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
		}
	}

	/**
	 * Gibt einen Benutzer mit genau dem Benutzernamen zurück, der angegeben wurde.
	 * @param username String, der im Objekt AbstractUser als Identifikator verwendet wird.
	 * @throws IOException Wird geworfen, wenn Fehler bei der MySQL Abfrage auftreten.
	 */
	public AbstractUser getUserByUsername(String username) throws IOException{
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
				case "administrator":
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
			delete = connection.prepareStatement("delete from beitrag where id=?");
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
		PreparedStatement search = null;
		ResultSet result = null;
		try {
			List<Ydiot> list = new ArrayList<Ydiot> ();
			connection = source.getConnection();
			search = connection.prepareStatement("select * from user where (username like ? or fullname like ?) and type=?");
			searchstring = "%"+searchstring+"%";
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
			update = connection.prepareStatement("update beitrag set creator=?, recipient=?, content=?, creation_date=? where id=?");
			update.setString(1, beitrag.getCreator());
			update.setString(2, beitrag.getRecep());
			update.setString(3, beitrag.getContent());
			update.setDate(4, new java.sql.Date (beitrag.getDate().getTime()));
			update.setLong(5, beitrag.getID());
			update.executeUpdate();
			result = statement.executeQuery("select * from stats where id="+beitrag.getID());
			List<String> compareLike = beitrag.getLikes();
			List<String> compareDislike = beitrag.getDislikes();
			List<String> compareReport = beitrag.getReportList();
			List<String> compareRead = beitrag.getReadList();
			String temp = null;
			
			// new version
			Action action = Action.DELETE;
			Long beitragId = beitrag.getID();
			Stats type = null;
			while (result.next()) {
				temp = result.getString("username");
				if (compareRead.contains(temp)) compareRead.remove(temp);
				else updateBeitragStats(action, beitragId, temp, Stats.READ);
				switch (type = Stats.valueOf(result.getString("type"))) {
				case LIKE:
					if (compareLike.contains(temp)) compareLike.remove(temp);
					else updateBeitragStats(action, beitragId, temp, type);
					break;
				case DISLIKE:
					if (compareDislike.contains(temp)) compareDislike.remove(temp);
					else updateBeitragStats(action, beitragId, temp, type);
					break;
				case REPORT:
					if (compareReport.contains(temp)) compareReport.remove(temp);
					else updateBeitragStats(action, beitragId, temp, type);
					break;
				case READ:
					break;
				default: throw new IOException ("Unknown rating type.");
				}
			}
			action = Action.INSERT;
			for (int i = 0; i < compareRead.size(); i++)
				this.updateBeitragStats(action, beitragId, compareRead.get(i), Stats.READ);
			for (int i = 0; i < compareLike.size(); i++)
				this.updateBeitragStats(action, beitragId, compareLike.get(i), Stats.LIKE);
			for (int i = 0; i< compareDislike.size(); i++)
				this.updateBeitragStats(action, beitragId, compareDislike.get(i), Stats.DISLIKE);
			for (int i = 0; i < compareReport.size(); i++)
				this.updateBeitragStats(action, beitragId, compareReport.get(i), Stats.REPORT);
			// end new version
			
			/*while (result.next()) {
				if (result.getString("type").equals(Stats.READ.toString())) compare.add(result.getString("username"));
				else compareRead.add(result.getString("username"));
			}
			temp.addAll(beitrag.getLikes());
			temp.removeAll(compare);
			String query = "insert into stats (id, username, type) values (?,?,?)";
			for (int i = 0; i < temp.size(); i++) {
				update.close();
				update = connection.prepareStatement(query);
				update.setLong(1, beitrag.getID());
				update.setString(2, temp.get(i));
				update.setString(3, "like");
			}
			temp.clear();
			temp.addAll(beitrag.getDislikes());
			temp.removeAll(compare);
			for (int i = 0; i < temp.size(); i++) {
				update.close();
				update = connection.prepareStatement(query);
				update.setLong(1, beitrag.getID());
				update.setString(2, temp.get(i));
				update.setString(3, "dislike");
			}
			temp.clear();
			temp.addAll(beitrag.getReportList());
			temp.removeAll(compare);
			for (int i = 0; i < temp.size(); i++) {
				update.close();
				update = connection.prepareStatement(query);
				update.setLong(1, beitrag.getID());
				update.setString(2, temp.get(i));
				update.setString(3, "report");
			}
			temp.clear();
			temp.addAll(beitrag.getReadList());
			temp.removeAll(compareRead);
			for (int i = 0; i < temp.size(); i++) {
				update.close();
				update = connection.prepareStatement(query);
				update.setLong(1, beitrag.getID());
				update.setString(2, temp.get(i));
				update.setString(3, "read");
			}*/
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
		PreparedStatement update = null;
		ResultSet result = null;
		try {
			connection = source.getConnection();
			update = connection.prepareStatement("update user set password=?, email=?, fullname=?, sex=?, birthday=?, description=?, lockeduntil=? where username=?");
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
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (friendResult != null) friendResult.close();
				if (friendStatement != null) friendStatement.close();
			} catch (SQLException e) {}	
		}
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
		PreparedStatement tempStatement = null;
		try {
			List<String> likeList = new ArrayList<String> ();
			List<String> dislikeList = new ArrayList<String> ();
			List<String> reportList = new ArrayList<String> ();
			List<String> readList = new ArrayList<String> ();
			tempStatement = connection.prepareStatement("select * from stats where id=?");
			tempStatement.setLong(1, result.getLong("id"));
			tempResult = tempStatement.executeQuery();
			while (tempResult.next()) {
				readList.add(tempResult.getString("username"));
				switch (Stats.valueOf(tempResult.getString("type"))) {
				case LIKE:
					likeList.add(tempResult.getString("username"));
					break;
				case DISLIKE:
					dislikeList.add(tempResult.getString("username"));
					break;
				case REPORT:
					reportList.add(tempResult.getString("username"));
					break;
				case READ:
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
	void updateFriendList (boolean delete, String user1, String user2) throws IOException {
		PreparedStatement update = null;
		try {
			String sql = null;
			if (delete) {
				sql = "delete from friendlist where user1=? user2=?";
			} else {
				sql = "insert into friendlist (user1, user2) values (?, ?)";
			}
			update = connection.prepareStatement(sql);
			update.setString(1, user1);
			update.setString(2, user2);
			update.executeUpdate();
		} catch (Throwable e) {
			throw new IOException(e.getMessage());
		} finally {
			try {
				if (update != null) connection.close();
			} catch (Throwable e) {}
		}
	}
	void updateBeitragStats (Action action, long beitragId, String username, Stats type) throws IOException {
		PreparedStatement update = null;
		try {
			String sql = null;
			if (action.equals(Action.DELETE)) {
				sql = "delete from stats where id=?, username=?, type=?";
			} else {
				sql = "insert into stats (id, username, type) values (?, ?, ?)";
			}
			update = connection.prepareStatement(sql);
			update.setLong(1, beitragId);
			update.setString(2, username);
			update.setString(3, type.toString());
			update.executeUpdate();
		} catch (Throwable e) {
			throw new IOException (e.getMessage());
		} finally {
			try {
				if (update != null) update.close();
			} catch (Throwable e) {}
		}
	}
}
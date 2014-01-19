package ydio.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ydio.Beitrag;
import ydio.data_type;
import ydio.user.*;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:11
 */
public interface DatabaseAccess {

	/**
	 * Hinzufügen des neuen Beitrag Objektes zur Datenbank.
	 * @param beitrag zu speicherndes Objekt.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public void addBeitrag(Beitrag beitrag) throws IOException;

	/**
	 * Neu erstellter Benutzer wird gespeichert.
	 * Falls ein Benutzer schon in der Datenbank existiert, wird hier entsprechend eine Exception geworfen.
	 * @param user Zu speichernder Benutzer für die Datenbank.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen. 
	 */
	public void addUser(AbstractUser user) throws IOException;
	
	/**
	 * Gibt Liste aller Benutzer zurück.
	 * Sind keine Benutzer vorhanden wird eine leere Liste zurückgegeben.
	 * @return List<AbstractUser> Enthält alle Benutzer Objekte der erbenden Klassen in der Datenbank.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public List<AbstractUser> getAllUsers() throws IOException;
	
	/**
	 * Gibt eine Liste aller Beiträge in der Datenbank zurück.
	 * Gibt leere Liste zurück, falls keine Daten vorhanden sind.
	 * @return List<Beitrag> Enthält alle Einträge in der Datenbank der Klasse Beitrag.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public List<Beitrag> getBeitragList(String username) throws IOException;

	/**
	 * Gibt eine Tabelle mit den gewünschten Forschungsdaten zurück.
	 * Diese sind für statistische Auswertung gedacht und entsprechen den Korrelationen, die im Parameter angegeben werden können
	 * @param data_type Gibt an welche Daten zurückgegeben werden sollen.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public List<String[]> getScientistData(data_type dataType) throws IOException;

	/**
	 * Sucht Benutzer über den Benutzernamen aus der Datenbank.
	 * @param username Benutzername, nach dem gesucht werden soll.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public AbstractUser getUserByUsername(String username) throws IOException;
	
	/**
	 * Gibt alle Benutzer der Klasse Ydiot zurück.
	 * @return List<Ydiot> Enthält alle Benutzer der Klasse Ydiot.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public List<Ydiot> getUserList() throws IOException;

	/**
	 * Löscht den Beitrag mit der ID des Objektes, das übergeben wird.
	 * @param beitrag Objekt, das aus der Datenbank gelöscht werden soll.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public void removeBeitrag(Beitrag beitrag) throws IOException;

	/**
	 * Löscht den Benutzer mit dem Benutzernamen des Benutzers, der als Parameter mit übergeben wird.
	 * @param user Zu löschender Benutzer.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public void removeUser(AbstractUser user) throws IOException;

	/**
	 * Sucht in den Instanzvariablen userName und fullName nach dem angegeben String.
	 * Gibt alle Benutzer aus, die diese Kriterien erfüllen.
	 * @param searchstring beliebiger Suchstring.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public List<Ydiot> search(String searchstring) throws IOException;

	/**
	 * Aktualisiert das in der Datenbank gespeicherte Beitrag Objekt (z.B. wenn neue Likes dazukommen)
	 * @param beitrag aktualisiertes Beitrag Objekt. Das alte Objekt mit derselben ID wird überschrieben.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public void updateBeitrag(Beitrag beitrag) throws IOException;

	/**
	 * Aktualisiert das schon existierende user Objekt mit dem angegeben Objekt in der Datenbank.
	 * @param user Das aktualisierte Benutzerobjekt.
	 * @throws IOException Bei Ausnahmefällen in der Datenbank wird eine Exception geworfen.
	 */
	public void updateUser(AbstractUser user) throws IOException;
}
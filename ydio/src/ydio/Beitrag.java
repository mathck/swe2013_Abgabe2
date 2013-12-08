package ydio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ydio.dao.DAO;
import ydio.dao.SQL;
import ydio.user.Ydiot;

/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:10
 */
public class Beitrag {

	private String content;
	private Ydiot creator;
	private Date date;
	private List<Ydiot> Dislikes;
	private long ID;
	private List<Ydiot> Likes;
	private DAO dao;
	
	/**
	 * nextID hält fest an welchem ID platz die Beiträge sich gerade befinden
	 * Beim erstellen eines neuen Betirages wird automatisch durch nextID 
	 * dem Beitrag eine ID zugeteilt
	 */
	private static long nextID;
	private List<Ydiot> readList;
	private Ydiot recep;
	private List<Ydiot> reportList;

	
	/**
	 * 
	 * @param creator Ersteller
	 * @param recep   Empfänger
	 * 
	 * Ein Beitrag kann nur durch die Angabe eines Erstellers(creator)
	 * und eines Empfï¿½ngers(recep) erstellt werden. 
	 * Recep ist der Ydiot auf wessen "Wand" der Beitrag erstellt wird.
	 * 
	 */
	
	public Beitrag(Ydiot creator, Ydiot recep){
		this.creator = creator;
		this.recep = recep;
		
		content = "";
		date = new Date();
		Dislikes = new ArrayList<Ydiot>();
		
		ID = nextID;
		nextID++;
		
		Likes = new ArrayList<Ydiot>();
		readList = new ArrayList<Ydiot>();
		reportList = new ArrayList<Ydiot>();
		try {
			dao = new SQL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Dieser Kontruktor ist fï¿½r das Laden von Beitrï¿½gen aus der Datenbank
	 * vorgesehen
	 * 
	 * @param creator
	 * @param recep
	 * @param content
	 * @param Dislikes
	 * @param ID
	 * @param Likes
	 * @param readList
	 * @param reportList
	 * @param date
	 */
	public Beitrag(Ydiot creator, Ydiot recep, String content, List<Ydiot> Dislikes, 
				  long ID, List<Ydiot> Likes, List<Ydiot> readList, List<Ydiot> reportList, Date date){
		
		this.creator = creator;
		this.recep = recep;
		this.content = content;
		this.Dislikes = Dislikes;
		this.ID = ID;
		this.Likes = Likes;
		this.readList = readList;
		this.reportList = reportList;
		this.date = date;
		
	}
	



	public Beitrag(String creator, String recep, String Dislikes,
			Object object, int int1, Object object2, Object object3,
			Object object4, java.sql.Date date2) {
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param user 
	 * Wenn noch keine Wertung seitens User stattgefunden hat
	 * wird  der user zur Dislike Liste des Beitrages hinzugefügt
	 */
	public void addDislike(Ydiot user){
		if(!getRated(user)){
			Dislikes.add(user);
		}
	}

	/**
	 * 
	 * @param user 
	 * Wenn noch keine Wertung seitens User stattgefunden hat
	 * wird  der user zur Likes Liste des Beitrages hinzugefügt
	 */
	public void addLike(Ydiot user){
		if(!getRated(user)){
			Likes.add(user);
		}
	}

	/**
	 * 
	 * @param user
	 * 
	 * Soll aufgerufen werden wenn der User dieses Beitrag ladet.
	 * Der user wird auf die readList hinzugefügt.
	 * 
	 * @return liefert TRUE wenn der User nicht schon vertreten ist, sonst FALSE
	 */
	public boolean addRead(Ydiot user){
		if(readList.contains(user)){
			return false;
		}
		else{
			readList.add(user);
			return true;
		}
	}

	/**
	 * 
	 * @param user
	 * Wenn noch kein Report seitens User stattgefunden hat
	 * wird der user zur reportList des Beitrages hinzugefügt
	 * @return liefert TRUE wenn der User nicht schon vertreten ist, sonst FALSE
	 */
	
	public boolean addReport(Ydiot user){
		if(reportList.contains(user)){
			return false;
		}
		else{
			reportList.add(user);
			return true;
		}
	}
	
	/**
	 * @return Gibt die Anzahl der User zurück, welche diesen Beitrag mit Dislike bewertet haben
	 */
	public int getCountDislike(){
		return Dislikes.size();
	}
	
	/**
	 * @return Gibt die Anzahl der User zurück, welche diesen Beitrag mit Like bewertet haben 
	 */
	public int getCountLikes(){
		return Likes.size();
	}
	
	/**
	 * 
	 * @return Gibt die Anzahl der User zurück, welche diesen Beitrag gemeldet haben
	 */
	public int getCountReports(){
		return reportList.size();
	}

	/**
	 * @param user
	 * 
	 * @return Gibt TRUE zurück wenn der übergebene user diesen Beitrag mit Disklike bewertet hat
	 */
	public boolean getDislike(Ydiot user){
		if(Dislikes.contains(user)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * @param user
	 * 
	 * @return Gibt TRUE zurück wenn der übergebene user diesen Beitrag mit Like bewertet hat
	 */
	public boolean getLike(Ydiot user){
		if(Likes.contains(user)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * @param user
	 * 
	 * @return Gibt TRUE zurück wenn der übergebene user diesen Beitrag gewertet hat
	 */
	public boolean getRated(Ydiot user){
		if(getLike(user) || getDislike(user)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * @param user
	 * 
	 * @return Gibt TRUE zurück wenn der übergebene user diesen Beitrag gelesen hat
	 */
	public boolean getRead(Ydiot user){
		if(readList.contains(user)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * @param user
	 * 
	 * @return Gibt TRUE zurück wenn der übergebene user diesen Beitrag gemeldet hat
	 */
	public boolean getReport(Ydiot user){
		if(reportList.contains(user)){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * 
	 * 
	 *  
	 */


	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public Ydiot getCreator() {
		return creator;
	}



	public void setCreator(Ydiot creator) {
		this.creator = creator;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public List<Ydiot> getDislikes() {
		return Dislikes;
	}



	public void setDislikes(List<Ydiot> dislikes) {
		Dislikes = dislikes;
	}



	public long getID() {
		return ID;
	}



	public void setID(long iD) {
		ID = iD;
	}



	public List<Ydiot> getLikes() {
		return Likes;
	}



	public void setLikes(List<Ydiot> likes) {
		Likes = likes;
	}



	public List<Ydiot> getReadList() {
		return readList;
	}



	public void setReadList(List<Ydiot> readList) {
		this.readList = readList;
	}



	public Ydiot getRecep() {
		return recep;
	}



	public void setRecep(Ydiot recep) {
		this.recep = recep;
	}



	public List<Ydiot> getReportList() {
		return reportList;
	}



	public void setReportList(List<Ydiot> reportList) {
		this.reportList = reportList;
	}
	
	
	public static void setnextID(long id){
		nextID = id;
	}
	
	public long getnextID(){
		return nextID;
	}

}
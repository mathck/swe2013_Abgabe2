package ydio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author mathck
 * @version 1.0
 * @created 04-Dez-2013 20:51:10
 */
public class Beitrag {

	private String content;
	private String creator;
	private Date date;
	private List<String> Dislikes;
	private long ID;
	private List<String> Likes;
	
	/**
	 * nextID h�lt fest an welchem ID platz die Beitr�ge sich gerade befinden
	 * Beim erstellen eines neuen Betirages wird automatisch durch nextID 
	 * dem Beitrag eine ID zugeteilt
	 */
	private static long nextID;
	private List<String> readList;
	private String recep;
	private List<String> reportList;

	
	/**
	 * 
	 * @param creator Ersteller
	 * @param recep   Empf�nger
	 * 
	 * Ein Beitrag kann nur durch die Angabe eines Erstellers(creator)
	 * und eines Empf�ngers(recep) erstellt werden. 
	 * Recep ist der String auf wessen "Wand" der Beitrag erstellt wird.
	 * 
	 */
	
	public Beitrag(String creator, String recep){
		this.creator = creator;
		this.recep = recep;
		
		content = "";
		date = new Date();
		Dislikes = new ArrayList<String>();
		
		ID = nextID;
		nextID++;
		
		Likes = new ArrayList<String>();
		readList = new ArrayList<String>();
		reportList = new ArrayList<String>();

	}
	
	/**
	 * Dieser Kontruktor ist f�r das Laden von Beitr�gen aus der Datenbank
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
	public Beitrag(String creator, String recep, String content, List<String> Dislikes, 
				  long ID, List<String> Likes, List<String> readList, List<String> reportList, Date date){
		
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

	/**
	 * 
	 * @param user 
	 * Wenn noch keine Wertung seitens User stattgefunden hat
	 * wird  der user zur Dislike Liste des Beitrages hinzugef�gt
	 */
	public void addDislike(String user){
		if(!getRated(user)){
			Dislikes.add(user);
		}
	}

	/**
	 * 
	 * @param user 
	 * Wenn noch keine Wertung seitens User stattgefunden hat
	 * wird  der user zur Likes Liste des Beitrages hinzugef�gt
	 */
	public void addLike(String user){
		if(!getRated(user)){
			Likes.add(user);
		}
	}

	/**
	 * 
	 * @param user
	 * 
	 * Soll aufgerufen werden wenn der User dieses Beitrag ladet.
	 * Der user wird auf die readList hinzugef�gt.
	 * 
	 * @return liefert TRUE wenn der User nicht schon vertreten ist, sonst FALSE
	 */
	public boolean addRead(String user){
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
	 * wird der user zur reportList des Beitrages hinzugef�gt
	 * @return liefert TRUE wenn der User nicht schon vertreten ist, sonst FALSE
	 */
	
	public boolean addReport(String user){
		if(reportList.contains(user)){
			return false;
		}
		else{
			reportList.add(user);
			return true;
		}
	}
	
	/**
	 * @return Gibt die Anzahl der User zur�ck, welche diesen Beitrag mit Dislike bewertet haben
	 */
	public int getCountDislike(){
		return Dislikes.size();
	}
	
	/**
	 * @return Gibt die Anzahl der User zur�ck, welche diesen Beitrag mit Like bewertet haben 
	 */
	public int getCountLikes(){
		return Likes.size();
	}
	
	/**
	 * 
	 * @return Gibt die Anzahl der User zur�ck, welche diesen Beitrag gemeldet haben
	 */
	public int getCountReports(){
		return reportList.size();
	}

	/**
	 * @param user
	 * 
	 * @return Gibt TRUE zur�ck wenn der �bergebene user diesen Beitrag mit Disklike bewertet hat
	 */
	public boolean getDislike(String user){
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
	 * @return Gibt TRUE zur�ck wenn der �bergebene user diesen Beitrag mit Like bewertet hat
	 */
	public boolean getLike(String user){
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
	 * @return Gibt TRUE zur�ck wenn der �bergebene user diesen Beitrag gewertet hat
	 */
	public boolean getRated(String user){
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
	 * @return Gibt TRUE zur�ck wenn der �bergebene user diesen Beitrag gelesen hat
	 */
	public boolean getRead(String user){
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
	 * @return Gibt TRUE zur�ck wenn der �bergebene user diesen Beitrag gemeldet hat
	 */
	public boolean getReport(String user){
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



	public String getCreator() {
		return creator;
	}



	public void setCreator(String creator) {
		this.creator = creator;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public List<String> getDislikes() {
		return Dislikes;
	}



	public void setDislikes(List<String> dislikes) {
		Dislikes = dislikes;
	}



	public long getID() {
		return ID;
	}



	public void setID(long iD) {
		ID = iD;
	}



	public List<String> getLikes() {
		return Likes;
	}



	public void setLikes(List<String> likes) {
		Likes = likes;
	}



	public List<String> getReadList() {
		return readList;
	}



	public void setReadList(List<String> readList) {
		this.readList = readList;
	}



	public String getRecep() {
		return recep;
	}



	public void setRecep(String recep) {
		this.recep = recep;
	}



	public List<String> getReportList() {
		return reportList;
	}



	public void setReportList(List<String> reportList) {
		this.reportList = reportList;
	}
	
	
	public static void setnextID(long id) {
		nextID = id;
	}
	
	public long getnextID() {
		return nextID;
	}

}
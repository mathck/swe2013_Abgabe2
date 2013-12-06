package ydio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	 * @param creator
	 * @param recep
	 * 
	 * Ein Beitrag kann nur durch die Angabe eines Erstellers(creator)
	 * und eines Empfängers(recep) erstellt werden. 
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
	}
	
	/**
	 * Dieser Kontruktor ist für das Laden von Beiträgen aus der Datenbank
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
	



	/**
	 * 
	 * @param user
	 */
	public void addDislike(Ydiot user){
		if(!getRated(user)){
			Dislikes.add(user);
		}
	}

	/**
	 * 
	 * @param user
	 */
	public void addLike(Ydiot user){
		if(!getRated(user)){
			Likes.add(user);
		}
	}

	/**
	 * 
	 * @param user
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

	public int getCountDislike(){
		return Dislikes.size();
	}

	public int getCountLikes(){
		return Likes.size();
	}

	public int getCountReports(){
		return reportList.size();
	}

	/**
	 * 
	 * @param user
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
	 * 
	 * @param user
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
	 * 
	 * @param user
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
	 * 
	 * @param user
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
	 * 
	 * @param getReport
	 */
	public boolean getReport(Ydiot user){
		if(reportList.contains(user)){
			return true;
		}
		else{
			return false;
		}
	}



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
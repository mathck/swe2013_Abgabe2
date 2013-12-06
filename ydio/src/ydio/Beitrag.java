package ydio;

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
	private static long nextID;
	private List<Ydiot> readList;
	private Ydiot recep;
	private List<Ydiot> reportList;

	public Beitrag(){
		
	}

	

	/**
	 * 
	 * @param user
	 */
	public void addDislike(Ydiot user){
		if(!Dislikes.contains(user)){
			Dislikes.add(user);
		}
	}

	/**
	 * 
	 * @param user
	 */
	public void addLike(Ydiot user){
		if(!Likes.contains(user)){
			Dislikes.add(user);
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

}
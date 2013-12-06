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

	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param user
	 */
	public void addDislike(Ydiot user){

	}

	/**
	 * 
	 * @param user
	 */
	public void addLike(Ydiot user){

	}

	/**
	 * 
	 * @param user
	 */
	public boolean addRead(Ydiot user){
		return false;
	}

	/**
	 * 
	 * @param user
	 */
	public boolean addReport(Ydiot user){
		return false;
	}

	public int getCountDislike(){
		return 0;
	}

	public int getCountLikes(){
		return 0;
	}

	public int getCountReports(){
		return 0;
	}

	/**
	 * 
	 * @param user
	 */
	public void getDislike(Ydiot user){

	}

	/**
	 * 
	 * @param user
	 */
	public void getLike(Ydiot user){

	}

	/**
	 * 
	 * @param user
	 */
	public boolean getRated(Ydiot user){
		return false;
	}

	/**
	 * 
	 * @param user
	 */
	public boolean getRead(Ydiot user){
		return false;
	}

	/**
	 * 
	 * @param getReport
	 */
	public boolean getReport(Ydiot getReport){
		return false;
	}

}
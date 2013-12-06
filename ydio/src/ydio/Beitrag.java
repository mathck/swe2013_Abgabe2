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

}
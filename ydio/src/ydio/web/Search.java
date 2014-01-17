package ydio.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Search {

	
	  public static void aufrufSearch(HttpServletRequest request, HttpServletResponse response, HttpSession session, RequestDispatcher JSPSearch) throws ServletException, IOException{
	   		try{
	   			JSPSearch.include(request, response);
	   		}catch(Exception e){
	   			e.printStackTrace();
	   		}
	   }
}



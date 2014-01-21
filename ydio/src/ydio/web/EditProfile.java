
package ydio.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditProfile {


	public static void aufrufEditProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session, RequestDispatcher JSPEditProfile) throws ServletException, IOException
	{
		try{
			
			JSPEditProfile.include(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

package ydio;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ydio.dao.DAO;
import ydio.dao.SQL;
import ydio.user.*;

public class Tests {
	
	static Ydiot y = null;
	static UserManagement um = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		y = new Ydiot("ydiot", "pw", "vorname nachname", "email@ydio.com", 'm', new Date(), "desc", null);
		//UserManagement um = new UserManagement();
	}
	
	@Test
	public void UserTest() {
		p(y.getFullName());
		p(y.getDescription());
		p(y.getSex());
		//y.setSex('f');
		p(y.getSex());
		
		//um.login("pw", "ydiot");
	}
	
	/*
	@Test
	public void LockedTest() throws IOException {
		Ydiot y = new Ydiot("vorname", "pw", "vorname nachname", "email@ydio.com", 'm', new Date(), "desc", null);
		UserManagement um = new UserManagement();
		
		um.login(y.getPassword(), y.getUsername());
		um.addBeitrag(new Beitrag(y,y));
	}
	*/

	public static void p(String s) {
		System.out.println(s);
	}
	
	public static void p(char s) {
		System.out.println(s);
	}
}

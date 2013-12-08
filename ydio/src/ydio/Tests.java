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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	
	@Test
	public void UserTest() {
		Ydiot y = new Ydiot("vorname", "pw", "vorname nachname", "email@ydio.com", 'm', new Date(), "desc", null);
		System.out.println(y.getDescription());
	}
	
	@Test
	public void LockedTest() throws IOException {
		Ydiot y = new Ydiot("vorname", "pw", "vorname nachname", "email@ydio.com", 'm', new Date(), "desc", null);
		UserManagement um = new UserManagement();
		
		SQL s = new SQL();
		um.login(y.getPassword(), y.getUsername());
		um.addBeitrag(new Beitrag(y,y));
		
	}

}

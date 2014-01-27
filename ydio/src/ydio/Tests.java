package ydio;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;


import ydio.exceptions.InvalidEmailInputException;
import ydio.exceptions.InvalidPasswordInputException;
import ydio.exceptions.InvalidSexInputException;
import ydio.user.*;

public class Tests {
	
	static Ydiot y = null;
	static UserManagement um = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		y = new Ydiot("ydiot", "ydiotydiot", "vorname nachname", "email@ydio.com", 'm', new Date(), "desc", null);
	}
	
	@Test
	public void WhenUserIsModified_HeIsModified() throws InvalidSexInputException {
		p(y.getFullName());
		p(y.getDescription());
		p(y.getSex());
		y.setSex('f');
		p(y.getSex());
	}
	
	@Test(expected=InvalidPasswordInputException.class)
	public void WhenPasswortIsToShort_ExceptionIsThrown() throws Exception {
		Ydiot ydiot = new Ydiot("ydiot2", "pw", "saldk asdlk", "sadasd@asdasd.com", 'f', new Date(), "des", null);
	}
	
	@Test(expected=InvalidEmailInputException.class)
	public void WhenEmailIsWrong_ExceptionIsThrown() throws Exception {
		Ydiot ydiot = new Ydiot("ydiot2", "pwasdsad", "saldk asdlk", "sadasdasdasdcom", 'f', new Date(), "des", null);
	}
	
	@Test(expected=InvalidSexInputException.class)
	public void WhenSexIsWrong_ExceptionIsThrown() throws Exception {
		Ydiot ydiot = new Ydiot("ydiot2", "pwasdsad", "saldk asdlk", "sadasda@sdasd.com", 'x', new Date(), "des", null);
	}
	
	@Test
	public void WhenBeitragIsLiked_ItIsLiked() {
		Beitrag beitrag = new Beitrag("asd", "asdasd");
		beitrag.addLike("asdasd");
		beitrag.addLike("asdasd2");
		beitrag.addLike("asdasd3");
		beitrag.addLike("asdasd4");
		
		assertEquals(4, beitrag.getCountLikes());
	}
	
	@Test
	public void WhenBeitragIsDisliked_ItIsDisliked() {
		Beitrag beitrag = new Beitrag("asd", "asdasd");
		beitrag.addDislike("asdasd");
		beitrag.addDislike("asdasd2");
		beitrag.addDislike("asdasd3");
		
		assertEquals(3, beitrag.getCountDislike());
	}
	
	@Test
	public void WhenBeitragGetsContent_ItGetsTheRightConetent() {
		Beitrag beitrag = new Beitrag("asd", "asdasd");

		String content = "abcsadasd";
		
		beitrag.setContent(content);
		
		assertEquals(content, beitrag.getContent());
	}


	public static void p(String s) {
		System.out.println(s);
	}
	
	public static void p(char s) {
		System.out.println(s);
	}
}

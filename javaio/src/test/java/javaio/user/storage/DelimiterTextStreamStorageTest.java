package javaio.user.storage;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import javaio.user.domain.Gender;
import javaio.user.domain.User;

public class DelimiterTextStreamStorageTest {

	Storage storage;

	User user;

	final String email = "a@b.c";

	@Before
	public void before() {
		this.storage = new DelimiterTextStreamStorage();
		this.user = new User(email, "a", 1, true, Gender.F);
	}

	@Test
	public void testSave() {

//		List<String> data = user.toData();
//		this.storage.save(data);
//		this.storage.save(data);
	}

	@Test
	public void testFind() {
		
		List<String> find = storage.find(email);
		System.err.println( find );
		
	}

}

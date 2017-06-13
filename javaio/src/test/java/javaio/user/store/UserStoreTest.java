package javaio.user.store;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javaio.user.domain.Gender;
import javaio.user.domain.User;
import javaio.user.storage.DelimiterTextStreamStorage;

public class UserStoreTest {
	
	UserStore userStore;
	
	User user;
	
	final String email = "aabaa@b.c";
	
	@Before
	public void before() {
		this.user = new User(email, "a", 1, true, Gender.F);
		this.userStore = new UserFileStore( new DelimiterTextStreamStorage() );
	}

	@Test
	public void testFind() {
		User find = userStore.find( email );
		System.out.println( find );
	}
	
//	@Test
//	public void testCreateUser() {
//		userStore.create(user);
//	}

//	@Test
//	public void testCreateListOfUser() {
//		userStore.create(user);
//	}
	
	
//
//
//	@Test
//	public void testFindAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testUpdate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDelete() {
//		fail("Not yet implemented");
//	}
}

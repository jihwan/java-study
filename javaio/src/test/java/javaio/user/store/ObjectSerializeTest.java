package javaio.user.store;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

import kosta.java.io.user.domain.Gender;
import kosta.java.io.user.domain.User;

public class ObjectSerializeTest {

	@Test
	public void test() throws FileNotFoundException, IOException {
		
		
		User user = new User("a@b.c", "aaa", 11, true, Gender.Female);
		
		
		String filename = "object.out";
		// save object to file
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
		oos.writeObject(user);
		oos.close();
	}

}

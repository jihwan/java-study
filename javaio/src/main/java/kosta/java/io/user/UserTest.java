package kosta.java.io.user;

import java.util.ArrayList;
import java.util.List;

import kosta.java.io.storage.StorageType;
import kosta.java.io.user.domain.Gender;
import kosta.java.io.user.domain.User;
import kosta.java.io.user.store.UserFileStore;
import kosta.java.io.user.store.UserStore;

public class UserTest {

	public static void main(String[] args) {
		
	    UserTest test = new UserTest();
		
//		test.testCreate();
//		test.testCreateUsers();
	    
		test.testRetrieve();
//	    test.testRetrieveAll();
//	    test.testRetrieveByName();
	    
//	    test.testUpdate();
//	    test.testDelete();
	}
	
	
//	private UserStore userStore = new UserFileStore(StorageType.DelimiterTextStreamStorage);
//	private UserStore userStore = new UserFileStore(StorageType.DelimiterTextStorage);
//	private UserStore userStore = new UserFileStore(StorageType.FixedDataStorage);
//	private UserStore userStore = new UserFileStore(StorageType.FixedIndexingStorage);
	private UserStore userStore = new UserFileStore(StorageType.FixedIndexingDataStorage);
	
	
	public void clearUserStorage() {
//	    new File("file-work" + File.separator + "comma-separator-storage" + File.separator, "user-storage.data").delete();
	}
	
	public void testCreate() {
		User user = new User("hkkang@nextree.co.kr", "hk", 20, true, Gender.Male);
		userStore.create(user);
	}
	
	public void testCreateUsers() {
		List<User> users = new ArrayList<>();
		
		for (int i = 0; i < 100; i++) {
		    int seq = i + 1;
		    boolean isOdd = (seq % 2) == 1;
		    
		    users.add(new User("email_" + seq, "name_" + seq % 10, seq, isOdd, isOdd ? Gender.Male : Gender.Female));
		}
		userStore.createUsers(users);
	}
	
	public void testRetrieve() {
		User user = userStore.retrieve("email_9");
		System.out.println(user);
	}

	public void testRetrieveByName() {
	    List<User> users = userStore.retrieveByName("name_5");
	    displayUsers(users);
	}
	
	public void testRetrieveAll() {
	    List<User> users = userStore.retrieveAll();
	    displayUsers(users);
	}
	
	private static void displayUsers(List<User> users) {
	    for (User user : users) {
            System.out.println(user);
        }
	}
	
	public void testUpdate() {
	    
	    User user = new User("email_5", "modified_name_5", 55, false, Gender.Female);
	    
	    userStore.update(user);
	}
	
	public void testDelete() {
	    userStore.delete("email_1");
	}
	
}

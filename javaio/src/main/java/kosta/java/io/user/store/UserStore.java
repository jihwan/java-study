package kosta.java.io.user.store;

import java.util.List;

import kosta.java.io.user.domain.User;

public interface UserStore {

	void create(User user);
	void createUsers(List<User> users);
	
	User retrieve(String email);
	List<User> retrieveAll();
	List<User> retrieveByName(String userName);
	
	void update(User user);
	void delete(String email);
}

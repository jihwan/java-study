package javaio.user.store;

import java.util.List;

import javaio.user.domain.User;

public interface UserStore {

	void create(User user);
	void create(List<User> users);
	
	User find(String email);
	List<User> findAll();
	
	void update(User user);
	void delete(String email);
}

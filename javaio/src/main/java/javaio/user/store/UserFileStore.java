package javaio.user.store;

import java.util.List;

import javaio.user.domain.User;
import javaio.user.storage.Storage;

public class UserFileStore implements UserStore {
	
	private Storage storage;
	
	public UserFileStore() {
	}
	
	public UserFileStore(Storage storage) {
		super();
		this.storage = storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	@Override
	public void create(User user) {
		this.storage.save(user.toData());
	}

	@Override
	public void create(List<User> users) {
		for (User user : users) {
			this.create(user);
		}
	}

	@Override
	public User find(String email) {
		List<String> find = storage.find(email);
		return User.toUser(find);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String email) {
		// TODO Auto-generated method stub
		
	}

}

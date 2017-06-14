package kosta.java.io.user.store;

import java.util.List;

import kosta.java.io.storage.Storage;
import kosta.java.io.storage.StorageFactory;
import kosta.java.io.storage.StorageType;
import kosta.java.io.storage.data.ResultData;
import kosta.java.io.user.domain.User;

public class UserFileStore implements UserStore {

    private static final String STORE_NAME = "user";
	private Storage storage;
	
	
	public UserFileStore(StorageType storageType) {
		this.storage = StorageFactory.getStorage(storageType, STORE_NAME);
	}
	
	
	@Override
	public void create(User user) {
		//
		storage.insert(user.toData());
	}

	@Override
	public void createUsers(List<User> users) {
		storage.insertMany(User.toDatas(users));
	}

	@Override
	public User retrieve(String email) {
		ResultData userData = storage.select(email);
		return userData.hasData() ?  User.toDomain(userData) : null;
	}

	@Override
	public List<User> retrieveAll() {
		List<ResultData> userDatas = storage.selectAll();
		return User.toDomains(userDatas);
	}
	
	@Override
    public List<User> retrieveByName(String userName) {
	    List<ResultData> userDatas = storage.selectBy(1, userName);
	    return User.toDomains(userDatas);
    }
	

	@Override
	public void update(User user) {
	    storage.update(user.getEmail(), user.toData());
	}

	@Override
	public void delete(String email) {
	    storage.delete(email);
	}

}

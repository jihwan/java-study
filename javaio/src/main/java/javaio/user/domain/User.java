package javaio.user.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3751369659099113899L;
	
	String email; // id
	String name;
	int age;
	boolean manager;
	Gender gender;
	
	public User(String email, String name, int age, boolean manager, Gender gender) {
		super();
		this.email = email;
		this.name = name;
		this.age = age;
		this.manager = manager;
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public boolean isManager() {
		return manager;
	}

	public Gender getGender() {
		return gender;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", age=" + age + ", manager=" + manager + ", gender=" + gender
				+ "]";
	}

	public List<String> toData() {
		final List<String> data = new ArrayList<>();
		data.add( getEmail() );
		data.add( getName() );
		data.add( String.valueOf(getAge()) );
		data.add( String.valueOf( isManager() ) );
		data.add( getGender().name() );
		return data;
	}

	public static User toUser(String[] row) {
		User user = new User(row[0], row[1], Integer.parseInt(row[2]), Boolean.parseBoolean(row[3]), Gender.valueOf(row[4]));
		return user;
	}
	

	public static User toUser(List<String> row) {
		User user = new User(row.get(0), row.get(1), Integer.parseInt(row.get(2)), Boolean.parseBoolean(row.get(3)), Gender.valueOf(row.get(4)));
		return user;
	}
}

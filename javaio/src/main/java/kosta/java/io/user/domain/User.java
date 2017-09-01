package kosta.java.io.user.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kosta.java.io.storage.data.ColumnSchema;
import kosta.java.io.storage.data.ParamData;
import kosta.java.io.storage.data.ResultData;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5844618919269723615L;
	private String email; // id
	private String name;

	private int age;
	private boolean manager;
	private Gender gender;

	private User() {
		//
	}

	public User(String email, String name, int age, boolean manager, Gender gender) {
		this.email = email;
		this.name = name;
		this.age = age;
		this.manager = manager;
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", age=" + age + ", manager=" + manager + ", gender=" + gender
				+ "]";
	}

	
	/************************************************************
	 * 데이터 저장기술 데이터 와 사용자 객체간 매핑 변환 기능 기술....
	 */
	
	public static List<User> toDomains(List<ResultData> datas) {

		List<User> users = new ArrayList<>();

		for (ResultData data : datas) {
			users.add(User.toDomain(data));
		}
		return users;
	}

	public static List<ParamData> toDatas(List<User> users) {

		List<ParamData> datas = new ArrayList<>();

		for (User user : users) {
			datas.add(user.toData());
		}
		return datas;
	}

	public static User toDomain(ResultData data) {

		User user = new User();

		user.email = data.getData().get(0);
		user.name = data.getData().get(1);
		user.age = Integer.valueOf(data.getData().get(2));
		user.manager = Boolean.valueOf(data.getData().get(3));
		user.gender = Gender.valueOf(data.getData().get(4));

		return user;
	}

	public ParamData toData() {

		ParamData data = new ParamData();

		data.add(email, getEmailSchema());
		data.add(name, getNameSchema());
		data.add(String.valueOf(age), getAgeSchema());
		data.add(String.valueOf(manager), getManagerSchema());

		data.add(gender.name(), getGenderSchema());

		return data;
	}

	/************************************************************
	 * 스키마 정의
	 */

	public static ColumnSchema getEmailSchema() {
		return new ColumnSchema("email", 50);
	}

	public static ColumnSchema getNameSchema() {
		return new ColumnSchema("name", 30);
	}

	public static ColumnSchema getAgeSchema() {
		return new ColumnSchema("age", 10);
	}

	public static ColumnSchema getManagerSchema() {
		return new ColumnSchema("manager", 10);
	}

	public static ColumnSchema getGenderSchema() {
		return new ColumnSchema("gender", 15);
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

}

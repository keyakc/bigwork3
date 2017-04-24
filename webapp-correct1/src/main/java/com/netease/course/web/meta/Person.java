package com.netease.course.web.meta;

public class Person {

	int id,usertype;
	String username,password,nickname;	
	
	public Person(int id, int usertype, String username, String password, String nickname) {
		this.id = id;
		this.usertype = usertype;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
		
	
}

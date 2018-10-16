package com.hnu.model;

public class User {
	private String telphone;
	private String name;
	private String password;
	public User(String telphone, String name, String password){
		this.telphone = telphone;
		this.name = name;
		this.password = password;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

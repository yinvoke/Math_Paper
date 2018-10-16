package com.hnu.yzw.main;

public class User {
	String username;
	String userpassword;
	String grade;
	
	public User(String username, String userpassword,String grade){
		this.username = username;
		this.userpassword = userpassword;
		this.grade = grade;
	}
	
	boolean checkmessage(String username, String userpassword) {
		if(this.username.equals(username) && this.userpassword.equals(userpassword)) {
			return true;
		}else {
			return false;
		}
	}
	
	String getgrade() {
		return this.grade;
	}
}

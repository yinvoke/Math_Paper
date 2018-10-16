package com.hnu.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import com.hnu.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Tesedemo {

	public static void main(String[] args) {
		FileReader fr;
		String jsonString = "";
		try {
			fr = new FileReader("usermessages\\message.json");
			char[] a = new char[1024];
			int num = 0;
			while ((num=fr.read(a)) !=-1) {
				jsonString += (new String(a,0,num));
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<User> UserList = new ArrayList<User>();
		try {
			JSONArray jsonArray = new JSONArray();
			jsonArray = jsonArray.fromObject(jsonString);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject= (JSONObject) jsonArray.get(i);
				UserList.add(new User(jsonObject.optString("telphone"), jsonObject.optString("name"), jsonObject.optString("password")));			
			}
			ArrayList<String> upList = new ArrayList<String>();
			for (int i = 0; i < UserList.size(); i++) {
				String temp;
				temp = UserList.get(i).getName()+UserList.get(i).getPassword();
				System.out.println(temp);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<User> getListPersonByArray(String jsonString){		
		ArrayList<User> personList=new ArrayList<User>();				
		try {			
			JSONArray jsonArray=new JSONArray();
			jsonArray = jsonArray.fromObject(jsonString);	
			for (int i = 0; i < jsonArray.size(); i++) {				
				JSONObject jsonObject= (JSONObject) jsonArray.get(i);				
				personList.add(new User(jsonObject.optString("telphone"), jsonObject.optString("name"), jsonObject.optString("password")));			
			}			
			return personList;		
		} catch (Exception e) {			
			// TODO Auto-generated catch block			
			e.printStackTrace();		
		}		
		return null;	
	}

}

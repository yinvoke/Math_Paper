package com.hnu.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hnu.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;



public class Login {
	String username;
	JFrame frame = new JFrame("数学试卷系统v2.0");

	//窗口设定
	public Login()
	{	
		JPanel jpanel=new JPanel() {
			public void paintComponent(Graphics g) {  
				ImageIcon icon=new ImageIcon("images/background.jpg");
				Image image = icon.getImage();
				g.drawImage(image,0,0,icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());

			}
		};
		ImageIcon logo = new ImageIcon("images/logo.png");
		JLabel i = new JLabel(logo);
		i.setBounds(200, 15, logo.getIconWidth(), logo.getIconHeight());
		frame.setSize(500,360);
		frame.add(i);
		frame.add(jpanel);
		placeComp(jpanel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//内容设定
	public void placeComp(JPanel jpanel)
	{
		Font font1 = new Font("黑体",0,16);
		Font font2 = new Font("宋体",0,12);
		jpanel.setLayout(null); 

		JLabel Userlabel = new JLabel("用户名");
		Userlabel.setBounds(125, 160, 120, 30);
		Userlabel.setFont(font1);
		jpanel.add(Userlabel);

		JTextField Usertext = new JTextField();
		Usertext.setBounds(185, 160, 175, 30);
		jpanel.add(Usertext);

		JLabel Passwordlabel=new JLabel("密  码");
		Passwordlabel.setBounds(125, 200, 120, 30);
		Passwordlabel.setFont(font1);
		jpanel.add(Passwordlabel);

		JPasswordField Passwordtext = new JPasswordField();
		Passwordtext.setBounds(185, 200, 175, 30);
		jpanel.add(Passwordtext);

		JButton loginbutton=new JButton("登录");
		loginbutton.setBounds(180, 250, 140, 30);
		loginbutton.setFont(font1);
		jpanel.add(loginbutton);

		JLabel Forgetbutton=new JLabel("忘记密码");
		Forgetbutton.setBounds(0, 305, 85, 20);
		Forgetbutton.setFont(font2);
		Forgetbutton.setForeground(Color.WHITE);
		jpanel.add(Forgetbutton);

		JButton Registerbutton=new JButton ("注册用户");
		Registerbutton.setBounds(415, 305, 85, 20);
		Registerbutton.setFont(font2);
		Registerbutton.setForeground(Color.WHITE);
		jpanel.add(Registerbutton);

		//注册
		Registerbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new Register();
			}
		});

		//忘记密码


		//登录
		loginbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String l = Usertext.getText()+"@"+String.valueOf(Passwordtext.getPassword());
				if(isexist(l,getTp()) || isexist(l,getUp()))
				{	
					username=Usertext.getText();
					frame.dispose();
					new Menu(username);
				}
				else
				{
					new Tips("请输入正确的账号密码");
				}
			}

			private boolean isexist(String l, ArrayList list) {
				for (int i = 0; i < list.size(); i++) {
					if(l.equals(list.get(i))) {
						return true;
					}
				}
				return false;
			}

			private ArrayList getUp() {
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
						upList.add(UserList.get(i).getName()+"@"+UserList.get(i).getPassword());
					}
					return upList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			private ArrayList getTp() {
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
					ArrayList<String> tpList = new ArrayList<String>();
					for (int i = 0; i < UserList.size(); i++) {
						tpList.add(String.valueOf(UserList.get(i).getTelphone())+"@"+String.valueOf(UserList.get(i).getPassword()));
					}
					return tpList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
}

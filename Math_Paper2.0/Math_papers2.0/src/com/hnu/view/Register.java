package com.hnu.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.hnu.model.Ali;
import com.hnu.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;









public class Register {
	int nowcode;
	JFrame frame = new JFrame("注册");

	//窗口设定
	public Register()
	{
		frame.setSize(500,360);

		JPanel jpanel=new JPanel(){
			public void paintComponent(Graphics g) {
				ImageIcon icon=new ImageIcon("images/background.jpg");
				Image image = icon.getImage();
				g.drawImage(image,0,0,icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());
				frame.setSize(500,360);
			}
		};
		frame.add(jpanel);
		placeComp(jpanel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	//内容设定
	public void placeComp(JPanel jpanel)
	{
		jpanel.setLayout(null); 
		Font font1 = new Font("黑体",0,16);

		JLabel tellabel = new JLabel("电话号码:");
		tellabel.setBounds(25,40,80,25);
		tellabel.setFont(font1);
		jpanel.add(tellabel);

		JTextField teltext = new JTextField();
		teltext.setBounds(130,40, 200, 25);
		jpanel.add(teltext);

		JButton codebutton = new JButton("获取验证码");
		codebutton.setBounds(350, 40, 120, 25);
		codebutton.setFont(font1);
		jpanel.add(codebutton);

		JLabel codelabel=new JLabel("验证码:");
		codelabel.setBounds(25, 80, 120, 25);
		codelabel.setFont(font1);
		jpanel.add(codelabel);

		JTextField codetext = new JTextField();
		codetext.setBounds(130, 80, 200, 25);
		jpanel.add(codetext);

		JLabel uselabel = new JLabel("用户名:");
		uselabel.setBounds(25,120,80,25);
		uselabel.setFont(font1);
		jpanel.add(uselabel);

		JTextField usertext = new JTextField();
		usertext.setBounds(130, 120, 200, 25);
		jpanel.add(usertext);

		JLabel passwordlabel = new JLabel("密码:");
		passwordlabel.setBounds(25,160,80,25);
		passwordlabel.setFont(font1);
		jpanel.add(passwordlabel);

		JPasswordField pwdtext = new JPasswordField();
		pwdtext.setBounds(130, 160, 200, 25);
		jpanel.add(pwdtext);

		JLabel repasswordlabel = new JLabel("确认密码:");
		repasswordlabel.setBounds(25,200,80,25);
		repasswordlabel.setFont(font1);
		jpanel.add(repasswordlabel);

		JPasswordField repassword = new JPasswordField();
		repassword.setBounds(130, 200, 200, 25);
		jpanel.add(repassword);

		JButton Registerbutton=new JButton("注册");
		Registerbutton.setBounds(180, 250, 120, 25);
		Registerbutton.setFont(font1);
		jpanel.add(Registerbutton);

		//获取验证码
		codebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList t = getTelArray();
				if(!isexist(teltext.getText(),t)) {
					Ali ali = new Ali();
					ali.setNewcode();
					String code = Integer.toString(ali.getNewcode());
					try {
						SendSmsResponse sendSms = ali.sendSms(teltext.getText(),code);
					} catch (ClientException e1) {
						e1.printStackTrace();
					}
					nowcode = ali.getNewcode();
				}else {
					new Tips("该手机号已被注册");
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
			
			private ArrayList getTelArray() {
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
					ArrayList<String> TelList = new ArrayList<String>();
					for (int i = 0; i < UserList.size(); i++) {
						TelList.add(UserList.get(i).getTelphone());
					}
					return TelList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		//注册
		Registerbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!String.valueOf(pwdtext.getPassword()).equals(String.valueOf(repassword.getPassword()))){
					new Tips("两次密码不匹配");
				} else if(String.valueOf(pwdtext.getPassword()).trim().length()!=String.valueOf(pwdtext.getPassword()).length()) {
					new Tips("密码不能含有非法字符");
				} else if(usertext.getText().trim().equals("")) {
					new Tips("用户名中不能为空");
				} else if((usertext.getText().trim().length()!=usertext.getText().length()) || usertext.getText().contains("@")) {
					new Tips("用户名中不能含有空格和@");
				} else if(!codetext.getText().equals(""+nowcode)){
					new Tips("验证码错误");
				} else {
					ArrayList<User> users = new ArrayList<User>();
					users = getUsers();
					User user = new User(teltext.getText(),usertext.getText(),String.valueOf(pwdtext.getPassword()));
					
					users.add(user);
					JSONArray ja = new JSONArray();
					ja = ja.fromObject(users);
					FileWriter fw;
					try {
						fw = new FileWriter("usermessages\\message.json");
						ja.write(fw);
						fw.close();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					new Tips("注册成功!");
					frame.dispose();
				}

			}

			private ArrayList<User> getUsers() {
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
					return UserList;
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		});
	}

	public ArrayList<User> getListTByArray(String jsonString){		
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


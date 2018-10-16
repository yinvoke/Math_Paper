package com.hnu.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Menu {
	JFrame frame = new JFrame("数学试卷系统v2.0");
	String username;
	//窗口设定
	public Menu(String username)
	{	
		this.username = username;
		JPanel jpanel=new JPanel() {
			public void paintComponent(Graphics g) {  
				ImageIcon icon=new ImageIcon("images/background.jpg");
				Image image = icon.getImage();
				g.drawImage(image,0,0,icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());

			}
		};
		frame.setSize(500,360);
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
		Font font1 = new Font("黑体",0,18);
		Font font2 = new Font("黑体",Font.BOLD,22);
		jpanel.setLayout(null); 
		
		JLabel Userlabel = new JLabel("当前用户: " + username);
		Userlabel.setBounds(25, 25, 250, 30);
		Userlabel.setFont(font2);
		jpanel.add(Userlabel);

		JLabel Tiplabel=new JLabel("注意啦!");
		Tiplabel.setBounds(25, 80, 280, 30);
		Tiplabel.setFont(font1);
		jpanel.add(Tiplabel);

		JLabel Tip1label=new JLabel("小学只有四则运算!");
		Tip1label.setBounds(25, 100, 280, 30);
		Tip1label.setFont(font1);
		jpanel.add(Tip1label);

		JLabel Tip2label=new JLabel("初中含有平方及开方运算!");
		Tip2label.setBounds(25, 120, 280, 30);
		Tip2label.setFont(font1);
		jpanel.add(Tip2label);

		JLabel Tip3label=new JLabel("高中含有三角函数运算!");
		Tip3label.setBounds(25, 140, 280, 30);
		Tip3label.setFont(font1);
		jpanel.add(Tip3label);
		
		JLabel Tip4label=new JLabel("试卷题目数量建议为50-150哦！");
		Tip4label.setBounds(25, 160, 280, 30);
		Tip4label.setFont(font1);
		jpanel.add(Tip4label);

		JButton Backbutton=new JButton("退出登录");
		Backbutton.setBounds(20, 250, 140, 30);
		Backbutton.setFont(font1);
		jpanel.add(Backbutton);

		JButton Gobutton=new JButton("马上做题");
		Gobutton.setBounds(330, 250, 140, 30);
		Gobutton.setFont(font1);
		jpanel.add(Gobutton);

		//退出登录
		Backbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Login();
			}
		});

		//马上做题
		Gobutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Select(username);
			}
		});
	}
}

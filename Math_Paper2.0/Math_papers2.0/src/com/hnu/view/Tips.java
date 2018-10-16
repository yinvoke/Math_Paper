package com.hnu.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;





public class Tips {
	JFrame frame = new JFrame("提示");
	
	//窗口设定
	public Tips(String word) {
		frame.setSize(250,180);
		JPanel jpanel=new JPanel();
		frame.add(jpanel);
		placeComp(jpanel,word);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
	public Tips(String word,double number,String username)
	{
		frame.setSize(250,180);
		JPanel jpanel=new JPanel();
		frame.add(jpanel);
		word+=number;
		placeComp(jpanel,word,username);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
	
	//内容设定
	public void placeComp(JPanel jpanel, String word) {
		jpanel.setLayout(null);
		Font font1 = new Font("黑体",0,16);

		JLabel Userlabel = new JLabel(word);
		Userlabel.setBounds(70,40,130,25);
		jpanel.add(Userlabel);

		JButton surebutton=new JButton("是");
		surebutton.setBounds(80, 100, 80, 25);
		surebutton.setFont(font1);
		jpanel.add(surebutton);

		surebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	public void placeComp(JPanel jpanel, String mark, String username)
	{
		jpanel.setLayout(null);
		Font font1 = new Font("黑体",0,16);

		JLabel Userlabel = new JLabel(mark);
		Userlabel.setBounds(70,40,130,25);
		jpanel.add(Userlabel);

		JButton surebutton=new JButton("是");
		surebutton.setBounds(80, 100, 80, 25);
		surebutton.setFont(font1);
		jpanel.add(surebutton);

		new Menu(username);
		surebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
}

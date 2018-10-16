package com.hnu.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;






public class Paper {
	int current=0;
	String s=null;
	JFrame frame = new JFrame("数学试卷系统v2.0");

	public Paper(String username, int number, String []problems, String level, String option[][],String Correctoption[])
	{
		frame.setSize(500,360);
		s=problems[0];
		JPanel jpanel=new JPanel(){
			public void paintComponent(Graphics g) {
				ImageIcon icon=new ImageIcon("images/background.jpg");
				Image image = icon.getImage();
				g.drawImage(image,0,0,icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());
				frame.setSize(500,360);
			}
		};
		frame.add(jpanel);
		placeComp(jpanel, username, number, problems, level,option,Correctoption);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//内容设定
	public void placeComp(JPanel jpanel, String username, int number, String []problems, String level, String option[][],String Correctoption[])
	{
		jpanel.setLayout(null);
		Font font1 = new Font("黑体",0,18);
		Font font2 = new Font("黑体",0,17);
		Font font3 = new Font("黑体",0,20);

		String Recordoption[]= new String[number];
		for(int i=0;i<number;i++)
		{
			Recordoption[i]="0";
		}

		JLabel Selectlabel = new JLabel("用户:"+username);
		Selectlabel.setBounds(15,10,100,40);
		Selectlabel.setFont(font1);
		jpanel.add(Selectlabel);

		JLabel Totallabel = new JLabel("已做/总题目数量:"+"0/"+number);
		Totallabel.setBounds(135,10,240,40);
		Totallabel.setFont(font1);
		jpanel.add(Totallabel);

		JLabel diffcultylabel = new JLabel("难度:"+level);
		diffcultylabel.setBounds(380,10,100,40);
		diffcultylabel.setFont(font1);
		jpanel.add(diffcultylabel);

		JLabel subjectlabel=new JLabel(current+1+". "+problems[current]);
		subjectlabel.setBounds(30, 90, 360, 35);
		subjectlabel.setFont(font3);
		jpanel.add(subjectlabel);

		JRadioButton randioButton1=new JRadioButton("A."+option[current][0]);
		JRadioButton randioButton2=new JRadioButton("B."+option[current][1]);
		JRadioButton randioButton3=new JRadioButton("C."+option[current][2]);
		JRadioButton randioButton4=new JRadioButton("D."+option[current][3]);

		randioButton1.setBounds(40,145,200,25);
		randioButton2.setBounds(250,145,200,25);
		randioButton3.setBounds(40,180,200,25);
		randioButton4.setBounds(250,180,200,25);
		randioButton1.setFont(font2);
		randioButton2.setFont(font2);
		randioButton3.setFont(font2);
		randioButton4.setFont(font2);
		randioButton1.setContentAreaFilled(false); 
		randioButton2.setContentAreaFilled(false); 
		randioButton3.setContentAreaFilled(false); 
		randioButton4.setContentAreaFilled(false); 
		jpanel.add(randioButton1);
		jpanel.add(randioButton2);
		jpanel.add(randioButton3);
		jpanel.add(randioButton4);
		ButtonGroup group=new ButtonGroup();
		group.add(randioButton1);
		group.add(randioButton2);
		group.add(randioButton3);
		group.add(randioButton4);

		JButton prebutton=new JButton("上一题");
		prebutton.setBounds(20, 250, 140, 30);
		prebutton.setFont(font1);
		jpanel.add(prebutton);

		JButton nextbutton=new JButton("下一题");
		nextbutton.setBounds(330, 250, 140, 30);
		nextbutton.setFont(font1);
		jpanel.add(nextbutton);
		if(number==1) {
			nextbutton.setText("交卷");
		}

		//上一道题
		prebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(current>0) {
					current--;
					if(current!=number-1) {
						nextbutton.setText("下一题");
					}
					subjectlabel.setText(current+1+". "+problems[current]);
					Totallabel.setText("已做/总题目数量:"+current+"/"+number);

					if(Recordoption[current].equals("A"))
					{
						randioButton1.setSelected(true);
					}
					else if(Recordoption[current].equals("B"))
					{
						randioButton2.setSelected(true);
					}
					else if(Recordoption[current].equals("C"))
					{
						randioButton3.setSelected(true);
					}
					else if(Recordoption[current].equals("D"))
					{
						randioButton4.setSelected(true);
					}
					else
					{
						group.clearSelection();
					}

					randioButton1.setText("A."+option[current][0]);
					randioButton2.setText("B."+option[current][1]);
					randioButton3.setText("C."+option[current][2]);
					randioButton4.setText("D."+option[current][3]);
				}
			}
		});

		//下一道题
		nextbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nextbutton.getText().equals("交卷")) {
					int grade = 0;
					for(int i=0;i<number;i++)
					{
						if(Recordoption[i].equals(Correctoption[i]))
						{
							grade++;

						}
					}
					new Tips("你的成绩为: ", (double)grade/(double)number*100, username);
					frame.dispose();
				}
				if(current<number-1 || number == 1) {
					current++;
					if(current==number-1) {
						nextbutton.setText("交卷");
					}
					subjectlabel.setText(current+1+". "+problems[current]);
					Totallabel.setText("已做/总题目数量:"+current+"/"+number);

					if(Recordoption[current].equals("A"))
					{
						randioButton1.setSelected(true);
					}
					else if(Recordoption[current].equals("B"))
					{
						randioButton2.setSelected(true);
					}
					else if(Recordoption[current].equals("C"))
					{
						randioButton3.setSelected(true);
					}
					else if(Recordoption[current].equals("D"))
					{
						randioButton4.setSelected(true);
					}
					else
					{
						group.clearSelection();
					}
					randioButton1.setText("A."+option[current][0]);
					randioButton2.setText("B."+option[current][1]);
					randioButton3.setText("C."+option[current][2]);
					randioButton4.setText("D."+option[current][3]);
				}
			}
		});


		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (randioButton1.isSelected()) {
					Recordoption[current]="A";
				}
				else if (randioButton2.isSelected()) {
					Recordoption[current]="B";
				}
				else if (randioButton3.isSelected()) {
					Recordoption[current]="C";
				}
				else if (randioButton4.isSelected()) {
					Recordoption[current]="D";
				}
				else {
					Recordoption[current]="0";
				}
			}
		};

		randioButton1.addItemListener(itemListener);
		randioButton2.addItemListener(itemListener);
		randioButton3.addItemListener(itemListener);
		randioButton4.addItemListener(itemListener);

	}
}

package com.hnu.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Stack;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;





public class Select {
	int number=0;
	String level=null;
	String problems[];
	int flag=0; //标志位，判断输入格式是否正确,0表示正确,1表示错误
	JFrame frame = new JFrame("数学试卷系统v2.0");

	//窗口设定
	public Select(String username)
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
		placeComp(jpanel,username);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//内容设定
	public void placeComp(JPanel jpanel,String username)
	{
		DecimalFormat df=new DecimalFormat("0.00");
		jpanel.setLayout(null);
		Font font1 = new Font("黑体",0,18);

		JLabel Selectlabel = new JLabel("难度");
		Selectlabel.setBounds(30,25,100,40);
		Selectlabel.setFont(font1);
		jpanel.add(Selectlabel);

		JRadioButton randioButton1=new JRadioButton("小学");
		JRadioButton randioButton2=new JRadioButton("初中");
		JRadioButton randioButton3=new JRadioButton("高中");
		randioButton1.setBounds(50,70,70,25);
		randioButton2.setBounds(190,70,70,25);
		randioButton3.setBounds(330,70,70,25);
		randioButton1.setContentAreaFilled(false); 
		randioButton2.setContentAreaFilled(false); 
		randioButton3.setContentAreaFilled(false);
		randioButton1.setFont(font1);
		randioButton2.setFont(font1);
		randioButton3.setFont(font1);
		jpanel.add(randioButton1);
		jpanel.add(randioButton2);
		jpanel.add(randioButton3);
		ButtonGroup group=new ButtonGroup();
		group.add(randioButton1);
		group.add(randioButton2);
		group.add(randioButton3);


		JLabel numberlabel=new JLabel("题目数量");
		numberlabel.setBounds(30, 140, 120, 25);
		numberlabel.setFont(font1);
		jpanel.add(numberlabel);

		JTextField numbertext = new JTextField();
		numbertext.setBounds(50, 185, 240, 25);
		jpanel.add(numbertext);


		JButton Yesbutton=new JButton("确定");
		Yesbutton.setBounds(180, 250, 140, 30);
		Yesbutton.setFont(font1);
		jpanel.add(Yesbutton);

		//按钮判断
		Yesbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(randioButton1.isSelected()) //判断选择了什么难度
				{
					level="小学";
					flag=0;
				}
				else if(randioButton2.isSelected())
				{
					level="初中";
					flag=0;
				}
				else if(randioButton3.isSelected())
				{
					level="高中";
					flag=0;
				}
				else
				{
					flag=1;
					new Tips("请选择难度");
				}

				if(numbertext.getText().trim().equals(""))//不为空
				{
					flag=1;
					new Tips("请输入数目");
				}
				else if(Numberisright(numbertext.getText()))//没有空格及其他特殊字母
				{
					flag=1;
					new Tips("请输入正确数目");
				}
				else
				{
					flag=0;
					number= Integer.parseInt(numbertext.getText());
				}

				if(flag==0 )
				{
					Double answer[] = new Double[number];
					problems=paper_generate(number,level);

					//答案
					for(int i=0; i < number ; i ++)
					{
						answer[i]=change(problems[i]);
					}

					//正确选项设置
					String Correctoption[] = new String[number];
					for(int i=0;i<number;i++)
					{
						long seed = System.nanoTime();
						Random random = new Random(seed);
						int rand=random.nextInt(4);

						if(rand==0)
						{
							Correctoption[i]="A";
						}
						else if(rand==1)
						{
							Correctoption[i]="B";
						}
						else if(rand==3)
						{
							Correctoption[i]="C";
						}
						else
						{
							Correctoption[i]="D";
						}
					}

					String option[][] = new String[number][4];

					for(int i=0;i<number;i++)
					{

						if(Correctoption[i].equals("A"))
						{
							option[i][0]=String.valueOf(answer[i]);
							option[i][1]=String.valueOf(answer[i]+(int)(1+Math.random()*(100-1+1)));
							option[i][2]=String.valueOf(answer[i]+(int)(1+Math.random()*(50-1+1)));
							option[i][3]=String.valueOf(answer[i]-(int)(1+Math.random()*(30-1+1)));
						}
						else if(Correctoption[i].equals("B"))
						{
							option[i][0]=String.valueOf(answer[i]+(int)(1+Math.random()*(100-1+1)));
							option[i][1]=String.valueOf(answer[i]);
							option[i][2]=String.valueOf(answer[i]+(int)(1+Math.random()*(50-1+1)));
							option[i][3]=String.valueOf(answer[i]-(int)(1+Math.random()*(30-1+1)));
						}
						else if(Correctoption[i].equals("C"))
						{
							option[i][0]=String.valueOf(answer[i]-(int)(1+Math.random()*(100-1+1)));
							option[i][1]=String.valueOf(answer[i]+(int)(1+Math.random()*(50-1+1)));
							option[i][2]=String.valueOf(answer[i]);
							option[i][3]=String.valueOf(answer[i]-(int)(1+Math.random()*(30-1+1)));
						}
						else if(Correctoption[i].equals("D"))
						{
							option[i][0]=String.valueOf(answer[i]+(int)(1+Math.random()*(100-1+1)));
							option[i][1]=String.valueOf(answer[i]+(int)(1+Math.random()*(50-1+1)));
							option[i][2]=String.valueOf(answer[i]+(int)(1+Math.random()*(30-1+1)));
							option[i][3]=String.valueOf(answer[i]);
						}
					}
					frame.dispose();
					new Paper(username,number,problems,level,option,Correctoption);
				}
			}
		});
	}

	//试卷生成
	String[] paper_generate(int number, String level) {
		String problems[] = new String[number];
		for(int i = 0; i < number; i++) {
			problems[i] = problem_generate(level);
			if((!isRight(problems[i])) && (!isExist(problems[i], problems))) {
				i--;
			}
		}
		return problems;
	}

	//重复判断
	boolean isExist(String problem, String problems[]) {
		for(String i : problems) {
			if(problem.equals(i)){
				return true;
			}
		}
		return false;
	}

	//题目生成
	String problem_generate(String level) {
		String sign[] = new String[] {"+","-","×","÷","²","√","sin","cos","tan"};
		//每个算式有number_num个数字，有number_num-1个运算符号，最多有number_num-2组括号
		int number_num = (int)(2+Math.random()*(5-2+1));
		int numbers[] = new int[number_num];
		int signs[] = new int[number_num-1];
		String problem = "";
		//随机数字
		for(int i = 0; i < number_num; i++) {
			numbers[i] = (int)(1+Math.random()*(100-1+1));
		}
		//随机运算符号
		if(level.equals("小学")) {
			for(int i = 0; i < number_num - 1; i++) {
				signs[i] = (int)(0+Math.random()*(3-0+1));
			}
		}else if(level.equals("初中")) {
			signs[0] = (int)(4+Math.random()*(5-4+1));
			for(int i = 1; i < number_num - 1; i++) {
				signs[i] = (int)(0+Math.random()*(5-0+1));
			}
		}else if(level.equals("高中")) {
			signs[0] = (int)(6+Math.random()*(8-6+1));
			for(int i = 1; i < number_num - 1; i++) {
				signs[i] = (int)(0+Math.random()*(8-0+1));
			}
		}
		//合并
		for(int i = 0; i < number_num - 1; i++) {
			problem += numbers[i];
			if(signs[i] >= 4 && signs[i] <= 8) {
				problem += sign[(int)(0+Math.random()*(3-0+1))];
			}
			problem += sign[signs[i]];
		}
		problem += numbers[number_num - 1];
		//加括号
		for(int i = 0; i < number_num -2; i++) {
			if((int)(0+Math.random()*(1-0+1))==1) {
				problem = brackets(problem);
			}
		}
		problem += "=";
		//处理格式
		char p[] = problem.toCharArray();
		for(int i = 0; i < p.length-1; i++) {
			if(p[i] == '²' && Character.isDigit(p[i+1])) {
				p[i] = p[i+1];
				p[i+1] = '²';
			}
		}
		problem = String.valueOf(p);
		return problem;
	}

	//加括号
	String brackets(String problem_inital) {
		String problem = problem_inital;
		int fcount = 0, bcount = 0;
		int f[] = new int[5];
		int b[] = new int[5];
		//确定(
		f[fcount++] = 0;
		for(int i = 1; i < problem.length(); i++) {
			if(Character.isDigit(problem.charAt(i)) && (problem.charAt(i-1)=='+')||(problem.charAt(i-1)=='-')||(problem.charAt(i-1)=='×')||(problem.charAt(i-1)=='÷')) {
				f[fcount++] = i;
			}
		}
		String fs="",bs="";
		int ping = (int)(0+Math.random()*(fcount-2-0+1));
		for(int i = 0; i < f[ping]; i++) {
			fs += problem.charAt(i);
		}
		for(int i = f[ping]; i < problem.length(); i++) {
			bs += problem.charAt(i);
		}
		problem = fs + "(" +bs;
		//确定)
		for(int i = f[ping] ; i < problem.length() - 1; i++) {
			if(Character.isDigit(problem.charAt(i)) && (problem.charAt(i+1)=='+')||(problem.charAt(i+1)=='-')||(problem.charAt(i+1)=='×')||(problem.charAt(i+1)=='÷')) {
				b[bcount++] = i+1;
			}
		}
		b[bcount++] = problem.length();
		fs="";
		bs="";
		ping = (int)(1+Math.random()*(bcount-1-1+1));
		for(int i = 0; i < b[ping]; i++) {
			fs += problem.charAt(i);
		}
		for(int i = b[ping]; i < problem.length(); i++) {
			bs += problem.charAt(i);
		}
		problem = fs + ")" +bs;
		return problem;
	}

	//括号合理性检测(全包围，单个数字包围，双括号包围)
	boolean isRight(String problem) {
		String a="((", b="))";
		int sign1=0,sign2=0;
		if(problem.charAt(0) == '(' && problem.charAt(problem.length()-2) == ')'){
			return false;
		}
		if(problem.indexOf(a) != -1 && problem.indexOf(b) != -1) {
			return false;
		}
		for(int i = 0; i < problem.length(); i++) {
			String temp = "";
			if(problem.charAt(i) == '(') {
				sign1 = i;
			}
			if(problem.charAt(i) == ')') {
				sign2 = i;
			}
			if(sign1<sign2) {
				for(int j = sign1 + 1; j < sign2; j++) {
					temp+=problem.charAt(j);
				}
				if(temp.indexOf("+") == -1 && temp.indexOf("-") == -1 && temp.indexOf("×") == -1 && temp.indexOf("÷") == -1) {
					return false;
				}
			}
		}
		return true;
	}

	//判断输入的number是否符合格式(true不合格,false合格)
	public boolean Numberisright(String name)
	{
		char []namee=name.toCharArray();
		for(int i=0;i < name.length(); i++)
		{
			if(namee[i] < '0' || namee[i] > '9')
			{
				return true;
			}
		}
		return false;
	}
	public  int priority(String operator)
	{
		if(operator.equals("+") || operator.equals("-"))
		{
			return 0;
		}
		else if(operator.equals("×") || operator.equals("÷"))
		{
			return 1;
		}
		else if(operator.equals("(") || operator.equals(")"))
		{
			return 2;
		}

		return -1;
	}

	//判断是否为操作符
	public  boolean isOperation(char s)
	{
		if(s=='+' || s=='-' || s=='×' || s=='÷' || s=='(' || s==')')
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//52-143变为后缀表达式  144-185计算后缀表达式的值   185输出结果类型为double
	public  Double change(String test)
	{
		int Operationstacklength=0;
		Stack<String> Operationstack = new Stack<String>();
		Stack<String> Operatorstack = new Stack<String>();

		char []tests = test.toCharArray();
		int len = tests.length-1;

		String sb="";
		for(int i=0;i<len;i++)
		{
			int flag=0;
			if(!isOperation(tests[i]))
			{
				sb+=tests[i];
			}
			else
			{
				if(!sb.equals(""))
				{
					Operationstack.push(sb);
					Operationstacklength++;
					sb="";
				}
				if(tests[i]=='(')
				{
					Operatorstack.push(String.valueOf(tests[i]));
				}
				else if(tests[i]==')')
				{
					while(!Operatorstack.peek().equals("("))
					{
						String m=Operatorstack.pop();
						Operationstack.push(m);
						Operationstacklength++;
					}
					Operatorstack.pop();
				}
				else
				{
					if(Operatorstack.empty())
					{
						Operatorstack.push(String.valueOf(tests[i]));
					}
					else if(priority(Operatorstack.peek())<priority(String.valueOf(tests[i])))
					{
						Operatorstack.push(String.valueOf(tests[i]));
					}
					else
					{
						while(!Operatorstack.empty() && priority(Operatorstack.peek())>=priority(String.valueOf(tests[i])))
						{
							if(Operatorstack.peek().equals("("))
							{
								flag=1;
								Operatorstack.push(String.valueOf(tests[i]));
								break;
							}
							String s=Operatorstack.pop();
							Operationstack.push(s);
							Operationstacklength++;

						}
						if(flag==0)
						{
							Operatorstack.push(String.valueOf(tests[i]));
						}
					}
				}

			}

			if(i==len-1 && !sb.equals(""))
			{
				Operatorstack.push(sb);
			}

		}

		while(!Operatorstack.empty())
		{
			String s=Operatorstack.pop();
			Operationstack.push(s);
			Operationstacklength++;
		}

		String []OperationString= new String[Operationstacklength];
		int i=Operationstacklength-1;
		while(!Operationstack.empty())
		{
			OperationString[i]=Operationstack.pop();
			i--;
		}

		if(Operationstacklength==0)
		{
			System.out.print(0);
		}

		Stack<Double> stack = new Stack<Double>();
		for(int j=0;j<Operationstacklength;j++)
		{
			String temp=symplify(OperationString[j]);

			if(temp.equals("+"))
			{
				double a=stack.pop();
				double b=stack.pop();
				stack.push(a+b);
			}
			else if(temp.equals("-"))
			{
				double a=stack.pop();
				double b=stack.pop();
				stack.push(b-a);
			}
			else if(temp.equals("×"))
			{
				double a=stack.pop();
				double b=stack.pop();
				stack.push(a*b);
			}
			else if(temp.equals("÷"))
			{
				double a=stack.pop();
				double b=stack.pop();
				stack.push(b/a);
			}
			else
			{
				stack.push(Double.parseDouble(temp));
			}
		}

		return stack.pop();

	}

	//用来将sin,cos,tan,根号平方计算变为string
	public  String symplify(String s)
	{
		int l=s.length();

		if(l==1)
		{
			return s;
		}
		else if(l<4)
		{
			if(s.substring(0,1).equals("√"))
			{
				String temp=(String) s.substring(1, l);
				return String.valueOf(Math.sqrt(Integer.parseInt(temp)));
			}
			else if(s.substring(l-1).equals("²"))
			{
				String temp=(String) s.substring(0, l-1);
				return String.valueOf(Math.pow(Integer.parseInt(temp),2));
			}
			else 
			{
				return s;
			}
		}
		else
		{
			if(s.substring(0,1).equals("√"))
			{
				String temp=(String) s.substring(1, l);
				return String.valueOf(Math.sqrt(Integer.parseInt(temp)));
			}
			else if(s.substring(l-1).equals("²"))
			{
				String temp=(String) s.substring(0, l-1);
				return String.valueOf(Math.pow(Integer.parseInt(temp),2));
			}
			else if(s.substring(0,3).equals("sin"))
			{
				String temp=(String) s.substring(3, l);
				return String.valueOf(Math.sin(Math.toRadians(Integer.parseInt(temp))));
			}
			else if(s.substring(0,3).equals("cos"))
			{
				String temp=(String) s.substring(3, l);
				return String.valueOf(Math.cos(Math.toRadians(Integer.parseInt(temp))));
			}
			else if(s.substring(0,3).equals("tan"))
			{
				String temp=(String) s.substring(3, l);
				return String.valueOf(Math.tan(Math.toRadians(Integer.parseInt(temp))));
			}
			else
			{
				return s;
			}
		}
	}
}

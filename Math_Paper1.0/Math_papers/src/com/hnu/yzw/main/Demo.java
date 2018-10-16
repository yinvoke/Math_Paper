package com.hnu.yzw.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


/**
 * 
 *作者：Yinvoker
 * 2019年9月24日
 */
public class Demo {

	public static void main(String[] args) {
		menu();	
	}

	//主菜单
	static void menu() {
		Scanner scn = new Scanner(System.in);
		System.out.println("\n===================");
		System.out.println("=         主菜单           =");
		System.out.println("===================");
		System.out.println("请在下列选项中选择");
		System.out.println("0.退出");
		System.out.println("1.登陆");
		System.out.println("===================");
		int select = scn.nextInt();
		if(select == 0) {
			System.out.println("欢迎再次使用本软件！");
			System.exit(0);
		}else if(select == 1) {
			login();
		}
		scn.close();
	}

	//登陆
	static void login() {
		Scanner scn = new Scanner(System.in);
		String username, userpassword, level = "";
		System.out.println("\n\n===================");
		System.out.println("=       登录界面          =");
		System.out.println("===================");
		System.out.print("请输入用户名：");
		username = scn.next();
		System.out.print("请输入密码：");
		userpassword = scn.next();
		System.out.println("===================");
		for(int i = 0; i < users.length; i++) {
			if(users[i].checkmessage(username, userpassword)) {
				level = users[i].getgrade();
				break;
			}
		}
		if(level.equals("")) {
			System.out.println("\n请输入正确的用户名、密码！");
			login();
		}else {
			System.out.println("\n\n=====登陆成功!=====");
			menu2(username, level);
		}
		scn.close();
	}

	//二级菜单
	static void menu2(String username, String level) {
		Scanner scn = new Scanner(System.in);
		int number;
		System.out.println("\n\n当前选择为" + level + "出题！");
		System.out.println("===================");
		System.out.println("=     功能选择          =");
		System.out.println("===================");
		System.out.println("请在下列选项中选择");
		System.out.println("0.退出当前账户");
		System.out.println("1.修改出题难度");
		System.out.println("2.生成题目");
		System.out.println("===================");
		int select = scn.nextInt();
		if(select == 0) {
			menu();
		}else if(select == 1) {
			selectlevel(username,level);
		}else if(select == 2) {
			do {
				System.out.print("准备生成"+level+"数学题目，请输入生成题目数量(10-30)：");
				number = scn.nextInt();
				if(number < 10 || number > 30) {
					System.out.println("输入有误，请重试");
				}
			}while(number < 10 || number > 30);
			paper_generate(username, number, level);
			System.out.println("出题完成，即将返回主菜单！\n");
			menu2(username,level);
		}
		scn.close();
	}

	//难度选择
	static void selectlevel(String username, String level) {
		Scanner scn = new Scanner(System.in);
		System.out.println("\n\n===================");
		System.out.println("=       难度选择          =");
		System.out.println("===================");
		System.out.println("请选择难度");
		System.out.println("0.小学");
		System.out.println("1.初中");
		System.out.println("2.高中");
		System.out.println("===================");
		int select = scn.nextInt();
		if(select == 0) {
			level = "小学";
		}else if(select == 1) {
			level = "初中";
		}else if(select == 2) {
			level = "高中";
		}
		menu2(username,level);
		scn.close();
	}

	//卷子生成
	static void paper_generate(String username, int number, String level) {
		File folder = new File("papers\\"+username);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		String problems[] = new String[number];
		for(int i = 0; i < number; i++) {
			problems[i] = problem_generate(level);
			if(itemIsExist(problems[i],username) || !isRight(problems[i])) {
				i--;
			}
		}
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String papername = dateFormat.format(date)+".txt";
		File file = new File(folder,papername);
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(file),true,"utf-8");
			for(int i = 0; i < problems.length-1; i++) {
				ps.append((i+1)+". "+problems[i]+"\n\n");
			}
			ps.append((problems.length)+". "+problems[problems.length-1]+"\n");
			ps.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncoding");
			e.printStackTrace();
		}
	}


	//题目生成
	static String problem_generate(String level) {
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
	static String brackets(String problem_inital) {
		String problem = problem_inital;
		int fcount = 0, bcount = 0;
		int f[] = new int[5];
		int b[] = new int[5];
		//确定（
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
		//确定）
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

	//括号合理性检测（全包围，单个数字包围，双括号包围）
	static boolean isRight(String problem) {
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

	//重复检测
	static boolean itemIsExist(String problem, String username) {
		File folder = new File("papers\\"+username);
		String all = "";
		File files[] = folder.listFiles();
		for(File f : files) {
			InputStream is = null;
			try {
				is = new FileInputStream(f);
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			byte content[] = new byte[1024];
			try {
				is.read(content);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			try {
				all += new String((content),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		if(all.indexOf(problem) == -1) {
			return false;
		}else {
			return true;
		}
	}

	//预定义用户
	static User pt1 = new User("张三1","123","小学");
	static User pt2 = new User("张三2","123","小学");
	static User pt3 = new User("张三3","123","小学");
	static User jt1 = new User("李四1","123","初中");
	static User jt2 = new User("李四2","123","初中");
	static User jt3 = new User("李四3","123","初中");
	static User ht1 = new User("王五1","123","高中");
	static User ht2 = new User("王五2","123","高中");
	static User ht3 = new User("王五3","123","高中");
	static User users[] = new User[] {pt1,pt2,pt3,jt1,jt2,jt3,ht1,ht2,ht3};


}

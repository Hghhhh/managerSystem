package com.huangguohang.www.view;
/**
 *主程序 
 */

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.util.NumAndPswIden;
import com.huangguohang.www.util.RootIden;

public class EmployeeManager extends JFrame implements ActionListener{
	JMenu fileMenu;
	JMenuItem window,insert,change,search,personal;
	JPanel pCenter = null;
	ManagerWindow mw;
	EmployeeAdd ea;
	EmployeeInquire ei;
	EmployeeModify ms;
	PersonalPage pg;
	CardLayout card = null;
	ResultSet rs = null;
	
	public EmployeeManager() {
		window = new JMenuItem("登录界面");
		insert = new JMenuItem("录入员工基本信息");
		change = new JMenuItem("修改员工基本信息");
		search = new JMenuItem("查询员工基本信息");
		personal = new JMenuItem("员工个人页面");
		insert.setVisible(false);
		change.setVisible(false);
		search.setVisible(false);
		personal.setVisible(false);
		fileMenu = new JMenu("菜单选项");
		fileMenu.add(window);
		fileMenu.add(insert);
		fileMenu.add(change);
		fileMenu.add(search);
		fileMenu.add(personal);//创建一个菜单，开始时只有登录界面选项可见
		window.addActionListener(this);
		insert.addActionListener(this);
		change.addActionListener(this);
		search.addActionListener(this);
		personal.addActionListener(this);//添加事件监听
		JMenuBar bar = new JMenuBar();
		bar.add(fileMenu);
		this.add(bar,BorderLayout.NORTH);//将菜单放在主程序上方		
		pCenter = new JPanel();
		card = new CardLayout();
		pCenter.setLayout(card);//设置一个JPanel组件放在主程序的中间,并设置这个组件的布局为CardLayout
		mw = new ManagerWindow();
		ea = new EmployeeAdd();
		pg = new PersonalPage();
		ei = new EmployeeInquire();
		ms = new EmployeeModify();
		pCenter.add("登录界面", mw);
		pCenter.add("录入界面", ea);
		pCenter.add("个人界面", pg);
		pCenter.add("查询界面", ei);
		pCenter.add("修改界面", ms);//添加各个界面
		this.add(pCenter, BorderLayout.CENTER);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(700, 300, 500, 280);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == window){
			card.show(pCenter, "登录界面");
		}
		if(e.getSource() == insert){
			card.show(pCenter, "录入界面");
			this.setBounds(700, 300, 600, 500);
		}
		if(e.getSource() == personal){
			card.show(pCenter, "个人界面");
			this.setBounds(600, 300, 1000, 500);
		}
		if(e.getSource() == search){
			card.show(pCenter, "查询界面");
			this.setBounds(700, 300, 775, 500);
			ei.modelRowAdd2();//显示时刷新一下
		}
		if(e.getSource() == change){
			card.show(pCenter, "修改界面");
			this.setBounds(700, 300, 600, 500);
		}
	}
	
	/**
	 * 内部类：登陆界面
	 *
	 */
	class ManagerWindow  extends JPanel implements ActionListener {
		JLabel welcome;
		JButton enter, exit;
		JTextField employeeNumber;
		JPasswordField password;
		public ManagerWindow() {
			welcome = new JLabel("欢迎来到员工管理系统",JLabel.CENTER);
			welcome.setFont(new Font("微软雅黑", Font.BOLD + Font.ITALIC, 25));//设置欢迎语
			enter = new JButton("确定");
			exit = new JButton("退出");
			enter.addActionListener(this);//添加事件监听
			exit.addActionListener(this);//添加事件监听
			employeeNumber = new JTextField(20);
			password = new JPasswordField(20);
			Box box1 = Box.createHorizontalBox();
			box1.add(new JLabel("工号:", JLabel.CENTER));
			box1.add(employeeNumber);
			Box box2 = Box.createHorizontalBox();
			box2.add(new JLabel("密码:", JLabel.CENTER));
			box2.add(password);
			Box boxH = Box.createVerticalBox();
			boxH.add(box1);
			boxH.add(Box.createVerticalStrut(40));
			boxH.add(box2);//添加员工号和密码的输入栏，放在一个Box组件中
			JPanel pCenter = new JPanel();
			pCenter.add(boxH);//创建一个JPanel组件来放置boxH
			Box box3 = Box.createHorizontalBox();
			box3.add(Box.createHorizontalStrut(170));
			box3.add(enter);
			box3.add(Box.createHorizontalStrut(10));
			box3.add(exit);//添加确定和退出按钮,并将这两个按钮放在一个Box组件中
			this.setLayout(new BorderLayout());
			this.add(welcome, BorderLayout.NORTH);//将欢迎语添加在上方
			this.add(pCenter, BorderLayout.CENTER);//员工号和密码的输入栏添加在登录界面的中间
			this.add(box3, BorderLayout.SOUTH);//将确定和退出按钮放在下方
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == enter) {
				//如果点击了确定
				String em = employeeNumber.getText().trim();//拿到输入框的员工号
				String ps = password.getText().trim();//拿到密码输入框的密码
				Dbope ope = new Dbope();//链接数据库
				rs = ope.getRs(em);//得到特定的结果集
				NumAndPswIden nap = new NumAndPswIden(em, ps, rs);	
				//传入此员工号密码和此结果集判断员工号和密码	
				if(nap.numIden()) {  
					//如果员工号正确,进行下面的操作
					if(!nap.pswIden()) {
						//如果密码不正确，则清空密码框输入的内容
						password.setText(null);
					}
					else {
						//如果密码正确，则进行下面操作
						Boolean flag = new RootIden(rs).rootIden();
						//判断员工的权限,如果为部长则返回true
						if(flag) {
							//部长可以查找，删除，修改，录入员工
							insert.setVisible(true);
							change.setVisible(true);
							search.setVisible(true);
							personal.setVisible(true);
							window.setVisible(false);	
							ea.setOpeNum(em);//为员工添加界面设置操作者的员工号
							ei.setOpeNum(em);//为员工查询界面设置操作者的员工号
							ms.setOpeNum(em);//为员工修改界面设置操作者的员工号
						}
						else {
							//普通员工只能查找员工和修改自己的信息
							search.setVisible(true);
							personal.setVisible(true);
							window.setVisible(false);
							ei.DeleteRoot(flag);//设置查询界面的删除按钮为不可见					
						}
						//登录成功自动跳转到个人界面
						pg.setRS(rs);//传入包含个人信息的结果集
						card.show(pCenter, "个人界面");
						EmployeeManager.this.setBounds(600, 300, 1000, 500);
					}
				}
				else {
					employeeNumber.setText(null);//如果员工号有误则清空所输入的内容
				}
				ope.db_close();//关闭数据库连接
			}
			if(e.getSource() == exit){
				//如果点击了退出
				EmployeeManager.this.dispose();
			}
		}	
	}
	
	public static void main(String[] args) {
		new EmployeeManager();
	}
}

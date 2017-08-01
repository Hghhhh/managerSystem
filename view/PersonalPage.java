package com.huangguohang.www.view;
/**
 *员工个人主页
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;

import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.po.Employee;
import com.huangguohang.www.service.EvaluationManager;
import com.huangguohang.www.util.*;

public class PersonalPage extends JPanel implements ActionListener{
	JButton save, cancel;
	JTextField employeeName, email, mobile;
	JLabel employeeNumber, department, departmentId,salary, employeeId, time;
	JPasswordField password, password2;
	JRadioButton man, woman;
	ButtonGroup group;
	EvaluationManager evaluation = null;
	JPanel pCenter = new JPanel();
	public PersonalPage() {
		save = new JButton("保存");
		save.addActionListener(this);//为保存按钮添加事件监听	
		employeeNumber = new JLabel();
		department = new JLabel();
		departmentId = new JLabel();
		salary = new JLabel();
		time = new JLabel();
		employeeId = new JLabel();
		employeeName = new JTextField(20);
		password = new JPasswordField(20);
		password2 = new JPasswordField(20);
		email = new JTextField(20);
		mobile = new JTextField(20);
		group = new ButtonGroup();
		man = new JRadioButton("男", true);
		woman = new JRadioButton("女", false);
		group.add(man);
		group.add(woman);
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("员工姓名:", JLabel.CENTER));
		box1.add(employeeName);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("性别:"));
		box2.add(man);
		box2.add(woman);
		Box box3 = Box.createHorizontalBox();
		box3.add(new JLabel("员工号:", JLabel.CENTER));
		box3.add(employeeNumber);
		Box box4 = Box.createHorizontalBox();
		box4.add(new JLabel("密码:"));
		box4.add(password);
		Box box44 = Box.createHorizontalBox();
		box44.add(new JLabel("确认密码:"));
		box44.add(password2);
		Box box5 = Box.createHorizontalBox();
		box5.add(new JLabel("部门:"));
		box5.add(department);
		Box box6 = Box.createHorizontalBox();
		box6.add(new JLabel("电话:"));
		box6.add(mobile);
		Box box7 = Box.createHorizontalBox();
		box7.add(new JLabel("邮箱:"));
		box7.add(email);
		Box box8 = Box.createHorizontalBox();
		box8.add(new JLabel("薪水:"));
		box8.add(salary);
		Box box10 = Box.createHorizontalBox();
		box10.add(new JLabel("任职日期:", JLabel.CENTER));
		box10.add(time);
		Box box11 = Box.createHorizontalBox();
		box11.add(new JLabel("您的Id:", JLabel.CENTER));
		box11.add(employeeId);
		Box boxH = Box.createVerticalBox();
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box4);
		boxH.add(boxH.createVerticalStrut(20));
		boxH.add(box44);
		boxH.add(boxH.createVerticalStrut(20));
		boxH.add(box6);
		boxH.add(boxH.createVerticalStrut(20));
		boxH.add(box7);
		//boxH放置员工姓名、密码、电话、邮箱这些可以修改的信息
		Box box = Box.createHorizontalBox();
		box.add(box11);
		box.add(box.createHorizontalStrut(10));
		box.add(box5);
		box.add(box.createHorizontalStrut(10));
		box.add(box3);
		box.add(box.createHorizontalStrut(10));
		box.add(box8);
		box.add(box.createHorizontalStrut(10));
		box.add(box10);
		//box放置员工Id、部门、员工号、薪水、任职日期这些不能修改的信息
		JPanel pc2 = new JPanel();
		pc2.add(boxH);
		pCenter.setLayout(new GridLayout(1,2));//设置pCenter的布局为GridLayout,一行两格
		pCenter.add(pc2);//第一个格子放置装员工姓名、密码、电话、邮箱的组件
		JPanel pNouth = new JPanel();
		pNouth.add(box);
		JPanel pSouth = new JPanel();
		pSouth.add(save);
		this.setLayout(new BorderLayout());
		this.add(pCenter, BorderLayout.CENTER);
		this.add(pNouth, BorderLayout.NORTH);
		this.add(pSouth, BorderLayout.SOUTH);
	}
	
	/**
	 * 获得员工信息
	 * @param 员工登录后传入具有该员工信息的结果集
	 */
	public void setRS(ResultSet rs) {
		try {	
				employeeNumber.setText(rs.getString("employeeNumber"));
				department.setText(rs.getString("department"));
				salary.setText( rs.getString("salary"));
				time.setText(rs.getString("time"));
				employeeName.setText(rs.getString("employeeName"));
				mobile.setText(rs.getString("mobile"));
				email.setText(rs.getString("email"));
				time.setText(rs.getString("time"));
				employeeId.setText(rs.getString("employeeId"));
				departmentId.setText(rs.getString("departmentId"));
				if(rs.getString("sex").equals("woman")) {
					woman.setSelected(true);
				}
				evaluation = new EvaluationManager(rs.getInt("employeeId"),rs.getString("departmentId"));
				//创建一个评价的界面
				pCenter.add(evaluation);//为pCenter的第二个格子添加此评价界面
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * actionPerformed
	 */
	public void actionPerformed(ActionEvent e) {
			if(password.getText().equals(password2.getText())) {
				//如果两次输入密码一致，则进行下面操作
				Boolean pswIden = new NumAndPswIden().pswIden2(password.getText());
				Boolean mobileIden = new MobileIden(mobile.getText()).mobileIden();
				Boolean emailIden = new EmailIden(email.getText()).emailIden();
				Boolean nameIden = new NameIden(employeeName.getText().trim()).nameIden();
				if(pswIden && mobileIden && emailIden && nameIden) {
					String num = employeeNumber.getText().trim();//获取员工号
					Dbope ope = new Dbope();
					Employee emp = new Employee(num); //创建一个员工对象
					emp.setDepartmentId(departmentId.getText());
					emp.setDepartment(department.getText());
					emp.setEmployeeName(employeeName.getText().trim());
					if(woman.isSelected()) {
						emp.setSex("woman");
					}
					emp.setPassword(password.getText());
					emp.setMobile(mobile.getText());
					emp.setEmail(email.getText());
					emp.setSalary(Integer.parseInt(salary.getText()));
					ope.getUpdate(emp);//为员工对象设值后传入更新信息
					ope.db_close();//关闭数据库
					JOptionPane.showMessageDialog(null, "保存成功！");
				}			
			}
			else {
				JOptionPane.showMessageDialog(null, "两次输入的密码不一致！");
			}			
		}
	
}
	


package com.huangguohang.www.view;
/**
 *员工添加
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.po.Employee;
import com.huangguohang.www.util.*;

public class EmployeeAdd extends JPanel implements ActionListener{
	JTextField  employeeNumber, employeeName, salary, email, mobile;
	JPasswordField password, password2;
	Choice department;
	JRadioButton man, woman;
	JButton insert, reset;
	ButtonGroup group = null;
	String opeNum = null;//操作者的员工号
	
	public EmployeeAdd() {
		department = new Choice();
		department.add("1.研发部");
		department.add("2.财务部");
		department.add("3.销售部");
		department.add("4.人事部");//创建一个Choice组件，添加了各个部门,前面为部门Id
		employeeNumber = new JTextField(20);//员工号输入框
		employeeName = new JTextField(20);//员工姓名输入框
		password = new JPasswordField(20);//密码输入框1
		password2 = new JPasswordField(20);//密码输入框2
		email = new JTextField(20);//邮箱输入框
		mobile = new JTextField(20);//电话输入框
		salary = new JTextField(20);//薪水输入框
		group = new ButtonGroup();
		man = new JRadioButton("男", true);
		woman = new JRadioButton("女", false);
		group.add(man);
		group.add(woman);//创建一个ButtonGroup放置性别，使性别选项互斥
		insert = new JButton("录入");
		reset = new JButton("重置");
		insert.addActionListener(this);
		reset.addActionListener(this);//为按钮添加事件监听
		Box box1 = Box.createVerticalBox();
		box1.add(new JLabel("员工姓名:", JLabel.CENTER));
		box1.add(employeeName);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("性别:", JLabel.CENTER));
		box2.add(man);
		box2.add(woman);
		Box box3 = Box.createVerticalBox();
		box3.add(new JLabel("员工号:", JLabel.CENTER));
		box3.add(employeeNumber);
		Box box4 = Box.createVerticalBox();
		box4.add(new JLabel("密码:", JLabel.CENTER));
		box4.add(password);
		Box box44 = Box.createVerticalBox();
		box44.add(new JLabel("确认密码:", JLabel.CENTER));
		box44.add(password2);
		Box box5 = Box.createVerticalBox();
		box5.add(new JLabel("部门:", JLabel.CENTER));
		box5.add(department);
		Box box6 = Box.createVerticalBox();
		box6.add(new JLabel("电话:", JLabel.CENTER));
		box6.add(mobile);
		Box box7 = Box.createVerticalBox();
		box7.add(new JLabel("邮箱:", JLabel.CENTER));
		box7.add(email);
		Box box8 = Box.createVerticalBox();
		box8.add(new JLabel("薪水:", JLabel.CENTER));
		box8.add(salary);
		Box boxH = Box.createVerticalBox();//将所以输入框和选项都添加进一个Box
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box3);
		boxH.add(box4);
		boxH.add(box44);
		boxH.add(box5);
		boxH.add(box6);
		boxH.add(box7);
		boxH.add(box8);
		JPanel pCenter = new JPanel();
		pCenter.add(boxH);
		JPanel pSouth = new JPanel();
		pSouth.add(insert);
		pSouth.add(reset);
		this.setLayout(new BorderLayout());
		this.add(pCenter, BorderLayout.CENTER);
		this.add(pSouth, BorderLayout.SOUTH);
	}
	
	/**
	 * 设置操作者的员工号
	 * @param opeNum
	 */
	public void setOpeNum(String opeNum) {
		this.opeNum = opeNum;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == insert){
			//如果点击了录入按钮，则进行下面的操作
			String num = employeeNumber.getText().trim();//获得输入的员工号
			Employee emp = null;
			if(new NumAndPswIden(num).numIden3()) {
				//判断员工号的格式是否正确以及是否已经存在了,如果输入的员工号可以用，则进行下面的操作
				if(password.getText().equals(password2.getText())) {
					//判断两次输入的密码是否一致，若一致则进行下面的操作
					Boolean nameIden = new NameIden(employeeName.getText().trim()).nameIden();
					Boolean mobileIden = new MobileIden(mobile.getText()).mobileIden();
					Boolean emailIden = new EmailIden(email.getText()).emailIden();
					Boolean salaryIden = new SalaryIden(salary.getText()).salaryIden();
					if(nameIden && mobileIden && emailIden && salaryIden) {
						//进行姓名、电话、邮箱、薪水格式判断,如果全部正确则进行下面操作
						emp = new Employee(num);
						emp.setPassword(password.getText());
						emp.setDepartmentId(department.getSelectedItem().substring(0, 1));
						emp.setDepartment(department.getSelectedItem().substring(2, 5));
						emp.setEmployeeName(employeeName.getText().trim());
						if(woman.isSelected()) {
							emp.setSex("woman");
						}
						emp.setMobile(mobile.getText());
						emp.setEmail(email.getText());
						emp.setSalary(Integer.parseInt(salary.getText()));
						//为新建的员工对象的属性设置具体值
						Dbope ope = new Dbope();
						ope.getAdd(emp);//录入员工
						JOptionPane.showMessageDialog(this, "录入成功");
						new OperationRecord(opeNum , num).addRecord();//添加员工后留下记录
						ope.db_close();//关闭数据库
					}					
				}
				else {
					JOptionPane.showMessageDialog(this, "两次输入的密码不一致！");
				}
			}		
		}
		if(e.getSource() == reset) {
			//如果点击了重置按钮，则设置所有文本输入框中的内容为空
			employeeNumber.setText(null);
			employeeName.setText(null);
			password.setText(null);
			password2.setText(null);
			email.setText(null);
			mobile.setText(null);
			salary.setText(null);
		}
	}
}

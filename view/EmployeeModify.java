package com.huangguohang.www.view;
/**
 * 员工修改界面
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;

import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.po.Employee;
import com.huangguohang.www.util.*;

public class EmployeeModify extends JPanel implements ActionListener {
	JTextField employeeNumber, employeeName, password, salary, email, mobile;
	Choice department;
	JRadioButton man, woman;
	JButton enter, reset, modify;
	ButtonGroup group = null;
	int flag = 0;
	String opeNum = null;//操作者员工号
	String num = null;//被操作者员工号
	
	public EmployeeModify() {
		modify = new JButton("开始修改");
		modify.addActionListener(this);
		employeeNumber = new JTextField(10);	
		department = new Choice();
		department.add("1.研发部");
		department.add("2.财务部");
		department.add("3.销售部");
		department.add("4.人事部");
		employeeName = new JTextField(10);
		password = new JTextField(10);
		email = new JTextField(10);
		mobile = new JTextField(10);
		salary = new JTextField(10);
		group = new ButtonGroup();
		man = new JRadioButton("男", true);
		woman = new JRadioButton("女", false);
		group.add(man);
		group.add(woman);
		enter = new JButton("录入修改");
		reset = new JButton("重置");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Button_enter_Listener(e);			
			}		
		});//为按钮enter添加事件监听
		reset.addActionListener(this);
		Box box1 = Box.createVerticalBox();
		box1.add(new JLabel("员工姓名:", JLabel.CENTER));
		box1.add(employeeName);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("性别:", JLabel.CENTER));
		box2.add(man);
		box2.add(woman);
		Box box3 = Box.createVerticalBox();
		box3.add(new JLabel("请输入员工号:", JLabel.CENTER));
		box3.add(employeeNumber);
		Box box4 = Box.createVerticalBox();
		box4.add(new JLabel("密码:", JLabel.CENTER));
		box4.add(password);
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
		Box boxH = Box.createVerticalBox();
		boxH.add(box3);
		boxH.add(modify);
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box5);
		boxH.add(box6);
		boxH.add(box7);
		boxH.add(box8);//讲所有文本输入框和选项放进一个Box中
		JPanel pCenter = new JPanel();
		pCenter.add(boxH);
		JPanel pSouth = new JPanel();
		pSouth.add(enter);
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
		if(e.getSource() == modify){
		//如果点击了modify按钮,则进行下面操作
			num = employeeNumber.getText().trim();//拿到要修改的员工号
			if(new NumAndPswIden(num).numIden2()){
				//如果员工号判断正确,则进行下面操作
				Dbope ope = new Dbope();
				ResultSet rs = ope.getRs(num);
				try {
					if(rs.next()) {
						employeeName.setText(rs.getString("employeeName"));
						if(rs.getString("sex").equals("woman")){				
							woman.setSelected(true);
						}
						if(rs.getString("sex").equals("man")){				
							man.setSelected(true);
						}
						password.setText(rs.getString("password"));
					 	String  dep = rs.getString("departmentId")+"."+rs.getString("department");
						department.select(dep);
						mobile.setText(rs.getString("mobile"));
						email.setText(rs.getString("email"));
						salary.setText(rs.getString("salary"));
						flag =1;//表明已经按过modify这个按钮
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					ope.db_close();	//关闭数据库
				}				
			}
		}
		if(e.getSource() == reset) {
			//如果点击了reset按钮，则将文本输入框中的内容清空
			employeeNumber.setText(null);
			employeeName.setText(null);
			password.setText(null);
			email.setText(null);
			mobile.setText(null);
			salary.setText(null);
		}
	}
	
	/**
	 * enter按钮的ActionListener
	 */
	public void Button_enter_Listener(ActionEvent e) {
			if(flag == 1) {	
				//先判断是否按下了上面的modify按钮
				String employeeNum2 = employeeNumber.getText().trim();
				if(!employeeNum2.equals(num)) {
					//员工号不能随便修改，如果员工试图修改员工号则不能通过
					JOptionPane.showMessageDialog(null, "请不要修改员工号！");
				}
				else {
					Boolean nameIden = new NameIden(employeeName.getText().trim()).nameIden();
					Boolean mobileIden = new MobileIden(mobile.getText()).mobileIden();
					Boolean emailIden = new EmailIden(email.getText()).emailIden();
					Boolean salaryIden = new SalaryIden(salary.getText()).salaryIden();
					if(nameIden && mobileIden && emailIden && salaryIden) {
						Dbope ope = new Dbope();
						Employee emp = new Employee(num); 
						emp.setDepartmentId(department.getSelectedItem().substring(0, 1));
						emp.setDepartment(department.getSelectedItem().substring(2, 5));
						emp.setEmployeeName(employeeName.getText().trim());
						if(woman.isSelected()) {
							emp.setSex("woman");
						}
						emp.setPassword(password.getText());
						emp.setMobile(mobile.getText());
						emp.setEmail(email.getText());
						emp.setSalary(Integer.parseInt(salary.getText()));
						ope.getUpdate(emp);
						ope.db_close();//关闭数据库
						new OperationRecord(opeNum, num).modifyRecord();//修改后留下记录
						JOptionPane.showMessageDialog(null, "修改成功！");
					}			
				}		
			}
			else {
				JOptionPane.showMessageDialog(null, "请先按开始修改！");
			}
		}
	
}

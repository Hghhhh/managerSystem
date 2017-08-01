package com.huangguohang.www.view;
/**
 * Ա���޸Ľ���
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
	String opeNum = null;//������Ա����
	String num = null;//��������Ա����
	
	public EmployeeModify() {
		modify = new JButton("��ʼ�޸�");
		modify.addActionListener(this);
		employeeNumber = new JTextField(10);	
		department = new Choice();
		department.add("1.�з���");
		department.add("2.����");
		department.add("3.���۲�");
		department.add("4.���²�");
		employeeName = new JTextField(10);
		password = new JTextField(10);
		email = new JTextField(10);
		mobile = new JTextField(10);
		salary = new JTextField(10);
		group = new ButtonGroup();
		man = new JRadioButton("��", true);
		woman = new JRadioButton("Ů", false);
		group.add(man);
		group.add(woman);
		enter = new JButton("¼���޸�");
		reset = new JButton("����");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Button_enter_Listener(e);			
			}		
		});//Ϊ��ťenter����¼�����
		reset.addActionListener(this);
		Box box1 = Box.createVerticalBox();
		box1.add(new JLabel("Ա������:", JLabel.CENTER));
		box1.add(employeeName);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("�Ա�:", JLabel.CENTER));
		box2.add(man);
		box2.add(woman);
		Box box3 = Box.createVerticalBox();
		box3.add(new JLabel("������Ա����:", JLabel.CENTER));
		box3.add(employeeNumber);
		Box box4 = Box.createVerticalBox();
		box4.add(new JLabel("����:", JLabel.CENTER));
		box4.add(password);
		Box box5 = Box.createVerticalBox();
		box5.add(new JLabel("����:", JLabel.CENTER));
		box5.add(department);
		Box box6 = Box.createVerticalBox();
		box6.add(new JLabel("�绰:", JLabel.CENTER));
		box6.add(mobile);
		Box box7 = Box.createVerticalBox();
		box7.add(new JLabel("����:", JLabel.CENTER));
		box7.add(email);
		Box box8 = Box.createVerticalBox();
		box8.add(new JLabel("нˮ:", JLabel.CENTER));
		box8.add(salary);
		Box boxH = Box.createVerticalBox();
		boxH.add(box3);
		boxH.add(modify);
		boxH.add(box1);
		boxH.add(box2);
		boxH.add(box5);
		boxH.add(box6);
		boxH.add(box7);
		boxH.add(box8);//�������ı�������ѡ��Ž�һ��Box��
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
	 * ���ò����ߵ�Ա����
	 * @param opeNum
	 */
	public void setOpeNum(String opeNum) {
		this.opeNum = opeNum;
	}
	
	public void actionPerformed(ActionEvent e) {	
		if(e.getSource() == modify){
		//��������modify��ť,������������
			num = employeeNumber.getText().trim();//�õ�Ҫ�޸ĵ�Ա����
			if(new NumAndPswIden(num).numIden2()){
				//���Ա�����ж���ȷ,������������
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
						flag =1;//�����Ѿ�����modify�����ť
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					ope.db_close();	//�ر����ݿ�
				}				
			}
		}
		if(e.getSource() == reset) {
			//��������reset��ť�����ı�������е��������
			employeeNumber.setText(null);
			employeeName.setText(null);
			password.setText(null);
			email.setText(null);
			mobile.setText(null);
			salary.setText(null);
		}
	}
	
	/**
	 * enter��ť��ActionListener
	 */
	public void Button_enter_Listener(ActionEvent e) {
			if(flag == 1) {	
				//���ж��Ƿ����������modify��ť
				String employeeNum2 = employeeNumber.getText().trim();
				if(!employeeNum2.equals(num)) {
					//Ա���Ų�������޸ģ����Ա����ͼ�޸�Ա��������ͨ��
					JOptionPane.showMessageDialog(null, "�벻Ҫ�޸�Ա���ţ�");
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
						ope.db_close();//�ر����ݿ�
						new OperationRecord(opeNum, num).modifyRecord();//�޸ĺ����¼�¼
						JOptionPane.showMessageDialog(null, "�޸ĳɹ���");
					}			
				}		
			}
			else {
				JOptionPane.showMessageDialog(null, "���Ȱ���ʼ�޸ģ�");
			}
		}
	
}

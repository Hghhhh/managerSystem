package com.huangguohang.www.view;
/**
 *Ա��������ҳ
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
		save = new JButton("����");
		save.addActionListener(this);//Ϊ���水ť����¼�����	
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
		man = new JRadioButton("��", true);
		woman = new JRadioButton("Ů", false);
		group.add(man);
		group.add(woman);
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("Ա������:", JLabel.CENTER));
		box1.add(employeeName);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("�Ա�:"));
		box2.add(man);
		box2.add(woman);
		Box box3 = Box.createHorizontalBox();
		box3.add(new JLabel("Ա����:", JLabel.CENTER));
		box3.add(employeeNumber);
		Box box4 = Box.createHorizontalBox();
		box4.add(new JLabel("����:"));
		box4.add(password);
		Box box44 = Box.createHorizontalBox();
		box44.add(new JLabel("ȷ������:"));
		box44.add(password2);
		Box box5 = Box.createHorizontalBox();
		box5.add(new JLabel("����:"));
		box5.add(department);
		Box box6 = Box.createHorizontalBox();
		box6.add(new JLabel("�绰:"));
		box6.add(mobile);
		Box box7 = Box.createHorizontalBox();
		box7.add(new JLabel("����:"));
		box7.add(email);
		Box box8 = Box.createHorizontalBox();
		box8.add(new JLabel("нˮ:"));
		box8.add(salary);
		Box box10 = Box.createHorizontalBox();
		box10.add(new JLabel("��ְ����:", JLabel.CENTER));
		box10.add(time);
		Box box11 = Box.createHorizontalBox();
		box11.add(new JLabel("����Id:", JLabel.CENTER));
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
		//boxH����Ա�����������롢�绰��������Щ�����޸ĵ���Ϣ
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
		//box����Ա��Id�����š�Ա���š�нˮ����ְ������Щ�����޸ĵ���Ϣ
		JPanel pc2 = new JPanel();
		pc2.add(boxH);
		pCenter.setLayout(new GridLayout(1,2));//����pCenter�Ĳ���ΪGridLayout,һ������
		pCenter.add(pc2);//��һ�����ӷ���װԱ�����������롢�绰����������
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
	 * ���Ա����Ϣ
	 * @param Ա����¼������и�Ա����Ϣ�Ľ����
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
				//����һ�����۵Ľ���
				pCenter.add(evaluation);//ΪpCenter�ĵڶ���������Ӵ����۽���
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * actionPerformed
	 */
	public void actionPerformed(ActionEvent e) {
			if(password.getText().equals(password2.getText())) {
				//���������������һ�£�������������
				Boolean pswIden = new NumAndPswIden().pswIden2(password.getText());
				Boolean mobileIden = new MobileIden(mobile.getText()).mobileIden();
				Boolean emailIden = new EmailIden(email.getText()).emailIden();
				Boolean nameIden = new NameIden(employeeName.getText().trim()).nameIden();
				if(pswIden && mobileIden && emailIden && nameIden) {
					String num = employeeNumber.getText().trim();//��ȡԱ����
					Dbope ope = new Dbope();
					Employee emp = new Employee(num); //����һ��Ա������
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
					ope.getUpdate(emp);//ΪԱ��������ֵ���������Ϣ
					ope.db_close();//�ر����ݿ�
					JOptionPane.showMessageDialog(null, "����ɹ���");
				}			
			}
			else {
				JOptionPane.showMessageDialog(null, "������������벻һ�£�");
			}			
		}
	
}
	


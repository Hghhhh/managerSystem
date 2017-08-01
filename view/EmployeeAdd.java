package com.huangguohang.www.view;
/**
 *Ա�����
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
	String opeNum = null;//�����ߵ�Ա����
	
	public EmployeeAdd() {
		department = new Choice();
		department.add("1.�з���");
		department.add("2.����");
		department.add("3.���۲�");
		department.add("4.���²�");//����һ��Choice���������˸�������,ǰ��Ϊ����Id
		employeeNumber = new JTextField(20);//Ա���������
		employeeName = new JTextField(20);//Ա�����������
		password = new JPasswordField(20);//���������1
		password2 = new JPasswordField(20);//���������2
		email = new JTextField(20);//���������
		mobile = new JTextField(20);//�绰�����
		salary = new JTextField(20);//нˮ�����
		group = new ButtonGroup();
		man = new JRadioButton("��", true);
		woman = new JRadioButton("Ů", false);
		group.add(man);
		group.add(woman);//����һ��ButtonGroup�����Ա�ʹ�Ա�ѡ���
		insert = new JButton("¼��");
		reset = new JButton("����");
		insert.addActionListener(this);
		reset.addActionListener(this);//Ϊ��ť����¼�����
		Box box1 = Box.createVerticalBox();
		box1.add(new JLabel("Ա������:", JLabel.CENTER));
		box1.add(employeeName);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("�Ա�:", JLabel.CENTER));
		box2.add(man);
		box2.add(woman);
		Box box3 = Box.createVerticalBox();
		box3.add(new JLabel("Ա����:", JLabel.CENTER));
		box3.add(employeeNumber);
		Box box4 = Box.createVerticalBox();
		box4.add(new JLabel("����:", JLabel.CENTER));
		box4.add(password);
		Box box44 = Box.createVerticalBox();
		box44.add(new JLabel("ȷ������:", JLabel.CENTER));
		box44.add(password2);
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
		Box boxH = Box.createVerticalBox();//������������ѡ���ӽ�һ��Box
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
	 * ���ò����ߵ�Ա����
	 * @param opeNum
	 */
	public void setOpeNum(String opeNum) {
		this.opeNum = opeNum;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == insert){
			//��������¼�밴ť�����������Ĳ���
			String num = employeeNumber.getText().trim();//��������Ա����
			Employee emp = null;
			if(new NumAndPswIden(num).numIden3()) {
				//�ж�Ա���ŵĸ�ʽ�Ƿ���ȷ�Լ��Ƿ��Ѿ�������,��������Ա���ſ����ã����������Ĳ���
				if(password.getText().equals(password2.getText())) {
					//�ж���������������Ƿ�һ�£���һ�����������Ĳ���
					Boolean nameIden = new NameIden(employeeName.getText().trim()).nameIden();
					Boolean mobileIden = new MobileIden(mobile.getText()).mobileIden();
					Boolean emailIden = new EmailIden(email.getText()).emailIden();
					Boolean salaryIden = new SalaryIden(salary.getText()).salaryIden();
					if(nameIden && mobileIden && emailIden && salaryIden) {
						//�����������绰�����䡢нˮ��ʽ�ж�,���ȫ����ȷ������������
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
						//Ϊ�½���Ա��������������þ���ֵ
						Dbope ope = new Dbope();
						ope.getAdd(emp);//¼��Ա��
						JOptionPane.showMessageDialog(this, "¼��ɹ�");
						new OperationRecord(opeNum , num).addRecord();//���Ա�������¼�¼
						ope.db_close();//�ر����ݿ�
					}					
				}
				else {
					JOptionPane.showMessageDialog(this, "������������벻һ�£�");
				}
			}		
		}
		if(e.getSource() == reset) {
			//�����������ð�ť�������������ı�������е�����Ϊ��
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

package com.huangguohang.www.service;
/**
 *����������
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;

import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.dao.Dbope2;
import com.huangguohang.www.po.Evaluation;
import com.huangguohang.www.util.NumAndPswIden;

public class EvaluationAdd extends JPanel implements ActionListener{
	int evaluatorId = 0, employeeId = 0;
	String departmentId = null;
	JTextField employeeNumber = new JTextField(15);
	Choice level = new Choice();
	JButton enter = new JButton("�������"); 
	JTextArea comment = new JTextArea("��������������(1-200���ַ�)", 10, 50);
	
	/**
	 * ���췽��
	 * @param evaluatorId
	 * @param �����ߵ�departmentId
	 */
	public EvaluationAdd(int evaluatorId, String departmentId) {
		this.evaluatorId = evaluatorId;
		this.departmentId = departmentId; 
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("��������Ҫ���۵�Ա����:"));
		box1.add(employeeNumber);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("��ѡ����Ҫ���۵ĵȼ�:"));
		box2.add(level);
		level.add("A");
		level.add("B");
		level.add("C");
		JPanel pNouth = new JPanel();
		pNouth.setLayout(new GridLayout(3,1));
		pNouth.add(box1);
		pNouth.add(box2);
		JPanel pSouth = new JPanel();
		pSouth.add(enter);
		enter.addActionListener(this);
		this.setLayout(new BorderLayout());
		this.add(pNouth, BorderLayout.NORTH);
		comment.setLineWrap(true);
		this.add(comment, BorderLayout.CENTER);
		this.add(pSouth, BorderLayout.SOUTH);
	}
    
	public void actionPerformed(ActionEvent arg0) {
		Dbope ope1 = new Dbope();
		Dbope2 ope2 = new Dbope2();
		ResultSet rs = null;
		String em = employeeNumber.getText().trim();
		int len = comment.getText().length();
		Boolean flag = new NumAndPswIden(em).numIden();//�ж������Ա�����Ƿ���ȷ
		if(flag) {
			if(len == 0 || len > 200) {
				//�ж����ﳤ��
				JOptionPane.showMessageDialog(null, "��ע�����ﳤ�ȣ�(1-200)");
			}
			else {
				rs= ope1.getRs(em);//�õ�һ��Ա����Ϊem���ض������
				try {
					if(rs.next()) {
						if(!rs.getString("departmentId").equals(departmentId)) {
							//�ж������Ա���Ƿ������������ͬ����
							JOptionPane.showMessageDialog(null, "����ͬ�����ŵ�Ա�������໥���ۣ�");
						}
						else if(rs.getInt("employeeId") == evaluatorId) {
							//�ж������Ա���Ƿ��������Լ�
							JOptionPane.showMessageDialog(null, "�Լ����������Լ�Ŷ��");
						}
						else {
							Evaluation eva = new Evaluation(evaluatorId, rs.getInt("employeeId"));
							eva.setComment(comment.getText());
							eva.setStartLevel(level.getSelectedItem());
							ope2.getAdd(eva);//�������
							JOptionPane.showMessageDialog(null, "�������ɹ�");
						}									
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			}
		}
		ope1.db_close();
		ope2.db_close();//�ر����ݿ�
	}
	
}

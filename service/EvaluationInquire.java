package com.huangguohang.www.service;
/**
 *�����ѯ���� 
 */

import java.awt.BorderLayout;
import java.awt.event.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Vector;

import javax.swing.*;

import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.dao.Dbope2;
import com.huangguohang.www.util.NumAndPswIden;

public class EvaluationInquire extends JPanel implements ActionListener{
	JList list = null;
	JScrollPane scrollPane = null;
	JButton fresh, search; 
	JTextField employeeNumber;
	Dbope2 ope = null;
	ResultSet rs = null;
	
	public EvaluationInquire() {
		 list = new JList(refresh());
		 scrollPane = new JScrollPane(list);
		 fresh = new JButton("ˢ��");
		 search = new JButton("����");
		 fresh.addActionListener(this);//����¼�����
		 search.addActionListener(this);//����¼�����
		 employeeNumber = new JTextField(20);
		 employeeNumber.setText("��������Ҫ�鿴��Ա��������");
		 Box box = Box.createHorizontalBox();
		 box.add(employeeNumber);
		 box.add(search);
		 box.add(fresh);
		 JPanel pSouth = new JPanel();
		 pSouth.add(box);
		 this.setLayout(new BorderLayout());
		 this.add(new JLabel("���е�����:", JLabel.LEFT), BorderLayout.NORTH);
		 this.add(scrollPane, BorderLayout.CENTER);
		 this.add(pSouth, BorderLayout.SOUTH);
	}
	
	/**
	 * ˢ�µķ���
	 */
	public Vector<String> refresh() {
		Vector<String> evaluation = new Vector<String>();
		ope = new Dbope2();
		rs = ope.getAllRs();
		String eva = null;
		try {
			while(rs.next()) {
				eva = "����Id:"+rs.getInt("id")+"     ��������Id:"+rs.getInt("employeeId")+"  ���۵ȼ�:"+rs.getString("startLevel")+"     ����:--"
					+rs.getString("comment")+" �������� ������Id: "+rs.getInt("evaluatorId");
				evaluation.addElement(eva);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ope.db_close();
		return evaluation;
	}
	
	/**
	 *����Ա���Ų�ѯʱ���� �˷���
	 *@param :һ������Ա���ž����Ľ����
	 *@return:������ѯ��Ϣ��һ��Vector����
	 */
	public Vector<String> afterSearch(ResultSet valu) {
		Vector<String> evaluation = new Vector<String>();
		String eva = null;
		try {
			while(valu.next()) {
				eva = "����Id:"+valu.getInt("id")+"     ��������Id:"+valu.getInt("employeeId")+"  ���۵ȼ�:"+valu.getString("startLevel")+"     ����:--"
					+valu.getString("comment")+" �������� ������Id: "+valu.getInt("evaluatorId");
				evaluation.addElement(eva);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return evaluation;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fresh) {
			list.removeAll();
			list.setListData(refresh());//ˢ������
		}
		if(e.getSource() == search) {
			//�������˲�ѯ��ť,��������Ĳ���
			String em = employeeNumber.getText().trim();//�õ������Ա����
			if(em.matches("\\d{1,}")) {//�ж������Ա�����Ƿ�ȫ������
				Dbope employ = new Dbope();//����tb_employee
				Dbope2 evalua = new Dbope2();//����tb_evaluation
				ResultSet rs1 = employ.getRs2(em);//�õ�ģ����ѯ�Ľ����
				ResultSet valu = null;
				Boolean flag = false;
				Vector<String> evaluation = new Vector<String>();
				String eva = null;
				flag = true;
				try {
					while(rs1.next()) {			
						valu = evalua.getRs(rs1.getInt("employeeId"));
						if(valu.next()) {
							eva = "����Id:"+valu.getInt("id")+"     ��������Id:"+valu.getInt("employeeId")
							+"  ���۵ȼ�:"+valu.getString("startLevel")+"     ����:--"
							+valu.getString("comment")+" �������� ������Id: "+valu.getInt("evaluatorId");
							evaluation.addElement(eva);//���������Ϣһ��һ���ؼӵ�������
						}
						flag = true;//�ж�һ����û���ҵ�Ա���ţ�����ҵ��ˣ���Ϊtrue 
					}
					list.removeAll();//���list
					list.setListData(evaluation);//�������е���Ϣ����list
					if(!flag) {
						JOptionPane.showMessageDialog(null, "���޴��ˣ�");
					}
					rs1.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					employ.db_close();
					evalua.db_close();//�ر����ݿ�
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "��ע��Ա���Ÿ�ʽ!");
			}
		}
	}
}

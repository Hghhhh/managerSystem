package com.huangguohang.www.service;
/**
 *�����޸Ľ��� 
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import java.util.Vector;

import javax.swing.*;

import com.huangguohang.www.dao.*;

public class EvaluationModify extends JPanel implements ActionListener{
	JList list = null;
	JScrollPane scrollPane = null;
	JButton  delete, fresh;
	Dbope2 ope = null;
	ResultSet rs = null;
	int evaluatorId;
	
	/**
	 * �����޸Ľ��湹�췽��
	 * @param evaluatorId
	 */
	public EvaluationModify(int evaluatorId) {
		this.evaluatorId = evaluatorId;
		list = new JList(refresh());
		scrollPane = new JScrollPane(list);
		delete = new JButton("ɾ����ѡ����");
		delete.addActionListener(this);
		fresh = new JButton("ˢ��");
		fresh.addActionListener(this);
		JPanel pSouth = new JPanel();
		pSouth.add(delete);
		pSouth.add(fresh);
		this.setLayout(new BorderLayout());
		this.add(new JLabel("��������Ա������������:", JLabel.LEFT), BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pSouth, BorderLayout.SOUTH);
	}

	/**
	 * ˢ�µķ���
	 * @return Vector<String>
	 */
	public Vector<String> refresh() {
		Vector<String> evaluation = new Vector<String>();
		ope = new Dbope2();
		rs = ope.getRs2(evaluatorId);
		String eva = null;
		try {
			while(rs.next()) {
				eva = "����Id:"+rs.getInt("id")+"     ��������Id:"+rs.getInt("employeeId")+"  ���۵ȼ�:"+rs.getString("startLevel")+"     ����:--"
					+rs.getString("comment");
				evaluation.addElement(eva);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ope.db_close();
		return evaluation;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == delete) {
			Object[] eval = (Object[])list.getSelectedValues();
			if(eval == null) {
				JOptionPane.showMessageDialog(null, "����ѡ��һ��Ҫɾ��������!");
			}
			else {
				int evaId;
				for(int i=0; i<eval.length; i++) {
					Dbope2 ope2 = new Dbope2();
					evaId = Integer.parseInt(eval[i].toString().substring(5, 11).trim());
					ope2.getDelete(evaId);
					ope2.db_close();
				}
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�!");
				list.removeAll();
				list.setListData(refresh());		
			}
		}
		if(e.getSource() == fresh) {	
			list.removeAll();
			list.setListData(refresh());//ˢ��
		}
	}
}

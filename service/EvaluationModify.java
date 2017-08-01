package com.huangguohang.www.service;
/**
 *评语修改界面 
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
	 * 评语修改界面构造方法
	 * @param evaluatorId
	 */
	public EvaluationModify(int evaluatorId) {
		this.evaluatorId = evaluatorId;
		list = new JList(refresh());
		scrollPane = new JScrollPane(list);
		delete = new JButton("删除所选评语");
		delete.addActionListener(this);
		fresh = new JButton("刷新");
		fresh.addActionListener(this);
		JPanel pSouth = new JPanel();
		pSouth.add(delete);
		pSouth.add(fresh);
		this.setLayout(new BorderLayout());
		this.add(new JLabel("您对其他员工的所有评价:", JLabel.LEFT), BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(pSouth, BorderLayout.SOUTH);
	}

	/**
	 * 刷新的方法
	 * @return Vector<String>
	 */
	public Vector<String> refresh() {
		Vector<String> evaluation = new Vector<String>();
		ope = new Dbope2();
		rs = ope.getRs2(evaluatorId);
		String eva = null;
		try {
			while(rs.next()) {
				eva = "评语Id:"+rs.getInt("id")+"     被评价者Id:"+rs.getInt("employeeId")+"  评价等级:"+rs.getString("startLevel")+"     评语:--"
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
				JOptionPane.showMessageDialog(null, "请您选择一条要删除的评语!");
			}
			else {
				int evaId;
				for(int i=0; i<eval.length; i++) {
					Dbope2 ope2 = new Dbope2();
					evaId = Integer.parseInt(eval[i].toString().substring(5, 11).trim());
					ope2.getDelete(evaId);
					ope2.db_close();
				}
				JOptionPane.showMessageDialog(null, "删除成功!");
				list.removeAll();
				list.setListData(refresh());		
			}
		}
		if(e.getSource() == fresh) {	
			list.removeAll();
			list.setListData(refresh());//刷新
		}
	}
}

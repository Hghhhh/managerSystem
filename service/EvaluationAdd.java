package com.huangguohang.www.service;
/**
 *添加评语界面
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
	JButton enter = new JButton("添加评语"); 
	JTextArea comment = new JTextArea("请输入您的评语(1-200个字符)", 10, 50);
	
	/**
	 * 构造方法
	 * @param evaluatorId
	 * @param 评价者的departmentId
	 */
	public EvaluationAdd(int evaluatorId, String departmentId) {
		this.evaluatorId = evaluatorId;
		this.departmentId = departmentId; 
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("请输入您要评价的员工号:"));
		box1.add(employeeNumber);
		Box box2 = Box.createHorizontalBox();
		box2.add(new JLabel("请选择您要评价的等级:"));
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
		Boolean flag = new NumAndPswIden(em).numIden();//判断输入的员工号是否正确
		if(flag) {
			if(len == 0 || len > 200) {
				//判断评语长度
				JOptionPane.showMessageDialog(null, "请注意评语长度！(1-200)");
			}
			else {
				rs= ope1.getRs(em);//得到一个员工号为em的特定结果集
				try {
					if(rs.next()) {
						if(!rs.getString("departmentId").equals(departmentId)) {
							//判断输入的员工是否和评价者是相同部门
							JOptionPane.showMessageDialog(null, "不是同个部门的员工不能相互评价！");
						}
						else if(rs.getInt("employeeId") == evaluatorId) {
							//判断输入的员工是否评价者自己
							JOptionPane.showMessageDialog(null, "自己不能评价自己哦！");
						}
						else {
							Evaluation eva = new Evaluation(evaluatorId, rs.getInt("employeeId"));
							eva.setComment(comment.getText());
							eva.setStartLevel(level.getSelectedItem());
							ope2.getAdd(eva);//添加评价
							JOptionPane.showMessageDialog(null, "添加评语成功");
						}									
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			}
		}
		ope1.db_close();
		ope2.db_close();//关闭数据库
	}
	
}

package com.huangguohang.www.service;
/**
 *评语查询界面 
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
		 fresh = new JButton("刷新");
		 search = new JButton("查找");
		 fresh.addActionListener(this);//添加事件监听
		 search.addActionListener(this);//添加事件监听
		 employeeNumber = new JTextField(20);
		 employeeNumber.setText("请输入请要查看的员工的评价");
		 Box box = Box.createHorizontalBox();
		 box.add(employeeNumber);
		 box.add(search);
		 box.add(fresh);
		 JPanel pSouth = new JPanel();
		 pSouth.add(box);
		 this.setLayout(new BorderLayout());
		 this.add(new JLabel("所有的评价:", JLabel.LEFT), BorderLayout.NORTH);
		 this.add(scrollPane, BorderLayout.CENTER);
		 this.add(pSouth, BorderLayout.SOUTH);
	}
	
	/**
	 * 刷新的方法
	 */
	public Vector<String> refresh() {
		Vector<String> evaluation = new Vector<String>();
		ope = new Dbope2();
		rs = ope.getAllRs();
		String eva = null;
		try {
			while(rs.next()) {
				eva = "评语Id:"+rs.getInt("id")+"     被评价者Id:"+rs.getInt("employeeId")+"  评价等级:"+rs.getString("startLevel")+"     评语:--"
					+rs.getString("comment")+" ———— 评价者Id: "+rs.getInt("evaluatorId");
				evaluation.addElement(eva);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ope.db_close();
		return evaluation;
	}
	
	/**
	 *输入员工号查询时调用 此方法
	 *@param :一个根据员工号决定的结果集
	 *@return:包含查询信息的一个Vector数组
	 */
	public Vector<String> afterSearch(ResultSet valu) {
		Vector<String> evaluation = new Vector<String>();
		String eva = null;
		try {
			while(valu.next()) {
				eva = "评语Id:"+valu.getInt("id")+"     被评价者Id:"+valu.getInt("employeeId")+"  评价等级:"+valu.getString("startLevel")+"     评语:--"
					+valu.getString("comment")+" ———— 评价者Id: "+valu.getInt("evaluatorId");
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
			list.setListData(refresh());//刷新数据
		}
		if(e.getSource() == search) {
			//如果点击了查询按钮,进行下面的操作
			String em = employeeNumber.getText().trim();//拿到输入的员工号
			if(em.matches("\\d{1,}")) {//判断输入的员工号是否全是数字
				Dbope employ = new Dbope();//连接tb_employee
				Dbope2 evalua = new Dbope2();//连接tb_evaluation
				ResultSet rs1 = employ.getRs2(em);//拿到模糊查询的结果集
				ResultSet valu = null;
				Boolean flag = false;
				Vector<String> evaluation = new Vector<String>();
				String eva = null;
				flag = true;
				try {
					while(rs1.next()) {			
						valu = evalua.getRs(rs1.getInt("employeeId"));
						if(valu.next()) {
							eva = "评语Id:"+valu.getInt("id")+"     被评价者Id:"+valu.getInt("employeeId")
							+"  评价等级:"+valu.getString("startLevel")+"     评语:--"
							+valu.getString("comment")+" ———— 评价者Id: "+valu.getInt("evaluatorId");
							evaluation.addElement(eva);//将评语的信息一条一条地加到数组中
						}
						flag = true;//判断一下有没有找到员工号，如果找到了，则为true 
					}
					list.removeAll();//清空list
					list.setListData(evaluation);//把数组中的信息放入list
					if(!flag) {
						JOptionPane.showMessageDialog(null, "查无此人！");
					}
					rs1.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					employ.db_close();
					evalua.db_close();//关闭数据库
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "请注意员工号格式!");
			}
		}
	}
}

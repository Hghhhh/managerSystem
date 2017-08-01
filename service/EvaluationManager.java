package com.huangguohang.www.service;
/**
 *评语主界面 
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import java.util.Vector;

import javax.swing.*;

import com.huangguohang.www.dao.Dbope2;

public class EvaluationManager extends JPanel implements ActionListener{
	JMenuBar bar;
	JMenu fileMenu;
	JMenuItem window,insert,change,search,personal;
	EvaluationWindow ew = null;
	EvaluationAdd ea = null;
	EvaluationInquire ei = null;
	EvaluationModify em = null;
	CardLayout card = null;
	JPanel pCenter = null;
	
	public EvaluationManager(int employeeId, String departmentId) {
		ew = new EvaluationWindow(employeeId); 
		ea = new EvaluationAdd(employeeId, departmentId);
		ei = new EvaluationInquire();
		em = new EvaluationModify(employeeId);
		window = new JMenuItem("初始界面");
		insert = new JMenuItem("添加我对其他员工的评价");
		change = new JMenuItem("修改我对其他员工的评价");
		search = new JMenuItem("查看所有员工评价");
		fileMenu = new JMenu("评价菜单");//创建一个菜单添加各个选项
		fileMenu.add(window);
		fileMenu.add(insert);
		fileMenu.add(change);
		fileMenu.add(search);
		window.addActionListener(this);//为各个菜单选项添加事件监听
		insert.addActionListener(this);
		change.addActionListener(this);
		search.addActionListener(this);
		bar = new JMenuBar();
		bar.add(fileMenu);
		pCenter = new JPanel();
		card = new CardLayout();
		pCenter.setLayout(card);//设置pCenter的布局为CardLayout
		pCenter.add("初始界面", ew);
		pCenter.add("录入界面", ea);
		pCenter.add("查询界面", ei);
		pCenter.add("修改界面", em);
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.add(pCenter, BorderLayout.CENTER);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == window){
			card.show(pCenter, "初始界面");
		}
		if(e.getSource() == insert){
			card.show(pCenter, "录入界面");
		}
		if(e.getSource() == search){
			card.show(pCenter, "查询界面");
			ei.list.removeAll();
			ei.list.setListData(ei.refresh());//显示时刷新一下
		}
		if(e.getSource() == change){
			card.show(pCenter, "修改界面");
			em.list.removeAll();
			em.list.setListData(em.refresh());//显示时刷新一下
		}
	}
	
	/**
	 * 内部类:初界面，用来放置该员工收到的所有评语
	 */
	class EvaluationWindow extends JPanel {
		JList list = null;
		JScrollPane scrollPane = null;
		Dbope2 ope = new Dbope2();
		ResultSet rs = null;
		
		public EvaluationWindow(int employeeId) {
			Vector<String> evaluation = new Vector<String>();
			rs = ope.getRs(employeeId);
			String eva = null;
			int i = 0;
			try {
				while(rs.next()) {
					//为数组添加内容
					i++;
					eva = i+". 评价等级: "+rs.getString("startLevel")+"     评语: "
						+rs.getString("comment")+" ———— 评价者Id: "+rs.getInt("evaluatorId");
					evaluation.addElement(eva);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				 ope.db_close();//关闭数据库
			}
			 list = new JList(evaluation);//为JList添加内容
			 scrollPane = new JScrollPane(list);//将list放置可滚动的JScrollPane中
			 this.setLayout(new BorderLayout());
			 this.add(new JLabel("你收到的评价有:", JLabel.LEFT), BorderLayout.NORTH);
			 this.add(scrollPane, BorderLayout.CENTER);
	}
	
}

}
package com.huangguohang.www.view;
/**
 * 员工查询窗口
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.util.NumAndPswIden;
import com.huangguohang.www.util.OperationRecord;

public class EmployeeInquire extends JPanel implements ActionListener{
	JTextField department, employeeNumber, employeeName, password, salary, email, mobile;
	JRadioButton man, woman;
	ButtonGroup group = null; 
	JButton inquest, delete, refresh;
	JMenu rank = null;
	JMenuItem rankSalary,rankDepartId;
	JMenuBar bar = null;
	JTable table = null;
	DefaultTableModel model = null;
	JScrollPane sp = null;
	String opeNum = null;//操作者员工号
	String num = null;//被操作者员工号
	
	public EmployeeInquire() {
		employeeNumber = new JTextField(20);
		inquest = new JButton("开始查询");
		inquest.addActionListener(this);//为查询按钮添加事件监听
		delete = new JButton("删除所选员工");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Button_delete_Listener(e);				
			}		
		});//为删除按钮添加事件监听
		rankSalary = new JMenuItem("按薪水排序");
		rankDepartId = new JMenuItem("按部门排序");
		rank = new JMenu("排序");
		rank.add(rankSalary);
		rank.add(rankDepartId);
		rankSalary.addActionListener(this);//按薪水排序添加事件监听
		rankDepartId.addActionListener(this);//为按部门Id排序添加事件监听
		bar = new JMenuBar();
		bar.add(rank);
		refresh = new JButton("刷新");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Button_refresh_Listener(e);
			}
		});//为刷新按钮添加事件监听
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("请输入员工号:"),JLabel.CENTER);
		box1.add(employeeNumber);
		Box box2 = Box.createHorizontalBox();
		box2.add(inquest);
		box2.add(refresh);
		box2.add(delete);
		box2.add(bar);
		Box boxH = Box.createVerticalBox();
		boxH.add(box1);
		boxH.add(box2);
		table = new JTable();//创建一个表格
		table.setRowHeight(20);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//设置为不自动调整大小
		model = (DefaultTableModel) table.getModel();//获取表格的模版
		model.setColumnIdentifiers(new Object[] {"员工Id", "部门Id", "部门",
		"姓名", "性别", "员工号", "薪水", "电话", "邮箱", "任职日期"});//设置表头
		modelRowAdd2();//为表格添加数据库中的所有信息
		table.setModel(model);//设置模版
		table.getTableHeader().setReorderingAllowed(false);//表头设为不滚动
		sp = new JScrollPane();
		sp.setColumnHeaderView(new JLabel("员工信息", JLabel.CENTER));
		sp.setViewportView(table);//创建一个JScrollPane组件来放置表格
		JPanel pNorth = new JPanel();
		pNorth.add(boxH);
		this.setLayout(new BorderLayout());
		this.add(pNorth, BorderLayout.NORTH);
		this.add(sp, BorderLayout.CENTER);
	}

	/**
	 * modelRowAdd:表格模版添加行方法一
	 * 添加的行的信息是根据传入的结果集来决定的
	 * @param 传入一个特定的结果集
	 */
	public void  modelRowAdd(ResultSet rs) {
		Boolean flag = false;
		model.setRowCount(0);
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
				flag = true;
			}
			if(!flag) {
				JOptionPane.showMessageDialog(null, "查无此人！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * modelRowAdd2:表格模版添加行方法二
	 * 添加数据库中所有员工信息
	 */
	public void  modelRowAdd2() {
		Dbope ope = new Dbope();
		ResultSet rs = ope.getAllRs();
		model.setRowCount(0);//先将表格清空
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ope.db_close();//关闭数据库
		}
	}
	
	/**
	 * modelRowAdd3:表格模版添加行方法三
	 * 按薪水升序添加数据库中所有员工信息
	 */
	public void  modelRowAdd3() {
		Dbope ope = new Dbope();
		ResultSet rs = ope.getAllRs2();
		model.setRowCount(0);//先将表格清空
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ope.db_close();//关闭数据库
		}
	}
	
	/**
	 * modelRowAdd4:表格模版添加行方法四
	 * 按部门Id升序添加数据库中所有员工信息
	 */
	public void  modelRowAdd4() {
		Dbope ope = new Dbope();
		ResultSet rs = ope.getAllRs3();
		model.setRowCount(0);//先将表格清空
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ope.db_close();//关闭数据库
		}
	}
	
	/**
	 * 根据传入的Boolean值确定员工的权限，如果为false则删除按钮设置为不可见
	 * @param flag
	 */
	public void DeleteRoot(Boolean flag) {
		if(!flag) {
			delete.setVisible(false);
		}
	}
	
	/**
	 * 设置操作者的员工号
	 * @param opeNum
	 */
	public void setOpeNum(String opeNum) {
		this.opeNum = opeNum;
	}
	
	/**
	 * actionPerformed:用来监听按钮-inquest
	 */
	public void actionPerformed(ActionEvent e) {
		String em = null;
		if(e.getSource() == inquest) {
			em = employeeNumber.getText().trim();//拿到输入的员工号		
			if(em.matches("\\d{1,}")) {//判断一下输入的是不是全是数字
				model.setRowCount(0);//查询成功后先将表格中的内容清空
				Dbope ope = new Dbope();
				ResultSet rs = ope.getRs2(em);
				modelRowAdd(rs);//然后再为表格添加特定的行
				ope.db_close();//关闭数据库
			}
			else {
				JOptionPane.showMessageDialog(null, "请注意员工号格式！");
			}
		}
		if(e.getSource() == rankSalary) {
			modelRowAdd3();
		}
		if(e.getSource() == rankDepartId) {
			modelRowAdd4();
		}
	}
	
	/**
	 * delete按钮的ActionListener
	 */
	public void Button_delete_Listener(ActionEvent arg0) {
		final String OPENUM = opeNum;
		int row = table.getSelectedRowCount();//被选中的行数，如果没有选中任何行则返回-1
		if(row != -1) { 
			Dbope ope = new Dbope();
			int[] rows = table.getSelectedRows();//所有被选中的行对应的索引
			if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "确定要删除员工信息吗？", "警告",
				JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE)){
				//点击了确定删除后，进行下面的操作
					for(int i=0; i<row; i++){
						int r = rows[i];
						num = (String) table.getValueAt(r, 5);//拿到被操作者的员工号
						new OperationRecord(OPENUM, num).deleteRecord();//留下删除记录
					 	ope.getDelete(num);	//删除选中的员工
					}
					JOptionPane.showMessageDialog(null, "删除成功！");
					modelRowAdd2();//删除成功后刷新一下
			}
			ope.db_close();//关闭数据库
		}}
	
	/**
	 * refresh按钮的ActionListener
	 */
	public void Button_refresh_Listener(ActionEvent arg0) {
		modelRowAdd2();							
	}
}

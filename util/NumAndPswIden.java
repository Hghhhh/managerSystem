package com.huangguohang.www.util;
/**
 * 判断输入的员工号和密码是否正确
 */

import java.awt.HeadlessException;

import java.sql.*;

import javax.swing.JOptionPane;

import com.huangguohang.www.dao.Dbope;

public class NumAndPswIden {

	String number, password;
	Dbope ope = null;
	ResultSet rs = null;
	
	/**
	 * 构造方法1:
	 * @param 传入员工号及密码,以及一个员工号为传入员工号的特定结果集,登录时调用
	 */
	public NumAndPswIden(String number, String password, ResultSet rs) {
		this.number = number;
		this.password = password;
		this.rs = rs;
	}
	/**
	 * 构造方法2:
	 * @param 传入员工号
	 */
	public NumAndPswIden(String number) {
		this.number = number;
	}
	/**
	 * 构造方法3
	 */
	public NumAndPswIden() {
		
	}
	
	/**
	 * 员工号验证方法1:判断员工号的格式、长度，以及是否可以查找到此员工号，登录时调用
	 * @return 登录时三个判断均正确则返回true，否则返回false
	 */
	public Boolean numIden() {
		Boolean flag = true;
		ope = new Dbope();
		rs = ope.getAllRs();
		if(number.length() < 5 || number.length() > 10){
			 //判断长度
			 flag = false;
			JOptionPane.showMessageDialog(null, "请注意员工号长度(5-10位)！");
		}
		if(flag) { 		
			//如果长度正确，则判断有无其他符号
			if(!number.matches("\\d{1,}")) {
				 flag = false;
				 JOptionPane.showMessageDialog(null, "员工号不能出现除数字以外的其他符号");
			}
		}
		 if(flag) {
			 //如果长度、格式正确的话，进行下面的操作
			 flag = false;
			 try {
				 //判断员工号是否存在
				while(rs.next()) {
					 if(number.equals(rs.getString("employeeNumber"))) {
						 flag = true;
						 break;
					 }
				 }
				if(flag == false) {
					JOptionPane.showMessageDialog(null, "查无此人！");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		return flag;
	}
	
	/**
	 * 员工号验证方法2:判断员工号的格式、长度，以及是否可以查找到此员工号，查询时调用
	 * @return 查询时三个判断均正确则返回true，否则返回false
	 */
	public Boolean numIden2() {
		Boolean flag = true;
		ope = new Dbope();
		rs = ope.getAllRs();
		if(number.length() < 5 || number.length() > 10){
			 //判断长度
			 flag = false;
			JOptionPane.showMessageDialog(null, "请注意员工号长度(5-10位)！");
		}
		if(flag) { 		
			//如果长度正确，则判断有无其他符号
			if(!number.matches("\\d{1,}")) {
				 flag = false;
				 JOptionPane.showMessageDialog(null, "员工号不能出现除数字以外的其他符号");
			}
		}
		 if(flag) {
			 //如果长度、格式正确的话，进行下面的操作
			 flag = false;
			 try {
				 //判断员工号是否存在
				while(rs.next()) {
					 if(number.equals(rs.getString("employeeNumber"))) {
						 flag = true;
						 break;
					 }
				 }
				if(flag == false) {
					JOptionPane.showMessageDialog(null, "查无此人！");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		 ope.db_close();
		return flag;
	}
	
	/**
	 * 员工号验证方法3:判断员工号的格式、长度以及是否以及存在，添加员工时调用
	 * @return 若格式长度正确且数据库中没有存在此员工号，则返回true，否则返回false
	 */
	public Boolean numIden3() {
		Boolean flag1 = true;
		Boolean flag2 = true;
		Boolean flag = true;
		ope = new Dbope();
		rs = ope.getAllRs();
		 if(number.length() < 5 || number.length() > 10){
			 flag1 = false;
			JOptionPane.showMessageDialog(null, "请注意员工号长度(5-10位)！");
		}
		 if(flag1) {
			 //如果长度正确的话，进行下面的操作
			 if(!number.matches("\\d{1,}")) { //判断员工号格式
				 flag1 = false;
				 JOptionPane.showMessageDialog(null, "员工号不能出现除数字以外的其他符号");
			 }
		 }
		 if(flag1) {
			 //如果长度、格式正确的话，进行下面的操作
			 try {
				 //判断员工号是否已经存在
				while(rs.next()) {
					 if(number.equals(rs.getString("employeeNumber"))) {
						 flag2 = false;
						 break;
					 }
				 }
				if(flag2 == false) {
					JOptionPane.showMessageDialog(null, "该员工号已经存在！");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		flag = (flag1 && flag2); //当格式、长度正确，且数据库中没有存在此员工号则为true
		ope.db_close();
		return flag;
	}
	
	/**
	 * 密码验证方法1:判断登录时密码是否正确
	 * @return 密码正确则为true，否则为false
	 */
	public Boolean pswIden() {
		ope = new Dbope();
		Boolean flag = true;
		try {
				if(password.equals(rs.getString("password"))) {
					JOptionPane.showMessageDialog(null, "登录成功");
				}
				else {
					JOptionPane.showMessageDialog(null, "密码错误！");
					flag = false;
				}
		} catch (HeadlessException | SQLException e) {
			e.printStackTrace();
		}
		ope.db_close();
		return flag;
	}
	
	/**
	 * 密码验证方法2:判断添加或修改密码时密码长度是否正确
	 * @return 密码长度正确则为true，否则为false
	 */
	public Boolean pswIden2(String psw) {
		Boolean flag = true;
		if(psw.length() < 6 || psw.length() > 20) {
			flag = false;
			JOptionPane.showMessageDialog(null, "请注意密码长度(6-20)!");
		}
		return flag;
	}
}

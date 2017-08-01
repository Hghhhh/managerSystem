package com.huangguohang.www.util;
/**
 *当员工对其他员工进行增删改操作时会留下记录，输出到D盘的 managementSystem.txt
 */

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import com.huangguohang.www.dao.Dbope;

public class OperationRecord {
	PrintWriter pr = null;
	FileWriter file = null;
	ResultSet employee1 = null, employee2 = null; 
	String opeNum = null, num = null;
	Dbope ope = new Dbope();
	Date now = null;
	/**
	 * 构造方法：传入操作者和被操作者的员工号
	 * @param opeNum,操作者
	 * @param num,被操作者
	 */
	public OperationRecord(String opeNum, String num) {
		try {
			file = new FileWriter("D:/managementSystem.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.opeNum = opeNum;
		this.num = num;
	}
	
	/**
	 * 添加记录
	 */
	public void addRecord() {		
		try {
			employee1 = ope.getRs(opeNum);//拿到包含操作者信息的结果集
			employee2 = ope.getRs(num);//拿到包含被操作者信息的结果集
			String em1 = null, em2 = null;
			if(employee1.next()) {
				if(employee2.next()) {
					em1 = "员工号为:" + employee1.getString("employeeNumber")
					+ " 姓名为:" + employee1.getString("employeeName") + "的员工添加了一名新员工:";
					//操作者的信息
					em2 = "\n新员工的员工号为:"+employee2.getString("employeeNumber")
					+ " 姓名为:" + employee2.getString("employeeName")
					+ " 部门为:" + employee2.getString("department");
					//被操作者的信息
					now = new Date();
					DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到秒）
					String opeTime = date.format(now);//操作的时间
					pr = new PrintWriter(file);
					pr.println(em1 + em2 + "---" + opeTime);
					file.close();
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pr.close();//关闭IO连接
			ope.db_close();//关闭数据库连接
		}		
	}
	
	/**
	 * 删除记录
	 */
	public void deleteRecord() {
		try {
			employee1 = ope.getRs(opeNum);
			employee2 = ope.getRs(num);
			String em1 = null, em2 = null;
			if(employee1.next()) {
				if(employee2.next()) {
					em1 = "员工号为:" + employee1.getString("employeeNumber")
					+ " 姓名为:" + employee1.getString("employeeName") + "的员工删除了一名员工:";
					em2 = "\n该员工的员工号为:"+employee2.getString("employeeNumber")
					+ " 姓名为:" + employee2.getString("employeeName")
					+ " 部门为:" + employee2.getString("department");
					now = new Date();
					DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到秒）
					String opeTime = date.format(now);//操作的时间
					pr = new PrintWriter(file);
					pr.println(em1 + em2 + "---" + opeTime);
					file.close();
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			ope.db_close();
		}	
	}
	
	/**
	 * 修改记录
	 */
	public void modifyRecord() {
		try {
			employee1 = ope.getRs(opeNum);
			employee2 = ope.getRs(num);
			String em1 = null, em2 = null;
			pr = new PrintWriter(file);
			if(employee1.next()) {
					em1 = "员工号为:" + employee1.getString("employeeNumber")
					+ " 姓名为:" + employee1.getString("employeeName") + "的员工修改了一名员工的信息:";			
				}
			if(employee2.next()) {
				em2 = "\n该员工的员工号为:"+employee2.getString("employeeNumber")+" 修改后的信息:"
						+ " 姓名为:" + employee2.getString("employeeName")
						+ " 部门为:" + employee2.getString("department")
						+ " 薪水为:" + employee2.getInt("salary");
			}
			now = new Date();
			DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //显示日期，时间（精确到秒）
			String opeTime = date.format(now);//操作的时间
			pr.println(em1 + em2 + "---" + opeTime);
			file.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			ope.db_close();
		}	
	}
}

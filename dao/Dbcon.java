package com.huangguohang.www.dao;
/**
 * �������ݿ�db_employeemanager,��ȡ���ݿ�����
 */

import java.sql.*;

public class Dbcon {
	private static String jdbcName = "com.mysql.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://localhost:3306/db_employeemanager";
	private static String dbUserName = "root";
	private static String dbPassword = "123456";
	
	public Dbcon() {
		try {
			getCon().close();
		} catch (Exception e) {
			System.out.println("-----getCon-----"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Connection getCon() throws Exception{		
		Class.forName(jdbcName);//��������
		Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		//��ȡ���ݿ�����
		return con;
	}

	
}

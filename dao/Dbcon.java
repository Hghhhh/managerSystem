package com.huangguohang.www.dao;
/**
 * 操作数据库db_employeemanager,获取数据库连接
 */

import java.sql.*;

public class Dbcon {
	private static String jdbcName = "com.mysql.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://localhost:3306/db_employeemanager";
	private static String dbUserName = "root";
	private static String dbPassword = "123456";
	
	public Connection getCon() throws Exception{		
		Class.forName(jdbcName);//加载驱动
		Connection con = (Connection) DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
		//获取数据库连接
		return con;
	}

	
}

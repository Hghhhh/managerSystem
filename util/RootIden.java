package com.huangguohang.www.util;
/**
 *权限判断 
 */

import java.sql.ResultSet;
import java.sql.SQLException;

public class RootIden {
	int root = 1;
	Boolean flag = true;
	
	public RootIden(int root) {
		this.root = root;
	}
	

	public RootIden(ResultSet rs) {
		try {
			if(rs.next()) {
				this.root = rs.getInt("root");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 如果权限为1即部长,则返回true,否则返回false
	 */
	public Boolean rootIden() {
		if(root == 2) {
			flag = false;
		}
		return flag;
	}
}

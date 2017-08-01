package com.huangguohang.www.util;
/**
 * email格式判断
 */

import javax.swing.JOptionPane;

public class EmailIden {
	String email = null;
	Boolean flag = true;
	public EmailIden(String email) {
		this.email = email;
	}
	
	public Boolean emailIden() {
		if(!email.matches("\\w{0,}\\@\\w{0,}\\.{1}\\w{3}")) {
			flag = false; 
			JOptionPane.showMessageDialog(null, "请注意邮箱的格式!");
		}		
		return flag;
	}
}

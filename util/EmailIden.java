package com.huangguohang.www.util;
/**
 * email��ʽ�ж�
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
			JOptionPane.showMessageDialog(null, "��ע������ĸ�ʽ!");
		}		
		return flag;
	}
}

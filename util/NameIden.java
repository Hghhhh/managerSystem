package com.huangguohang.www.util;
/**
 * name格式判断
 */

import javax.swing.JOptionPane;

public class NameIden {
	String name;
	Boolean flag = true;

	public NameIden(String name) {
		this.name = name;
	}
	
	public Boolean nameIden() {	
	 if(name.length() == 0 || name.length() > 20) {
		JOptionPane.showMessageDialog(null, "姓名长度有误！");
		flag = false;
	 }
		return flag;
	}
	
}

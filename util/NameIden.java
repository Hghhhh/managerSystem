package com.huangguohang.www.util;
/**
 * name��ʽ�ж�
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
		JOptionPane.showMessageDialog(null, "������������");
		flag = false;
	 }
		return flag;
	}
	
}

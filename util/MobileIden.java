package com.huangguohang.www.util;
/**
 * mobile格式判断
 */

import javax.swing.JOptionPane;

public class MobileIden {
	String mobile = null;
	Boolean flag = true;
	
	/**
	 * 构造方法
	 * @param mobile
	 */
	public MobileIden(String mobile) {
		this.mobile = mobile;
	}
	
	public Boolean mobileIden() {
		if(!(mobile.matches("\\d{4}\\p{Punct}\\d{8}") || mobile.matches("\\d{11}"))) {
			JOptionPane.showMessageDialog(null, "请注意电话号码的格式！");
			flag = false;
		}
		return flag;
	}
	
}

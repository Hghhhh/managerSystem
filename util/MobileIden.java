package com.huangguohang.www.util;
/**
 * mobile��ʽ�ж�
 */

import javax.swing.JOptionPane;

public class MobileIden {
	String mobile = null;
	Boolean flag = true;
	
	/**
	 * ���췽��
	 * @param mobile
	 */
	public MobileIden(String mobile) {
		this.mobile = mobile;
	}
	
	public Boolean mobileIden() {
		if(!(mobile.matches("\\d{4}\\p{Punct}\\d{8}") || mobile.matches("\\d{11}"))) {
			JOptionPane.showMessageDialog(null, "��ע��绰����ĸ�ʽ��");
			flag = false;
		}
		return flag;
	}
	
}

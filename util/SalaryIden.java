package com.huangguohang.www.util;
/**
 *salary��ʽ�ж� 
 */

import javax.swing.JOptionPane;

public class SalaryIden {
	String salary = null ;
	Boolean flag = true;
	
	public SalaryIden(String salary) {
		this.salary = salary;
	}
	
	public Boolean salaryIden() {
		for(int i=0; i<salary.length(); i++) {
			char c = salary.charAt(i);
			if(c < '0' || c >'9') {
				JOptionPane.showMessageDialog(null, "нˮΪ�����Ҳ������������ţ�");
				flag = false;
				break;
			}
		}		
		 if(salary.length() == 0 || salary.length() > 9){
			 flag = false;
			JOptionPane.showMessageDialog(null, "нˮ����Ϊ���Ҳ��ܳ���999999999!");
		}
		return flag;
	}
	
}

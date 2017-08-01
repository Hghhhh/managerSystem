package com.huangguohang.www.util;
/**
 *salary格式判断 
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
				JOptionPane.showMessageDialog(null, "薪水为整数且不能有其他符号！");
				flag = false;
				break;
			}
		}		
		 if(salary.length() == 0 || salary.length() > 9){
			 flag = false;
			JOptionPane.showMessageDialog(null, "薪水不能为空且不能超过999999999!");
		}
		return flag;
	}
	
}

package com.huangguohang.www.util;
/**
 * �ж������Ա���ź������Ƿ���ȷ
 */

import java.awt.HeadlessException;

import java.sql.*;

import javax.swing.JOptionPane;

import com.huangguohang.www.dao.Dbope;

public class NumAndPswIden {

	String number, password;
	Dbope ope = null;
	ResultSet rs = null;
	
	/**
	 * ���췽��1:
	 * @param ����Ա���ż�����,�Լ�һ��Ա����Ϊ����Ա���ŵ��ض������,��¼ʱ����
	 */
	public NumAndPswIden(String number, String password, ResultSet rs) {
		this.number = number;
		this.password = password;
		this.rs = rs;
	}
	/**
	 * ���췽��2:
	 * @param ����Ա����
	 */
	public NumAndPswIden(String number) {
		this.number = number;
	}
	/**
	 * ���췽��3
	 */
	public NumAndPswIden() {
		
	}
	
	/**
	 * Ա������֤����1:�ж�Ա���ŵĸ�ʽ�����ȣ��Լ��Ƿ���Բ��ҵ���Ա���ţ���¼ʱ����
	 * @return ��¼ʱ�����жϾ���ȷ�򷵻�true�����򷵻�false
	 */
	public Boolean numIden() {
		Boolean flag = true;
		ope = new Dbope();
		rs = ope.getAllRs();
		if(number.length() < 5 || number.length() > 10){
			 //�жϳ���
			 flag = false;
			JOptionPane.showMessageDialog(null, "��ע��Ա���ų���(5-10λ)��");
		}
		if(flag) { 		
			//���������ȷ�����ж�������������
			if(!number.matches("\\d{1,}")) {
				 flag = false;
				 JOptionPane.showMessageDialog(null, "Ա���Ų��ܳ��ֳ������������������");
			}
		}
		 if(flag) {
			 //������ȡ���ʽ��ȷ�Ļ�����������Ĳ���
			 flag = false;
			 try {
				 //�ж�Ա�����Ƿ����
				while(rs.next()) {
					 if(number.equals(rs.getString("employeeNumber"))) {
						 flag = true;
						 break;
					 }
				 }
				if(flag == false) {
					JOptionPane.showMessageDialog(null, "���޴��ˣ�");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		return flag;
	}
	
	/**
	 * Ա������֤����2:�ж�Ա���ŵĸ�ʽ�����ȣ��Լ��Ƿ���Բ��ҵ���Ա���ţ���ѯʱ����
	 * @return ��ѯʱ�����жϾ���ȷ�򷵻�true�����򷵻�false
	 */
	public Boolean numIden2() {
		Boolean flag = true;
		ope = new Dbope();
		rs = ope.getAllRs();
		if(number.length() < 5 || number.length() > 10){
			 //�жϳ���
			 flag = false;
			JOptionPane.showMessageDialog(null, "��ע��Ա���ų���(5-10λ)��");
		}
		if(flag) { 		
			//���������ȷ�����ж�������������
			if(!number.matches("\\d{1,}")) {
				 flag = false;
				 JOptionPane.showMessageDialog(null, "Ա���Ų��ܳ��ֳ������������������");
			}
		}
		 if(flag) {
			 //������ȡ���ʽ��ȷ�Ļ�����������Ĳ���
			 flag = false;
			 try {
				 //�ж�Ա�����Ƿ����
				while(rs.next()) {
					 if(number.equals(rs.getString("employeeNumber"))) {
						 flag = true;
						 break;
					 }
				 }
				if(flag == false) {
					JOptionPane.showMessageDialog(null, "���޴��ˣ�");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		 ope.db_close();
		return flag;
	}
	
	/**
	 * Ա������֤����3:�ж�Ա���ŵĸ�ʽ�������Լ��Ƿ��Լ����ڣ����Ա��ʱ����
	 * @return ����ʽ������ȷ�����ݿ���û�д��ڴ�Ա���ţ��򷵻�true�����򷵻�false
	 */
	public Boolean numIden3() {
		Boolean flag1 = true;
		Boolean flag2 = true;
		Boolean flag = true;
		ope = new Dbope();
		rs = ope.getAllRs();
		 if(number.length() < 5 || number.length() > 10){
			 flag1 = false;
			JOptionPane.showMessageDialog(null, "��ע��Ա���ų���(5-10λ)��");
		}
		 if(flag1) {
			 //���������ȷ�Ļ�����������Ĳ���
			 if(!number.matches("\\d{1,}")) { //�ж�Ա���Ÿ�ʽ
				 flag1 = false;
				 JOptionPane.showMessageDialog(null, "Ա���Ų��ܳ��ֳ������������������");
			 }
		 }
		 if(flag1) {
			 //������ȡ���ʽ��ȷ�Ļ�����������Ĳ���
			 try {
				 //�ж�Ա�����Ƿ��Ѿ�����
				while(rs.next()) {
					 if(number.equals(rs.getString("employeeNumber"))) {
						 flag2 = false;
						 break;
					 }
				 }
				if(flag2 == false) {
					JOptionPane.showMessageDialog(null, "��Ա�����Ѿ����ڣ�");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 }
		flag = (flag1 && flag2); //����ʽ��������ȷ�������ݿ���û�д��ڴ�Ա������Ϊtrue
		ope.db_close();
		return flag;
	}
	
	/**
	 * ������֤����1:�жϵ�¼ʱ�����Ƿ���ȷ
	 * @return ������ȷ��Ϊtrue������Ϊfalse
	 */
	public Boolean pswIden() {
		ope = new Dbope();
		Boolean flag = true;
		try {
				if(password.equals(rs.getString("password"))) {
					JOptionPane.showMessageDialog(null, "��¼�ɹ�");
				}
				else {
					JOptionPane.showMessageDialog(null, "�������");
					flag = false;
				}
		} catch (HeadlessException | SQLException e) {
			e.printStackTrace();
		}
		ope.db_close();
		return flag;
	}
	
	/**
	 * ������֤����2:�ж���ӻ��޸�����ʱ���볤���Ƿ���ȷ
	 * @return ���볤����ȷ��Ϊtrue������Ϊfalse
	 */
	public Boolean pswIden2(String psw) {
		Boolean flag = true;
		if(psw.length() < 6 || psw.length() > 20) {
			flag = false;
			JOptionPane.showMessageDialog(null, "��ע�����볤��(6-20)!");
		}
		return flag;
	}
}

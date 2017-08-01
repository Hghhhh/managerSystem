package com.huangguohang.www.util;
/**
 *��Ա��������Ա��������ɾ�Ĳ���ʱ�����¼�¼�������D�̵� managementSystem.txt
 */

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import com.huangguohang.www.dao.Dbope;

public class OperationRecord {
	PrintWriter pr = null;
	FileWriter file = null;
	ResultSet employee1 = null, employee2 = null; 
	String opeNum = null, num = null;
	Dbope ope = new Dbope();
	Date now = null;
	/**
	 * ���췽������������ߺͱ������ߵ�Ա����
	 * @param opeNum,������
	 * @param num,��������
	 */
	public OperationRecord(String opeNum, String num) {
		try {
			file = new FileWriter("D:/managementSystem.txt", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.opeNum = opeNum;
		this.num = num;
	}
	
	/**
	 * ��Ӽ�¼
	 */
	public void addRecord() {		
		try {
			employee1 = ope.getRs(opeNum);//�õ�������������Ϣ�Ľ����
			employee2 = ope.getRs(num);//�õ���������������Ϣ�Ľ����
			String em1 = null, em2 = null;
			if(employee1.next()) {
				if(employee2.next()) {
					em1 = "Ա����Ϊ:" + employee1.getString("employeeNumber")
					+ " ����Ϊ:" + employee1.getString("employeeName") + "��Ա�������һ����Ա��:";
					//�����ߵ���Ϣ
					em2 = "\n��Ա����Ա����Ϊ:"+employee2.getString("employeeNumber")
					+ " ����Ϊ:" + employee2.getString("employeeName")
					+ " ����Ϊ:" + employee2.getString("department");
					//�������ߵ���Ϣ
					now = new Date();
					DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //��ʾ���ڣ�ʱ�䣨��ȷ���룩
					String opeTime = date.format(now);//������ʱ��
					pr = new PrintWriter(file);
					pr.println(em1 + em2 + "---" + opeTime);
					file.close();
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pr.close();//�ر�IO����
			ope.db_close();//�ر����ݿ�����
		}		
	}
	
	/**
	 * ɾ����¼
	 */
	public void deleteRecord() {
		try {
			employee1 = ope.getRs(opeNum);
			employee2 = ope.getRs(num);
			String em1 = null, em2 = null;
			if(employee1.next()) {
				if(employee2.next()) {
					em1 = "Ա����Ϊ:" + employee1.getString("employeeNumber")
					+ " ����Ϊ:" + employee1.getString("employeeName") + "��Ա��ɾ����һ��Ա��:";
					em2 = "\n��Ա����Ա����Ϊ:"+employee2.getString("employeeNumber")
					+ " ����Ϊ:" + employee2.getString("employeeName")
					+ " ����Ϊ:" + employee2.getString("department");
					now = new Date();
					DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //��ʾ���ڣ�ʱ�䣨��ȷ���룩
					String opeTime = date.format(now);//������ʱ��
					pr = new PrintWriter(file);
					pr.println(em1 + em2 + "---" + opeTime);
					file.close();
				}
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			ope.db_close();
		}	
	}
	
	/**
	 * �޸ļ�¼
	 */
	public void modifyRecord() {
		try {
			employee1 = ope.getRs(opeNum);
			employee2 = ope.getRs(num);
			String em1 = null, em2 = null;
			pr = new PrintWriter(file);
			if(employee1.next()) {
					em1 = "Ա����Ϊ:" + employee1.getString("employeeNumber")
					+ " ����Ϊ:" + employee1.getString("employeeName") + "��Ա���޸���һ��Ա������Ϣ:";			
				}
			if(employee2.next()) {
				em2 = "\n��Ա����Ա����Ϊ:"+employee2.getString("employeeNumber")+" �޸ĺ����Ϣ:"
						+ " ����Ϊ:" + employee2.getString("employeeName")
						+ " ����Ϊ:" + employee2.getString("department")
						+ " нˮΪ:" + employee2.getInt("salary");
			}
			now = new Date();
			DateFormat date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM); //��ʾ���ڣ�ʱ�䣨��ȷ���룩
			String opeTime = date.format(now);//������ʱ��
			pr.println(em1 + em2 + "---" + opeTime);
			file.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pr.close();
			ope.db_close();
		}	
	}
}

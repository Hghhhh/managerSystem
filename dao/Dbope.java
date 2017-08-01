package com.huangguohang.www.dao;
/**
 * �������ݿ�db_employeemanager�е�tb_employee���ݱ�
 */

import java.sql.*;

import com.huangguohang.www.po.Employee;

public class Dbope {
	Connection con = null;
	PreparedStatement pstmt = null;
	int flag = 0;
	
	public Dbope() {
		try {
			con = new Dbcon().getCon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ѯ���ݱ���������Ϣ
	 * @return ���ݱ�tb_employee�еİ���������Ϣ�Ľ����
	 */
	public ResultSet getAllRs() {
		ResultSet rs = null;
		try {			
			pstmt = con.prepareStatement("select * from tb_employee");
			rs = pstmt.executeQuery();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	/**
	 * ��нˮ��С�����ѯ���ݱ���������Ϣ
	 * @return ���ݱ�tb_employee�еİ���������Ϣ����˳��Ľ����
	 */
	public ResultSet getAllRs2() {
		ResultSet rs = null;
		try {			
			pstmt = con.prepareStatement("select * from tb_employee order by salary ASC");
			rs = pstmt.executeQuery();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	/**
	 * ������Id�����ѯ���ݱ���������Ϣ
	 * @return ���ݱ�tb_employee�еİ���������Ϣ����˳��Ľ����
	 */
	public ResultSet getAllRs3() {
		ResultSet rs = null;
		try {			
			pstmt = con.prepareStatement("select * from tb_employee order by departmentId ASC");
			rs = pstmt.executeQuery();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return rs;
	}
	
	/**
	 *��ѯ���ݿ���ĳЩ�ض�����Ϣ ,ֻҪ��Ա��������
	 *@param Ҫ��ѯ��Ա����Ա����
	 *@return ���ݱ�tb_employee�еİ����ض�Ա����Ϣ�Ľ����
	 */
	public ResultSet getRs(String num) {
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from tb_employee where `employeeNumber`=?");
			pstmt.setString(1, num);
			rs = pstmt.executeQuery();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 *ģ����ѯ���ݿ���ĳЩ�ض�����Ϣ ,ֻҪ��Ա��������
	 *@param Ҫ��ѯ��Ա����Ա����
	 *@return ���ݱ�tb_employee�еİ����ض�Ա����Ϣ�Ľ����
	 */
	public ResultSet getRs2(String num) {
		String nums = "%" + num + "%";
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from tb_employee where `employeeNumber`like ?");
			pstmt.setString(1, nums);
			rs = pstmt.executeQuery();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 *��ѯ���ݿ���ĳЩ�ض�����Ϣ ,ֻҪ��Ա��Id����
	 *@param Ҫ��ѯ��Ա����Ա��Id
	 *@return ���ݱ�tb_employee�еİ����ض�Ա����Ϣ�Ľ����
	 */
	public ResultSet getRs3(int id) {
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from tb_employee where `employeeId`=?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * �������ݿ��е���Ϣ
	 * @param ��Ҫ�����ض���Ա������
	 */
	public int getAdd(Employee emp){
		int result = 0;
		try {
			pstmt = con.prepareStatement("insert into tb_employee"
					+ " values(null,?,?,?,?,?,?,?,?,?,now(),2)");
			pstmt.setString(1, emp.getDepartmentId());
			pstmt.setString(2, emp.getDepartment());
			pstmt.setString(3, emp.getEmployeeName());
			pstmt.setString(4, emp.getSex());
			pstmt.setString(5, emp.getEmployeeNumber());
			pstmt.setString(6, emp.getPassword());
			pstmt.setInt(7, emp.getSalary());
			pstmt.setString(8, emp.getMobile());
			pstmt.setString(9, emp.getEmail());
			result = pstmt.executeUpdate();
			flag = 1;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;  
	}
	
	/**
	 * ɾ�����ݿ��е���Ϣ
	 * @param ֻ��Ҫ����Ҫɾ����Ա������
	 */
	public int getDelete(String num) {
		int result = 0;
		try {
			pstmt = con.prepareStatement("delete from tb_employee where `employeeNumber`=?");
			pstmt.setString(1, num);
			result = pstmt.executeUpdate();
			flag = 2;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;  
	}
	
	/**
	 * �޸����ݿ��е���Ϣ
	 * @param ��Ҫ�����޸ĺ��Ա������
	 */
	public int getUpdate(Employee emp) {
		int result = 0;
		try {
			pstmt = con.prepareStatement("update tb_employee set departmentId=?,employeeName=?,"
					+ "sex=?,password=?,salary=?,mobile=?,email=?,department=? where employeeNumber=?");
			pstmt.setString(1, emp.getDepartmentId());
			pstmt.setString(2, emp.getEmployeeName());
			pstmt.setString(3, emp.getSex());
			pstmt.setString(4, emp.getPassword());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setString(6, emp.getMobile());
			pstmt.setString(7, emp.getEmail());
			pstmt.setString(8, emp.getDepartment());
			pstmt.setString(9, emp.getEmployeeNumber());
			result = pstmt.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * �ر����ݿ�����
	 */
	public void db_close() {
		try {
			if(flag == 0) {
				//���flag == 0 ��ʾû�е����κ���ɾ����е��κη�������ֱ�ӹر�Connection
				con.close();
			}
			if(flag == 1) {
				//���flag == 1 ��ʾ��������ɾ����е�ĳһ����������Ҫ�ر�Connection��PreparedStatement
				pstmt.close();
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

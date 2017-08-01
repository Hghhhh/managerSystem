package com.huangguohang.www.dao;
/**
 * 操作数据库db_employeemanager中的tb_employee数据表
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
	 * 查询数据表中所有信息
	 * @return 数据表tb_employee中的包含所有信息的结果集
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
	 * 按薪水大小升序查询数据表中所有信息
	 * @return 数据表tb_employee中的包含所有信息的有顺序的结果集
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
	 * 按部门Id升序查询数据表中所有信息
	 * @return 数据表tb_employee中的包含所有信息的有顺序的结果集
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
	 *查询数据库中某些特定的信息 ,只要把员工号输入
	 *@param 要查询的员工的员工号
	 *@return 数据表tb_employee中的包含特定员工信息的结果集
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
	 *模糊查询数据库中某些特定的信息 ,只要把员工号输入
	 *@param 要查询的员工的员工号
	 *@return 数据表tb_employee中的包含特定员工信息的结果集
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
	 *查询数据库中某些特定的信息 ,只要把员工Id输入
	 *@param 要查询的员工的员工Id
	 *@return 数据表tb_employee中的包含特定员工信息的结果集
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
	 * 增加数据库中的信息
	 * @param 需要传入特定的员工对象
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
	 * 删除数据库中的信息
	 * @param 只需要传入要删除的员工号码
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
	 * 修改数据库中的信息
	 * @param 需要传入修改后的员工对象
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
	 * 关闭数据库连接
	 */
	public void db_close() {
		try {
			if(flag == 0) {
				//如果flag == 0 表示没有调用任何增删查改中的任何方法，故直接关闭Connection
				con.close();
			}
			if(flag == 1) {
				//如果flag == 1 表示调用了增删查改中的某一方法，故需要关闭Connection和PreparedStatement
				pstmt.close();
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

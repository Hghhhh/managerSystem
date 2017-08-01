package com.huangguohang.www.dao;
/**
 * 操作数据库db_employeemanager中的tb_evaluation数据表
 */

import java.sql.*;

import com.huangguohang.www.po.Evaluation;

public class Dbope2 {
	Connection con = null;
	PreparedStatement pstmt = null;
	int flag = 0;
	
	public Dbope2() {
		try {
			con = new Dbcon().getCon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加评价
	 * @param 需要传入一个评价对象
	 */
	public int getAdd(Evaluation eva) {
		int result = 0;
		try {
			pstmt = con.prepareStatement("insert into tb_evaluation values(null,?,?,?,?)");
			pstmt.setInt(1, eva.getEmployeeId());
			pstmt.setString(2, eva.getStartLevel());
			pstmt.setString(3, eva.getComment());
			pstmt.setInt(4, eva.getValuatorId());
			result = pstmt.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除评价
	 * @param 只要输入某条评价的Id
	 */
	public int getDelete(int id) {
		int result = 0;
		try {
			pstmt = con.prepareStatement("delete from tb_evaluation where id=?");
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询所有数据库中所有评价
	 * @return 包含所有评价信息的结果集
	 */
	public ResultSet getAllRs() {
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from tb_evaluation");
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
	 * 查询某条评价
	 * @param 被评价员工的ID
	 * @return 包含此条评价信息的结果集
	 */
	public ResultSet getRs(int employeeId) {
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from tb_evaluation where `employeeId`=?");
			pstmt.setInt(1, employeeId);
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
	 * 查询某条评价
	 * @param 评价者的Id
	 * @return 包含此条评价信息的结果集
	 */
	public ResultSet getRs2(int evaluatorId) {
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement("select * from tb_evaluation where `evaluatorId`=?");
			pstmt.setInt(1, evaluatorId);
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
	 * 修改评价
	 * @param 需要修改的评价的Id,以及修改后的评语和评价等级
	 */
	public int getUpdate(int id, String comment, String level) {
		int result = 0;
		try {
			pstmt = con.prepareStatement("update tb_evaluation set comment = ? startLevel = ? where id =?");
			pstmt.setString(1, comment);
			pstmt.setString(2, level);
			pstmt.setInt(3, id);
			result = pstmt.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 关闭数据库连接
	 */
	public void db_close() {
		try {
			if(flag == 1) {
				//如果flag == 1 表示调用了增删查改中的某一方法，故需要关闭Connection和PreparedStatement
				pstmt.close();
				con.close();
			}
			else{
				con.close();
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
}

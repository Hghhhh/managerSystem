package com.huangguohang.www.dao;
/**
 * �������ݿ�db_employeemanager�е�tb_evaluation���ݱ�
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
	 * �������
	 * @param ��Ҫ����һ�����۶���
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
	 * ɾ������
	 * @param ֻҪ����ĳ�����۵�Id
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
	 * ��ѯ�������ݿ�����������
	 * @return ��������������Ϣ�Ľ����
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
	 * ��ѯĳ������
	 * @param ������Ա����ID
	 * @return ��������������Ϣ�Ľ����
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
	 * ��ѯĳ������
	 * @param �����ߵ�Id
	 * @return ��������������Ϣ�Ľ����
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
	 * �޸�����
	 * @param ��Ҫ�޸ĵ����۵�Id,�Լ��޸ĺ����������۵ȼ�
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
	 * �ر����ݿ�����
	 */
	public void db_close() {
		try {
			if(flag == 1) {
				//���flag == 1 ��ʾ��������ɾ����е�ĳһ����������Ҫ�ر�Connection��PreparedStatement
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

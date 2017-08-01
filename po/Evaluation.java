package com.huangguohang.www.po;
/**
 * 数据库db_employeemanager中的tb_evaluation对应的实体类
 */

public class Evaluation {
	String comment = null;
	int id;
	int valuatorId;
	int employeeId;
	String startLevel = "A";
	
	public Evaluation(int valuaorId, int employeeId) {
		this.valuatorId = valuaorId;
		this.employeeId = employeeId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValuatorId() {
		return valuatorId;
	}

	public void setValuatorId(int valuatorId) {
		this.valuatorId = valuatorId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(String startLevel) {
		this.startLevel = startLevel;
	}

	
}

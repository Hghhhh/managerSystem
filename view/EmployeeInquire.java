package com.huangguohang.www.view;
/**
 * Ա����ѯ����
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.util.NumAndPswIden;
import com.huangguohang.www.util.OperationRecord;

public class EmployeeInquire extends JPanel implements ActionListener{
	JTextField department, employeeNumber, employeeName, password, salary, email, mobile;
	JRadioButton man, woman;
	ButtonGroup group = null; 
	JButton inquest, delete, refresh;
	JMenu rank = null;
	JMenuItem rankSalary,rankDepartId;
	JMenuBar bar = null;
	JTable table = null;
	DefaultTableModel model = null;
	JScrollPane sp = null;
	String opeNum = null;//������Ա����
	String num = null;//��������Ա����
	
	public EmployeeInquire() {
		employeeNumber = new JTextField(20);
		inquest = new JButton("��ʼ��ѯ");
		inquest.addActionListener(this);//Ϊ��ѯ��ť����¼�����
		delete = new JButton("ɾ����ѡԱ��");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Button_delete_Listener(e);				
			}		
		});//Ϊɾ����ť����¼�����
		rankSalary = new JMenuItem("��нˮ����");
		rankDepartId = new JMenuItem("����������");
		rank = new JMenu("����");
		rank.add(rankSalary);
		rank.add(rankDepartId);
		rankSalary.addActionListener(this);//��нˮ��������¼�����
		rankDepartId.addActionListener(this);//Ϊ������Id��������¼�����
		bar = new JMenuBar();
		bar.add(rank);
		refresh = new JButton("ˢ��");
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Button_refresh_Listener(e);
			}
		});//Ϊˢ�°�ť����¼�����
		Box box1 = Box.createHorizontalBox();
		box1.add(new JLabel("������Ա����:"),JLabel.CENTER);
		box1.add(employeeNumber);
		Box box2 = Box.createHorizontalBox();
		box2.add(inquest);
		box2.add(refresh);
		box2.add(delete);
		box2.add(bar);
		Box boxH = Box.createVerticalBox();
		boxH.add(box1);
		boxH.add(box2);
		table = new JTable();//����һ�����
		table.setRowHeight(20);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//����Ϊ���Զ�������С
		model = (DefaultTableModel) table.getModel();//��ȡ����ģ��
		model.setColumnIdentifiers(new Object[] {"Ա��Id", "����Id", "����",
		"����", "�Ա�", "Ա����", "нˮ", "�绰", "����", "��ְ����"});//���ñ�ͷ
		modelRowAdd2();//Ϊ���������ݿ��е�������Ϣ
		table.setModel(model);//����ģ��
		table.getTableHeader().setReorderingAllowed(false);//��ͷ��Ϊ������
		sp = new JScrollPane();
		sp.setColumnHeaderView(new JLabel("Ա����Ϣ", JLabel.CENTER));
		sp.setViewportView(table);//����һ��JScrollPane��������ñ��
		JPanel pNorth = new JPanel();
		pNorth.add(boxH);
		this.setLayout(new BorderLayout());
		this.add(pNorth, BorderLayout.NORTH);
		this.add(sp, BorderLayout.CENTER);
	}

	/**
	 * modelRowAdd:���ģ������з���һ
	 * ��ӵ��е���Ϣ�Ǹ��ݴ���Ľ������������
	 * @param ����һ���ض��Ľ����
	 */
	public void  modelRowAdd(ResultSet rs) {
		Boolean flag = false;
		model.setRowCount(0);
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
				flag = true;
			}
			if(!flag) {
				JOptionPane.showMessageDialog(null, "���޴��ˣ�");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * modelRowAdd2:���ģ������з�����
	 * ������ݿ�������Ա����Ϣ
	 */
	public void  modelRowAdd2() {
		Dbope ope = new Dbope();
		ResultSet rs = ope.getAllRs();
		model.setRowCount(0);//�Ƚ�������
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ope.db_close();//�ر����ݿ�
		}
	}
	
	/**
	 * modelRowAdd3:���ģ������з�����
	 * ��нˮ����������ݿ�������Ա����Ϣ
	 */
	public void  modelRowAdd3() {
		Dbope ope = new Dbope();
		ResultSet rs = ope.getAllRs2();
		model.setRowCount(0);//�Ƚ�������
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ope.db_close();//�ر����ݿ�
		}
	}
	
	/**
	 * modelRowAdd4:���ģ������з�����
	 * ������Id����������ݿ�������Ա����Ϣ
	 */
	public void  modelRowAdd4() {
		Dbope ope = new Dbope();
		ResultSet rs = ope.getAllRs3();
		model.setRowCount(0);//�Ƚ�������
		try {
			while(rs.next()){
				model.addRow(new Object[] {rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), 
						rs.getString(6), rs.getString(8), rs.getString(9),
						rs.getString(10),rs.getString(11)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ope.db_close();//�ر����ݿ�
		}
	}
	
	/**
	 * ���ݴ����Booleanֵȷ��Ա����Ȩ�ޣ����Ϊfalse��ɾ����ť����Ϊ���ɼ�
	 * @param flag
	 */
	public void DeleteRoot(Boolean flag) {
		if(!flag) {
			delete.setVisible(false);
		}
	}
	
	/**
	 * ���ò����ߵ�Ա����
	 * @param opeNum
	 */
	public void setOpeNum(String opeNum) {
		this.opeNum = opeNum;
	}
	
	/**
	 * actionPerformed:����������ť-inquest
	 */
	public void actionPerformed(ActionEvent e) {
		String em = null;
		if(e.getSource() == inquest) {
			em = employeeNumber.getText().trim();//�õ������Ա����		
			if(em.matches("\\d{1,}")) {//�ж�һ��������ǲ���ȫ������
				model.setRowCount(0);//��ѯ�ɹ����Ƚ�����е��������
				Dbope ope = new Dbope();
				ResultSet rs = ope.getRs2(em);
				modelRowAdd(rs);//Ȼ����Ϊ�������ض�����
				ope.db_close();//�ر����ݿ�
			}
			else {
				JOptionPane.showMessageDialog(null, "��ע��Ա���Ÿ�ʽ��");
			}
		}
		if(e.getSource() == rankSalary) {
			modelRowAdd3();
		}
		if(e.getSource() == rankDepartId) {
			modelRowAdd4();
		}
	}
	
	/**
	 * delete��ť��ActionListener
	 */
	public void Button_delete_Listener(ActionEvent arg0) {
		final String OPENUM = opeNum;
		int row = table.getSelectedRowCount();//��ѡ�е����������û��ѡ���κ����򷵻�-1
		if(row != -1) { 
			Dbope ope = new Dbope();
			int[] rows = table.getSelectedRows();//���б�ѡ�е��ж�Ӧ������
			if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��Ա����Ϣ��", "����",
				JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE)){
				//�����ȷ��ɾ���󣬽�������Ĳ���
					for(int i=0; i<row; i++){
						int r = rows[i];
						num = (String) table.getValueAt(r, 5);//�õ��������ߵ�Ա����
						new OperationRecord(OPENUM, num).deleteRecord();//����ɾ����¼
					 	ope.getDelete(num);	//ɾ��ѡ�е�Ա��
					}
					JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
					modelRowAdd2();//ɾ���ɹ���ˢ��һ��
			}
			ope.db_close();//�ر����ݿ�
		}}
	
	/**
	 * refresh��ť��ActionListener
	 */
	public void Button_refresh_Listener(ActionEvent arg0) {
		modelRowAdd2();							
	}
}

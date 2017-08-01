package com.huangguohang.www.service;
/**
 *���������� 
 */

import java.awt.*;
import java.awt.event.*;

import java.sql.*;

import java.util.Vector;

import javax.swing.*;

import com.huangguohang.www.dao.Dbope2;

public class EvaluationManager extends JPanel implements ActionListener{
	JMenuBar bar;
	JMenu fileMenu;
	JMenuItem window,insert,change,search,personal;
	EvaluationWindow ew = null;
	EvaluationAdd ea = null;
	EvaluationInquire ei = null;
	EvaluationModify em = null;
	CardLayout card = null;
	JPanel pCenter = null;
	
	public EvaluationManager(int employeeId, String departmentId) {
		ew = new EvaluationWindow(employeeId); 
		ea = new EvaluationAdd(employeeId, departmentId);
		ei = new EvaluationInquire();
		em = new EvaluationModify(employeeId);
		window = new JMenuItem("��ʼ����");
		insert = new JMenuItem("����Ҷ�����Ա��������");
		change = new JMenuItem("�޸��Ҷ�����Ա��������");
		search = new JMenuItem("�鿴����Ա������");
		fileMenu = new JMenu("���۲˵�");//����һ���˵���Ӹ���ѡ��
		fileMenu.add(window);
		fileMenu.add(insert);
		fileMenu.add(change);
		fileMenu.add(search);
		window.addActionListener(this);//Ϊ�����˵�ѡ������¼�����
		insert.addActionListener(this);
		change.addActionListener(this);
		search.addActionListener(this);
		bar = new JMenuBar();
		bar.add(fileMenu);
		pCenter = new JPanel();
		card = new CardLayout();
		pCenter.setLayout(card);//����pCenter�Ĳ���ΪCardLayout
		pCenter.add("��ʼ����", ew);
		pCenter.add("¼�����", ea);
		pCenter.add("��ѯ����", ei);
		pCenter.add("�޸Ľ���", em);
		this.setLayout(new BorderLayout());
		this.add(bar, BorderLayout.NORTH);
		this.add(pCenter, BorderLayout.CENTER);
	}
	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == window){
			card.show(pCenter, "��ʼ����");
		}
		if(e.getSource() == insert){
			card.show(pCenter, "¼�����");
		}
		if(e.getSource() == search){
			card.show(pCenter, "��ѯ����");
			ei.list.removeAll();
			ei.list.setListData(ei.refresh());//��ʾʱˢ��һ��
		}
		if(e.getSource() == change){
			card.show(pCenter, "�޸Ľ���");
			em.list.removeAll();
			em.list.setListData(em.refresh());//��ʾʱˢ��һ��
		}
	}
	
	/**
	 * �ڲ���:�����棬�������ø�Ա���յ�����������
	 */
	class EvaluationWindow extends JPanel {
		JList list = null;
		JScrollPane scrollPane = null;
		Dbope2 ope = new Dbope2();
		ResultSet rs = null;
		
		public EvaluationWindow(int employeeId) {
			Vector<String> evaluation = new Vector<String>();
			rs = ope.getRs(employeeId);
			String eva = null;
			int i = 0;
			try {
				while(rs.next()) {
					//Ϊ�����������
					i++;
					eva = i+". ���۵ȼ�: "+rs.getString("startLevel")+"     ����: "
						+rs.getString("comment")+" �������� ������Id: "+rs.getInt("evaluatorId");
					evaluation.addElement(eva);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				 ope.db_close();//�ر����ݿ�
			}
			 list = new JList(evaluation);//ΪJList�������
			 scrollPane = new JScrollPane(list);//��list���ÿɹ�����JScrollPane��
			 this.setLayout(new BorderLayout());
			 this.add(new JLabel("���յ���������:", JLabel.LEFT), BorderLayout.NORTH);
			 this.add(scrollPane, BorderLayout.CENTER);
	}
	
}

}
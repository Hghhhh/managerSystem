package com.huangguohang.www.view;
/**
 *������ 
 */

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import com.huangguohang.www.dao.Dbope;
import com.huangguohang.www.util.NumAndPswIden;
import com.huangguohang.www.util.RootIden;

public class EmployeeManager extends JFrame implements ActionListener{
	JMenu fileMenu;
	JMenuItem window,insert,change,search,personal;
	JPanel pCenter = null;
	ManagerWindow mw;
	EmployeeAdd ea;
	EmployeeInquire ei;
	EmployeeModify ms;
	PersonalPage pg;
	CardLayout card = null;
	ResultSet rs = null;
	
	public EmployeeManager() {
		window = new JMenuItem("��¼����");
		insert = new JMenuItem("¼��Ա��������Ϣ");
		change = new JMenuItem("�޸�Ա��������Ϣ");
		search = new JMenuItem("��ѯԱ��������Ϣ");
		personal = new JMenuItem("Ա������ҳ��");
		insert.setVisible(false);
		change.setVisible(false);
		search.setVisible(false);
		personal.setVisible(false);
		fileMenu = new JMenu("�˵�ѡ��");
		fileMenu.add(window);
		fileMenu.add(insert);
		fileMenu.add(change);
		fileMenu.add(search);
		fileMenu.add(personal);//����һ���˵�����ʼʱֻ�е�¼����ѡ��ɼ�
		window.addActionListener(this);
		insert.addActionListener(this);
		change.addActionListener(this);
		search.addActionListener(this);
		personal.addActionListener(this);//����¼�����
		JMenuBar bar = new JMenuBar();
		bar.add(fileMenu);
		this.add(bar,BorderLayout.NORTH);//���˵������������Ϸ�		
		pCenter = new JPanel();
		card = new CardLayout();
		pCenter.setLayout(card);//����һ��JPanel���������������м�,�������������Ĳ���ΪCardLayout
		mw = new ManagerWindow();
		ea = new EmployeeAdd();
		pg = new PersonalPage();
		ei = new EmployeeInquire();
		ms = new EmployeeModify();
		pCenter.add("��¼����", mw);
		pCenter.add("¼�����", ea);
		pCenter.add("���˽���", pg);
		pCenter.add("��ѯ����", ei);
		pCenter.add("�޸Ľ���", ms);//��Ӹ�������
		this.add(pCenter, BorderLayout.CENTER);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBounds(700, 300, 500, 280);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == window){
			card.show(pCenter, "��¼����");
		}
		if(e.getSource() == insert){
			card.show(pCenter, "¼�����");
			this.setBounds(700, 300, 600, 500);
		}
		if(e.getSource() == personal){
			card.show(pCenter, "���˽���");
			this.setBounds(600, 300, 1000, 500);
		}
		if(e.getSource() == search){
			card.show(pCenter, "��ѯ����");
			this.setBounds(700, 300, 775, 500);
			ei.modelRowAdd2();//��ʾʱˢ��һ��
		}
		if(e.getSource() == change){
			card.show(pCenter, "�޸Ľ���");
			this.setBounds(700, 300, 600, 500);
		}
	}
	
	/**
	 * �ڲ��ࣺ��½����
	 *
	 */
	class ManagerWindow  extends JPanel implements ActionListener {
		JLabel welcome;
		JButton enter, exit;
		JTextField employeeNumber;
		JPasswordField password;
		public ManagerWindow() {
			welcome = new JLabel("��ӭ����Ա������ϵͳ",JLabel.CENTER);
			welcome.setFont(new Font("΢���ź�", Font.BOLD + Font.ITALIC, 25));//���û�ӭ��
			enter = new JButton("ȷ��");
			exit = new JButton("�˳�");
			enter.addActionListener(this);//����¼�����
			exit.addActionListener(this);//����¼�����
			employeeNumber = new JTextField(20);
			password = new JPasswordField(20);
			Box box1 = Box.createHorizontalBox();
			box1.add(new JLabel("����:", JLabel.CENTER));
			box1.add(employeeNumber);
			Box box2 = Box.createHorizontalBox();
			box2.add(new JLabel("����:", JLabel.CENTER));
			box2.add(password);
			Box boxH = Box.createVerticalBox();
			boxH.add(box1);
			boxH.add(Box.createVerticalStrut(40));
			boxH.add(box2);//���Ա���ź������������������һ��Box�����
			JPanel pCenter = new JPanel();
			pCenter.add(boxH);//����һ��JPanel���������boxH
			Box box3 = Box.createHorizontalBox();
			box3.add(Box.createHorizontalStrut(170));
			box3.add(enter);
			box3.add(Box.createHorizontalStrut(10));
			box3.add(exit);//���ȷ�����˳���ť,������������ť����һ��Box�����
			this.setLayout(new BorderLayout());
			this.add(welcome, BorderLayout.NORTH);//����ӭ��������Ϸ�
			this.add(pCenter, BorderLayout.CENTER);//Ա���ź����������������ڵ�¼������м�
			this.add(box3, BorderLayout.SOUTH);//��ȷ�����˳���ť�����·�
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == enter) {
				//��������ȷ��
				String em = employeeNumber.getText().trim();//�õ�������Ա����
				String ps = password.getText().trim();//�õ���������������
				Dbope ope = new Dbope();//�������ݿ�
				rs = ope.getRs(em);//�õ��ض��Ľ����
				NumAndPswIden nap = new NumAndPswIden(em, ps, rs);	
				//�����Ա��������ʹ˽�����ж�Ա���ź�����	
				if(nap.numIden()) {  
					//���Ա������ȷ,��������Ĳ���
					if(!nap.pswIden()) {
						//������벻��ȷ���������������������
						password.setText(null);
					}
					else {
						//���������ȷ��������������
						Boolean flag = new RootIden(rs).rootIden();
						//�ж�Ա����Ȩ��,���Ϊ�����򷵻�true
						if(flag) {
							//�������Բ��ң�ɾ�����޸ģ�¼��Ա��
							insert.setVisible(true);
							change.setVisible(true);
							search.setVisible(true);
							personal.setVisible(true);
							window.setVisible(false);	
							ea.setOpeNum(em);//ΪԱ����ӽ������ò����ߵ�Ա����
							ei.setOpeNum(em);//ΪԱ����ѯ�������ò����ߵ�Ա����
							ms.setOpeNum(em);//ΪԱ���޸Ľ������ò����ߵ�Ա����
						}
						else {
							//��ͨԱ��ֻ�ܲ���Ա�����޸��Լ�����Ϣ
							search.setVisible(true);
							personal.setVisible(true);
							window.setVisible(false);
							ei.DeleteRoot(flag);//���ò�ѯ�����ɾ����ťΪ���ɼ�					
						}
						//��¼�ɹ��Զ���ת�����˽���
						pg.setRS(rs);//�������������Ϣ�Ľ����
						card.show(pCenter, "���˽���");
						EmployeeManager.this.setBounds(600, 300, 1000, 500);
					}
				}
				else {
					employeeNumber.setText(null);//���Ա������������������������
				}
				ope.db_close();//�ر����ݿ�����
			}
			if(e.getSource() == exit){
				//���������˳�
				EmployeeManager.this.dispose();
			}
		}	
	}
	
	public static void main(String[] args) {
		new EmployeeManager();
	}
}

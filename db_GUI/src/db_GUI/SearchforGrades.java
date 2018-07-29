package db_GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class SearchforGrades extends JFrame implements ActionListener {
	/*
	 * 成绩查询
	 */
	private static final long serialVersionUID = 1L;
	JPanel jp1,jp2,jp3,jp4;//面板
	JButton jb1,jb2,jb3;//按钮
	JTextField jtf1;//文本
	JLabel jlb1;//标签
	
	static Connection dbConn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	//构造函数
	public SearchforGrades() {
		// 连接数据库	
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/***?useSSL=false&&serverTimezone=GMT%2B8";
		String user = "root";
		String password = "***";
		try{
			Class.forName(driver);
			dbConn = DriverManager.getConnection(url, user, password);
			/*
			 * if(!dbConn.isClosed())
			 *  System.out.println("连接成功");
			 */
				
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("连接失败");
		}
		//创建面板
		jp1 = new JPanel();//学号面板
		jp2 = new JPanel();//查询按钮面板
		jp3 = new JPanel();
		jp4 = new JPanel();
			
		//创建标签
		jlb1 = new JLabel("学号");
			
		//创建按钮
		jb1=new JButton("查询各科成绩");
		jb2=new JButton("查询平均成绩");
		jb3=new JButton("查询必修课平均成绩");
		//设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
			
		//创建文本框
		jtf1=new JTextField(11);
			
		//设置布局管理
		this.setLayout(new GridLayout(4, 1));//网格式布局
			
		//加入各个组件
		jp1.add(jlb1);
		jp1.add(jtf1);
			
		jp2.add(jb1);
		jp3.add(jb2);
		jp4.add(jb3);
			
		//加入到JFrame
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
						
		//设置窗体
		this.setTitle("查询成绩");//窗体标签
		this.setSize(300, 200);//窗体大小
		this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
		this.setVisible(true);//显示窗体
						
		//锁定窗体
		this.setResizable(false);
	}
	
	//响应函数
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand()=="查询各科成绩")
		{
			try {
				String sql = "SELECT student.Sno, sc.Cno, course.Cname, teacher.Tname,course.Credit, sc.Grade "
						+ "FROM sc,student,teacher,course "
						+ "WHERE sc.sno = student.sno "
						+ "AND sc.tno = teacher.tno "
						+ "AND student.sno = ? "
						+ "AND course.cno = sc.cno";
				ps=dbConn.prepareStatement(sql);
				ps.setString(1, jtf1.getText());
				rs=ps.executeQuery();
				JFrame jf = new JFrame("各科成绩");
				//创建一个面板容器
				JPanel jp = new JPanel();
				while(rs.next())
				{
					JLabel jl1 = new JLabel(rs.getString(1));
					JLabel jl2 = new JLabel(rs.getString(2));
					JLabel jl3 = new JLabel(rs.getString(3));
					JLabel jl4 = new JLabel(rs.getString(4));
					JLabel jl5 = new JLabel(rs.getString(5));
					JLabel jl6 = new JLabel(rs.getString(6));
					jp.add(jl1);
					jp.add(jl2);
					jp.add(jl3);
					jp.add(jl4);
					jp.add(jl5);
					jp.add(jl6);
				}
				jf.add(jp);
				jf.setBounds(200, 200, 300, 300);
				jf.setSize(250, 400);
				jf.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand()=="查询平均成绩")
		{
			try {
				String sql = "SELECT sc.Sno, SUM(sc.Grade*course.Credit)/SUM(course.Credit) AS 'AvgAll' "
						+ "FROM sc,course "
						+ "WHERE Sno = ? "
						+ "AND sc.cno = course.cno "
						+ "GROUP BY sc.sno";
				ps=dbConn.prepareStatement(sql);
				ps.setString(1, jtf1.getText());
				rs=ps.executeQuery();
				JFrame jf = new JFrame("平均成绩");
				//创建一个面板容器
				JPanel jp = new JPanel();
				while(rs.next())
				{
					JLabel jl1 = new JLabel(rs.getString(1));
					JLabel jl2 = new JLabel(rs.getString(2));
					jp.add(jl1);
					jp.add(jl2);
				}
				jf.add(jp);
				jf.setBounds(200, 200, 300, 300);
				jf.setSize(400, 100);
				jf.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand()=="查询必修课平均成绩")
		{
			try {
				String sql = "SELECT sc.Sno, SUM(sc.Grade*course.Credit)/SUM(course.Credit) AS 'AvgRe' "
						+ "FROM sc,course "
						+ "WHERE sc.Sno = ? "
						+ "AND sc.isOpen = '必修' "
						+ "And sc.cno = course.cno "
						+ "GROUP BY sc.sno";
				ps=dbConn.prepareStatement(sql);
				ps.setString(1, jtf1.getText());
				rs=ps.executeQuery();
				JFrame jf = new JFrame("必修课平均成绩");
				//创建一个面板容器
				JPanel jp = new JPanel();
				while(rs.next())
				{
					JLabel jl1 = new JLabel(rs.getString(1));
					JLabel jl2 = new JLabel(rs.getString(2));
					jp.add(jl1);
					jp.add(jl2);
				}
				jf.add(jp);
				jf.setBounds(200, 200, 300, 300);
				jf.setSize(400, 100);
				jf.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}				
	}
}


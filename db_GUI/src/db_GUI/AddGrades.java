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

public class AddGrades extends JFrame implements ActionListener {
	static Connection dbConn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	//定义组件
	JPanel jp1,jp2,jp3,jp4,jp5;//面板
	JLabel jlb1,jlb2,jlb3,jlb4;//标签
	JTextField jtf1,jtf2,jtf3,jtf4;//文本
	JButton jb1;//按钮
	
	//构造函数
	public AddGrades()
	{
		// 连接数据库；
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
		jp2 = new JPanel();//课程号面板
		jp3 = new JPanel();//成绩面板
		jp4 = new JPanel();//补考记录面板
		jp5 = new JPanel();//录入按钮面板
			
		//创建标签
		jlb1 = new JLabel("学        号");
		jlb2 = new JLabel("课  程  号");
		jlb3 = new JLabel("成        绩");
		jlb4 = new JLabel("是否补考");

		//创建按钮
		jb1=new JButton("录入");
		//设置监听
		jb1.addActionListener(this);
			
		//创建文本框
		jtf1=new JTextField(5);
		jtf2=new JTextField(2);
		jtf3=new JTextField(3);
		jtf4=new JTextField(2);
			
		//设置布局管理
		this.setLayout(new GridLayout(4, 1));//网格式布局
			
		//加入各个组件
		jp1.add(jlb1);
		jp1.add(jtf1);
			
		jp2.add(jlb2);
		jp2.add(jtf2);
			
		jp3.add(jlb3);
		jp3.add(jtf3);
			
		jp4.add(jlb4);
		jp4.add(jtf4);
			
		jp5.add(jb1);
			
		//加入到JFrame
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		this.add(jp5);
						
		//设置窗体
		this.setTitle("录入成绩");//窗体标签
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
		if(e.getActionCommand()=="录入")
		{
			try {
				String sql = "UPDATE SC SET Grade = ?, ismakeup = ? WHERE Sno = ? AND Cno = ?";
					ps=dbConn.prepareStatement(sql);
					ps.setString(1, jtf3.getText());
					ps.setString(2, jtf4.getText());
					ps.setString(3, jtf1.getText());
					ps.setString(4, jtf2.getText());
					if(ps.executeUpdate()==1)
					{
						JOptionPane.showMessageDialog(null,"添加成功！","提示消息",JOptionPane.WARNING_MESSAGE);
						dispose();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		//清空文本框和密码框
		public void clear()
		{
			jtf1.setText("");
			jtf2.setText("");
			jtf3.setText("");
			jtf4.setText("");
		}
}

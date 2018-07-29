package db_GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SearchforGivingLessons extends JFrame implements ActionListener {
	/*
	 * 查询任课教师
	 */
	private static final long serialVersionUID = 1L;
	JPanel jp1,jp2;//面板
	JButton jb1;//按钮
	JTextField jtf1;//文本
	JLabel jlb1;//标签
	
	static Connection dbConn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	//构造函数
	public SearchforGivingLessons() {
				
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
				
		//创建标签
		jlb1 = new JLabel("学号");
				
		//创建按钮
		jb1=new JButton("查询任课教师");
		
		//设置监听
		jb1.addActionListener(this);
				
		//创建文本框
		jtf1=new JTextField(11);
		
		//设置布局管理
		this.setLayout(new GridLayout(2, 1));//网格式布局
				
		//加入各个组件
		jp1.add(jlb1);
		jp1.add(jtf1);
				
		jp2.add(jb1);
				
		//加入到JFrame
		this.add(jp1);
		this.add(jp2);
				
		//设置窗体
		this.setTitle("查询任课教师");//窗体标签
		this.setSize(300, 100);//窗体大小
		this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
		this.setVisible(true);//显示窗体
							
		//锁定窗体
		this.setResizable(false);
	}
	//响应函数
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand()=="查询任课教师")
		{
			try {
				String sql = "SELECT SC.Cno, course.cname, teacher.tname "
						+ "FROM SC, teacher, course "
						+ "WHERE sc.Sno = ? "
						+ "AND sc.tno = teacher.tno "
						+ "AND sc.cno = course.cno";
				ps=dbConn.prepareStatement(sql);
				ps. setString(1, jtf1.getText());
				rs=ps.executeQuery();
				JFrame jf = new JFrame("选课情况及任课教师");
				//创建一个面板容器
				JPanel jp = new JPanel();
				while(rs.next())
				{
					JLabel jl1 = new JLabel(rs.getString(1));
					JLabel jl2 = new JLabel(rs.getString(2));
					JLabel jl3 = new JLabel(rs.getString(3));
					jp.add(jl1);
					jp.add(jl2);
					jp.add(jl3);
				}
				jf.add(jp);
				jf.setBounds(200, 200, 300,300);
				jf.setSize(290, 300);
				jf.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}

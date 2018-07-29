package db_GUI;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentLogin extends JFrame implements ActionListener {
	/*
	 * 学生登录，功能选择
	 */
	private static final long serialVersionUID = 1L;
	
	// 学生学号，用于之后查找
	private static String sno = null;
		
	//定义组件
	JPanel jp1; // 面板
	JButton jb1,jb2; // 按钮
	
	static Connection dbConn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	//构造函数
	public StudentLogin(String key){
		//初始化学生学号
		sno = key;
		
		// 连接数据库	
		Login win = new Login();
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/***?useSSL=false&&serverTimezone=GMT%2B8";
		String user = "root";
		String password = "***";
		try
        {
            Class.forName(driver);
            dbConn = DriverManager.getConnection(url, user, password);
            //System.out.println("连接数据库成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.print("连接失败");
        }
		//创建面板
		jp1 = new JPanel();//用户名面板
		//创建按钮
		jb1=new JButton("查询基本信息");
		jb2=new JButton("查询所有成绩");
		//设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);	
		//加入各个组件
		jp1.add(jb1);
		jp1.add(jb2);
		//加入到JFrame
		this.add(jp1);
		//设置窗体
		this.setTitle("学生" + sno);//窗体标签
		this.setSize(300, 100);//窗体大小
		this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
		this.setVisible(true);//显示窗体
							
		//锁定窗体
		this.setResizable(false);
	}
	
	// 响应函数
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "查询基本信息") {
			try {
				String sql = "SELECT Sno,Sname,Ssex,Sbirth,Sdept,Sgrade,SClass "
						+ "FROM student "
						+	"WHERE Sno =?";
				ps=dbConn.prepareStatement(sql);
				ps.setString(1, sno);
				rs=ps.executeQuery();
				System.out.println(sno);
				JFrame jf = new JFrame(sno + "基本信息");
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
					JLabel jl7 = new JLabel(rs.getString(7));
					jp.add(jl1);
					jp.add(jl2);
					jp.add(jl3);
					jp.add(jl4);
					jp.add(jl5);
					jp.add(jl6);
					jp.add(jl7);
				}
				jf.add(jp);
				jf.setBounds(200, 200, 300, 300);
				jf.setSize(400, 100);
				jf.setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand()=="查询所有成绩")
		{
			try {
				String sql = "SELECT student.Sno,sc.Cno,course.Cname,sc.isOpen,course.Credit,sc.Grade "
						+ "FROM student,sc,teacher,course "
						+ "WHERE student.Sno = sc.Sno "
						+ "AND sc.Tno = teacher.Tno "
						+ "AND sc.Cno = course.Cno "
						+ "AND sc.Sno = ?";
				ps=dbConn.prepareStatement(sql);
				ps.setString(1, sno);
				rs=ps.executeQuery();
				JFrame jf = new JFrame(sno + "各科成绩");
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
				e1.printStackTrace();
			}
		}
	}

}

package db_GUI;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	/*
	 *  用户登录 程序入口 
	 */
	private static final long serialVersionUID = 1L;
	
	// 定义组件
	JPanel jp1, jp2, jp3, jp4; // 面板
	JLabel jlb1, jlb2, jlb3; // 标签
	JButton jb1, jb2; // 按钮
	JRadioButton jr1, jr2; ButtonGroup jb; // 单选按钮
	JTextField jtf; // 文本
	JPasswordField jpf; // 密码
	
	// 设定用户名、密码
	String username;
	String pwd;
	
	Connection dbConn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public static void main(String[] args) {
		Login win = new Login();
		return;
	}
	
	// 构造函数
	public Login() {
		// 连接数据库；
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/***?useSSL=false&&serverTimezone=GMT%2B8";
		String user = "root";
		String password = "***";
		try{
			Class.forName(driver);
			dbConn = DriverManager.getConnection(url, user, password);
			
			// if(!dbConn.isClosed())
			  //System.out.println("连接成功");
			 
				
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("连接失败");
		}
		// 创建面板
		jp1 = new JPanel(); // 用户名
		jp2 = new JPanel(); // 密码
		jp3 = new JPanel(); // 权限
		jp4 = new JPanel(); // 登录&重置按钮
		
		// 创建标签
		jlb1 = new JLabel("用户名");
		jlb2 = new JLabel("密    码");
		jlb3 = new JLabel("权    限");
		
		// 创建按钮
		jb1 = new JButton("登录");
		jb2 = new JButton("重置");
		
		// 设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		// 创建文本框
		jtf = new JTextField(15);
		
		// 创建密码框
		jpf = new JPasswordField(15);
		
		// 设置单选按钮
		jr1 = new JRadioButton("教师");
		jr2 = new JRadioButton("学生"); 
		
		// 设置按钮组，使两个按钮只能同时选中一个
		jb = new ButtonGroup();
		jb.add(jr2);
		jb.add(jr1);
		
		// 设置布局管理
		this.setLayout(new GridLayout(4, 1)); // 网格式布局
		
		// 加入组件
		jp1.add(jlb1);
		jp1.add(jtf);
			
		jp2.add(jlb2);
		jp2.add(jpf);
		
		jp3.add(jlb3);
		jp3.add(jr1);
		jp3.add(jr2);
		
		jp4.add(jb1);
		jp4.add(jb2);
		
		// 加入到框架中
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		
		// 设置窗体
		this.setTitle("用户登录"); // 窗体标签
		this.setSize(300, 200); // 窗体大小
		this.setLocationRelativeTo(null); // 居中显示
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭JFrame
		this.setVisible(true); // 显示窗体
		
		// 锁定窗体
		this.setResizable(false);
		
	}
	public void actionPerformed(ActionEvent e) {

		if(e.getActionCommand() == "登录"){
			// 选中教师登录
			if(jr1.isSelected()){
				// 火箭车
				try{
					String sql = "select * from teacher";
					ps = dbConn.prepareStatement(sql);
					rs = ps.executeQuery();
					// 判断是否登录成功
					if (jtf.getText().isEmpty() && jpf.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "请输入用户名和密码", "提示消息", JOptionPane.WARNING_MESSAGE);
					else if (jtf.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "请输入用户名", "提示消息", JOptionPane.WARNING_MESSAGE);
					else if (jpf.getText().isEmpty())
						JOptionPane.showMessageDialog(null, "请输入密码", "提示消息", JOptionPane.WARNING_MESSAGE);
					else{
						boolean flag = false;
						while(rs.next()){
							username = rs.getString(1);
							pwd = rs.getString(6);
							if (username.equals(jtf.getText()) && pwd.equals(jpf.getText())){
								flag = true;
								JOptionPane.showMessageDialog(null, "登录成功", "提示消息", JOptionPane.WARNING_MESSAGE);
								clear();
								dispose();
								TeacherLogin tea = new TeacherLogin();
							}
						}
						if(flag == false){
							JOptionPane.showMessageDialog(null,"用户名或者密码错误！\n请重新输入","提示消息",JOptionPane.ERROR_MESSAGE);
							//清空输入框
							clear();
						}
					}
				}
				catch (SQLException e1){
					e1.printStackTrace();
				}
			}
			// 学生登录
			else if(jr2.isSelected())//学生在登录系统
			{
				//创建火箭车
				try 
				{
					String sql = "select * from Student";
					ps=dbConn.prepareStatement(sql);
					//ResultSet结果集,大家可以把ResultSet理解成返回一张表行的结果集
					rs=ps.executeQuery();
					//判断是否登陆成功
					if(jtf.getText().isEmpty()&&jpf.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null,"请输入用户名和密码！","提示消息",JOptionPane.WARNING_MESSAGE);
					}
					else if(jtf.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null,"请输入用户名！","提示消息",JOptionPane.WARNING_MESSAGE);
					}
					else if(jpf.getText().isEmpty())
					{
						JOptionPane.showMessageDialog(null,"请输入密码！","提示消息",JOptionPane.WARNING_MESSAGE);
					}
					else
					{
						boolean flag = false;
						while(rs.next())
						{
							username=rs.getString(1);
							pwd=rs.getString(8);
							String sno = jtf.getText();
							if(username.equals(sno) && pwd.equals(jpf.getText()))
							{
								//System.out.println("登录成功");
								flag = true;
								JOptionPane.showMessageDialog(null, "登录成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
								clear();
								//关闭当前界面
								dispose();
								//创建一个新界面
								StudentLogin stu = new StudentLogin(sno);
							}
						}
						if(flag == false)
						{
							JOptionPane.showMessageDialog(null,"用户名或者密码错误！\n请重新输入","提示消息",JOptionPane.ERROR_MESSAGE);
							//清空输入框
							clear();
						}
					}
				} 
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		else if(e.getActionCommand()=="重置"){
			clear();
		}
		
	}
	// 清空文本框和密码框
	public void clear(){
		jtf.setText("");
		jpf.setText("");
	}
}

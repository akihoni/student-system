package db_GUI;

import java.sql.*;

public class Connection_test {
	/*
	 * 测试能否连接
	 */

	public static void main(String[] args) {
		// 驱动程序名
		String driver = "com.mysql.cj.jdbc.Driver";
		// URL指向要访问的数据库名
		String url = "jdbc:mysql://127.0.0.1:3306/***?useSSL=false&&serverTimezone=GMT%2B8";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "***";
		try
		{
			// 加载驱动程序
			Class.forName(driver);
			// 连接数据库
			Connection conn = DriverManager.getConnection(url, user, password);
			if(!conn.isClosed())
				System.out.println("连接成功");
			// 设置ps变量
			PreparedStatement ps = null;
			// 要执行的SQL语句
			String sql = "select * from teacher";
			// 执行SQL语句
			ps = conn.prepareStatement(sql);
			// 结果
			ResultSet rs = ps.executeQuery(sql);
			System.out.println("----------------");
			System.out.println("执行结果如下：");
			System.out.println("----------------");
			System.out.println(" 编号" + "\t" + " 姓名");
			System.out.println("----------------");
			String name = null;
			while(rs.next()){
				name = rs.getString("Tname");
				name = new String(name.getBytes("utf-8"), "utf-8");
				System.out.println(rs.getString("Tno") + "\t" + name);
			}
			rs.close();
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("连接失败");
		}
	}

}

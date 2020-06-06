package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class BaseDao {
	private static final String DRIVER="com.mysql.jdbc.Driver";
	private static final String URL="jdbc:mysql://localhost:3306/blog";
	private static final String root="root";
	private static final String password="234111";
	public BaseDao()
	{
		
	}
	protected Connection getConnection()
	{
		try {
			Class.forName(DRIVER);
			Connection con=DriverManager.getConnection(URL,root,password);
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
		
	}
	protected void closeAll(Connection conn,Statement stmt)
	{
		if(null!=conn){
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("CLOSE ERROR"+conn);
				e.printStackTrace();
			}
		}
		if(null!=stmt){
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println("CLOSE ERROR"+stmt);
				e.printStackTrace();
			}
		}
	}
	
	protected void closeAll(Connection conn,PreparedStatement pstmt)
	{
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		if(null!=pstmt){
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("CLOSE ERROR"+pstmt);
				e.printStackTrace();
			}
		}
	}
	protected void closeAll(Connection conn,Statement stat,ResultSet rs)
	{
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	}
	protected void closeAll(Connection conn,PreparedStatement prep,ResultSet rs)
	{
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
	}

}

package Impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import bean.User;
import dao.UserDao;
import jdbc.BaseDao;

public class UserImpl extends BaseDao implements UserDao{
	
	Connection conn=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;

	@Override
	public List<User> searchUser(int id) {
		// TODO 自动生成的方法存根
		
		List<User> user=new ArrayList<User>();
		String sql="select * from users where id like ?";
try {
			
			conn=getConnection();
			
			if(conn!=null)
			{
				System.out.println("连接成功"+conn);
			}
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				
				user.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		
		return user;
	}
	
	public void close()
	{
		if(null!=conn){
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("CLOSE ERROR"+conn);
				e.printStackTrace();
			}
		}
	}

}

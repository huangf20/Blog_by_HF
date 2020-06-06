package Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import bean.Article;
import bean.User;
import dao.ArticleDao;
import jdbc.BaseDao;

public class ArticleImpl extends BaseDao implements ArticleDao{
	
	Connection conn=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;

	@Override
	public void insert() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void delete() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void updata() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public List<Article> select(Article a) {
		// TODO 自动生成的方法存根
		List<Article> articles=new ArrayList<Article>();
		String sql="select * from articles";
		
		try {
			
			conn=getConnection();
			
			if(conn!=null)
			{
				System.out.println("连接成功"+conn);
			}
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				//System.out.println("\t"+rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getInt(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
				articles.add(new Article(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7)));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		
		return articles;
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

	@Override
	public List<Article> search(Article a) {
		// TODO 自动生成的方法存根
		List<Article> articles=new ArrayList<Article>();
		String sql="select * from articles where title like ?";
		
		try {
			
			conn=getConnection();
			
			if(conn!=null)
			{
				System.out.println("连接成功"+conn);
			}
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, "%"+a.getTitle()+"%");
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				//System.out.println("\t"+rs.getInt(1)+"\t"+rs.getInt(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getInt(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7));
				articles.add(new Article(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7)));
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		
		return articles;
	}

	@Override
	public List<User> checkHaveOrNot(User u) {
		// TODO 自动生成的方法存根
		List<User> user=new ArrayList<User>();
		String sql="select * from users where name like ? and password like ?";
		
		try {
			
			conn=getConnection();
			
			if(conn!=null)
			{
				System.out.println("连接成功"+conn);
			}
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			pstmt.setString(1, u.getName());
			pstmt.setString(2, u.getPassword());
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

	@Override
	public boolean addArticle(Article a) {
		// TODO 自动生成的方法存根
		boolean success=false;
		
		
		
		/*try {
			
			conn=getConnection();
			
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			
			pstmt.setInt(1, maxId()+1);
			pstmt.setInt(2, a.getUser_id());
			pstmt.setString(3, a.getTitle());
			pstmt.setString(4, a.getContent());
			pstmt.setString(5, a.getIamge());
			rs=pstmt.executeQuery();
			success=true;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		QueryRunner qr=new QueryRunner();
		//对象
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Connection conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/blog","root","234111");
			conn=getConnection();
			String sql="INSERT into articles VALUES(?,?,?,?,0,SYSDATE(),?)";
			Object[] objs=new Object[] {maxId()+1,a.getUser_id(),a.getTitle(),a.getContent(),a.getIamge()};
			qr.update(conn, sql, objs);
			success=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//加载驱动
		
		
		return success;
	}

	@Override
	public int maxId() {
		// TODO 自动生成的方法存根
		String sql="SELECT  MAX(article_id) FROM articles";
		int maxid=0;
		
		try {
			
			conn=getConnection();
			
			pstmt=(PreparedStatement) conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				maxid=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return maxid;
	}

	@Override
	public boolean deleteArticle(int id) {
		// TODO 自动生成的方法存根
		boolean success=false;
		
		QueryRunner qr=new QueryRunner();
		//对象
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Connection conn=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/blog","root","234111");
			conn=getConnection();
			String sql="DELETE FROM articles where article_id=?";
			Object[] objs=new Object[] {id};
			qr.update(conn, sql, objs);
			success=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return success;
	}
	

}

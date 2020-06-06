package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Impl.ArticleImpl;
import bean.Article;

/**
 * Servlet implementation class ArticleServlet
 */
@WebServlet("/ArticleServlet")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=utf-8");
		//请求解决乱码
		request.setCharacterEncoding("utf-8");
		//响应解决乱码
		response.setCharacterEncoding("utf-8");
		Gson gson=new Gson();
		ArticleImpl at=new ArticleImpl();
		
		String word=(String)request.getParameter("word");
		String title=(String)request.getParameter("title");
		String articleId=(String)request.getParameter("article_id");
		
		if(word!=null)
		{
			
			
			Article a=new Article(word);
			String str=gson.toJson(at.search(a));
			at.close();
			response.getWriter().print(str);
		}
		else if(title!=null)
		{
			String content=(String)request.getParameter("content");
			int user_id=Integer.parseInt((String)request.getParameter("user_id"));
			String random=(int) (Math.random()*10+1)+"";
			String image="http://192.168.0.110:8080/img/"+random+".jpg";
			Article a=new Article(user_id,title,content,image);
			
			if(at.addArticle(a))
			{
				response.getWriter().print("发表成功！");
			}
			
			
			at.close();
		}
		else if(articleId!=null)
		{
			if(at.deleteArticle(Integer.parseInt(articleId) ))
			{
				response.getWriter().print("删除成功！");
			}
			
		}
		else
		{
			Article a=new Article();
			String str=gson.toJson(at.select(a));
			at.close();
			response.getWriter().print(str);
		}
		
		
		
		
		
		
		//request.setAttribute("data", str);
		//request.getRequestDispatcher("/article.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

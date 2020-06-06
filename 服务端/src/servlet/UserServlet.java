package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import Impl.ArticleImpl;
import Impl.UserImpl;
import bean.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
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
		String id=request.getParameter("userId");
		if(id!=null)
		{
			
			Gson gson=new Gson();
			UserImpl us=new UserImpl();
			String str=gson.toJson(us.searchUser(Integer.parseInt( id)));
			us.close();
			response.getWriter().print(str);
		}
		else
		{
			Gson gson=new Gson();
			ArticleImpl at=new ArticleImpl();
			String name=request.getParameter("name");
			String password=request.getParameter("password");
			User user=new User(name,password);
			String str=gson.toJson(at.checkHaveOrNot(user));
			at.close();
			response.getWriter().print(str);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

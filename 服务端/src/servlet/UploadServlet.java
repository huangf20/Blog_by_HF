package servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;




/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String path = request.getRealPath("/upload");
		response.getWriter().print(path);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		response.setContentType("text/html;charset=utf-8");
		//请求解决乱码
		request.setCharacterEncoding("utf-8");
		//响应解决乱码
		response.setCharacterEncoding("utf-8");
		

		DiskFileItemFactory factory = new DiskFileItemFactory();
		String path = request.getRealPath("/upload");

		File file=new File(path);
		
		if(!file.exists()){
			 
			file.mkdirs();
			 
			}

		factory.setRepository(new File(path));
		factory.setSizeThreshold(1024*1024) ;//设置 缓存的大小
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		try {
			List<FileItem> list = (List<FileItem>)upload.parseRequest( request);

			for(FileItem item : list)
			{
				String name = item.getFieldName();
				if(item.isFormField())
				{

					String value = item.getString() ;

					request.setAttribute(name, value);
				}
				else
				{

					String value = item.getName() ;

					int start = value.lastIndexOf("\\");

					String filename = value.substring(start+1);
					request.setAttribute(name, filename);
					item.write( new File(path,filename) );//第三方提供的
					System.out.println("上传成功："+filename);
					response.getWriter().print(filename);
				}
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			System.out.println("上传失败");
			 
			response.getWriter().print("上传失败："+e.getMessage());
		}
		
	}

}

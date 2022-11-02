import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
			
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.print("<html>");
		out.print("<head><title>나의 첫 서블릿</title></head>");
		out.print("<body>");
		out.print("<h1>웹서버프로그래밍</h1>");
		out.print("</body>");
		out.print("</html>");
		
		out.close();
		
	}


}

package net.javaguides.login.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import net.javaguides.login.bean.LoginBean;
import net.javaguides.login.database.LoginDao;

import java.io.*; 
import java.util.Locale; 

/**
 * @email Ramesh Fadatare
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;
	ResultSet rs = null;


	public void init() {
		loginDao = new LoginDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter(  ); 
		response.setContentType("text/html"); 
		    out.println("<html>");
		    out.println("<head>");

		out.println("<title> Table's content </title>");
		    out.println("</head>");
		    out.println("<body>");
		    out.println("<H1>Languages' list</H1></br>"); 

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String languagename = request.getParameter("languagename");
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);
		loginBean.setLanguagename(languagename);

		try {
		    Statement prepStat = null;
			ResultSet rs = null;

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn = DriverManager
					.getConnection("jdbc:mysql://45.67.231.181:3306/mio_glossario?characterEncoding=latin1&useConfigs=maxPerformance", loginBean.getUsername(), loginBean.getPassword());

					// Step 2:Create a statement using connection object
			prepStat = conn.createStatement();

			System.out.println(prepStat);
			rs = prepStat.executeQuery("call	mio_glossario.sp_view_mg_languages	('"+loginBean.getLanguagename()+"',@ret_value,@err_mess) ;");
			
			if (rs != null) {
				//HttpSession session = request.getSession();
				// session.setAttribute("username",username);
//				response.sendRedirect("loginsuccess.jsp");
					int rowCount = 0;
	
					 out.println("<form action=\"<%=request.getContextPath()%>/login\" method=\"post\">") ;
					 out.println("<P ALIGN='center'><TABLE BORDER=1>");
					 java.sql.ResultSetMetaData rsmd = rs.getMetaData();
					 int columnCount = rsmd.getColumnCount();
					 // table header
					 out.println("<TR>");
					 for (int i = 0; i < columnCount; i++) {
					   out.println("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
					   }
					 out.println("</TR>");
					 // the data
					 while (rs.next()) {
						 String cur_str = "" ;
					  rowCount++;
					  out.println("<TR>");
					  for (int i = 0; i < columnCount; i++) {
						  try {
						  	cur_str = rs.getString(i + 1) ;
						  } catch(NullPointerException | SQLException e) {
							  cur_str = "";
						  }
						  out.println("<TD>" + cur_str + "</TD>");
					    }
					  out.println("</TR>");
					  }
					 out.println("</TABLE></P>");
					 out.println("<input type=\"submit\" value=\"Submit\" />");
					 out.println("</form>");
						
					prepStat.close();
				 } else {
				HttpSession session = request.getSession();
				//session.setAttribute("user", username);
				//response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}

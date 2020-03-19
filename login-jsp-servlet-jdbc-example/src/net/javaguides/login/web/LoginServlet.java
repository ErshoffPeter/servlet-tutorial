package net.javaguides.login.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

		out.println("<title> Any Title </title>");
		    out.println("</head>");
		    out.println("<body>");
		    out.println("<H1>Last Name from a Servlet</H1></br>"); 

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);

		try {
			ResultSet rs = null;

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conn = DriverManager
					.getConnection("jdbc:mysql://45.67.231.181:3306/mio_glossario?characterEncoding=latin1&useConfigs=maxPerformance", loginBean.getUsername(), loginBean.getPassword());

					// Step 2:Create a statement using connection object
					PreparedStatement prepStat = conn
							.prepareStatement("call	mio_glossario.sp_view_mg_languages	('cv',@ret_value,@err_mess) ;") ;

			System.out.println(prepStat);
			rs = prepStat.executeQuery();
			
			if (rs != null) {
				//HttpSession session = request.getSession();
				// session.setAttribute("username",username);
//				response.sendRedirect("loginsuccess.jsp");
					int rowCount = 0;
	
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
					  rowCount++;
					  out.println("<TR>");
					  for (int i = 0; i < columnCount; i++) {
					    out.println("<TD>" + rs.getString(i + 1) + "</TD>");
					    }
					  out.println("</TR>");
					  }
					 out.println("</TABLE></P>");
						
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

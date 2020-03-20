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
	private LoginBean loginBean ; 
	private ResultSet rs ;

	public void init() {
		loginDao = new LoginDao();
		loginBean = new LoginBean();
		rs = null;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType( "text/html" );
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter(  ); 
		
		response.setContentType("text/html"); 
			out.println("<!DOCTYPE html>");
			out.println("<html lang=\"en\">");
			out.println("<html>");
		    out.println("<meta charset=\"UTF-8\" />");
		    out.println("<head>");
		    out.println("<meta charset=\"UTF-8\" />");

		out.println("<title> Table's content </title>");
		    out.println("</head>");
		    out.println("<body>");
		    out.println("<meta charset=\"UTF-8\" />");
		    out.println("<H1>Languages' list</H1></br>"); 

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String languagename = request.getParameter("languagename");
		
		if(username != "" && username != null)
			loginBean.setUsername(username);
		
		if(password != "" && password != null)
			loginBean.setPassword(password);
		
		if(languagename != "" && languagename != null)
			loginBean.setLanguagename(languagename);

		try {
			
			rs = loginDao.validate(loginBean); 
			
			if (rs != null) {
				//HttpSession session = request.getSession();
				// session.setAttribute("username",username);
//				response.sendRedirect("loginsuccess.jsp");
					int rowCount = 0;
	
					 out.println("<form action=\""+request.getContextPath()+"\\login.jsp\" method=\"post\">") ;
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
							  System.err.println("Message: " + e.getMessage());
							  cur_str = "";
						  }
						  out.println("<TD>" + cur_str + "</TD>");
						  
//						  if ( rsmd.getColumnLabel(i + 1).compareTo("native_name")==0)
//							  System.out.println("[native_name]: " + cur_str);
					    }
					  out.println("</TR>");
					  }
					 out.println("</TABLE></P>");
					 out.println("<input type=\"submit\" value=\"Submit\" />");
					 out.println("</form>");
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

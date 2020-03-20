package net.javaguides.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.javaguides.login.bean.LoginBean;

public class LoginDao {
	private Connection conn = null;

	public boolean isValid(int timeout) throws SQLException {
		if (conn != null)
			return conn.isValid(timeout);
		else
			return false;
	}

	public ResultSet validate(LoginBean loginBean) throws ClassNotFoundException {
	    Statement stat = null;
		ResultSet rs = null;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try{
			if (! isValid(3)) {
			
			conn = DriverManager
					.getConnection("jdbc:mysql://45.67.231.181:3306/mio_glossario?useUnicode=true&characterEncoding=utf-8&useConfigs=maxPerformance", loginBean.getUsername(), loginBean.getPassword());
			}
					// Step 2:Create a statement using connection object
			stat = conn.createStatement();

			System.out.println(stat);
			rs = stat.executeQuery("call	mio_glossario.sp_view_mg_languages	('"+loginBean.getLanguagename()+"',@ret_value,@err_mess) ;");
			
			} catch (SQLException e) { 
			// process sql exception
			printSQLException(e);
			conn = null;
			rs = null ;
			}
		
		return rs;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}

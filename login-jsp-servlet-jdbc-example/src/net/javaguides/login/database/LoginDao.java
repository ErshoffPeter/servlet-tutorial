package net.javaguides.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.javaguides.login.bean.LoginBean;

public class LoginDao {

	public Connection validate(LoginBean loginBean) throws ClassNotFoundException {
		Connection conn = null;
		
		Class.forName("com.mysql.jdbc.Driver");

		try{
			conn = DriverManager
					.getConnection("jdbc:mysql://45.67.231.181:3306/mio_glossario?characterEncoding=latin1&useConfigs=maxPerformance", loginBean.getUsername(), loginBean.getPassword());		
							} catch (SQLException e) { 
			// process sql exception
			printSQLException(e);
			conn = null;
			}
		return conn;
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

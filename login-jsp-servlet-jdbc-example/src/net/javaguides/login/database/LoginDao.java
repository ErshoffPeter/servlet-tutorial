package net.javaguides.login.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.javaguides.login.bean.LoginBean;

public class LoginDao {

	public ResultSet validate(LoginBean loginBean) throws ClassNotFoundException {
		ResultSet rs = null;

		Class.forName("com.mysql.jdbc.Driver");

		try (Connection conn = DriverManager
				.getConnection("jdbc:mysql://45.67.231.181:3306/mio_glossario?characterEncoding=latin1&useConfigs=maxPerformance", loginBean.getUsername(), loginBean.getPassword());

				// Step 2:Create a statement using connection object
				PreparedStatement prepStat = conn
						.prepareStatement("call	mio_glossario.sp_view_mg_languages	('cv',@ret_value,@err_mess) ;")) {

			System.out.println(prepStat);
			rs = prepStat.executeQuery();
			
			prepStat.close();

		} catch (SQLException e) {
			// process sql exception
			printSQLException(e);
			
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

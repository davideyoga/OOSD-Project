package gamingplatform.utils.connectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC{
	
	private static String host = "localhost";
	
	private static String user = "user";
	
	private static String password = "password";
	
	
	public static Connection getConnection() throws SQLException{
			
		Connection connection = DriverManager.getConnection(host, user, password);
		
		return connection;
	}

}
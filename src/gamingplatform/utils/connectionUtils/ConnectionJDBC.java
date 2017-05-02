package gamingplatform.utils.connectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC{
	
	static String host = "localhost";
	
	static String user = "user";
	
	static String password = "password";
	
	
	public static Connection getConnection() throws SQLException{
			
		Connection connection = DriverManager.getConnection(host, user, password);
		
		return connection;
		
	}
	
	
	
	
	
}
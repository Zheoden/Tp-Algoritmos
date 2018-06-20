package xpress;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UtnConnectionFactory
{
	private static Connection conn;

	public static Connection getConnection() throws IOException, SQLException {
		if(conn == null)
		{
			Properties prop = new Properties();
			InputStream is = UtnConnectionFactory.class.getResourceAsStream("JdbcUtil.properties");
			prop.load(is);
			conn = DriverManager.getConnection(prop.getProperty("jdbc.connection.url"),prop.getProperty("jdbc.connection.user"),prop.getProperty("jdbc.connection.password"));
		}
		return conn;
	}
}

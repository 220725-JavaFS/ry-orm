package com.revature.utils;

import java.sql.Connection; //java.sql is the JDBC package
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtil {

//	//figure out method connect method to call from ORM
	private static Connection connection;
	private static ConnectionUtil cdb;
	static final String url = "jdbc:postgresql://javafs220725.ctl0w09tjsjs.us-east-1.rds.amazonaws.com:5432/poject1";
	static final String username = "postgres";
	static final String password = "password1234";

	//constructor
	private ConnectionUtil() {}


	public Connection getConnection() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			return connection;
		}
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();//error message
		}
		return connection;
	}
	//establishing connection with synchronized lock
		public static synchronized ConnectionUtil getConn() {
			if (cdb == null) {
				cdb = new ConnectionUtil();
			}
			return cdb;
		}

//	public static void main(String[] args) {
//		try {
//			getConnection();
//			System.out.println("Connection Successful");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}


}

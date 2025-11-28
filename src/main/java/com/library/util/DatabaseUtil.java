package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
	
	private static final String URL = "jdbc:mysql://localhost:3306/library_db";
	private static final String USER = "root";
	private static final String PASS = "13052001";
	
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("MySQL Driver non trouvé", e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
	  
	public static void close(AutoCloseable c) {
		if(c != null) {
			try { c.close();} catch(Exception e) {}
		}
	}

}

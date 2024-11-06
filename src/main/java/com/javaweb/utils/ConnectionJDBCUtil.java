package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBCUtil {
	private static String url = "jdbc:mysql://localhost:3306/estatebasic";
    private static String username = "root";
    private static String password = "123456789";
    
    public static Connection getConnection() {
    	Connection con = null;
    	try {
    		con = DriverManager.getConnection(url,username,password);

    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
		return con;
    }
}

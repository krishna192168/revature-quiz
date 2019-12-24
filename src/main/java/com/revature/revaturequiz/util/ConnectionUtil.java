package com.revature.revaturequiz.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {
	
	private ConnectionUtil() {}
	
	static ConnectionUtil conn = null;
	
	public static ConnectionUtil getInstance()
	{
		if(conn == null)
		{
			return new ConnectionUtil();
		} else {
			return conn;
		}
	}
	
	public static void close(Connection conn)
	{
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection conn,PreparedStatement pstmt)
	{
		try {
			conn.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection conn,PreparedStatement pstmt,ResultSet rs)
	{
		try {
			conn.close();
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

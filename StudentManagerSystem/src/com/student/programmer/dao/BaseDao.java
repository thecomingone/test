package com.student.programmer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
		private String dbUrl = "jdbc:sqlserver://localhost:1433; DatabaseName=stu_db";
		private String dbUser = "sa";
		private String dbPassword = "19991001";
		private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		private Connection connection = null;
		public Connection getConnection(){
			try {
				Class.forName(driverName);
				connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				System.out.println("Connection Successful!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Connection  Fail!");
				e.printStackTrace();				
			}
			return connection;
		}
		
		public void closeCon(){
			if(connection != null)
				try {
					connection.close();
					System.out.println("Close DateBase!");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
//返回一个结果集
	public ResultSet query(String sql){
		try {
			PreparedStatement prepareStatement = getConnection().prepareStatement(sql);
			return prepareStatement.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
//返回一个布尔值
	public boolean update(String sql){
		try {
			return getConnection().prepareStatement(sql).executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isEmpty(String str) {
		if(str == null || "".equals(str))return true;
		return false;
	}
}

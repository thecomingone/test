package com.student.programmer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.programmer.domain.Admin;

public class AdminDao extends BaseDao {
	
	public Admin login(String adminName ,String password){
		String sql = "select * from s_admin where admin_name = '" + adminName + "' and password = '" + password + "'";
//		System.out.println(sql);
		ResultSet resultSet = query(sql);
		try {
			if(resultSet.next()){
				Admin admin = new Admin();
				admin.setId(resultSet.getInt("id"));
				admin.setAdminName(resultSet.getString("admin_name"));
				admin.setPassword(resultSet.getString("password"));
				System.out.println(admin.getAdminName());
				return admin;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean editPassword(Admin admin,String newPassword) {
		// TODO Auto-generated method stub
		String sql = "update s_admin set password = '"+newPassword+"' where admin_name = '" + admin.getAdminName()+"'";
		return update(sql);
	}
}

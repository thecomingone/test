package com.student.programmer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.programmer.domain.*;


public class ClazzDao extends BaseDao {
//	public List<Clazz> getClazzList(Clazz clazz,Page page){
//		List<Clazz> ret = new ArrayList<Clazz>();
//		String sql = "select * from s_clazz ";
//		if(true){
//			sql += "where name like '%" + clazz + "%'";
//		}
//		sql += " limit " + page.getStart() + "," + page.getPageSize();
//		ResultSet resultSet = query(sql);
//		try {
//			while(resultSet.next()){
//				Clazz cl = new Clazz();
//				cl.setId(resultSet.getInt("id"));
//				//cl.setName(resultSet.getString("name"));
//				//cl.setInfo(resultSet.getString("info"));
//				ret.add(cl);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ret;
//	}
//	public int getClazzListTotal(Clazz clazz){
//		int total = 0;
//		String sql = "select count(*)as total from s_clazz ";
//		if(!StringUtil.isEmpty(clazz.getName())){
//			sql += "where name like '%" + clazz + "%'";
//		}
//		//ResultSet resultSet = query(sql);
//		try {
//			while(resultSet.next()){
//				//total = resultSet.getInt("total");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return 0;
//	}
//	public boolean addClazz(Clazz clazz){
//		String sql = "insert into s_clazz(clazz_no,department,info) values('"+clazz.getClazz_no()+"','"+clazz.getDepartment()+"','"+clazz.getInfor()+"') ";
//		return update(sql);
//	}
//	public boolean deleteClazz(int id){
//		String sql = "delete from s_clazz where id = "+id;
//		return update(sql);
//	}
//	public boolean editClazz(Clazz clazz) {
//		// TODO Auto-generated method stub
//		String sql = "update s_clazz set name = '"+clazz.getName()+"',info = '"+clazz.getInfo()+"' where id = " + clazz.getId();
//		return update(sql);
//	}
//	
}

package com.student.programmer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.programmer.domain.Page;
import com.student.programmer.domain.Student;

public class StudentDao extends BaseDao {
	public boolean addStudent(Student student){
		String sql = "insert into s_student(stu_id,stu_name,sex,department,clazz,ID_card,telephone) values('"+student.getStu_id()+"','"+student.getStu_name()+"'";
		sql += ",'" + student.getSex() + "','" + student.getDepartment()+ "'";
		sql += ",'" + student.getClazz() + "','" + student.getIDcard() + "'";
		sql += ",'" + student.getTelephone() + "')";
		return update(sql);
	}
	public boolean editStudent(Student student) {
		// TODO Auto-generated method stub
		String sql = "update s_student set stu_name = '"+student.getStu_name()+"'";
		sql += ",sex = '" + student.getSex() + "'";
		sql += ",department = '" + student.getDepartment() + "'";
		sql += ",clazz = '" + student.getClazz() + "'";
		sql += ",telephone = '" + student.getTelephone() + "'";
		sql += ",ID_card = '" + student.getIDcard()+ "'";
		sql += " where stu_id = '" + student.getStu_id()+ "'";
		return update(sql);
	}
	public boolean setStudentPhoto(Student student) {
		// TODO Auto-generated method stub
		String sql = "update s_student set photo = ? where stu_id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setBinaryStream(1, student.getPhoto());
			prepareStatement.setString(2, student.getStu_id());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return update(sql);
	}
	public boolean deleteStudent(String ids) {
		// TODO Auto-generated method stub
		String sql = "delete from s_student where stu_id in("+ids+")";
		return update(sql);
	}
	public Student getStudent(String id){
		String sql = "select * from s_student where stu_id = '" + id+"'";
		Student student = null;
		ResultSet resultSet = query(sql);
		try {
			if(resultSet.next()){
				student = new Student();
				student.setStu_id(resultSet.getString("stu_id"));
				student.setClazz(resultSet.getString("clazz"));
				//System.out.println(student.getClazz());
				student.setTelephone(resultSet.getString("telephone"));
				student.setStu_name(resultSet.getString("stu_name"));
				student.setIDcard(resultSet.getString("ID_card"));
				student.setPhoto(resultSet.getBinaryStream("photo"));
				student.setDepartment(resultSet.getString("department"));
				student.setSex(resultSet.getString("sex"));
				return student;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;
	}
	public List<Student> getStudentList(Student student){
		List<Student> ret = new ArrayList<Student>();
		String sql = "select * from s_student ";
		if(!isEmpty(student.getStu_name())){
			sql += "and stu_name like '%" + student.getStu_name() + "%'";
		}
		if(!isEmpty(student.getClazz())){
			sql += "and clazz like '%" + student.getClazz() + "%'";
		}
		if(!isEmpty(student.getStu_id())){
			sql += "and stu_id like '%" + student.getStu_id() + "%'";
		}
		//sql += " limit " + page.getStart() + "," + page.getPageSize();
		//limit用法  第一个参数指定第一个返回记录行的偏移量，第二个参数指定返回记录行的最大数目。
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));//把sql语句中的第一个and换为where
		try {
			while(resultSet.next()){
				System.out.println(resultSet.getString("stu_name"));
				Student s = new Student();
				s.setStu_id(resultSet.getString("stu_id"));
				s.setClazz(resultSet.getString("clazz"));
				s.setTelephone(resultSet.getString("telephone"));
				s.setStu_name(resultSet.getString("stu_name"));
				s.setIDcard(resultSet.getString("ID_card"));
				s.setPhoto(resultSet.getBinaryStream("photo"));
				s.setDepartment(resultSet.getString("department"));
				s.setSex(resultSet.getString("sex"));
				ret.add(s);//把该学生对象加入list数组
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public int getStudentListTotal(Student student){
		int total = 0;
		String sql = "select count(*)as total from s_student ";
		if(!isEmpty(student.getStu_name())){
			sql += "and stu_name like '%" + student.getStu_name() + "%'";
		}
		if(!isEmpty(student.getClazz())){
			sql += "and clazz like '%" + student.getClazz() + "%'";
		}
		if(!isEmpty(student.getStu_id())){
			sql += "and stu_id like '%" + student.getStu_id() + "%'";
		}
		ResultSet resultSet = query(sql.replaceFirst("and", "where"));
		try {
			while(resultSet.next()){
				total = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	
	
}

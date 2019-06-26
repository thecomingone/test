package com.student.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.student.programmer.domain.Page;
import com.student.programmer.dao.ClazzDao;
import com.student.programmer.dao.StudentDao;
import com.student.programmer.domain.Clazz;
//import com.ischoolbar.programmer.model.Page;
import com.student.programmer.domain.Student;


public class StudentServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toStudentListView".equals(method)){
			studentList(request,response);
		}else if("AddStudent".equals(method)){
			addStudent(request,response);
		}else if("StudentList".equals(method)){
		getStudentList(request,response);
		}else if("EditStudent".equals(method)){
			editStudent(request,response);
		}else if("DeleteStudent".equals(method)){
			deleteStudent(request,response);
		}
	}
	//删除学生信息
	private void deleteStudent(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		//request.getParameterValues(String   name)是获得如checkbox类（名字相同，但值有多个）的数据。   接收数组变量 ，如checkobx类型     
		//request.getParameter（String   name）是获得相应名的数据，如果有重复的名，则返回第一个的值 . 接收一般变量 ，如text类型
	
		String idStr = "";
		for(String id : ids){
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		StudentDao studentDao = new StudentDao();
		if(studentDao.deleteStudent(idStr)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				studentDao.closeCon();
			}
		}
	}
	//修改学生信息
	private void editStudent(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String stuId=request.getParameter("stu_id");
		//System.out.println(stuId);
		StudentDao studentDao = new StudentDao();
		Student student=studentDao.getStudent(stuId);
		String name = request.getParameter("stu_name");
		String sex = request.getParameter("sex");
		String telephone = request.getParameter("telephone");
		String department = request.getParameter("department");
		String IDcard=request.getParameter("IDcard");
		String clazz=request.getParameter("clazz");
		//System.out.println(clazz);
		//System.out.println(student.getClazz());
		student.setClazz(clazz);
		student.setDepartment(department);
		student.setIDcard(IDcard);
		student.setSex(sex);
		student.setStu_name(name);
		student.setTelephone(telephone);
		if(studentDao.editStudent(student)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				studentDao.closeCon();
			}
		}
	}
	//获得学生列表
	
	private void getStudentList(HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("stu_name");
		String clazz=request.getParameter("clazz");	
		Student student = new Student();
		student.setStu_name(name);
		student.setClazz(clazz);
		StudentDao studentDao = new StudentDao();
		List<Student> ret= studentDao.getStudentList(student);
		//int total = studentDao.getStudentListTotal(student);
		studentDao.closeCon();
		response.setCharacterEncoding("UTF-8");
		try {
				response.getWriter().write(JSONArray.fromObject(ret).toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//增加学生
	private void addStudent(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String stu_name = request.getParameter("stu_name");
		String stu_id = request.getParameter("stu_id");
		String sex = request.getParameter("sex");
		String telephone = request.getParameter("telephone");
		String department = request.getParameter("department");
		String clazz = request.getParameter("clazz");
		String IDcard = request.getParameter("IDcard");
		Student student = new Student();
		student.setClazz(clazz);
		student.setDepartment(department);
		student.setIDcard(IDcard);
		student.setSex(sex);
		student.setStu_id(stu_id);
		student.setStu_name(stu_name);
		student.setTelephone(telephone);		
		StudentDao studentDao = new StudentDao();
		if(studentDao.addStudent(student)){
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				studentDao.closeCon();
			}
		}
	}
	
	private void studentList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher("/StudentList.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

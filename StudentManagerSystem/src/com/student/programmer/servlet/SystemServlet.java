package com.student.programmer.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.student.programmer.dao.AdminDao;
import com.student.programmer.domain.Admin;

public class SystemServlet extends HttpServlet {


	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toPersonalView".equals(method)){    //如果请求是访问个人中心页面
			personalView(request,response);
			return;
		}else if("EditPasswod".equals(method)){    //如果请求是修改密码
			editPassword(request,response);
			return;
		}
		try {
			HttpSession session=request.getSession();
			   String id = session.getId();
			   ServletContext context = session.getServletContext();
			   if(context.getAttribute("userCount")==null)
				{
					context.setAttribute("userCount", 0);
				}
			    int count=(Integer) context.getAttribute("userCount");
			   count++;
			  context.setAttribute("userCount", count);
			  System.out.println("用户" + id +"来到系统了！");
			  System.out.println("当前系统在线人数："+count);
			  response.setContentType("text/html;charset=utf-8");//设置客户端的解码方式
			request.getRequestDispatcher("/system.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//修改密码
	private void editPassword(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newpassword");
		response.setCharacterEncoding("UTF-8");
//		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		
			Admin admin = (Admin)request.getSession().getAttribute("user");
			System.out.println(admin.getPassword());
			System.out.println(password);
			if(!admin.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			AdminDao adminDao = new AdminDao();
			//如果更新密码成功
			if(adminDao.editPassword(admin, newPassword)){
				try {
					admin.setPassword(newPassword);
					request.getSession().setAttribute("user", admin);
					response.getWriter().write("editSuccess");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					adminDao.closeCon();
				}
			}else{
				try {
					response.getWriter().write("editFail!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					adminDao.closeCon();
				}
			}		
	}
	//个人信息界面
	private void personalView(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher("/personalView.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

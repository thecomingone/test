package com.student.programmer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.student.programmer.dao.AdminDao;
import com.student.programmer.domain.Admin;



public class LoginServlet extends HttpServlet {
	public LoginServlet() {
		super();
	}
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
//		 try {
//		          //让当前的线程睡眠2秒钟，模拟网络延迟而导致表单重复提交的现象
//			       Thread.sleep(2*1000); 
//		        } 
//		 catch (InterruptedException e) {
//			             e.printStackTrace();
//			    }
		HttpSession session = request.getSession();
		String serverValue = (String) session.getAttribute("token");// 服务端存取的Token
		String clientValue = request.getParameter("token");// 获取客户端的Token
		System.out.println(clientValue);
		System.out.println(serverValue);
		if (serverValue != null && clientValue != null
				&& serverValue.equals(clientValue)) {
			System.out.println("处理用户数据！");
				String vcode=request.getParameter("vcode");//获取客户端的验证码
				String userName=request.getParameter("userName");
				String password=request.getParameter("password");
				String realCode=session.getAttribute("code").toString();//获取服务端存储的验证码对象
				//如果服务器存储的验证码为空
				if(isEmpty(vcode)){
					response.getWriter().write("vcodeError");
					return;
				}
				if(!vcode.toUpperCase().equals(realCode.toUpperCase())){
					response.getWriter().write("vcodeError");
					return;
				}
				
				String loginStatus = "loginFaild";
				AdminDao adminDao = new AdminDao();
				Admin admin = adminDao.login(userName, password);
				adminDao.closeCon();
				if(admin == null){
					response.getWriter().write("loginError");
					return;
				}
				else{
					session.removeAttribute("token");
				    session.setAttribute("user", admin);//将admin对象添加到session中的用户属性user中去
				   // Admin admin2=(Admin)session.getAttribute("user");
				   // System.out.println(admin2.getPassword());
				    loginStatus = "loginSuccess";				    
				    response.getWriter().write(loginStatus);				 

				}
		}
							
//	一种显示方法			<script language='javascript'>
//		alert('用户名或密码错误，请重新输入!!');
//		window.location.href='login.jsp';
//		</script>")				    	 
									
					
		else {
			if (session.getAttribute("user") != null) {
				PrintWriter out = response.getWriter();
				System.out.println("请不要重复提交数据！");
				response.sendRedirect("system.jsp");
			} else {				
				PrintWriter out = response.getWriter();
				System.out.println("请不要重复提交数据！");
				 out.print("<script language='javascript'>alert('请不要重复提交数据!');window.location.href='login.jsp';</script>");
			}
		}

	}
		public static boolean isEmpty(String str) {
			if(str == null || "".equals(str))return true;
			return false;
		}
	public void init() throws ServletException {
		// Put your code here
	}

}

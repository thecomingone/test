package com.student.programmer.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		Object attr = session.getAttribute("user");//用户实体
		String str  = req.getRequestURI();
		System.out.println("url===" + str);
		if(!str.equals("/J2EE1/Captcha") && !str.equals("/J2EE1/Verify") &&!str.equals("/J2EE1/Token")&&!str.equals("/J2EE1/UserInvalidate")&& attr == null  )
		{
			req.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}

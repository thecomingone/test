package com.student.programmer.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CpachaServlet extends HttpServlet {

	private int height=30;
	private int width=100;
	/**
	 * Constructor of the object.
	 */
	public CpachaServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	 doPost(request,response);
	}

	public static int getRandomNum(int max)
	{
		return (int)(Math.random()*max);
	}
	public static char getRandomChar()
	{
		String string="0123456789abcdefghijklmnopqrstuvwxyz";
		int index=getRandomNum(string.length());
		return string.charAt(index);
	}
	public static String drawCode(Graphics g,int num)
     {
		String rs="";
		int p=10;
		Font font=new Font("Times New Roman",Font.BOLD,25);
		for(int i=0;i<num;i++)
		{
			Color c=new Color(getRandomNum(256),getRandomNum(256),getRandomNum(256));
			g.setColor(c);
			g.setFont(font);
			char code=getRandomChar();
			g.drawString(code+"", p, 20);
			p+=20;
			rs+=code;
		}
		return rs;
	}
		public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html;charset=utf-8");
           response.setContentType("img/jpeg");
           BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);//创建图层
           Graphics g=img.getGraphics();//创建画布
           g.setColor(Color.YELLOW);
           g.fillRect(0, 0, width, height);
           g.setColor(Color.blue);
           g.drawRect(1, 1, width-1, height-1);//画边框
           for(int i=0;i<getRandomNum(10);i++)
           {
        	   int x = getRandomNum(width);
        	   int y = getRandomNum(height);
        	   int xl = getRandomNum(30);
        	   int yl = getRandomNum(30);
        	   g.drawLine(x, y, x + xl, y + yl);
           }
           String code=drawCode(g,4);
           HttpSession session=request.getSession();
           System.out.println("Code:"+code);
           session.setAttribute("code", code);
           ImageIO.write(img, "jpg", response.getOutputStream());
	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}

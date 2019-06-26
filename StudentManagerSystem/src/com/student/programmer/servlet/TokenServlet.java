package com.student.programmer.servlet;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.BASE64Encoder;
public class TokenServlet {		
		 private static final long serialVersionUID = -884689940866074733L;		  
	     private static final TokenServlet instance = new TokenServlet();
	     public static TokenServlet getInstance(){
	         return instance;
	     }	     
		 public TokenServlet() {
			super();
		}
	  
			     /*
			      *单例设计模式（保证类的对象在内存中只有一个）
			      *1、把类的构造函数私有
			      *2、自己创建一个类的对象
			      *3、对外提供一个公共的方法，返回类的对象
			     */

		 public String makeToken(){  //checkException
			         //  
			         String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
			         //数据指纹   128位长   16个字节  md5
			        try {
			             MessageDigest md = MessageDigest.getInstance("md5");
			             byte md5[] =  md.digest(token.getBytes());
			            //base64编码--任意二进制编码明文字符   
			             BASE64Encoder encoder = new BASE64Encoder();
			             return encoder.encode(md5);
			         } catch (NoSuchAlgorithmException e) {
			             throw new RuntimeException(e);
			         }
			     }
			 }

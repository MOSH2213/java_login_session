package com.uniqudeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// do post servlet method beins from here
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//uemail named string type variable stores the values that comes from the login jsp file 
		String uemail = request.getParameter("username");
		//upwd named string type variable stores the values that comes from the login jsp file 
		String upwd = request.getParameter("password");
		
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		if(uemail == null || uemail.equals("")){
		    
			request.setAttribute("status","invalidEmail");
		    dispatcher=request.getRequestDispatcher("login.jsp");
		    dispatcher.forward(request,response);
		
		}
		if(upwd == null || upwd.equals("")){
		    
			request.setAttribute("status","invalidUpwd");
		    dispatcher=request.getRequestDispatcher("login.jsp");
		    dispatcher.forward(request,response);
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "#True12345");
			PreparedStatement pst = con.prepareStatement("select * from user where uemail=? and upwd=?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			} else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

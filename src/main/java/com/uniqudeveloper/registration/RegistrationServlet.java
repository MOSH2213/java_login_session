package com.uniqudeveloper.registration;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String reupwd = request.getParameter("re_pass");
		String umobile = request.getParameter("contact");
		
		RequestDispatcher dispatcher = null;
		// prints the data entered to the inputs

//		PrintWriter out = response.getWriter();
//		out.print(uname);
//		out.print(umail);
//		out.print(upwd);
//		out.print(umobile);
		Connection con = null;

		if (uname == null || uname.equals("")) {

			request.setAttribute("status", "invalidname");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);

		}
		if (uemail == null || uemail.equals("")) {

			request.setAttribute("status", "invalidEmail");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);

		}
		//checks the pasword emptiness and reconfirm password match using elseif
		if (upwd == null || upwd.equals("")) {

			request.setAttribute("status", "invalidpwd");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);

		}
		else if(!upwd.equals(reupwd)){
			
			request.setAttribute("status","invalidreupwd");
			dispatcher=request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request,response);
		}
		//checks the maobile number emptinesss and length ecedding 10 
	    if (umobile == null || umobile.equals("")) {

			request.setAttribute("status", "invalidnumber");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);

		}
		else if (umobile.length()>10) {

			request.setAttribute("status", "invalidlength");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);

		}
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "#True12345");
			PreparedStatement pst = con.prepareStatement("insert into user(uname,upwd,uemail,umobile) values(?,?,?,?)");

			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);

			int rowc = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");

			if (rowc > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.jsp.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/mobile")
public class mobile extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String num=req.getParameter("phnum");
		String pass=req.getParameter("pass");
		
		String url="jdbc:mysql://localhost:3307/teca44?user=root&password=1318";
		String quary="select * from bank where mobilenumber=? and Password=?";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(quary);
			
			ps.setString(1, num);
			ps.setString(2, pass);
			
			ResultSet rs = ps.executeQuery();
			PrintWriter pw=resp.getWriter();
			resp.setContentType("text/html");
			
			if (rs.next()) {
				int samt=rs.getInt(5);
				HttpSession session=req.getSession();
				session.setAttribute("samnt", samt);
				session.setAttribute("snum", num);
				session.setAttribute("spass", pass);
				RequestDispatcher red = req.getRequestDispatcher("Mobilenum.html");
				red.include(req, resp);
			} else {
				RequestDispatcher red = req.getRequestDispatcher("mobile.html");
				red.include(req, resp);
				pw.println("<center><h1 style=\"color: red;\" >Inavalid cradentiales</h1></center>");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

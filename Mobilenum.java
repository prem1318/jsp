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
@WebServlet("/Mobilenum")
public class Mobilenum extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String mnum=req.getParameter("mb");
		
		String url="jdbc:mysql://localhost:3307/teca44?user=root&password=1318";
		String quary="select * from bank where mobilenumber=? ";
		
	
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement ps=connection.prepareStatement(quary);
			ps.setString(1, mnum);
			ResultSet rs = ps.executeQuery();
			PrintWriter pw=resp.getWriter();
			resp.setContentType("text/html");
			
			if (rs.next()) {
				int ramt=rs.getInt(5);
				String rname = rs.getString(2);
				HttpSession session=req.getSession();
				session.setAttribute("ramnt", ramt);
				session.setAttribute("rnum", mnum);
				session.setAttribute("rname", rname);
				RequestDispatcher red = req.getRequestDispatcher("mamoun.html");
				red.include(req, resp);
			} else {
				RequestDispatcher red = req.getRequestDispatcher("Mobilenum.html");
				red.include(req, resp);
				pw.println("<center><h1 style=\"color: red;\" >Enter valid mobile num</h1></center>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

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
@WebServlet("/Amount")
public class Amount extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String amt1=req.getParameter("amt");
		int amt=Integer.parseInt(amt1);
		
		HttpSession session=req.getSession();
		int dmt = (int) session.getAttribute("amnt");
	
			PrintWriter pw=resp.getWriter();
			resp.setContentType("text/html");
			String uname = (String) session.getAttribute("nm");
			String password = (String) session.getAttribute("ps");
			if (amt>=0) {
				if (dmt>=amt) {
					int sub=dmt-amt;
					String update="update bank set Amount=? where mobilenumber=? and Password=?";
					String url=(String) session.getAttribute("url");
					
					 
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection connection = DriverManager.getConnection(url);
						PreparedStatement ps=connection.prepareStatement(update);
						ps.setInt(1, sub);
						ps.setString(2, uname);
						ps.setString(3, password);
						int result = ps.executeUpdate();
						
						if (result!=0) {
							RequestDispatcher red = req.getRequestDispatcher("JspBank.html");
							red.include(req, resp);
							pw.println("<center><h1 style=\"color: green;\" >Transaction Sucessfull</h1></center>");
						} else {

						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else {
					RequestDispatcher red = req.getRequestDispatcher("Amount.html");
					red.include(req, resp);
					pw.println("<center><h1 style=\"color: red;\" >Insufficent Fundes</h1></center>");
				}
				
				
			} else {
				RequestDispatcher red = req.getRequestDispatcher("Amount.html");
				red.include(req, resp);
				pw.println("<center><h1 style=\"color: red;\" >Insufficent Fundes</h1></center>");
			}

	}
}

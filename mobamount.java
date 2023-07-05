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
@WebServlet("/mobamount")
public class mobamount extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			String amt1=req.getParameter("amt");
			int amut=Integer.parseInt(amt1);
			
			HttpSession session=req.getSession();
			int samt = (int) session.getAttribute("samnt");
			String snum = (String) session.getAttribute("snum");
			String spass = (String) session.getAttribute("spass");
			String rnum = (String) session.getAttribute("rnum");
			int ramnt = (int) session.getAttribute("ramnt");
			String rname = (String) session.getAttribute("rname");
		
			PrintWriter pw=resp.getWriter();
			resp.setContentType("text/html");
		
	
		
	
			
			if (amut>0) {
				if (samt>=amut) {
					int sub=samt-amut;
					String update="update bank set Amount=? where mobilenumber=? and Password=?";
					String url="jdbc:mysql://localhost:3307/teca44?user=root&password=1318";
					
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection connection=DriverManager.getConnection(url);
						PreparedStatement ps=connection.prepareStatement(update);
						ps.setInt(1, sub);
						ps.setString(2, snum);
						ps.setString(3, spass);
						int result = ps.executeUpdate();
						
						if (result!=0) {
							int uamt=amut+ramnt;
							
							String update1="update bank set Amount=? where mobilenumber=?";
							
							PreparedStatement ps1=connection.prepareStatement(update1);
							ps1.setInt(1,uamt );
							ps1.setString(2,rnum);
							int result1 = ps1.executeUpdate();
							
							if (result!=0) {
							RequestDispatcher red = req.getRequestDispatcher("JspBank.html");
							red.include(req, resp);
							pw.println("<center><h1 style=\"color: green;\" >Transaction Successfull</h1></center>");
							pw.println("<center><h1 style=\"color: blue;\" >Name : "+rname+"</h1></center>");
							String stnum=rnum.substring(0, 2);
							String lnum=rnum.substring(7, 10);
							String fnum=stnum+"*****"+lnum;
							pw.println("<center><h1 style=\"color: blue;\" >Mobile Number : "+fnum+"</h1></center>");
							pw.println("<center><h1 style=\"color: blue;\" >Amount : "+amut+"</h1></center>");
							} else {
								RequestDispatcher red = req.getRequestDispatcher("mamoun.html");
								red.include(req, resp);
								pw.println("<center><h1 style=\"color: red;\" >Server Busy</h1></center>");
							}
							
						}else {
							RequestDispatcher red = req.getRequestDispatcher("mamoun.html");
							red.include(req, resp);
							pw.println("<center><h1 style=\"color: red;\" >Server Busy</h1></center>");
						}
						
						
		
						
					
				}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				}
				else {
					RequestDispatcher red = req.getRequestDispatcher("mamoun.html");
					red.include(req, resp);
					pw.println("<center><h1 style=\"color: red;\" >Insufficent amount</h1></center>");
				}
			}
			else {
				RequestDispatcher red = req.getRequestDispatcher("mamoun.html");
				red.include(req, resp);
				pw.println("<center><h1 style=\"color: red;\" >Please enter the correct amount</h1></center>");
			}
				
	}
}

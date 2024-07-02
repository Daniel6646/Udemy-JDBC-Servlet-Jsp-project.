package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.cj.protocol.Resultset;
import com.mysql.jdbc.Statement;


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet implements Servlet {
	
	private static final long serialVersionUID = 1L;

	//link from context.xml to connect to our student table db
	@Resource(name = "jdbc/web_student_tracker")
	public DataSource datasource;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//Step 1.Setup print writer	
	PrintWriter out =response.getWriter();
	response.setContentType("text/xml");	
	
	//Step 2.Set up a connection to database	
	Connection myCon = null;
	java.sql.Statement stmt = null;
	ResultSet rs = null;
	
	//Step 3.Create a sql statement.
	try {
		myCon = datasource.getConnection();
		String sql = "select * from student";
		
		stmt = myCon.createStatement();
		rs=  stmt.executeQuery(sql);
		
		while(rs.next()) {
			
		String email =	rs.getString("email");
		System.out.println("Email values from database:: "+email);
		
		}
	}
	
	catch (Exception e) {
		e.printStackTrace();
	}

	
	
	
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	

}

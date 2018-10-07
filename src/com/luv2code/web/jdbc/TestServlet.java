package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/web_student_tracker")
	
	private DataSource dataSource;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Setup the printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		//Connect to database
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
	
			myConn = dataSource.getConnection();
			
			//Create SQL statement
			
			
			myStmt = myConn.createStatement();
			
			//Execute SQL query
			String sql = "select * from student";
			myRs= myStmt.executeQuery(sql);
			
			//Process the resultset
			
		while (myRs.next()) {
			
			Integer id  = myRs.getInt("id");
			String first_name = myRs.getString("first_name");
			String last_name = myRs.getString("last_name");
			String email= myRs.getString("email");
		
			
			out.println(id+"  "+first_name+"  "+last_name+"  "+email );
			
			
		}
			
}

	catch (Exception exc){
		exc.printStackTrace();
	}
	}
}
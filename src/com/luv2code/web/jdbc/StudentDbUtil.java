package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource datasource;

	public StudentDbUtil(DataSource thedatasource) {
		 datasource=thedatasource;
	}
	
	public List<Student> getStudents() throws Exception{
		
		List<Student> students = new ArrayList<Student>();
		
		Connection myConn = null;
		Statement myStmt = null; 
		ResultSet myRs = null;
		
		try {
			
			myConn = datasource.getConnection();
			String sql = "select * from student";
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery(sql);
			while(myRs.next()) {
				
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_Name");
				String lastName = myRs.getString("last_Name");
				String email = myRs.getString("email");
				
				Student tempStudent = new Student (id, firstName, lastName, email);
				students.add(tempStudent);
			}
		return students;
	}
		finally {
			close (myConn,myStmt, myRs);
		}
}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if(myRs!=null) {
				myRs.close();
			}
			if(myStmt!=null) {
				myStmt.close();
			}
			if(myConn!=null) {
				myConn.close();					
			}
		}catch (Exception excp) {
			excp.printStackTrace();
		}
		
	}

	public void addStudent(Student theStudent) throws SQLException {
		
		Connection myConn=null;
		PreparedStatement myStmt = null;
		 try {
			 
			 myConn = datasource.getConnection();
			 String sql = "insert into student "+ "(first_name, last_name, email)"+"values (?,?,?)";
			 
			 myStmt = myConn.prepareStatement(sql);
			 
			 myStmt.setString(1, theStudent.getFirstName());
			 myStmt.setString(2, theStudent.getLastName());
			 myStmt.setString(3, theStudent.getEmail());
			 
			 myStmt.execute();
		 
		 }
		finally {
			close (myConn,myStmt,null);
		}
		
		
	}
}


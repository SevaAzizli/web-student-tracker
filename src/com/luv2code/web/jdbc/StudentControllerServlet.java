package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// list the students ... in mvc fashion
		try {
			
			String thecommand = request.getParameter("command");
			
			if(thecommand ==null) {
				thecommand = "LIST";
			}
			switch (thecommand) {
			
			case "LIST":
				listStudents(request, response);
				break;
				
			case "ADD":
				 addStudent (request, response);
				 break;
				 
				 default:
					 listStudents(request, response); 
			
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}			
		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		
		Student theStudent = new Student(firstName, lastName, email);
		  
		studentDbUtil.addStudent(theStudent);
		
		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// get students from db util
		List<Student> students = studentDbUtil.getStudents();
		
		// add students to the request
		request.setAttribute("STUDENT_LIST", students);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-student.jsp");
		dispatcher.forward(request, response);
	}

}














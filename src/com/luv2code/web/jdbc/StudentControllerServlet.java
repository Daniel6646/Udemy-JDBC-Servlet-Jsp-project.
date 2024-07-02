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



@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private StudentDBUtil studentDBUtil;
	
	//link from context.xml to connect to our student table db
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;


	//first mnethod called by servlet, when servlet is first loaded 
	//or initialized
	@Override
	public void init() throws ServletException {

		super.init();
		
		try {
	//create db util and pass in connection pool/datasource		
			studentDBUtil = new StudentDBUtil(dataSource);
			
		
		}
		
		catch(Exception e) {
			throw new ServletException();

		}
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
		
			//read command parameter
			String theCommand = request.getParameter("command");
			System.out.println("The command is "+theCommand);
			//if command is missing, defaut get back to lisitng students
			if(theCommand == null) {
				theCommand ="LIST";
			}
			
			//route to appropriate method
			switch (theCommand) {
		
			case "LIST": {
				
				listStudent(request,response);
				break;
			}
			
			case "ADD": {
				
				addStudent(request,response);
				break;
			}
			
			case "LOAD": {
				
				loadStudent(request,response);
				break;
			}
			
			
			case "UPDATE":{
				updateStudent(request, response);
				break;
			}
			
			case "DELETE": {
				deleteStudent(request, response);
				break;
			}
				
			default:
				listStudent(request,response);
			}
			
		}
		
		catch (Exception e) {

			e.printStackTrace();
			throw new ServletException();
		}
		
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			// read student id from form data
			String theStudentId = request.getParameter("studentId");
			
			// delete student from database
			studentDBUtil.deleteStudent(theStudentId);
			
			// send them back to "list students" page
			listStudent(request, response); 
		}
	
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			// read student info from form data
			int id = Integer.parseInt(request.getParameter("studentId"));
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			
			// create a new student object
			Student theStudent = new Student(id, firstName, lastName, email);
			
			// perform update on database
			studentDBUtil.updateStudent(theStudent);
			
			// send them back to the "list students" page
			listStudent(request, response);
			
		}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//read student id from form data
		String theStudentId = request.getParameter("studentId");
		System.out.println("loadStudent() student id"+theStudentId);
		theStudentId = theStudentId.trim();
		// get stduent from database (dbutil)
		Student theStudent =  studentDBUtil.getStudent(theStudentId);
		System.out.println("loadStudent() student values"+theStudent);
		//place student in request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		//send to jsp pageupdate-student-form.jsp
		RequestDispatcher requestDispatcher =
				request.getRequestDispatcher("/update-student-form.jsp");
		requestDispatcher.forward(request, response);
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

	//read info from form data
	
	String firstName = request.getParameter("firstName");	
	String lastName = request.getParameter("lastName");
	String email = request.getParameter("email");
	
	//create a new student object
	Student student = new  Student(firstName, lastName, email);
	
	//add student to databsae
	studentDBUtil.addStudent(student);	
	
	// send backto main page( the student list)
	listStudent(request, response);
		
	}

	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//get student from db utils
	List<Student> studentList = studentDBUtil.getStudents();
	
	//add student to request
	request.setAttribute("STUDENT_LIST", studentList);
		
	// send to jsp page
	RequestDispatcher requestDispatcher = request.getRequestDispatcher("/list-students.jsp");
	requestDispatcher.forward(request, response);
	
	}
	
	
	
	
	
}

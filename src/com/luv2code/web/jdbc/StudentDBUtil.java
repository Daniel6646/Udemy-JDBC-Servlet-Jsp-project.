package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxConnection.Close;

public class StudentDBUtil {

	private DataSource dataSource;

	public StudentDBUtil(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	
	public void deleteStudent(String theStudentId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// convert student id to int
			int studentId = Integer.parseInt(theStudentId);
			
			// get connection to database
			myConn = dataSource.getConnection();
			
			// create sql to delete student
			String sql = "delete from student where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setInt(1, studentId);
			
			// execute sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}	
	}
	
	public List<Student> getStudents() throws Exception {
		
		List<Student> studentsList = new ArrayList<>();
		
		
		Connection myCon = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
 
	
		try {
			
			//get connection
			myCon =	dataSource.getConnection();
			
			//create sql statement
			String sql  = "select * from student order by last_name";
			
			stmt = myCon.createStatement();
			
			//execute query
			rs=  stmt.executeQuery(sql);
			
			while (rs.next()) {
				
			int id = rs.getInt("id");
			String firstName = rs.getString("first_name");
			String last_name = rs.getString("last_name");
			String email = rs.getString("email");

			Student student = new Student(id, firstName, last_name, email);
			
			studentsList.add(student);
			
			}
			
		}
		
		catch (Exception e) {

			e.printStackTrace();
		}
		
		finally {
			
			close(myCon,stmt,rs); 
			
		}
		return studentsList;
	}


	private void close(Connection myCon, Statement stmt, ResultSet rs) {

		if(myCon != null) {
			
			myCon = null;
		}
		
			if(stmt != null) {
						
			stmt = null;
		}
			
			if(rs != null) {
				
			rs = null;
		}
	}


	public void addStudent(Student student) {

	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try {
	//create sql for insert
	conn = dataSource.getConnection();
		
	//set param value for student
	String sql = "Insert into student(first_name,last_name,email) values(?,?,?)";	
	pstmt = conn.prepareStatement(sql);
	
	pstmt.setString(1, student.getFirstName());
	pstmt.setString(2, student.getLastName());
	pstmt.setString(3, student.getEmail());

	// execute sql insert
	pstmt.execute();
	
	}
	
	catch (Exception e) {
		e.printStackTrace();
   }
	
	finally {
	
		// clean the jdbc code
		close(conn, pstmt, null);
	}
	
		
	}


	public Student getStudent(String theStudentId) throws Exception {

		Student theStudent = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int studentId;
		
		try {
			System.out.println("first line after load inside getstudent()");
			// convert student id into int
			System.out.println("studentid after firstline after load"+theStudentId+"id");
			studentId = Integer.parseInt(theStudentId);
			System.out.println("Studrntid in studentdbutil getstudent "+studentId);
			//get conn to database
			conn = dataSource.getConnection();
			
			//create sql to get student
			String sql = "select * from student where id = ?";
			
			//create preparedstatement
			pstmt = conn.prepareStatement(sql);
			System.out.println("pstmt getstudent "+sql);
			//set params
			pstmt.setInt(1, studentId);
			
			//execute statement
			rs = pstmt.executeQuery();
			System.out.println("rs getStudent() "+rs);
			//retrieve data from resultset
			if(rs.next()) {
				
			String firstName = 	rs.getString("first_name");
			String lastName = 	rs.getString("last_name");
			String email = 	rs.getString("email");

				theStudent = new Student(studentId, firstName, lastName, email);
			}
			else {
				
				throw new Exception("Could not find student id"+studentId);
			}
			
			
			
		}
		
		catch (Exception e) {
			e.printStackTrace();		}

		finally {
			
			close(conn, pstmt, rs);
		}
		return theStudent;
	}

	
	public void updateStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update student "
						+ "set first_name=?, last_name=?, email=? "
						+ "where id=?";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}
	
	
}

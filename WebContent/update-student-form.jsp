<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>

<title>Update Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
<link type="text/css" rel="stylesheet" href="css/add-student-style.css">

</head>

<body>

	<div id="wrapper">
		<div id="header">

			<h2>NorthEastern University</h2>

		</div>
	</div>

	<div id="container">

		<h3>Update Student</h3>

		<form action="StudentControllerServlet" method="GET">

			<input type="hidden" name="command" value="UPDATE">
			
			<!-- need to track student id to update and send it over to servlet which will update value of object based on id -->
			<input type="hidden" name="studentId" value="${THE_STUDENT.id}">
			

			<table>

				<tbody>
				
					<tr>
						<td>First Name</td>
						<td><input type="text" name="firstName" 
						value="${THE_STUDENT.firstName}" ></td>
					</tr>

					<tr>
						<td>Last Name</td>
						<td><input type="text" name="lastName" 
						value="${THE_STUDENT.lastName}" ></td>
					</tr>
					
					<tr>
						<td>Email</td>
						<td><input type="text" name="email" 
						value="${THE_STUDENT.email}" ></td>
					</tr>
					
					
						<tr>
						<td><input type="submit" value="Save" class="save"></td>
					</tr>

				</tbody>

			</table>

		</form>
		
		<div style="clear:both">  </div>
		
		<p>
		<a href="StudentControllerServlet"> Back to Homepage </a>
		 </p>
		
	</div>

</body>
</html>
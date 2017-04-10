<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Login | Chatroom</title>
</head>
<body>
<h1>
	Welcome to chatroom!  
</h1>

<p>Insert your nickname and password to log in.</p>
<p style="color: red;">
	<% 
		String error = request.getParameter("error");
		if (error == null){
			error = "";
		}
	%>
		<%= error %>
</p>

<form:form method="POST" modelAttribute="user">
	<table>
	
		<tr>
			<td><label for="nickname">Nickname:</label></td>
			<td><form:input path="nickname" id="nickname" /></td>
			<td><form:errors path="nickname" cssClass="nickname" /></td>
		</tr>
		<tr>
			<td><label for="password">Password:</label></td>
			<td><form:input type="password" path="password" id="password" /></td>
			<td><form:errors path="password" cssClass="password" />
		</tr>
		<tr>
			<td><br><input type="submit" value="Log in"/></td>
		</tr>
			
		

	</table>
</form:form>



</body>
</html>

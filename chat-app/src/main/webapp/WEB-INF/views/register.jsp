<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Register | Chatroom</title>
</head>
<body>
<h1>
	Register yourself to the chatroom!  
</h1>

<p>Insert your nickname and password twice to register.</p>
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
			<td><label for="password">Re-Password:</label></td>
			<td><form:input type="password" path="repassword" id="repassword" /></td>
			<td><form:errors path="repassword" cssClass="repassword" />
		</tr>
		<tr>
			<td><br><input type="submit" value="Register"/></td>
		</tr>

	</table>
	
	
</form:form>
<button onclick="location.href = '/';" id="myButton">Back to home</button>

</body>
</html>

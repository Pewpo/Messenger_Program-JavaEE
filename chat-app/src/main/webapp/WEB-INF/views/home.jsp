<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Welcome!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<p>Insert your nickname to log in</p>
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
			<td><form:errors path="password" cssClass="password" /></td>
			<td><input type="submit" value="Log in"/></td>
		</tr>

	</table>
</form:form>



</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Chat | Chatroom</title>
	<script src="jquery-3.2.0.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		   $('#messagesTextArea').scrollTop($('#messagesTextArea')[0].scrollHeight);
		});


</script>
</head>
<body>
<h1>
	This is a chatroom!
</h1>

<p>  Messages: <br> <textarea id="messagesTextArea" rows="30" cols="200">${messages}</textarea></p>
<form:form method="POST" modelAttribute="message">
	<table>
		<tr>
			<td><label for="mes">Message:</label></td>
			<td><form:input path="mes" id="mes" /></td>
			<td><form:errors path="mes" cssClass="mes" /></td>
			<td><input type="submit" value="Send"/></td>
		</tr>

	</table>
</form:form>


</body>
</html>

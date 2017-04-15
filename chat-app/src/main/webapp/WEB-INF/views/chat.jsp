<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<%@ page import="fi.jamk.chatapp.User" %>
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
<% String username = (String)request.getSession().getAttribute("username");
   String currentlyChatting = "";
   if (request.getSession().getAttribute("currentChat") == null){
		currentlyChatting = "";
   }else{
	   currentlyChatting = (String)request.getSession().getAttribute("currentChat");
   }
%>
   
<h1>
	This is a chatroom!
</h1>

<p>Logged in as: <b><%= username %></b></p><form:form method="POST" modelAttribute="user"><input type="submit" name="action" value="Log out"/></form:form>

<div >
	<div style="display:inline-block">
		<p>Currently chatting with: <b><%= currentlyChatting %></b>
		<p>Messages: <br> <textarea id="messagesTextArea" rows="30" cols="100">${messages}</textarea></p>
	</div>	
	<div style="display:inline-block; vertical-align: top; margin-left: 20px;">
	<p>Select a person to chat with.</p>
	<% String selected = ""; %>
		<form:form method="POST" modelAttribute="user">
			<select name="user">
				<c:forEach var="username" items="${users}">
					<% 
						String currentChat = (String)request.getSession().getAttribute("currentChat");
						if (currentChat != null){	
							User user = (User) pageContext.getAttribute("username");
							System.out.println("currentchat: " + currentChat + " user nickname: " + user.getNickname());
							if (currentChat.intern() == user.getNickname().intern()){
								System.out.println("taas täällä");
								selected = "selected";
							}else{
								selected = "";
							}
						}
					%>
				  		<option <%=selected %> value="${username}">${username}</option>	
				</c:forEach>
			</select>
			<input type="submit" name="action" value="Chat"/>
		</form:form>
	</div>
</div>

<form:form method="POST" modelAttribute="message">
	<table>
		<tr>
			<td><label for="mes">Message:</label></td>
			<td><form:input path="mes" id="mes" /></td>
			<td><form:errors path="mes" cssClass="mes" /></td>
			<td><input type="submit" name="action" value="Send"/></td>
		</tr>

	</table>
</form:form>


</body>
</html>

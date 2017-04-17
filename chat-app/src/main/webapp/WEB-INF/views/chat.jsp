<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<%@ page import="fi.jamk.chatapp.User" %>
<html>
<head>
	<title>Chat | Chatroom</title>
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript">
		$(document).ready(function(){
		   $('#messagesTextArea').scrollTop($('#messagesTextArea')[0].scrollHeight);
		});
		
		$(function () {
		    $("#mestextarea").keypress(function (e) {
		        var code = (e.keyCode ? e.keyCode : e.which);
		        if (code == 13 && !e.shiftKey) {
		        	e.preventDefault();
		        	$("#submitSend").click();
		            return true;
		        }
		    });
		});
		
		function init(){
		    document.getElementById("mestextarea").focus();
		}
	
		var idleTime = 0;
		$(document).ready(function () {
		    //Increment the idle time counter every minute.
		    var idleInterval = setInterval(timerIncrement, 1000); // 1 minute

		    //Zero the idle timer on mouse movement.
		    $(this).mousemove(function (e) {
		        idleTime = 0;
		    });
		    $(this).keypress(function (e) {
		        idleTime = 0;
		    });
		});

		function timerIncrement() {
		    idleTime = idleTime + 1;
		    if (idleTime > 3) { // 20 minutes
				$('#selectChat').trigger('click');
		    }
		}

</script>
</head>
<body onload="init()">
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

<% 
		String error = request.getParameter("error");
		String status = request.getParameter("status");
		if (error == null){
			error = "";
		}
		if (status == null){
			status = "";
		}
	%>
<p style="color: red;">
		<%= error %>
</p>

<p>Logged in as: <b><%= username %></b></p><form:form method="POST" modelAttribute="user"><input type="submit" name="action" value="Log out"/></form:form>

<div >
	<div style="display:inline-block">
		<p>Currently chatting with: <b><%= currentlyChatting %></b>
		<p>Messages: <br> <textarea readonly id="messagesTextArea" rows="30" cols="100">${messages}</textarea></p>
	</div>	
	<div style="display:inline-block; vertical-align: top; margin-left: 20px;">
	<p>Select a person to chat with.</p>
	<% String selected = ""; %>
		<form:form method="POST" modelAttribute="user">
			<select name="user">
				<option value=""></option>	
				<c:forEach var="username" items="${users}">
					<% 
						String currentChat = (String)request.getSession().getAttribute("currentChat");
						if (currentChat != null){	
							User user = (User) pageContext.getAttribute("username");
							if (currentChat.intern() == user.getNickname().intern()){
								selected = "selected";
							}else{
								selected = "";
							}
						}
					%>
				  		<option <%=selected %> value="${username}">${username}</option>	
				</c:forEach>
			</select>
			<input type="submit" id="selectChat" name="action" value="Chat"/>
		</form:form>
	</div>
</div>

<form:form id="mesform" method="POST" modelAttribute="message">
	<table>
		<tr>
			<td><label for="mes">Message:</label></td>
		</tr>
			
		<tr>
			<td><form:textarea rows="5" cols="100" path="mes" id="mestextarea" /></td>
			<td><form:errors path="mes" cssClass="mes" /></td>
			<td><input type="submit" id="submitSend" name="action" value="Send"/></td>
		</tr>

	</table>
</form:form>


</body>
</html>

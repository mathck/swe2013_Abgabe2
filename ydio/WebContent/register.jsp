
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="ydio.UserManagement" %>
<%! UserManagement um;%>
<%! boolean login = false; %>
<% if(session.getAttribute("status") !=null &&  session.getAttribute("status").equals("logged in")){ %>
<% um = (UserManagement)session.getAttribute("um");%>
<% if(um.isSessionActive()){ %>
<% login = true; %>
<% } %>
<% } %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<title>ydio - your data is ours</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>


<!-- HEADER START -->
<div id="header">
<ul>
	<li><a href="UIController?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px;" height="50px" src="img/logo.png" alt="logo" /></a></li>
	<% if(login){ %><li style="float: right; padding-right: 20px;">Logout (<%= um.getSession().getUsername() %>)</li><% } %>
</ul>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<% if(request.getAttribute("error") !=null){ %>
<h2><%= request.getAttribute("error") %></h2>
<%	} %>
<h1>Willkommen auf ydio!</h1>
<h2>Melde dich jetzt noch an und gib uns deine Daten.</h2>
<form action="UIController" method="post">
	<table>
	<tr>
	<td width="200px">Username</td>
	<td><input type="text" name="username"></td>
	</tr>
	<tr>
	<td width="200px">Name</td>
	<td><input type="text" name="fullname"></td>
	</tr>
	<tr>
	<td>Passwort</td>
	<td><input type="password" name="password"></td>
	</tr>
	<tr>
	<td>E-Mail</td>
	<td><input type="email" name="email"></td>
	</tr>
	<tr>
	<input type="radio" name="sex" value="m"/>Männlich
	<input type="radio" name="sex" value="f"/>Weiblich
	</tr>
	<tr>
	<td>Geburtstag(yyyy-MM-dd)</td>
	<td><input type="text" name="date"></td>
	</tr>
	<tr>
	<td>Deine Beschreibung</td>
	<td><textarea name="desc" rows="4" cols="30"></textarea></td>
	</tr>
	<tr>
	 <input type="hidden" name="gewuenschteSeite" value="completeRegistration">
	 <td><input type="submit" value="Registrieren"/><br></td>
	 </tr>
	</table>
</form>
</div>
</div>
<!-- CONTENT OVER -->



<!-- FOOTER START -->
<div id="footer">
	<div id="footer_wrapper">
		<a href="https://github.com/mathck/swe2013_Abgabe2">Github link</a> | 
		SWE Abgabe 2, 2013
	</div>
</div>
<!-- FOOTER OVER -->


</body>
</html>
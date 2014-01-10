

    
<%@ page import="javax.servlet.http.HttpServletRequest" %>
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
<table><tr>
<td><a href="Uicontroller?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px;" height="50px" src="img/logo.png" alt="logo" /></a></td>
<td><input type="search" size="50" name="search"></td>
<% if(login){ %>
<td><a href="Uicontroller?gewuenschteSeite=userpage"><%= um.getSession().getUsername() %></a></td>
<td><a href="Uicontroller?gewuenschteSeite=logout">Logout</a></td>
</tr>
<% } %></table>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<% if(request.getAttribute("error") !=null){ %>
<h2><%= request.getAttribute("error") %></h2>
<%} %>
<h1>Log dich jetzt ein!</h1>
<form action="Uicontroller" method="post">
	<table>
	<tr>
	<td width="200px">Username</td>
	<td><input type="text" name="username"></td>
	</tr>
	<tr>
	<tr>
	<td>Passwort</td>
	<td><input type="password" name="password"></td>
	</tr>
	<tr>
	 <td></td>
	 <input type="hidden" name="gewuenschteSeite" value="verifylogin">
	  <td><input type="submit" value="Login"><br></td>
	 </tr>
	 <tr><td><br /></td></tr>
	 <tr>
	 <td><a href="http://sahws2013.cs.univie.ac.at:8080/ydio/Uicontroller?gewuenschteSeite=register">Registriere dich jetzt</a></td>
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
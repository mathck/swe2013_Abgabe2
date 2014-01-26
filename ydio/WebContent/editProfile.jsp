<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
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
<ul>
	<li><a href="UIController?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px; padding-right: 50px;" height="50px" src="img/logo.png" alt="logo" /></a></li>
	<li>
		<form id="searchbox" action="UIController" method="post">
			<input type="search" size="30" name="searchstring" style="height: 45px; font-size: 22px;" placeholder="search by username ...">
			<input type="hidden" name="gewuenschteSeite" value="search">
			<input style="width: 100px; height: 45px; font-size: 22px; color: #333333;" type="submit" value="search">
		</form>
	</li>
	<% if(login){ %><li style="float: right; padding-right: 30px; font-size: 22px;"><a href=UIController?gewuenschteSeite=logout> Logout (<%= um.getSession().getUsername() %>)</a></li><% } %>
</ul>
</div>
<!-- HEADER OVER -->

<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<% if(request.getAttribute("error") !=null){ %>
<h2><font color="#FF0000"><%= request.getAttribute("error") %></font></h2>
<%} %>
<h1>Hier kannst du deine Profildaten ändern!</h1>
<form action="UIController" method="post">
	<table>
	<tr>
	<td width="200px">Fullname</td>
	<td><input type="text" name="fullname"></td>
	</tr>
	<tr>
	<tr>
	<td>E-Mail</td>
	<td><input type="text" name="email"></td>
	</tr>
	<tr>
	<td>Birthday(yyyy-MM-dd)</td>
	<td><input type="text" name="date"></td>
	</tr>
	<tr>
	<td>Description</td>
	<td><input type="text" name="desc"></td>
	</tr>
	<tr>
	 <td></td>
	 <input type="hidden" name="gewuenschteSeite" value="editProfile">
	 <input type="hidden" name="action" value="process">
	  <td><input type="submit" value="Änderungen abschicken"><br></td>
	 </tr>
	 <tr><td><br /></td></tr>
	 
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
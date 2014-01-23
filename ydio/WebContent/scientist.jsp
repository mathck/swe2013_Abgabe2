<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="ydio.UserManagement" %>
<%@ page import="ydio.Beitrag" %>
<%@ page import="java.util.List" %>
<%@ page import="ydio.user.Ydiot"%>
<%@ page import="ydio.user.Forscher"%>
<%! UserManagement um;%>
<%! boolean login = false; %>
<%! int number = 0; %>
<%! Forscher target = null; %>
<% 
if(session.getAttribute("status") !=null &&  session.getAttribute("status").equals("logged in")){
  um = (UserManagement)session.getAttribute("um");
  if(um.isSessionActive()){
    login = true;
    target = (Forscher) um.getTarget();
  } 
} 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>ydio - <%= session.getAttribute("username") %></title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

<!-- HEADER START -->
<div id="header">
<ul>
	<li><a href="UIController?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px; padding-right: 50px;" height="50px" src="img/logo.png" alt="logo" /></a></li>
	<li>
	</li>
	<% if(login){ %><li style="float: right; padding-right: 30px; font-size: 22px;"><a href=UIController?gewuenschteSeite=logout> Logout (<%= um.getSession().getUsername() %>)</a></li><% } %>
</ul>
</div>
<!-- HEADER OVER -->

<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<% if(request.getAttribute("error") !=null){ %>
<h2><%= request.getAttribute("error") %></h2>
<% } %>
<h1><%=  target.getUsername()  %></h1>

<div class="box">

<table>
<tr>
<td width="200px">Username</td>
<td><%=  target.getUsername()  %></td>
</tr>
<tr>
<td width="200px">Fullname</td>
<td><%=  target.getFullName()  %></td>
</tr>
<tr>
<td>E-Mail</td>
<td><%= target.getEMail()  %></td>
</tr>
<tr>
<td>Birthday</td>
<td><%=  target.getBirthday()  %></td>
</tr>
<tr>

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

</center>
</body>
</html>

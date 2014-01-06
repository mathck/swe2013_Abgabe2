<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>ydio - <%= session.getAttribute("username") %></title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>


<!-- HEADER START -->
<div id="header">
<table style="width:100%"><tr>
<td width="10px"><a href="Uicontroller?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px;" height="50px" src="img/logo.png" alt="logo" /></a></td>
<td><input type="search" size="50" name="search"></td>
<% if(session.getAttribute("status") !=null && session.getAttribute("status").equals("logged in")){ %>
<td align="center"><a href="Uicontroller?gewuenschteSeite=userpage"><%= session.getAttribute("username") %></a></td>
<td align="center"><a href="Uicontroller?gewuenschteSeite=logout">Logout</a></td>
</tr>
<%	session.setAttribute("error",null); 
} %>
</table>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<% if(session.getAttribute("error") !=null){ %>
<h2><%= session.getAttribute("error") %></h2>
<% } %>
<h1><%= session.getAttribute("username") %></h1>
<table>
<tr>
<td width="200px">Username</td>
<td><%= session.getAttribute("username") %></td>
</tr>
<tr>
<td width="200px">Fullname</td>
<td><%= session.getAttribute("fullname") %></td>
</tr>
<tr>
<td>E-Mail</td>
<td><%= session.getAttribute("email") %></td>
</tr>
<tr>
<td>Geburtstag</td>
<td><%= session.getAttribute("date") %></td>
</tr>
<% if (request.getParameter("desc") != null) {
        out.println("<tr><td>Beschreibung</td><td>" + request.getParameter("desc") + "</td></tr>");
    }
%>
</table>

<% int number=3;
	for(int i=1;i<=number;i++)
	{
%>
<div class="beitrag">TODO insert all beitrags like this</div>
<%} %>

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
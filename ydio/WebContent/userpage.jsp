<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>ydio - TODO username</title>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>


<!-- HEADER START -->
<div id="header">
<table><tr>
<td><a href="Uicontroller?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px;" height="50px" src="img/logo.png" alt="logo" /></a></td>
<td><input type="search" size="50" name="search"></td>
<td><a href="Uicontroller?gewuenschteSeite=userpage"><%= request.getParameter("username") %></a></td>
</tr></table>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
<h1>TODO username</h1>
<table>
<tr>
<td width="200px">Username</td>
<td><%= request.getParameter("username") %></td>
</tr>
<tr>
<td width="200px">Fullname</td>
<td><%= request.getParameter("fullname") %></td>
</tr>
<tr>
<td>E-Mail</td>
<td><%= request.getParameter("email") %></td>
</tr>
<tr>
<td>Geburtstag</td>
<td>Datum</td>
</tr>
<tr>
<td>Beschreibung</td>
<td><%= request.getParameter("desc") %></td>
</tr>
</table>

<div class="beitrag">
TODO insert all beitrags like this
</div>

<div class="beitrag">
TODO insert all beitrags like this
</div>

</div>
<!-- CONTENT OVER -->



<!-- FOOTER START -->
<div id="footer">
<a href="https://github.com/mathck/swe2013_Abgabe2">Github link</a> | 
SWE Abgabe 2, 2013
</div>
<!-- FOOTER OVER -->

</center>
</body>
</html>
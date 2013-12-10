<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="java.util.Vector" %>
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
<td><a href="index.html"><img style="float: left; padding-left: 20px;" height="50px" src="img/logo.png" alt="logo" /></a></td>
<td><input type="search" size="50" name="search"></td>
<td><a href="userpage.html">my username</a></td>
</tr></table>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
<h1>Willkommen auf ydio!</h1>
<h2>Melde dich jetzt noch an und gib uns deine Daten.</h2>
<table>
<tr>
<td width="200px">Username</td>
<td><input type="text" name="username"></td>
</tr>
<tr>
<td width="200px">Vorname</td>
<td><input type="text" name="firstName"></td>
</tr>
<tr>
<td width="200px">Nachname</td>
<td><input type="text" name="secondName"></td>
</tr>
<tr>
<td>Passwort</td>
<td><input type="password" name="password"></td>
</tr>
<tr>
<td>Passwort nochmal</td>
<td><input type="password" name="password"></td>
</tr>
<tr>
<td>E-Mail</td>
<td><input type="email" name="email"></td>
</tr>
<tr>
<td>Geburtstag</td>
<td><input type="date" name="date"></td>
</tr>
<tr>
<td>Deine Beschreibung</td>
<td><textarea rows="4" cols="30"></textarea></td>
</tr>
</table>
</div>
<!-- CONTENT OVER -->



<!-- FOOTER START -->
<div id="footer">
<a href="https://github.com/mathck/swe2013_Abgabe2">Github link</a> | 
SWE Abgabe 2, 2013
</div>
<!-- FOOTER OVER -->


</body>
</html>
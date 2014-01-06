

    
<%@ page import="javax.servlet.http.HttpServletRequest" %>
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
<td><a href="Uicontroller?gewuenschteSeite=userpage"><%= session.getAttribute("username") %></a></td>
</tr></table>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
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
	 <td><a href="Uicontroller?gewuenschteSeite=register">Registriere dich jetzt</a></td>
	 <input type="hidden" name="gewuenschteSeite" value="verifylogin">
	  <td><input type="submit" value="Login"/><br></td>
	 </tr>
	 
	</table>
</form>
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
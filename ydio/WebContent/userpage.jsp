<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ydio.UserManagement" %>
<%@ page import="ydio.Beitrag" %>
<%@ page import="java.util.List" %>
<%! UserManagement um;%>
<%! boolean login = false; %>
<%! List<Beitrag> list; %>
<%! int number = 0; %>
<% if(session.getAttribute("status") !=null &&  session.getAttribute("status").equals("logged in")){ %>
<% um = (UserManagement)session.getAttribute("um");%>
<% if(um.isSessionActive()){ %>
<% login = true; %>
<% list = um.getBeitragListByUsername(um.getSession().getUsername());%>
<% } %>
<% } %>
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
<% if(login){ %>
<td align="center"><a href="Uicontroller?gewuenschteSeite=userpage"><%= um.getSession().getUsername() %></a></td>
<td align="center"><a href="Uicontroller?gewuenschteSeite=logout">Logout</a></td>
<% } %>
</tr>
</table>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<% if(request.getAttribute("error") !=null){ %>
<h2><%= request.getAttribute("error") %></h2>
<% } %>
<h1><%=  um.getSession().getUsername()  %></h1>
<table>
<tr>
<td width="200px">Username</td>
<td><%=  um.getSession().getUsername()  %></td>
</tr>
<tr>
<td width="200px">Fullname</td>
<td><%=  um.getSession().getFullName()  %></td>
</tr>
<tr>
<td>E-Mail</td>
<td><%= um.getSession().getEMail()  %></td>
</tr>
<tr>
<td>Geburtstag</td>
<td><%=  um.getSession().getBirthday()  %></td>
</tr>
<% if (request.getParameter("desc") != null) {
        out.println("<tr><td>Beschreibung</td><td>" + request.getParameter("desc") + "</td></tr>");
    }
%>
</table>

<% number = list.size(); %>
<%= number %>
<% for(int i = 0; i < number; i++){%>
<div class="beitrag"><%= list.get(i).getContent() %></div>
<%} %>

<form action="Uicontroller" method="post">
	<table>
	<tr>
	<tr>
	<tr>
	<td width="200px" height="50px">Inhalt</td>
	<td><input type="text" name="content"></td>
	</tr>
	<tr>
	<tr>
	 <td></td>
	 <input type="hidden" name="gewuenschteSeite" value="addBeitrag">
	  <td><input type="submit" value="Veröffentlichen"><br></td>
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

</center>
</body>
</html>
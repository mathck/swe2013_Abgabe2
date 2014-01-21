<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="ydio.UserManagement" %>
<%@ page import="ydio.user.Ydiot" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%! UserManagement um;%>
<%! boolean login = false; %>
<%! List<Ydiot> resultlist = new ArrayList<Ydiot>(); %>
<% if(session.getAttribute("status") != null &&  session.getAttribute("status").equals("logged in")){ %>
<% um = (UserManagement)session.getAttribute("um");%>
<% if(um.isSessionActive()){ %>
<% login = true; %>
<% resultlist = um.search((String)request.getAttribute("searchstring")); %>
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
	<li>
		<form id="searchbox" action="UIController" method="post">
			<input type="search" size="50" name="searchstring">
			<input type="hidden" name="gewuenschteSeite" value="search">
			<input style="width: 100px;" type="submit" value="search">
		</form>
	</li>
	<% if(login){ %><li style="float: right; padding-right: 20px;">Logout (<%= um.getSession().getUsername() %>)</li><% } %>
</ul>
</div>
<!-- HEADER OVER -->

<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<% if(request.getAttribute("error") !=null){ %>
<h2><%= request.getAttribute("error") %></h2>
<%	}  %>
<h1>Das sind deine Suchergebnisse: <%resultlist.size();%></h1>
<% for(int i = 0; i < resultlist.size(); i++){%>
<a href="UIController?gewuenschteSeite=userpage&target=<%= resultlist.get(i).getUsername() %>"><%= resultlist.get(i).getUsername() %></a>
<%}   %>

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

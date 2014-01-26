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
<%! List<String[]> ydiotenliste = null; %>
<%! List<String[]> beitragliste = null; %>
<% 
if(session.getAttribute("status") !=null &&  session.getAttribute("status").equals("logged in")){
  um = (UserManagement)session.getAttribute("um");
  if(um.isSessionActive()){
    login = true;
    target = (Forscher) um.getTarget();
  }
ydiotenliste = um.getScientistData("ydiot");
beitragliste = um.getScientistData("beitrag");
} 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>ydio - <%= target.getUsername() %></title>
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
<h2><font color="#FF0000"><%= request.getAttribute("error") %></font></h2>
<% } %>
<h1>Statistik Seite f&uuml;r: <%=  target.getUsername()  %></h1>

<div class="box">
<h2>Beitrag-Statistiken</h2>
<table name="beitragstats">
  <th>
    <td>Datum</td>
    <td>Verfasser</td>
    <td>Inhalt</td>
    <td>Likes</td>
    <td>Dislikes</td>
    <td>Reported</td>
    <td>Gelesen</td>
  </th>
  <%for(int i = 0; i< beitragliste.size(); i++){ %>
  <% String[] data = beitragliste.get(i); %>
  <tr>
  	<td>
    <td><%= data[0] %></td>
    <td><%= data[1] %></td>
    <td><%= data[2] %></td>
    <td><%= data[3] %></td>
    <td><%= data[4] %></td>
    <td><%= data[5] %></td>
    <td><%= data[6] %></td>
  </tr>
<% }%>
</table>

<h2>Ydioten-Statistiken</h2>
<table name="ydiotenstats">
  <th>
    <td>Geburtstag</td>
    <td>Geschlecht</td>
    <td>Erstellte Beitr&auml;ge</td>
    <td>von Anderen gelesen</td>
    <td>Likes erhalten</td>
    <td>Dislikes erhalten</td>
    <td>Reports erhalten</td>
    <td>Beitr&auml;ge gelesen</td>
    <td>Like vergeben</td>
    <td>Dislike vergeben</td>
    <td>Reported</td>
  </th>
  <%for(int i = 0; i< ydiotenliste.size(); i++){ %>
  <% String[] data = ydiotenliste.get(i); %>
  <tr>
  	<td>
    <td><%= data[0] %></td>
    <td><%= data[1] %></td>
    <td><%= data[2] %></td>
    <td><%= data[3] %></td>
    <td><%= data[4] %></td>
    <td><%= data[5] %></td>
    <td><%= data[6] %></td>
    <td><%= data[7] %></td>
    <td><%= data[8] %></td>
    <td><%= data[9] %></td>
    <td><%= data[10] %></td>
  </tr>
<% } %>
</table>

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

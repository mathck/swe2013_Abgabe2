<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ydio.UserManagement" %>
<%@ page import="ydio.Beitrag" %>
<%@ page import="java.util.List" %>
<%@ page import="ydio.user.Ydiot"%>
<%! UserManagement um;%>
<%! boolean login = false; %>
<%! List<Beitrag> list; %>
<%! int number = 0; %>
<%! Ydiot target = null; %>
<% if(session.getAttribute("status") !=null &&  session.getAttribute("status").equals("logged in")){ %>
<% um = (UserManagement)session.getAttribute("um");%>
<% if(um.isSessionActive()){ %>
<% login = true; %>
<% target = (Ydiot) um.getTarget(); %>
<% list = um.getBeitragListByUsername(target.getUsername());%>
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
<td width="10px"><a href="UIController?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px;" height="50px" src="img/logo.png" alt="logo" /></a></td>
<td>
<form id="searchbox" action="UIController" method="post">
  <input type="search" size="50" name="searchstring"></td>
  <input type="hidden" name="gewuenschteSeite" value="search">
  <input type="submit" value="go">
</form>
</td>
<% if(login){ %>
<td align="center"><a href="UIController?gewuenschteSeite=userpage"><%= um.getSession().getUsername() %></a></td>
<td align="center"><a href="UIController?gewuenschteSeite=logout">Logout</a></td>
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
<h1><%=  target.getUsername()  %></h1>
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
<td>Friends:</td>
<% String friends = "";
	for(int i = 0; i<target.getFriendList().size(); i++){
		friends += target.getFriendList().get(i) + " ";
	}
%>
<td><%=  friends  %></td>
</tr>
<% if (request.getParameter("desc") != null) {
        out.println("<tr><td>Beschreibung</td><td>" + request.getParameter("desc") + "</td></tr>");
    }
%>
<%if(!target.getUsername().equals(um.getSession().getUsername())){ %>
<form action="UIController" method="post">
<input type="hidden" name="gewuenschteSeite" value="addFriend">
<input type="submit" value="Add to your Friendlist">  
</form>
<%} %>
</table>	


<!-- Button zum Nutzerdaten �ndern -->
<%if(target.getUsername().equals(um.getSession().getUsername())){ %>
<a href="UIController?gewuenschteSeite=editProfile&action=open"><button type="button">Nutzerdaten �ndern</button></a>
<%} %>


<% number = list.size(); %>
<%= number %>
<% for(int i = 0; i < number; i++){%>
<div class="beitrag"><%= list.get(i).getContent() %><br />
<%=list.get(i).getCountLikes() %> Likes | <%=list.get(i).getCountDislike() %> Dislikes<br />
<a href="UIController?gewuenschteSeite=rateBeitrag&action=like&id=<%=list.get(i).getID()%>">Like</a> | <a href="#">Dislike</a> | <a href="#">Report</a>
</div>
<%} %>

<form action="UIController" method="post">
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
	  <td><input type="submit" value="Ver�ffentlichen"><br></td>
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

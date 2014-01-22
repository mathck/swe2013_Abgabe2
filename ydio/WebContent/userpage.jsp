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

</div>



<!-- Button zum Nutzerdaten ändern -->
<%if(target.getUsername().equals(um.getSession().getUsername())){ %>
<div class="box"><a href="UIController?gewuenschteSeite=editProfile&action=open"><button type="button">Nutzerdaten ändern</button></a></div>
<%} %>

<div class="box">
<form action="UIController" method="post">
	<table>
	<tr>
	
	<td><textarea style="font-size: 18px; padding: 10px;" rows=2 cols=50 name="content" placeholder="Neuer Beitrag ..."></textarea></td>
	 <input type="hidden" name="gewuenschteSeite" value="addBeitrag">
	 </tr><tr>
	  <td style="padding-top: 10px; padding-left: 3px;"><input style="width: 100px; height: 36px; font-size: 20px; color: #333333;" type="submit" value="senden"><br></td>
	</table>
	</tr>
</form>
</div>

<% number = list.size(); %>
<% for(int i = number-1; i >= 0; i--){%>
<div class="beitrag">
<b><%=list.get(i).getCreator() %></b><br /><br />
<%= list.get(i).getContent() %><br /><br />
<hr>
<p align="right" style="margin-bottom: 0px; padding-right: 15px;">
<%=list.get(i).getCountLikes() %> <a href="UIController?gewuenschteSeite=rateBeitrag&action=like&id=<%=list.get(i).getID()%>">Like</a> | <%=list.get(i).getCountDislike() %> <a href="UIController?gewuenschteSeite=rateBeitrag&action=dislike&id=<%=list.get(i).getID()%>">Dislike</a> | <a href="UIController?gewuenschteSeite=reportBeitrag&id=<%=list.get(i).getID()%>">Report</a> | <%= list.get(i).getReadList().size() %> gelesen
</p>
</div>
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

<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ydio.UserManagement" %>
<%@ page import="ydio.Beitrag" %>
<%@ page import="java.util.List" %>
<%@ page import="ydio.user.Ydiot"%>
<%@ page import="ydio.user.AbstractUser" %>
<%@ page import="ydio.user.Administrator" %>
<%! UserManagement um;%>
<%! boolean login = false; %>
<%! List<Beitrag> list; %>
<%! int number = 0; %>
<%! AbstractUser target = null; %>
<% 
if(session.getAttribute("status") !=null &&  session.getAttribute("status").equals("logged in"))
{ 
  um = (UserManagement)session.getAttribute("um");
  if(um.isSessionActive())
  { 
    login = true; 
    target = um.getTarget(); 
  }
  list = um.getBeitragList(true); 
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
<h1>Verwaltungsseite von: <%=  target.getUsername()  %></h1>
<h2> Liste b&ouml;ser Beitr&auml;ge </h2>
<table name="reported_beitrag">
  <tr style="font-weight:bold">
    <td>Username</td>
    <td>Beitrag</td>
    <td>Datum</td>
    <td>Beitrag l&ouml;schen</td>
    <td>User 1 Tag sperren</td>
    <td>Reporting entfernen</td>
    <td></td>
  </tr>
<% for(int i = 0; i < list.size(); ++i){ %>
  <tr>
    <form method="post" action="UIController">
      <td><%= list.get(i).getCreator() %></td>
      <td><%= list.get(i).getContent() %></td>  
      <td><%= list.get(i).getDate().toString() %></td>  
      <td><input type="checkbox" name="deleteID" value="<%= list.get(i).getID() %>" /> L&ouml;schen</td>  
      <td><input type="checkbox" name="lockUser" value="<%= list.get(i).getCreator() %>" /> Sperren</td>  
      <td><input type="checkbox" name="notbad" value="<%= list.get(i).getID() %>" /> Nicht B&ouml;se</td>  
      <input type="hidden" name="gewuenschteSeite" value="handleReport" />
      <td><input type="submit" value="Los" /></td>
    </form>
<% } %>
</table>

<p></p>

<div name="lock_user">
  <table>
    <form name="lock_user" method="post" action="UIController">
    <tr>  
      <td style="font-weight:bold">User sperren:</td> 
      <td><input type="text" name="username" placeholder="Username eingeben..." /></td><td>
      <input type="text" name="lockdate" placeholder="Sperrdatum (yyyy-MM-dd2)"></td>
      <td><input type="submit" value="Sperren"></td>
    </tr>
    <input type="hidden" name="gewuenschteSeite" value="lockUserByDate">
    </form>

    <form name="unlock_user" method="post" action="UIController">
    <tr>  
      <td style="font-weight:bold">User entsperren:</td>
      <td><input type="text" name="username" placeholder="Username eingeben..." /></td>
      
      <td><input type="submit" value="Entsperren"></td>
    </tr>
    <input type="hidden" name="gewuenschteSeite" value="unlockUser">
    </form>
  </table>
</div>

<% if(target instanceof Administrator) { %>
<form name="create_admin" method="post" action="UIController">
<h3> Erstelle einen neuen Admin </h3>
  <table name="create_admin">
    <tr>
      <td width="200px">Username</td>
      <td><input type="text" name="username"></td>
    </tr>
    <tr>
      <td width="200px">Name</td>
      <td><input type="text" name="fullname"></td>
    </tr>
    <tr>
      <td>Passwort</td>
      <td><input type="password" name="password"></td>
    </tr>
    <tr>
      <td>E-Mail</td>
      <td><input type="email" name="email"></td>
    </tr>
    <tr>
      <td><input type="radio" name="sex" value="m"/>M&auml;nnlich
      <input type="radio" name="sex" value="f"/>Weiblich</td>
    </tr>
    <tr>
      <td>Geburtstag(yyyy-MM-dd)</td>
      <td><input type="text" name="date"></td>
    </tr>
    <tr>
      <td><input type="submit" value="Erstellen"></td>
    </tr>
  </table>
  <input type="hidden" name="gewuenschteSeite" value="registerAdmin" />
</form>

<form name="create_mod" method="post" action="UIController">
<h3> Erstelle einen neuen Moderator</h3>
  <table name="create_mod">
    <tr>
      <td width="200px">Username</td>
      <td><input type="text" name="username"></td>
    </tr>
    <tr>
      <td width="200px">Name</td>
      <td><input type="text" name="fullname"></td>
    </tr>
    <tr>
      <td>Passwort</td>
      <td><input type="password" name="password"></td>
    </tr>
    <tr>
      <td>E-Mail</td>
      <td><input type="email" name="email"></td>
    </tr>
    <tr>
      <td><input type="radio" name="sex" value="m"/>M&auml;nnlich
      <input type="radio" name="sex" value="f"/>Weiblich</td>
    </tr>
    <tr>
      <td>Geburtstag(yyyy-MM-dd)</td>
      <td><input type="text" name="date"></td>
    </tr>
    <tr>
      <td><input type="submit" value="Erstellen"></td>
    </tr>
  </table>
  <input type="hidden" name="gewuenschteSeite" value="registerModerator" />
</form>

<form name="create_scientist" method="post" action="UIController">
<h3> Erstelle einen neuen Forscher</h3>
  <table name="create_scientist">
    <tr>
      <td width="200px">Username</td>
      <td><input type="text" name="username"></td>
    </tr>
    <tr>
      <td width="200px">Name</td>
      <td><input type="text" name="fullname"></td>
    </tr>
    <tr>
      <td>Passwort</td>
      <td><input type="password" name="password"></td>
    </tr>
    <tr>
      <td>E-Mail</td>
      <td><input type="email" name="email"></td>
    </tr>
    <tr>
      <td><input type="radio" name="sex" value="m"/>M&auml;nnlich
      <input type="radio" name="sex" value="f"/>Weiblich</td>
    </tr>
    <tr>
      <td>Geburtstag(yyyy-MM-dd)</td>
      <td><input type="text" name="date"></td>
    </tr>
    <tr>
      <td><input type="submit" value="Erstellen"></td>
    </tr>
  </table>
  <input type="hidden" name="gewuenschteSeite" value="registerScientist" />
</form>
<% } %>

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

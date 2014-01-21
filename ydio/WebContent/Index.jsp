

    

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
<td><a href="userpage.html">my username</a></td>
</tr></table>
</div>
<div id="header">
<ul>
	<li><a href="UIController?gewuenschteSeite=userpage"><img style="float: left; padding-left: 20px;" height="50px" src="img/logo.png" alt="logo" /></a></li>
</ul>
</div>
<!-- HEADER OVER -->



<!-- CONTENT START -->
<div id="content">
<div id="content_wrapper">
<h1>Willkommen auf ydio!</h1>
<h2>Melde dich jetzt noch an und gib uns deine Daten.</h2>
<form action="Uicontroller" method="post">
	<input type="hidden" name="action" value="register">
	<table>
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
	<td>Geburtstag</td>
	<td><input type="date" name="date"></td>
	</tr>
	<tr>
	<td>Deine Beschreibung</td>
	<td><textarea name="desc" rows="4" cols="30"></textarea></td>
	</tr>
	<tr>
	 <td><input type="submit" value="submit"/><br></td>
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

</body>
</html>
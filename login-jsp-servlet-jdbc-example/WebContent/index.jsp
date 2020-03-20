<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Language list retrieving</title>
</head>
<body>
	<div align="center">
		<h1>Just Login Form</h1>
		<form action="<%=request.getContextPath()%>/login" method="post">
			<table style="with: 100%">
				<tr>
					<td>UserName</td>
					<td><input type="text" name="username" /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" /></td>
				</tr>
				<tr>
					<td>Language name</td>
					<td><input type="text" name="languagename" /></td>
				</tr>

			</table>
			<input type="submit" value="Submit" />
		</form>
	</div>
</body>
</html>
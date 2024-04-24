<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Map"%>
<%@ page import="proto.Column"%>
<%@ page import="proto.TableColumn"%>
<%@ page import="proto.TableData"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<link
	href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@1,600&family=Merriweather&family=Roboto+Slab:wght@600;700&family=Roboto:wght@700&family=Young+Serif&display=swap"
	rel="stylesheet">
<title>Login</title>
<style>
body {
	font-family: 'Young Serif', serif;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

form {
	text-align: center;
	width: 400px;
	padding: 28px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

label {
	display: block;
	margin-top: 10px;
	text-align: left;
	font-size: 17px;
}

input, button {
	font-family: 'Young Serif', serif;
	width: 100%;
	padding: 10px;
	margin: 10px 0;
	box-sizing: border-box;
	border: 1px solid #ccc;
	border-radius: 3px;
	font-size: 17px;
}

button {
	color: black;
	cursor: pointer;
	font-family: 'Young Serif', serif;
	font-size: 22px;
}

h2 {
	margin-bottom: 6%;
	font-size: 30px;
}

a {
	font-size: 20px;
}

.logout-btn {
	background-color: #f44336;
	color: white;
	padding: 10px 15px;
	border: none;
	border-radius: 3px;
	cursor: pointer;
}
</style>
</head>
<body>
	<form action="login" method="post">
		<h2>Login</h2>
		<label for="username">UserName: <input type="text"
			id="username" name="username" required placeholder="Enter your name"></label><br>
		<label for="password">Password: <input type="password"
			id="password" name="password" required
			placeholder="Enter your password"> <span toggle="#password"
			class="eye-toggle far fa-eye"></span>
		</label><br>
		<button type="submit">Sign In</button>
		Not a member?<a href="signup.jsp">Sign Up</a>
	</form>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@1,600&family=Merriweather&family=Roboto+Slab:wght@600;700&family=Roboto:wght@700&family=Young+Serif&display=swap"
	rel="stylesheet">
<title>User SignUp</title>
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
	width: 430px;
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
	color: brown;
	font-weight: bold;
	cursor: pointer;
	font-family: 'Young Serif', serif;
}

h2 {
	margin-bottom: 6%;
	font-size: 30px;
}
p{
    text-align: justify;
   
}
a {
	font-size: 20px;
}
</style>
</head>
<body>
	<form action="signup" method="Post">
	<h2>Sign Up</h2>
    <label>User Name</label><input type="text" name="username" required placeholder="Enter your name"><br>
    <label>First Name</label><input type="text" name="firstName" required placeholder="Enter your firstname"><br>
    <label>Last Name</label><input type="text" name="lastName" required placeholder="Enter your lastname"><br>
    <label>Date of Birth</label><input type="date" name="dob" placeholder="YYYY-MM-DD" required><br>
    <label>Email Address</label><input type="email" name="email" required placeholder="Enter your email"><br>
    <label>Password</label><input type="password" name="password" required placeholder="Enter your password"><br>
    <input type="password" name="confirmPassword" required placeholder="Confirm your password"><br>
    <br>
 <button type="submit">SIGN UP</button>
        Already a member?<a href="login.jsp">Login instead.</a>
</form>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://fonts.googleapis.com/css2?family=Lora:ital,wght@1,600&family=Merriweather&family=Roboto+Slab:wght@600;700&family=Roboto:wght@700&family=Young+Serif&display=swap"
	rel="stylesheet">
<title>User Update</title>
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
	width: 376px;
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

input {
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
}

h2 {
	margin-bottom: 18%;
	font-size: 30px;
}

a {
	font-size: 20px;
	text-decoration: none;
	color: black;
}
</style>
</head>
<body>
	<form action="userProfile" method="post">
		<h2>Update User Information</h2>
		<label for="userName">User Name:</label> <input type="text"
			id="userName" name="userName" required><br>
		<label for="firstName">First Name:</label> <input type="text"
			id="firstName" name="firstName" required><br> <label
			for="lastName">Last Name:</label> <input type="text" id="lastName"
			name="lastName" required><br> <label for="dob">Date
			of Birth:</label> <input type="date" id="dob" name="dob" required><br>
		<label for="email">Email Address:</label><input type="email"
			name="email" required><br>
		<button value="UpdateUser">Update</button>

		<button>
			<a href="homepage.jsp">Cancel</a>
		</button>
	</form>

</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/AddEmailServlet" method="post">
        <label for="username">UserName:</label>
        <input type="text" id="username" name="username" required>
        <br>
        <label for="emailAddress">Email Address:</label>
        <input type="email" id="emailAddress" name="emailAddress" required>
        <br>
        <input type="submit" value="Add Email">
    </form>
</body>
</html>
<!-- Handling the email primary account to add email -->
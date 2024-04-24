<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Delete User</title>
</head>
<body>
    <h2>Delete User</h2>
    <form action="deleteuser" method="post">
        <label for="username">UserName:</label>
        <input type="text" id="username" name="username" required>
        <button type="submit">Delete User</button>
    </form>
</body>
</html>

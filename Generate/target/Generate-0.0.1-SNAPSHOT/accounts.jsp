<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Account</title>
</head>
<body>
    <h1>User Account</h1>
<%-- 
    <% 
        User user = (User)request.getAttribute("user");
        if (user != null) {
    %>
    <p>Welcome, <%= user.getUsername() %>!</p>
    <p>Email: <%= user.getEmail() %></p>
    <!-- Add more user details as needed -->
    <% } else { %>
    <p>No user details available.</p>
    <% } %> --%>


    <!-- Profile Information -->
    <div>
        <h2>Profile</h2>
        <h3>Personal Information</h3>
        <ul>
            <li>Full Name: Madhu m</li>
            <li>Display Name: Madhu</li>
            <!-- Add more personal information items as needed -->
        </ul>

        <h3>Email Addresses</h3>
        <ul>
            <li>madhumathi.murthy+t1@zohotest.com (Added 2 months ago)</li>
            <!-- Add more email addresses as needed -->
        </ul>

        <!-- Add other profile information sections as needed -->
    </div>
</body>
</html>

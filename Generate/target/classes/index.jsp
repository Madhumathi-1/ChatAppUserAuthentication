<%@page import="com.connectiondb.SessionUtils"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
</head>
<body>

    <% 
        String username = (String) session.getAttribute("Username");

        if (username != null) {
            response.sendRedirect("homepage.jsp");
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Username")) {
                    username = cookie.getValue();
                    System.out.println("Index page (cookie): " + username);
                    response.sendRedirect("userprofile.jsp");
                    return;
                }
            }
        }
    %>
</body>
</html>

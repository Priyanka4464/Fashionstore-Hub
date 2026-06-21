<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login</title>

<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/admin-login.css">

</head>

<body>

<div class="login-container">

    <div class="logo">
        👔
    </div>

    <h2>Admin Login</h2>

    <% if(request.getParameter("error") != null){ %>
        <p class="error">
            Invalid Username or Password
        </p>
    <% } %>

    <form action="<%=request.getContextPath()%>/AdminLoginServlet"
          method="post">

        <div class="form-group">
            <label for="username">Username</label>

            <input type="text"
                   id="username"
                   name="username"
                   placeholder="Enter Username"
                   required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>

            <input type="password"
                   id="password"
                   name="password"
                   placeholder="Enter Password"
                   required>
        </div>

        <button type="submit" class="login-btn">
            Login
        </button>

    </form>

</div>

</body>
</html>
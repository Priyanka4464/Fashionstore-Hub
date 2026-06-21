<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login - Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <h2>Fashion Store</h2>
    <div class="nav-links">
        <a href="home">Home</a>
        <a href="products">Products</a>
        <a href="register">Register</a>
    </div>
</div>

<!-- LOGIN CARD -->
<div class="auth-wrapper">
    <div class="auth-card">

        <h2>👋 Welcome Back</h2>
        <p class="subtitle">Login to your Fashion Store account</p>

        <%-- Success message after registration --%>
        <c:if test="${param.registered == 'true'}">
            <div class="alert-success">
                ✅ Registration successful! Please login.
            </div>
        </c:if>

        <%-- Error message --%>
        <c:if test="${not empty error}">
            <div class="alert-error">
                ⚠️ ${error}
            </div>
        </c:if>

        <form action="login" method="post">

            <div class="form-group">
                <label>Email Address</label>
                <input type="email"
                       name="email"
                       placeholder="Enter your email"
                       value="${param.email}"
                       required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password"
                       name="password"
                       placeholder="Enter your password"
                       required>
            </div>

            <button type="submit" class="btn-primary">
                Login →
            </button>

        </form>

        <div class="divider">or</div>

        <div class="auth-footer">
            Don't have an account?
            <a href="register">Register here</a>
        </div>

    </div>
</div>

</body>
</html>
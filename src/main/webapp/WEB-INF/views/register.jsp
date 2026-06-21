<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register - Fashion Store</title>
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
        <a href="login">Login</a>
    </div>
</div>

<!-- REGISTER CARD -->
<div class="auth-wrapper">
    <div class="auth-card">

        <h2> Create Account</h2>
        <p class="subtitle">Join Fashion Store today</p>

        <c:if test="${not empty error}">
            <div class="alert-error">⚠️ ${error}</div>
        </c:if>

        <form action="register" method="post">

            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="name"
                       placeholder="Enter your full name"
                       value="${param.name}" required>
            </div>

            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email"
                       placeholder="Enter your email"
                       value="${param.email}" required>
            </div>

            <div class="form-group">
                <label>Phone Number</label>
                <input type="tel" name="phone"
                       placeholder="Enter your phone number"
                       value="${param.phone}">
            </div>

            <!-- GENDER -->
            <div class="form-group">
                <label>Gender</label>
                <select name="gender" class="form-select" required>
                    <option value="" disabled selected>Select gender</option>
                    <option value="Male"   ${param.gender == 'Male'   ? 'selected' : ''}>Male</option>
                    <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other"  ${param.gender == 'Other'  ? 'selected' : ''}>Other</option>
                </select>
            </div>

            <!-- ADDRESS -->
            <div class="form-group">
                <label>Address</label>
                <textarea name="address"
                          placeholder="Enter your address"
                          rows="3"
                          class="form-textarea">${param.address}</textarea>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password"
                       placeholder="Create a password" required>
            </div>

            <div class="form-group">
                <label>Confirm Password</label>
                <input type="password" name="confirmPassword"
                       placeholder="Confirm your password" required>
            </div>

            <button type="submit" class="btn-primary">
                Create Account →
            </button>

        </form>

        <div class="divider">or</div>

        <div class="auth-footer">
            Already have an account?
            <a href="login">Login here</a>
        </div>

    </div>
</div>

</body>
</html>
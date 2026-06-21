<%@ page contentType="text/html;charset=UTF-8" %>

<footer class="footer">

    <div class="footer-container">

        <div class="footer-section">
            <h2>Fashion Store</h2>
            <p>Discover trendy fashion for Men, Women & Kids.</p>
        </div>

        <div class="footer-section">
            <h3>Quick Links</h3>
            <a href="<%= request.getContextPath() %>/home">Home</a>
            <a href="<%= request.getContextPath() %>/products">Products</a>
            <a href="<%= request.getContextPath() %>/cart">Cart</a>
            <a href="<%= request.getContextPath() %>/login">Login</a>
        </div>

        <div class="footer-section">
            <h3>Support</h3>
            <p>Email: support@fashionstore.com</p>
            <p>Phone: +91 9876543210</p>
        </div>

    </div>

    <div class="footer-bottom">
        © 2026 Fashion Store | All Rights Reserved
    </div>

</footer>
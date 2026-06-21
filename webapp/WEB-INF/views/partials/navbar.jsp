<%@ page contentType="text/html;charset=UTF-8" %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">

<header class="navbar">

    <div class="logo">
        <a href="<%= request.getContextPath() %>/home">Fashion Store</a>
    </div>

    <form class="search-bar" action="<%= request.getContextPath() %>/products" method="get">
        <input type="text" name="keyword" placeholder="Search products..." />
        <button type="submit">Search</button>
    </form>

    <nav class="nav-links">
        <a href="<%= request.getContextPath() %>/home">Home</a>
        <a href="<%= request.getContextPath() %>/products">Products</a>
        <a href="<%= request.getContextPath() %>/cart">Cart</a>
        <a href="<%= request.getContextPath() %>/login">Login</a>
        
    </nav>

</header>
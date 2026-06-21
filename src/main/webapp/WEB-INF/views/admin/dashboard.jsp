<%
if(session.getAttribute("admin")==null){
    response.sendRedirect("login.jsp");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
</head>
<body>

<h1>Admin Dashboard</h1>

<h3>Welcome
<%=session.getAttribute("admin")%>
</h3>

<hr>

<a href="products.jsp">
Manage Products
</a>

<br><br>

<a href="orders.jsp">
Manage Orders
</a>

<br><br>

<a href="customers.jsp">
Manage Customers
</a>

<br><br>

<a href="../adminLogout">
Logout
</a>

</body>
</html>
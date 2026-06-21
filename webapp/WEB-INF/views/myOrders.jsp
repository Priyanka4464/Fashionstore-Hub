<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Orders - Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/myOrders.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <h2>Fashion Store</h2>
    <div class="nav-links">
        <a href="home">Home</a>
        <a href="products">Products</a>
        <a href="cart">Cart</a>
        <a href="myOrders" style="color:#3498db;"> Orders</a>
        <c:choose>
            <c:when test="${not empty sessionScope.userName}">
                <span style="font-weight:600; color:#3498db;">👤 ${sessionScope.userName}</span>
                <a href="logout" style="color:#e74c3c;">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="login">Login</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="container">

    <div class="page-title">📋 My Orders</div>

    <c:choose>
        <c:when test="${empty orders}">
            <!-- EMPTY STATE -->
            <div class="empty-orders">
                <div class="icon">📦</div>
                <h3>No orders yet!</h3>
                <p>You haven't placed any orders. Start shopping now!</p>
                <a href="products" class="btn-shop">🛍️ Shop Now</a>
            </div>
        </c:when>

        <c:otherwise>
            <c:forEach var="order" items="${orders}">
                <div class="order-card">

                    <!-- ORDER HEADER -->
                    <div class="order-header">
                        <div>
                            <div class="order-number">Order # ${order.orderNumber}</div>
                            
                            <div class="order-date">
                                Placed on: <fmt:formatDate value="${order.createdAt}"
                                                           pattern="dd MMM yyyy, hh:mm a"/>
                            </div>
                        </div>
                        <div style="display:flex; align-items:center; gap:10px;">
                            <span class="payment-badge">${order.paymentMethod}</span>
                            <span class="status-badge status-${order.orderStatus}">
                                ${order.orderStatus}
                            </span>
                        </div>
                    </div>

                    <!-- ORDER ITEMS -->
                    <div class="order-items">
                        <c:forEach var="item" items="${order.orderItemsList}">
                            <div class="order-item-row">
                                <div>
                                    <div class="item-name">${item.productName}</div>
                                    <div class="item-qty">Qty: ${item.quantity}</div>
                                </div>
                                <div class="item-subtotal">
                                    ₹ <fmt:formatNumber value="${item.subtotal}" maxFractionDigits="2"/>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- ORDER FOOTER -->
                    <div class="order-footer">
    <div class="order-meta">
        📍 ${order.city}, ${order.state}
    </div>
    <div style="display:flex; align-items:center; gap:15px;">
        <div class="order-total">
            Total: ₹ <fmt:formatNumber value="${order.finalAmount}" maxFractionDigits="2"/>
        </div>
        <!-- CANCEL BUTTON - only show if order is PLACED -->
        <c:if test="${order.orderStatus == 'PLACED'}">
            <form action="cancelOrder" method="post" style="margin:0;"
                  onsubmit="return confirm('Are you sure you want to cancel this order?')">
                <input type="hidden" name="orderId" value="${order.orderId}">
                <button type="submit" class="btn-cancel">❌ Cancel Order</button>
            </form>
        </c:if>
    </div>
</div>

                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>

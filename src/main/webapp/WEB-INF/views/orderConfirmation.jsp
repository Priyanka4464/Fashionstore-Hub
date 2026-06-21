<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation - Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .confirmation-card {
            background: white;
            border-radius: 20px;
            padding: 40px;
            max-width: 600px;
            width: 100%;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            text-align: center;
        }

        .success-icon {
            width: 80px;
            height: 80px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 20px;
            font-size: 40px;
            color: white;
        }

        h1 {
            font-size: 28px;
            color: #2c3e50;
            margin-bottom: 10px;
        }

        .subtitle {
            font-size: 14px;
            color: #999;
            margin-bottom: 30px;
        }

        .order-info {
            background: #f8f9fa;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;
            text-align: left;
        }

        .order-info h3 {
            font-size: 16px;
            color: #3498db;
            margin-bottom: 15px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-size: 14px;
        }

        .info-row strong {
            color: #2c3e50;
        }

        .order-items {
            background: #f8f9fa;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;
            text-align: left;
        }

        .order-items h3 {
            font-size: 16px;
            color: #3498db;
            margin-bottom: 15px;
        }

        .item {
            display: flex;
            justify-content: space-between;
            padding: 8px 0;
            border-bottom: 1px solid #eee;
            font-size: 14px;
        }

        .item:last-child { border-bottom: none; }

        .total {
            display: flex;
            justify-content: space-between;
            font-size: 20px;
            font-weight: 700;
            color: #27ae60;
            margin-top: 15px;
            padding-top: 15px;
            border-top: 2px solid #ddd;
        }

        .delivery-address {
            background: #f8f9fa;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;
            text-align: left;
        }

        .delivery-address h3 {
            font-size: 16px;
            color: #3498db;
            margin-bottom: 10px;
        }

        .delivery-address p {
            font-size: 14px;
            color: #666;
            line-height: 1.6;
        }

        .actions {
            display: flex;
            gap: 15px;
            justify-content: center;
        }

        .btn {
            padding: 12px 30px;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 600;
            font-size: 14px;
            transition: transform 0.2s;
            display: inline-block;
        }

        .btn:hover { transform: translateY(-2px); }

        .btn-primary {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
        }

        .btn-secondary {
            background: white;
            color: #3498db;
            border: 2px solid #3498db;
        }
    </style>
</head>
<body>

<div class="confirmation-card">

    <div class="success-icon">✓</div>

    <h1>Order Placed Successfully!</h1>
    <p class="subtitle">Thank you for shopping with Fashion Store</p>

    <!-- ORDER INFO -->
    <div class="order-info">
        <h3>📋 Order Details</h3>
        <div class="info-row">
            <span>Order Number</span>
            <strong>${order.orderNumber}</strong>
        </div>
        <div class="info-row">
            <span>Order Date</span>
            <strong><fmt:formatDate value="${order.createdAt}" pattern="dd MMM yyyy, hh:mm a"/></strong>
        </div>
        <div class="info-row">
            <span>Payment Method</span>
            <strong>${order.paymentMethod}</strong>
        </div>
        <div class="info-row">
            <span>Order Status</span>
            <strong style="color:#27ae60;">${order.orderStatus}</strong>
        </div>
    </div>

    <!-- ORDER ITEMS -->
    <div class="order-items">
        <h3>🛍️ Ordered Items</h3>
        <c:forEach var="item" items="${orderItems}">
            <div class="item">
                <span>${item.productName} × ${item.quantity}</span>
                <span>₹ <fmt:formatNumber value="${item.subtotal}" maxFractionDigits="2"/></span>
            </div>
        </c:forEach>

        <div class="total">
            <span>Total Amount</span>
            <span>₹ <fmt:formatNumber value="${order.finalAmount}" maxFractionDigits="2"/></span>
        </div>
    </div>

    <!-- DELIVERY ADDRESS -->
    <div class="delivery-address">
        <h3>🚚 Delivery Address</h3>
        <p>
            <strong>${order.name}</strong><br>
            ${order.addressLine}<br>
            ${order.city}, ${order.state} - ${order.pincode}<br>
            Phone: ${order.phone}
        </p>
    </div>

    <!-- ACTIONS -->
   <div class="actions">
    <a href="home" class="btn btn-primary">🏠 Back to Home</a>
    <a href="myOrders" class="btn btn-secondary">📋 My Orders</a>
    <a href="products" class="btn btn-secondary">🛍️ Continue Shopping</a>
</div>

</div>

</body>
</html>
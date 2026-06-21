<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Shopping Cart - Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <h2>Fashion Store</h2>
    <div class="nav-links">
        <a href="home">Home</a>
        <a href="products">Products</a>
        <a href="cart">Cart</a>
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

<div class="cart-container">

    <c:choose>
        <c:when test="${empty cartItems}">
            <!-- EMPTY CART -->
            <div class="empty-cart" style="flex:1;">
                <h3>🛒 Your cart is empty</h3>
                <p style="color:#999; margin-bottom:20px;">
                    Start shopping and add items to your cart!
                </p>
                <a href="products" class="shop-btn">Browse Products</a>
            </div>
        </c:when>

        <c:otherwise>
            <!-- CART ITEMS -->
            <div class="cart-items">

                <div class="cart-header">
                    <h2>🛒 Shopping Cart (${cartItems.size()} items)</h2>
                </div>

                <c:forEach var="item" items="${cartItems}">
                    <div class="cart-item">

                        
                             <img src="${pageContext.request.contextPath}/assets/${item.imageUrl}" alt="${item.productName}" class="item-image" onerror="this.src='${pageContext.request.contextPath}/assets/images/placeholder.jpg'">
                             

                        <div class="item-details">
                            <div>
                                <h3 class="item-name">${item.productName}</h3>
                                <p class="item-price">₹ ${item.price}</p>
                                <p class="item-discount-price">
                                    ₹ <fmt:formatNumber value="${item.discountedPrice}" 
                                                        maxFractionDigits="2"/>
                                    <span style="font-size:13px; color:#e74c3c; margin-left:8px;">
                                        (${item.discountPercent}% OFF)
                                    </span>
                                </p>
                            </div>

                            <p style="font-size:13px; color:#999;">
                                Subtotal: ₹ <fmt:formatNumber value="${item.subtotal}" 
                                                             maxFractionDigits="2"/>
                            </p>
                        </div>

                        <div class="item-actions">
                            <!-- QUANTITY UPDATE -->
                            <form action="cart" method="post" style="margin:0;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                <div class="qty-control">
                                    <button type="submit" 
                                            class="qty-btn" 
                                            name="quantity" 
                                            value="${item.quantity - 1}">−</button>
                                    <input type="text" 
                                           class="qty-display" 
                                           value="${item.quantity}" 
                                           readonly>
                                    <button type="submit" 
                                            class="qty-btn" 
                                            name="quantity" 
                                            value="${item.quantity + 1}">+</button>
                                </div>
                            </form>

                            <!-- REMOVE BUTTON -->
                            <form action="cart" method="post" style="margin:0;">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                <button type="submit" class="remove-btn">🗑️ Remove</button>
                            </form>
                        </div>

                    </div>
                </c:forEach>

            </div>

            <!-- CART SUMMARY -->
            <div class="cart-summary">
                <h3>Order Summary</h3>

                <div class="summary-row">
                    <span>Total MRP</span>
                    <span class="value">₹ <fmt:formatNumber value="${totalPrice}" 
                                                           maxFractionDigits="2"/></span>
                </div>

                <div class="summary-row discount">
                    <span>Discount</span>
                    <span class="value">- ₹ <fmt:formatNumber value="${totalDiscount}" 
                                                             maxFractionDigits="2"/></span>
                </div>

                <div class="summary-row">
                    <span>Delivery Charges</span>
                    <span class="value" style="color:#27ae60;">FREE</span>
                </div>

                <div class="summary-row total">
                    <span>Total Amount</span>
                    <span>₹ <fmt:formatNumber value="${finalPrice}" 
                                             maxFractionDigits="2"/></span>
                </div>

                <%-- NEW --%>
<a href="checkout" class="checkout-btn" style="text-decoration:none; display:block; text-align:center;">
    Proceed to Checkout →
</a>

                <a href="products" class="continue-shopping">← Continue Shopping</a>
            </div>

        </c:otherwise>
    </c:choose>

</div>

</body>
</html>
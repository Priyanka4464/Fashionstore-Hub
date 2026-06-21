<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout - Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/checkout.css">
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

<!-- PROGRESS BAR -->
<div class="progress-bar">
    <div class="steps">
        <div class="step active" id="step-indicator-1">
            <div class="step-circle">1</div>
            <div class="step-label">Shipping &<br>Address</div>
        </div>
        <div class="step" id="step-indicator-2">
            <div class="step-circle">2</div>
            <div class="step-label">Payment<br>Method</div>
        </div>
        <div class="step" id="step-indicator-3">
            <div class="step-circle">3</div>
            <div class="step-label">Order<br>Review</div>
        </div>
    </div>
</div>

<div class="checkout-container">

    <!-- LEFT: FORM STEPS -->
    <div class="checkout-form">

        <c:if test="${not empty error}">
            <div class="error">⚠️ ${error}</div>
        </c:if>

        <!-- STEP 1: SHIPPING ADDRESS -->
        <div class="form-panel active" id="panel-1">
            <div class="panel-title">🏠 Shipping & Delivery Address</div>

            <div class="form-section">
                <h4>Personal Info</h4>
                <div class="form-grid">
                    <div class="form-group">
                        <label>Full Name *</label>
                        <input type="text" id="name" placeholder="Enter full name"
                               value="${user.name}" required>
                    </div>
                    <div class="form-group">
                        <label>Phone Number *</label>
                        <input type="tel" id="phone" placeholder="10-digit mobile number"
                               value="${user.phone}" required>
                    </div>
                </div>
            </div>

            <div class="form-section">
                <h4>Delivery Address</h4>
                <div class="form-grid full">
                    <div class="form-group">
                        <label>Street Address *</label>
                        <input type="text" id="address" placeholder="House No, Street, Area" required>
                    </div>
                </div>
                <div class="form-grid three" style="margin-top:15px;">
                    <div class="form-group">
                        <label>City *</label>
                        <input type="text" id="city" placeholder="City" required>
                    </div>
                    <div class="form-group">
                        <label>State *</label>
                        <select id="state">
                            <option value="">Select State</option>
                            <option>Andhra Pradesh</option>
                            <option>Karnataka</option>
                            <option>Kerala</option>
                            <option>Maharashtra</option>
                            <option>Tamil Nadu</option>
                            <option>Telangana</option>
                            <option>Delhi</option>
                            <option>Gujarat</option>
                            <option>Rajasthan</option>
                            <option>Uttar Pradesh</option>
                            <option>West Bengal</option>
                            <option>Punjab</option>
                            <option>Haryana</option>
                            <option>Madhya Pradesh</option>
                            <option>Bihar</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Pincode *</label>
                        <input type="text" id="pincode" placeholder="6-digit pincode"
                               maxlength="6" required>
                    </div>
                </div>
            </div>

            <div class="btn-row">
                <button class="btn-next" onclick="goToStep2()">
                    Continue to Payment →
                </button>
            </div>
        </div>

        <!-- STEP 2: PAYMENT -->
        <div class="form-panel" id="panel-2">
            <div class="panel-title">💳 Select Payment Method</div>

            <div class="form-section">
                <h4>Choose How to Pay</h4>
                <div class="payment-options">
                    <div class="payment-card">
                        <input type="radio" name="paymentMethod" id="cod" value="COD" checked>
                        <label for="cod">
                            <span class="pay-icon">💵</span>
                            Cash on Delivery
                        </label>
                    </div>
                    <div class="payment-card">
                        <input type="radio" name="paymentMethod" id="upi" value="UPI">
                        <label for="upi">
                            <span class="pay-icon">📱</span>
                            UPI / GPay
                        </label>
                    </div>
                    <div class="payment-card">
                        <input type="radio" name="paymentMethod" id="card" value="CARD">
                        <label for="card">
                            <span class="pay-icon">💳</span>
                            Credit / Debit Card
                        </label>
                    </div>
                </div>
            </div>

            <div class="btn-row">
                <button class="btn-back" onclick="goToStep(1)">← Back</button>
                <button class="btn-next" onclick="goToStep3()">
                    Review Order →
                </button>
            </div>
        </div>

        <!-- STEP 3: ORDER REVIEW -->
        <div class="form-panel" id="panel-3">
            <div class="panel-title">📋 Review Your Order</div>

            <div class="form-section">
                <h4>Delivery Details</h4>
                <div class="review-row">
                    <span>Name</span>
                    <span id="review-name">—</span>
                </div>
                <div class="review-row">
                    <span>Phone</span>
                    <span id="review-phone">—</span>
                </div>
                <div class="review-row">
                    <span>Address</span>
                    <span id="review-address">—</span>
                </div>
                <div class="review-row">
                    <span>Payment</span>
                    <span id="review-payment">—</span>
                </div>
            </div>

            <!-- ACTUAL FORM SUBMITTED TO SERVER -->
            <form action="checkout" method="post" id="order-form">
                <input type="hidden" name="name"          id="f-name">
                <input type="hidden" name="phone"         id="f-phone">
                <input type="hidden" name="address"       id="f-address">
                <input type="hidden" name="city"          id="f-city">
                <input type="hidden" name="state"         id="f-state">
                <input type="hidden" name="pincode"       id="f-pincode">
                <input type="hidden" name="paymentMethod" id="f-payment">

                <div class="btn-row">
                    <button type="button" class="btn-back" onclick="goToStep(2)">← Back</button>
                    <button type="submit" class="btn-place-order">
                        ✅ Place Order
                    </button>
                </div>
            </form>
        </div>

    </div>

    <!-- RIGHT: ORDER SUMMARY -->
    <div class="order-summary">
        <h3>🛒 Order Summary</h3>

        <!-- ITEMS -->
        <c:forEach var="item" items="${cartItems}">
            <div class="order-item">
                <img src="${pageContext.request.contextPath}/assets/${item.imageUrl}"
                     alt="${item.productName}"
                     onerror="this.src='${pageContext.request.contextPath}/assets/images/placeholder.jpg'">
                <div class="order-item-info">
                    <h4>${item.productName}</h4>
                    <p>Qty: ${item.quantity} &nbsp;|&nbsp; ${item.discountPercent}% OFF</p>
                    <p class="item-price">
                        ₹ <fmt:formatNumber value="${item.subtotal}" maxFractionDigits="2"/>
                    </p>
                </div>
            </div>
        </c:forEach>

        <hr class="summary-divider">

        <div class="summary-row">
            <span>Total MRP</span>
            <span>₹ <fmt:formatNumber value="${totalPrice}" maxFractionDigits="2"/></span>
        </div>
        <div class="summary-row discount">
            <span>Discount</span>
            <span>- ₹ <fmt:formatNumber value="${totalDiscount}" maxFractionDigits="2"/></span>
        </div>
        <div class="summary-row">
            <span>Delivery</span>
            <span style="color:#27ae60;">FREE</span>
        </div>
        <div class="summary-row total">
            <span>Total</span>
            <span>₹ <fmt:formatNumber value="${finalPrice}" maxFractionDigits="2"/></span>
        </div>

        <div class="savings-badge">
            🎉 You save ₹ <fmt:formatNumber value="${totalDiscount}" maxFractionDigits="2"/> on this order!
        </div>
    </div>

</div>

<script>
    function goToStep(step) {
        document.querySelectorAll('.form-panel').forEach(p => p.classList.remove('active'));
        document.querySelectorAll('.step').forEach(s => s.classList.remove('active', 'done'));

        for (let i = 1; i < step; i++) {
            const ind = document.getElementById('step-indicator-' + i);
            ind.classList.add('done');
            ind.querySelector('.step-circle').textContent = '✓';
        }

        document.getElementById('panel-' + step).classList.add('active');
        document.getElementById('step-indicator-' + step).classList.add('active');

        window.scrollTo(0, 0);
    }

    function goToStep2() {
        const name    = document.getElementById('name').value.trim();
        const phone   = document.getElementById('phone').value.trim();
        const address = document.getElementById('address').value.trim();
        const city    = document.getElementById('city').value.trim();
        const state   = document.getElementById('state').value;
        const pincode = document.getElementById('pincode').value.trim();

        if (!name)                          { alert('Please enter your full name.');           return; }
        if (!phone || phone.length < 10)    { alert('Please enter a valid phone number.');     return; }
        if (!address)                       { alert('Please enter your street address.');      return; }
        if (!city)                          { alert('Please enter your city.');                return; }
        if (!state)                         { alert('Please select your state.');              return; }
        if (!pincode || pincode.length < 6) { alert('Please enter a valid 6-digit pincode.');  return; }

        goToStep(2);
    }

    function goToStep3() {
        const name    = document.getElementById('name').value.trim();
        const phone   = document.getElementById('phone').value.trim();
        const address = document.getElementById('address').value.trim();
        const city    = document.getElementById('city').value.trim();
        const state   = document.getElementById('state').value;
        const pincode = document.getElementById('pincode').value.trim();
        const payment = document.querySelector('input[name="paymentMethod"]:checked').value;

        document.getElementById('review-name').textContent    = name;
        document.getElementById('review-phone').textContent   = phone;
        document.getElementById('review-address').textContent = address + ', ' + city + ', ' + state + ' - ' + pincode;
        document.getElementById('review-payment').textContent = payment;

        document.getElementById('f-name').value    = name;
        document.getElementById('f-phone').value   = phone;
        document.getElementById('f-address').value = address;
        document.getElementById('f-city').value    = city;
        document.getElementById('f-state').value   = state;
        document.getElementById('f-pincode').value = pincode;
        document.getElementById('f-payment').value = payment;

        goToStep(3);
    }
</script>

</body>
</html>

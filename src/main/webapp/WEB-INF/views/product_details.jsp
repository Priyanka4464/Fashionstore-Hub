<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>${product.productName} - Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap"
          rel="stylesheet">
    <%-- ✅ Correct - includes /assets --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/productDetail.css">
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <h2>Fashion Store</h2>
    <div class="nav-links">
        <a href="home">Home</a>
        <a href="products">Products</a>
        <a href="cart">Cart 🛍️ ${sessionScope.cartCount != null ? sessionScope.cartCount : 0}</a>
        <a href="myOrders">My Orders</a>
        <a href="login">Login</a>
    </div>
</div>

<!-- BREADCRUMB -->
<div class="breadcrumb">
    <a href="home">Home</a> &rsaquo;
    <a href="products">Products</a> &rsaquo;
    ${product.productName}
</div>

<!-- DETAIL CARD -->
<div class="detail-container">

    <!-- LEFT: IMAGE -->
    <div class="product-image-section">
        <img class="main-image"
     src="${pageContext.request.contextPath}/assets/${product.imageUrl}"
     alt="${product.productName}"
     onerror="this.src='${pageContext.request.contextPath}/assets/images/placeholder.jpg'">
    </div>

    <!-- RIGHT: INFO -->
    <div class="product-info-section">

        <!-- CATEGORY BADGE -->
        <div>
            <span class="category-badge">Category ID: ${product.categoryId}</span>
        </div>

        <!-- TITLE -->
        <h1 class="product-title">${product.productName}</h1>

        <!-- DESCRIPTION -->
        <p class="product-description">${product.description}</p>

        <hr class="divider">

        <!-- PRICE -->
        <div class="price-section">
            <p class="price-old">MRP: ₹ ${product.price}</p>
            <div class="price-row">
                <span class="price-new">
                    ₹ ${product.price - (product.price * product.discountPercent / 100)}
                </span>
                <span class="discount-badge">${product.discountPercent}% OFF</span>
            </div>
            <p class="you-save">
                You save: ₹ ${product.price * product.discountPercent / 100}
            </p>
        </div>

        <!-- SIZE SELECTOR -->
        <div class="size-section">
            <h3>Select Size</h3>
            <div class="sizes">
                <button type="button" class="size-btn" onclick="selectSize(this)">XS</button>
                <button type="button" class="size-btn" onclick="selectSize(this)">S</button>
                <button type="button" class="size-btn" onclick="selectSize(this)">M</button>
                <button type="button" class="size-btn" onclick="selectSize(this)">L</button>
                <button type="button" class="size-btn" onclick="selectSize(this)">XL</button>
                <button type="button" class="size-btn" onclick="selectSize(this)">XXL</button>
            </div>
        </div>

        <!-- QUANTITY -->
        <div class="quantity-section">
            <h3>Quantity</h3>
            <div class="quantity-control">
                <button type="button" class="qty-btn" onclick="changeQty(-1)">−</button>
                <input class="qty-display" id="qty" type="text" value="1" readonly>
                <button type="button" class="qty-btn" onclick="changeQty(1)">+</button>
            </div>
        </div>
<!-- ACTION BUTTONS -->
<div class="action-buttons">
    <form action="cart" method="post"
          style="flex:1; display:flex;"
          onsubmit="return submitCart(event)">
        <input type="hidden" name="productId"  value="${product.productId}">
        <input type="hidden" name="quantity"   id="hiddenQty" value="1">
        <input type="hidden" name="action"     value="add">
        <input type="hidden" name="sizeLabel"  id="hiddenSize" value="M"> <%-- ← ADD THIS --%>
        <button type="submit" class="btn-cart"> Add to Cart</button>
    </form>
           
        </div>

        <hr class="divider">

        <!-- DELIVERY INFO -->
        <div class="delivery-info">
            <div class="delivery-item">
                <span class="icon">🚚</span>
                <span><strong>Free Delivery</strong> on orders above ₹499</span>
            </div>
            <div class="delivery-item">
                <span class="icon">↩️</span>
                <span><strong>Easy Returns</strong> within 7 days</span>
            </div>
            <div class="delivery-item">
                <span class="icon">✅</span>
                <span><strong>Secure Payment</strong> 100% guaranteed</span>
            </div>
            <div class="delivery-item">
                <span class="icon">🏷️</span>
                <span><strong>Authentic Product</strong> verified quality</span>
            </div>
        </div>

    </div>
</div>

<!-- BACK LINK -->
<a href="products" class="back-link">← Back to Products</a>

<!-- TOAST NOTIFICATION -->
<div class="toast" id="toast">✅ Added to cart successfully!</div>

<script>
    // Size selection
    function selectSize(btn) {
        document.querySelectorAll('.size-btn')
                .forEach(b => b.classList.remove('selected'));
        btn.classList.add('selected');
    }

    // Quantity control
    function changeQty(delta) {
        const qtyInput  = document.getElementById('qty');
        const hiddenQty = document.getElementById('hiddenQty');
        let current = parseInt(qtyInput.value);
        current += delta;
        if (current < 1)  current = 1;
        if (current > 10) current = 10;
        qtyInput.value  = current;
        hiddenQty.value = current;
    }

    // Toast on Add to Cart ← REPLACED showToast with submitCart
    function submitCart(e) {
        e.preventDefault();

        // Check size selected
        const selectedBtn = document.querySelector('.size-btn.selected');
        if (!selectedBtn) {
            alert('Please select a size first!');
            return false;
        }

        // Set size into hidden field
        document.getElementById('hiddenSize').value = selectedBtn.textContent.trim();

        // Show toast
        const toast = document.getElementById('toast');
        toast.classList.add('show');
        setTimeout(() => toast.classList.remove('show'), 3000);

        // Submit form after short delay so user sees the toast
        setTimeout(() => e.target.submit(), 500);
        return false;
    }
</script>

</body>
</html>
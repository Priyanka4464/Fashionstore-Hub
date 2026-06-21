<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;500;600;700&family=Jost:wght@300;400;500;600&display=swap" rel="stylesheet">
    <style>
        * { box-sizing: border-box; margin: 0; padding: 0; }

        :root {
            --purple-dark:   #2d1b69;
            --purple-main:   #5b21b6;
            --purple-mid:    #7c3aed;
            --purple-soft:   #ede9fe;
            --purple-light:  #f5f3ff;
            --purple-muted:  #a78bfa;
            --purple-border: #ddd6fe;
            --white: #ffffff;
            --text-dark: #1e1b4b;
            --text-gray: #6b7280;
        }

        body { font-family: 'Jost', sans-serif; background: var(--white); color: var(--text-dark); }

        .announce-bar { background: var(--purple-dark); color: #d8b4fe; font-size: 12px; padding: 8px 40px; display: flex; justify-content: space-between; align-items: center; }
        .announce-bar .marquee { display: flex; gap: 32px; }
        .announce-bar .marquee span::before { content: "·"; margin-right: 10px; color: var(--purple-muted); }
        .announce-bar .right-links { display: flex; gap: 20px; }
        .announce-bar .right-links a { color: #d8b4fe; text-decoration: none; font-size: 12px; }
        .announce-bar .right-links a:hover { color: #fff; }

        .navbar { background: var(--white); border-bottom: 1px solid var(--purple-border); padding: 14px 40px; display: flex; align-items: center; gap: 20px; position: sticky; top: 0; z-index: 100; }
        .navbar-logo { font-family: 'Playfair Display', serif; font-size: 20px; font-weight: 700; color: var(--purple-main); text-decoration: none; white-space: nowrap; margin-right: 10px; }
        .search-wrap { flex: 1; max-width: 480px; display: flex; border: 1.5px solid var(--purple-border); border-radius: 6px; overflow: hidden; }
        .search-wrap select { border: none; border-right: 1.5px solid var(--purple-border); background: var(--purple-light); color: var(--purple-main); font-family: 'Jost', sans-serif; font-size: 13px; padding: 0 14px; outline: none; cursor: pointer; min-width: 130px; }
        .search-wrap input { flex: 1; border: none; outline: none; padding: 10px 14px; font-family: 'Jost', sans-serif; font-size: 13px; color: var(--text-dark); background: var(--white); }
        .search-wrap input::placeholder { color: #9ca3af; }
        .search-btn { background: var(--purple-main); color: var(--white); border: none; padding: 0 22px; font-family: 'Jost', sans-serif; font-size: 13px; font-weight: 500; cursor: pointer; transition: background 0.2s; }
        .search-btn:hover { background: var(--purple-mid); }
        .nav-right { display: flex; align-items: center; gap: 18px; margin-left: auto; }
        .nav-right a { text-decoration: none; color: var(--text-dark); font-size: 14px; font-weight: 500; transition: color 0.2s; }
        .nav-right a:hover { color: var(--purple-main); }
        .cart-btn { background: var(--purple-main) !important; color: var(--white) !important; padding: 8px 18px; border-radius: 6px; }
        .cart-btn:hover { background: var(--purple-mid) !important; }
        .user-name { color: var(--purple-main); font-weight: 600; font-size: 14px; }
        .logout-link { color: var(--purple-muted) !important; }

        .tab-nav { background: var(--white); border-bottom: 1px solid var(--purple-border); padding: 0 40px; display: flex; }
        .tab-nav a { text-decoration: none; color: var(--text-gray); font-size: 14px; font-weight: 400; padding: 12px 18px; border-bottom: 2px solid transparent; transition: all 0.2s; }
        .tab-nav a.active, .tab-nav a:hover { color: var(--purple-main); border-bottom-color: var(--purple-main); font-weight: 500; }

        .hero { background: var(--purple-light); display: flex; align-items: center; justify-content: space-between; padding: 60px 40px 60px 60px; min-height: 340px; overflow: hidden; }
        .hero-left { max-width: 440px; }
        .hero-badge { display: inline-block; background: var(--purple-soft); color: var(--purple-main); font-size: 12px; font-weight: 600; letter-spacing: 1px; text-transform: uppercase; padding: 5px 14px; border-radius: 20px; margin-bottom: 18px; }
        .hero-title { font-size: 44px; font-weight: 700; line-height: 1.15; color: var(--text-dark); margin-bottom: 14px; font-family: 'Playfair Display', serif; }
        .hero-sub { font-size: 15px; color: var(--text-gray); line-height: 1.65; margin-bottom: 30px; font-weight: 300; }
        .hero-btns { display: flex; gap: 14px; }
        .btn-primary { padding: 13px 28px; background: var(--purple-main); color: var(--white); text-decoration: none; font-size: 14px; font-weight: 500; border-radius: 6px; border: 2px solid var(--purple-main); cursor: pointer; font-family: 'Jost', sans-serif; transition: background 0.2s; display: inline-block; }
        .btn-primary:hover { background: var(--purple-mid); border-color: var(--purple-mid); }
        .btn-outline { padding: 13px 28px; background: transparent; color: var(--purple-main); text-decoration: none; font-size: 14px; font-weight: 500; border-radius: 6px; border: 2px solid var(--purple-main); cursor: pointer; font-family: 'Jost', sans-serif; transition: all 0.2s; display: inline-block; }
        .btn-outline:hover { background: var(--purple-main); color: var(--white); }
        .hero-right { display: flex; align-items: flex-end; gap: 10px; flex-shrink: 0; }
        .hero-img-card { border-radius: 12px; overflow: hidden; background: var(--purple-soft); }
        .hero-img-card img { width: 100%; height: 100%; object-fit: cover; object-position: center top; display: block; }
         .hic-1,
         .hic-2,
         .hic-3,
         .hic-4 {
          width: 120px;
          height: 200px;
                }

        .trust-bar { background: var(--purple-main); display: grid; grid-template-columns: repeat(4, 1fr); }
        .trust-item { padding: 20px 16px; text-align: center; border-right: 1px solid rgba(255,255,255,0.15); }
        .trust-item:last-child { border-right: none; }
        .trust-title { font-size: 14px; font-weight: 600; color: #fff; margin-bottom: 3px; }
        .trust-sub { font-size: 12px; color: #ddd6fe; }

        .cat-section { padding: 48px 40px 32px; background: var(--white); }
        .section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 22px; }
        .section-title-main { font-size: 22px; font-weight: 600; color: var(--text-dark); font-family: 'Playfair Display', serif; }
        .view-all-link { color: var(--purple-main); font-size: 14px; font-weight: 500; text-decoration: none; }
        .view-all-link:hover { color: var(--purple-mid); text-decoration: underline; }
        .cat-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px; }
        .cat-card { position: relative; border-radius: 12px; overflow: hidden; height: 280px; cursor: pointer; background: var(--purple-soft); }
        .cat-card img { width: 100%; height: 100%; object-fit: cover; object-position: center top; transition: transform 0.5s ease; }
        .cat-card:hover img { transform: scale(1.05); }
        .cat-overlay { position: absolute; bottom: 0; left: 0; right: 0; padding: 20px 18px; background: linear-gradient(to top, rgba(45,27,105,0.82), transparent); }
        .cat-overlay h3 { font-family: 'Playfair Display', serif; font-size: 18px; font-weight: 600; color: #fff; margin-bottom: 4px; }
        .cat-overlay a { color: #ddd6fe; text-decoration: none; font-size: 12px; font-weight: 500; letter-spacing: 1px; }
        .cat-overlay a:hover { color: #fff; }

        .products-section { padding: 48px 40px; background: var(--purple-light); }
        .section-tag { font-size: 12px; letter-spacing: 2px; text-transform: uppercase; color: var(--purple-main); font-weight: 600; margin-bottom: 4px; }
        .section-title { font-family: 'Playfair Display', serif; font-size: 26px; font-weight: 600; color: var(--text-dark); }
        .products-grid { display: grid; grid-template-columns: repeat(5, 1fr); gap: 16px; margin-top: 24px; }
        .product-card { background: var(--white); border-radius: 10px; overflow: hidden; border: 1px solid var(--purple-border); transition: box-shadow 0.2s, border-color 0.2s; }
        .product-card:hover { border-color: var(--purple-muted); box-shadow: 0 4px 20px rgba(124,58,237,0.1); }
        .product-img-wrap { position: relative; height: 220px; background: var(--purple-soft); overflow: hidden; }
        .product-img-wrap img { width: 100%; height: 100%; object-fit: contain; transition: transform 0.4s ease; }
        .product-card:hover .product-img-wrap img { transform: scale(1.05); }
        .discount-badge { position: absolute; top: 10px; left: 10px; background: #ef4444; color: #fff; font-size: 10px; font-weight: 600; padding: 3px 8px; border-radius: 20px; }
        .product-actions { position: absolute; bottom: -46px; left: 0; right: 0; padding: 8px 10px; background: rgba(255,255,255,0.96); transition: bottom 0.3s ease; }
        .product-card:hover .product-actions { bottom: 0; }
        .product-actions form button { display: block; width: 100%; padding: 9px; background: var(--purple-main); color: white; border: none; font-family: 'Jost', sans-serif; font-size: 12px; font-weight: 500; cursor: pointer; border-radius: 5px; transition: background 0.2s; }
        .product-actions form button:hover { background: var(--purple-mid); }
        .product-info { padding: 12px; }
        .product-name { font-size: 13px; font-weight: 500; color: var(--text-dark); margin-bottom: 3px; }
        .product-desc { font-size: 11px; color: var(--text-gray); font-weight: 300; margin-bottom: 8px; }
        .product-prices { display: flex; align-items: center; gap: 7px; flex-wrap: wrap; }
        .price-old { font-size: 11px; color: #bbb; text-decoration: line-through; }
        .price-new { font-size: 14px; font-weight: 600; color: var(--purple-main); }
        .price-discount { font-size: 11px; color: #ef4444; font-weight: 600; }

        .view-all-wrap { text-align: center; margin-top: 30px; }
        .view-all-btn {
            display: inline-block;
            padding: 12px 40px;
            background: var(--purple-main);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-size: 15px;
            font-weight: 600;
            font-family: 'Jost', sans-serif;
            transition: background 0.2s;
        }
        .view-all-btn:hover { background: var(--purple-mid); }

        .footer { background: var(--purple-dark); border-top: 1px solid #3d2a7a; padding: 50px 40px 22px; }
        .footer-grid { display: grid; grid-template-columns: 2fr 1fr 1fr 1fr 1fr; gap: 30px; margin-bottom: 40px; }
        .footer-brand h3 { font-family: 'Playfair Display', serif; font-size: 18px; color: var(--white); letter-spacing: 2px; margin-bottom: 10px; }
        .footer-brand p { color: #c4b5fd; font-size: 13px; font-weight: 300; line-height: 1.7; max-width: 200px; }
        .footer-col h4 { font-size: 11px; letter-spacing: 2px; text-transform: uppercase; color: var(--white); margin-bottom: 16px; font-weight: 600; }
        .footer-col a { display: block; color: #c4b5fd; text-decoration: none; font-size: 13px; font-weight: 300; margin-bottom: 8px; transition: color 0.2s; }
        .footer-col a:hover { color: #fff; }
        .footer-bottom { border-top: 1px solid #3d2a7a; padding-top: 20px; display: flex; justify-content: space-between; align-items: center; }
        .footer-bottom p { color: #c4b5fd; font-size: 12px; font-weight: 300; }
        .footer-bottom-links { display: flex; gap: 18px; }
        .footer-bottom-links a { color: #c4b5fd; text-decoration: none; font-size: 12px; transition: color 0.2s; }
        .footer-bottom-links a:hover { color: #fff; }
    </style>
</head>
<body>

<!-- ANNOUNCEMENT BAR -->
<div class="announce-bar">
    <div class="marquee">
        Free Shipping on orders over ₹999
        <span>Easy 30-day returns</span>
        <span>New arrivals every week!</span>
    </div>
    <div class="right-links">
        <a href="#">Track Order</a>
        <a href="#">Help &amp; Support</a>
    </div>
</div>

<!-- NAVBAR -->
<div class="navbar">
    <a href="home" class="navbar-logo">Fashion Store</a>
    <form action="products" method="get" class="search-wrap">
        <select name="categoryId">
            <option value="">All Categories</option>
            <option value="1">Men</option>
            <option value="2">Women</option>
            <option value="3">Kids</option>
        </select>
        <input type="text" name="keyword" placeholder="Search for products, brands and more...">
        <button type="submit" class="search-btn">Search</button>
    </form>
    <div class="nav-right">
        <c:choose>
            <c:when test="${not empty sessionScope.userName}">
                <span class="user-name">👤 ${sessionScope.userName}</span>
                <a href="logout" class="logout-link">Logout</a>
            </c:when>
            <c:otherwise>
                <a href="login">Login</a>
            </c:otherwise>
        </c:choose>
        <a href="cart" class="cart-btn">
            Cart  ${sessionScope.cartCount != null ? sessionScope.cartCount : 0}
        </a>
    </div>
</div>

<!-- TAB NAV -->
<div class="tab-nav">
    <a href="home" class="active">Home</a>
    <a href="products">Products</a>
    <a href="products?categoryId=1">Men</a>
    <a href="products?categoryId=2">Women</a>
    <a href="products?categoryId=3">Kids</a>
   
    
</div>

<!-- HERO -->
<div class="hero">
    <div class="hero-left">
        
        <h1 class="hero-title">Elevate Your Style</h1>
        <p class="hero-sub">
            Discover the latest trends in fashion.<br>
            Premium quality, unmatched comfort.
        </p>
        <div class="hero-btns">
            <a href="products" class="btn-primary">Shop Now</a>
            <a href="products" class="btn-outline">View Collections</a>
        </div>
    </div>
    <div class="hero-right">
        <div class="hero-img-card hic-1">
            <img src="${pageContext.request.contextPath}/assets/images/men_tshirt.jpg" alt="Men Fashion">
        </div>
        <div class="hero-img-card hic-2">
            <img src="${pageContext.request.contextPath}/assets/images/women_dress.jpg" alt="Women Fashion">
        </div>
        <div class="hero-img-card hic-3">
            <img src="${pageContext.request.contextPath}/assets/images/women_top.jpg" alt="Women Top">
        </div>
        <div class="hero-img-card hic-4">
            <img src="${pageContext.request.contextPath}/assets/images/kids_hoodie.jpg" alt="Kids Fashion">
        </div>
    </div>
</div>

<!-- TRUST BAR -->
<div class="trust-bar">
    <div class="trust-item">
        <div class="trust-title">🚚 Free Shipping</div>
        <div class="trust-sub">On orders over ₹999</div>
    </div>
    <div class="trust-item">
        <div class="trust-title">🔒 Secure Payment</div>
        <div class="trust-sub">100% secure checkout</div>
    </div>
    <div class="trust-item">
        <div class="trust-title">↩️ Easy Returns</div>
        <div class="trust-sub">30-day return policy</div>
    </div>
    <div class="trust-item">
        <div class="trust-title">💬 24/7 Support</div>
        <div class="trust-sub">Dedicated support</div>
    </div>
</div>

<!-- SHOP BY CATEGORY -->
<div class="cat-section">
    <div class="section-header">
        <h2 class="section-title-main">Shop by Category</h2>
        
    </div>
    <div class="cat-grid">
        <div class="cat-card">
            <img src="${pageContext.request.contextPath}/assets/images/mens_Jocket.jpg" alt="Men">
            <div class="cat-overlay">
                <h3>Men</h3>
                <a href="products?categoryId=1">Explore →</a>
            </div>
        </div>
        <div class="cat-card">
            <img src="${pageContext.request.contextPath}/assets/images/women_top.jpg" alt="Women">
            <div class="cat-overlay">
                <h3>Women</h3>
                <a href="products?categoryId=2">Explore →</a>
            </div>
        </div>
        <div class="cat-card">
            <img src="${pageContext.request.contextPath}/assets/images/kids_shirt.jpg" alt="Kids">
            <div class="cat-overlay">
                <h3>Kids</h3>
                <a href="products?categoryId=3">Explore →</a>
            </div>
        </div>
        
    </div>
</div>

<!-- PRODUCTS SECTION -->
<div class="products-section">
    <div class="section-header">
        <div>
          
            <div class="section-title">Featured Products</div>
        </div>
    </div>

    <div class="products-grid">
        <c:forEach var="p" items="${products}">
            <div class="product-card">
                <div class="product-img-wrap">
                    <img src="${pageContext.request.contextPath}/assets/${p.imageUrl}"
                         alt="${p.productName}"
                         onerror="this.src='${pageContext.request.contextPath}/assets/images/placeholder.jpg'">
                    <span class="discount-badge">${p.discountPercent}% OFF</span>
                    <div class="product-actions">
                        <form action="productDetails" method="get">
                            <input type="hidden" name="id" value="${p.productId}">
                            <button type="submit">View Details</button>
                        </form>
                    </div>
                </div>
                <div class="product-info">
                    <div class="product-name">${p.productName}</div>
                    <div class="product-desc">${p.description}</div>
                    <div class="product-prices">
                        <span class="price-old">₹${p.price}</span>
                        <span class="price-new">₹${p.price - (p.price * p.discountPercent / 100)}</span>
                        <span class="price-discount">${p.discountPercent}% OFF</span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- VIEW ALL BUTTON -->
    <div class="view-all-wrap">
        <a href="products" class="view-all-btn">View All Products →</a>
    </div>

</div>

<!-- FOOTER -->
<div class="footer">
    <div class="footer-grid">
        <div class="footer-brand">
            <h3>FASHION STORE</h3>
            <p>Modern fashion made for everyday elegance.</p>
        </div>
        <div class="footer-col">
            <h4>Shop</h4>
            <a href="products?categoryId=2">Women</a>
            <a href="products?categoryId=1">Men</a>
            <a href="products?categoryId=3">Kids</a>
            <a href="products">All Products</a>
        </div>
          <div class="footer-col">
            <h4>SUPPORT</h4>
           <p>📧 <a href="https://mail.google.com/mail/?view=cm&to=support@fashionstore.com" target="_blank">support@fashionstore.com</a></p>

           
        </div>
        
        <div class="footer-col">
            <h4>Account</h4>
            <a href="myOrders">My Orders</a>
            <a href="cart">My Cart</a>
            <c:choose>
                <c:when test="${not empty sessionScope.userName}">
                    <a href="logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a href="login">Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="footer-bottom">
        <p>© 2026 Fashion Store. All rights reserved.</p>
        <div class="footer-bottom-links">
            <a href="#">Privacy Policy</a>
            <a href="#">Terms &amp; Conditions</a>
            <a href="#">Cookie Policy</a>
        </div>
    </div>
</div>

</body>
</html>
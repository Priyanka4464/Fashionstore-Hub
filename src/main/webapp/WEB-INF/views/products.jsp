<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>All Products - Fashion Store</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <style>
        body { margin: 0; font-family: 'Poppins', sans-serif; background: #f7f7f7; }

        .navbar { display: flex; justify-content: space-between; align-items: center;
                  padding: 15px 40px; background: white; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .navbar h2 { margin: 0; color: #3498db; }
        .nav-links a { margin: 0 10px; text-decoration: none; color: black; font-weight: 500; }

        /* LAYOUT */
        .page-layout { display: flex; gap: 20px; padding: 30px; }

        /* SIDEBAR */
        .sidebar { width: 220px; min-width: 220px; background: white; padding: 20px;
                   border-radius: 15px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); height: fit-content; }
        .sidebar h3 { color: #3498db; margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 8px; }
        .sidebar label { display: block; margin: 10px 0; cursor: pointer; font-size: 14px; }
        .sidebar input[type="radio"] { margin-right: 8px; accent-color: #3498db; }
        .sidebar input[type="number"] { width: 100%; padding: 6px; border: 1px solid #ddd;
                                        border-radius: 8px; margin-top: 5px; box-sizing: border-box; }
        .sidebar .apply-btn { width: 100%; padding: 10px; background: #3498db; color: white;
                              border: none; border-radius: 8px; cursor: pointer; margin-top: 10px;
                              font-family: 'Poppins', sans-serif; font-size: 14px; }
        .sidebar .clear-btn { display: block; text-align: center; color: gray;
                              text-decoration: none; margin-top: 8px; font-size: 13px; }
        .sidebar input[type="text"] { width: 100%; padding: 6px; border: 1px solid #ddd;
                                      border-radius: 8px; margin-top: 5px; box-sizing: border-box; }

        /* PRODUCTS GRID */
        .products-area { flex: 1; }
        .products-area h2 { margin-top: 0; }
        .products { display: flex; flex-wrap: wrap; gap: 20px; }

        .card { width: 200px; background: white; border-radius: 15px; overflow: hidden;
                box-shadow: 0 2px 8px rgba(0,0,0,0.12); transition: 0.3s; }
        .card:hover { transform: translateY(-5px); }
        .card img { width: 100%; height: 220px; object-fit: cover; }
        .card-body { padding: 10px; text-align: center; }
        .card-body h4 { margin: 5px 0; font-size: 15px; }
        .price-old { text-decoration: line-through; color: gray; font-size: 13px; margin: 2px 0; }
        .price-new { color: green; font-weight: bold; font-size: 17px; margin: 2px 0; }
        .discount  { color: #e74c3c; font-weight: bold; font-size: 13px; margin: 2px 0; }
        .btn { background: black; color: white; padding: 8px 14px; border: none;
               border-radius: 5px; margin-top: 8px; cursor: pointer; font-size: 13px; }

        .no-products { color: gray; font-size: 16px; margin: 40px auto; }
    </style>
</head>
<body>

<!-- NAVBAR -->
<div class="navbar">
    <h2>Fashion Store</h2>
    <div class="nav-links">
        <a href="home">Home</a>
        <a href="products">Products</a>
       <a href="cart">Cart 🛍 ${sessionScope.cartCount != null ? sessionScope.cartCount : 0}</a>
        <a href="myOrders"> Orders</a>
   
    </div>
</div>

<div class="page-layout">

    <!-- SIDEBAR FILTERS -->
    <div class="sidebar">
        <form action="products" method="get">

            <h3>🔍 Search</h3>
            <input type="text" name="keyword" 
                   placeholder="Search products..." 
                   value="${keyword}">

            <h3>👗 Category</h3>
            <label>
                <input type="radio" name="categoryId" value=""
                    ${selectedCategory == 0 ? 'checked' : ''}> All
            </label>
            <label>
                <input type="radio" name="categoryId" value="1"
                    ${selectedCategory == 1 ? 'checked' : ''}> Men
            </label>
            <label>
                <input type="radio" name="categoryId" value="2"
                    ${selectedCategory == 2 ? 'checked' : ''}> Women
            </label>
            <label>
                <input type="radio" name="categoryId" value="3"
                    ${selectedCategory == 3 ? 'checked' : ''}> Kids
            </label>

            <h3>💰 Price Range</h3>
            <input type="number" name="minPrice" 
                   placeholder="Min ₹" 
                   value="${minPrice}">
            <input type="number" name="maxPrice" 
                   placeholder="Max ₹" 
                   value="${maxPrice}" 
                   style="margin-top:8px;">

            <button type="submit" class="apply-btn">Apply Filters</button>
            <a href="products" class="clear-btn">✕ Clear Filters</a>

        </form>
    </div>

    <!-- PRODUCTS GRID -->
    <div class="products-title">
        <h2>Explore Our Fashion Collection
            
        </h2>

        <div class="products">

            <c:if test="${empty products}">
                <p class="no-products">No products found. Try different filters.</p>
            </c:if>

            <c:forEach var="p" items="${products}">
    <div class="card">
        <img src="${pageContext.request.contextPath}/assets/${p.imageUrl}"
             alt="${p.productName}">
        <div class="card-body">
            <h4>${p.productName}</h4>
            <p class="price-old">₹ ${p.price}</p>
            <p class="price-new">
                ₹ ${p.price - (p.price * p.discountPercent / 100)}
            </p>
            <p class="discount">${p.discountPercent}% OFF</p>
            <form action="productDetails" method="get">
                <input type="hidden" name="id" value="${p.productId}">
                <button class="btn">View Details</button>
            </form>
        </div>
    </div>
</c:forEach>

        </div>
    </div>

</div>
</body>
</html>
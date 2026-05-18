<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="shopParams" value="?category=${param.category}&search=${param.search}&sort=${param.sort}&page=${param.page}" />

<nav class="seller-navbar">
    <div class="nav-brand">
        <a href="${pageContext.request.contextPath}/shop${shopParams}">
            <span class="brand-highlight">FMC</span>
            <span>FindMyComponent</span>
        </a>
    </div>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/shop${shopParams}"
           class="nav-item ${empty param.activeTab || param.activeTab == 'marketplace' ? 'active' : ''}">
            Marketplace
        </a>

        <c:if test="${not empty sessionScope.user && sessionScope.role == 'BUYER'}">
            <a href="${pageContext.request.contextPath}/buyer/cart"
               class="nav-item ${param.activeTab == 'cart' ? 'active' : ''}">
                Cart
            </a>
        </c:if>

        <c:if test="${not empty sessionScope.user && sessionScope.role == 'SELLER'}">
            <a href="${pageContext.request.contextPath}/seller/products"
               class="nav-item ${param.activeTab == 'seller' ? 'active' : ''}">
                Seller Hub
            </a>
        </c:if>
    </div>

    <div class="nav-actions">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <span class="nav-right">
                    Hi, <c:out value="${sessionScope.user.username}"/>
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="nav-logout">
                    Logout
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login" class="nav-right">
                    Sign In
                </a>
                <a href="${pageContext.request.contextPath}/register" class="nav-logout">
                    Register
                </a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>

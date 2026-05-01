<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="/WEB-INF/templates/head.jsp">
    <jsp:param name="title" value="FindMyComponent — Login" />
    <jsp:param name="cssFile" value="auth" />
</jsp:include>

<body>
<div class="register-page">

    <div class="register-header">
        <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="FMC Logo" />
        <h1>FindMyComponent</h1>
    </div>

    <div class="register-card">
        <h2>Welcome back</h2>

        <%-- Success message from register --%>
        <c:if test="${not empty success}">
            <div class="alert alert-success" role="alert">
                <span class="alert-icon">✓</span>
                <span><c:out value="${success}" /></span>
            </div>
        </c:if>

        <%-- Error message --%>
        <c:if test="${not empty error}">
            <div class="alert alert-error" role="alert">
                <span class="alert-icon">⚠</span>
                <span><c:out value="${error}" /></span>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post" novalidate>

            <div class="form-group">
                <label for="username">Username or Email</label>
                <input type="text" id="username" name="username"
                       placeholder="Enter username or email"
                       value="<c:out value='${param.username}' default='' />"
                       autocomplete="username" required />
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password"
                       placeholder="Enter your password"
                       autocomplete="current-password" required />
            </div>

            <%-- Remember Me — name="rememberMe" matches LoginServlet --%>
            <label class="checkbox-group" for="rememberMe">
                <input type="checkbox" id="rememberMe" name="rememberMe" />
                <div>
                    Remember me
                    <span>Stay logged in for 7 days</span>
                </div>
            </label>

            <button type="submit" class="btn-register">Log In</button>

        </form>

        <p class="register-footer">
            Don't have an account?
            <a href="${pageContext.request.contextPath}/register">Register</a>
        </p>
    </div>

</div>
</body>
</html>
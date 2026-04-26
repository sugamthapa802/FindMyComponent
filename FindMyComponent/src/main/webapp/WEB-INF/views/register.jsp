<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="/WEB-INF/templates/head.jsp">
    <jsp:param name="title" value="FindMyComponent — Register" />
    <jsp:param name="cssFile" value="auth" />
</jsp:include>

<body>
<div class="register-page">

    <div class="register-header">
        <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="FMC Logo" />
        <h1>FindMyComponent</h1>
    </div>

    <div class="register-card">
        <h2>Create your account</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error" role="alert">
                <span class="alert-icon">⚠</span>
                <span><c:out value="${error}" /></span>
            </div>
        </c:if>

        <c:if test="${not empty success}">
            <div class="alert alert-success" role="alert">
                <span class="alert-icon">✓</span>
                <span><c:out value="${success}" /></span>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" novalidate>

            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" placeholder="e.g. john_doe"
                       value="<c:out value='${param.username}' default='' />"
                       autocomplete="username" required />
            </div>

            <div class="form-group">
                <label for="email">Email address</label>
                <input type="email" id="email" name="email" placeholder="you@example.com"
                       value="<c:out value='${param.email}' default='' />"
                       autocomplete="email" required />
            </div>

            <div class="section-divider"><span>Personal details</span></div>

            <div class="form-row">
                <div class="form-group">
                    <label for="firstName">First name</label>
                    <input type="text" id="firstName" name="firstName" placeholder="First"
                           value="<c:out value='${param.firstName}' default='' />"
                           autocomplete="given-name" />
                </div>
                <div class="form-group">
                    <label for="lastName">Last name</label>
                    <input type="text" id="lastName" name="lastName" placeholder="Last"
                           value="<c:out value='${param.lastName}' default='' />"
                           autocomplete="family-name" />
                </div>
            </div>

            <div class="form-group">
                <label for="phone">Phone number</label>
                <input type="text" id="phone" name="phone" placeholder="+1 555 000 0000"
                       value="<c:out value='${param.phone}' default='' />"
                       autocomplete="tel" />
            </div>

            <div class="section-divider"><span>Security</span></div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password"
                       placeholder="Min. 6 characters" autocomplete="new-password" required />
            </div>

            <div class="form-group">
                <label for="confirmPassword">Confirm password</label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       placeholder="Repeat your password" autocomplete="new-password" required />
            </div>

            <label class="checkbox-group" for="registerAsSeller">
                <input type="checkbox" id="registerAsSeller" name="registerAsSeller" />
                <div>
                    Register as a Seller
                    <span>List and sell electronic components on the platform</span>
                </div>
            </label>

            <button type="submit" class="btn-register">Create Account</button>

        </form>

        <p class="register-footer">
            Already have an account?
            <a href="${pageContext.request.contextPath}/login">Log in</a>
        </p>
    </div>

</div>
</body>
</html>
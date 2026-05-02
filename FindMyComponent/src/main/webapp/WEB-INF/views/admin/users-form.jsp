<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/templates/head.jsp">
    <jsp:param name="title" value="FMC Admin — Edit User" />
    <jsp:param name="cssFile" value="categories" />
</jsp:include>

<body>
<div class="admin-page">
    <div class="admin-header">
        <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="FMC Logo" />
        <h1>Edit User Profile</h1>
    </div>

    <div class="admin-card" style="max-width: 600px; margin: 0 auto;">
        <!-- Action matches doPost: /admin/users?action=update -->
        <form action="${pageContext.request.contextPath}/admin/users?action=update" method="post">
            <input type="hidden" name="id" value="${editUser.id}">

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 30px; padding: 15px; border: 1px dashed var(--border); border-radius: var(--radius-sm);">
                <div>
                    <label style="font-family: 'Orbitron', sans-serif; font-size: 0.65rem; color: var(--muted); text-transform: uppercase;">Role</label>
                    <div style="color: var(--accent); font-weight: bold; font-size: 0.9rem;">${roleName}</div>
                </div>
                <div>
                    <label style="font-family: 'Orbitron', sans-serif; font-size: 0.65rem; color: var(--muted); text-transform: uppercase;">Username</label>
                    <div style="color: var(--text); font-size: 0.9rem;">@${editUser.username}</div>
                </div>
            </div>

            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" value="<c:out value='${editUser.firstName}'/>" required>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" value="<c:out value='${editUser.lastName}'/>" required>
            </div>

            <div class="form-group">
                <label for="phone">Phone Number</label>
                <input type="text" id="phone" name="phone" value="<c:out value='${editUser.phone}'/>">
            </div>

            <c:choose>
                <c:when test="${isEditingSelf}">
                    <div class="alert" style="background: rgba(79, 142, 247, 0.05); border: 1px solid var(--border);">
                        <span style="font-size: 0.8rem; color: var(--muted);">Account status cannot be modified for your own session.</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <label>Account Status</label>
                        <select name="isActive" class="btn-submit" style="background: var(--surface); color: var(--text); border: 1px solid var(--border); width: 100%; text-align: left; padding: 12px; font-family: 'Exo 2', sans-serif;">
                            <option value="true" ${editUser.active ? 'selected' : ''}>ACTIVE</option>
                            <option value="false" ${!editUser.active ? 'selected' : ''}>SUSPENDED / INACTIVE</option>
                        </select>
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="form-actions">
                <button type="submit" class="btn-primary">Update Profile</button>
                <a href="${pageContext.request.contextPath}/admin/users" class="btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
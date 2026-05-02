<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-INF/templates/head.jsp">
    <jsp:param name="title" value="FMC Admin — ${category == null ? 'Add' : 'Edit'} Category" />
    <jsp:param name="cssFile" value="categories" />
</jsp:include>

<body>
<div class="admin-page" style="max-width: 550px;">
    <div class="admin-header">
        <img src="${pageContext.request.contextPath}/static/images/logo.png" alt="FMC Logo" />
        <h1>FMC Admin</h1>
    </div>

    <div class="admin-card">
        <h2>${category == null ? 'New Category' : 'Edit Category'}</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">
                <span class="alert-icon">⚠</span> ${error}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/categories?action=save" method="post">
            <c:if test="${category != null}">
                <input type="hidden" name="id" value="${category.id}">
            </c:if>

            <div class="form-group">
                <label for="name">Category Name</label>
                <input type="text" id="name" name="name" placeholder="e.g. Sensors & Modules"
                       value="<c:out value='${category.name}' />" required />
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" rows="5"
                          placeholder="Describe the items in this category..."><c:out value='${category.description}' /></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-submit">${category == null ? 'Create Category' : 'Update Category'}</button>
                <a href="${pageContext.request.contextPath}/admin/categories" class="btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
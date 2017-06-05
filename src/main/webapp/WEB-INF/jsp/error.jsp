<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="reusable/header.jspf"%>
<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <%@include file="reusable/greeting.jspf"%>
                <h2><fmt:message key="error.pageName" bundle="${bundle}"/></h2>
            </div>
        </header>

        <%@include file="reusable/nav.jspf"%>

        <article>
            <%@include file="reusable/statusMessage.jspf"%>
            <h3><fmt:message key="login.descriptionHeader" bundle="${bundle}"/></h3>
            <p><fmt:message key="login.description" bundle="${bundle}"/></p>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>

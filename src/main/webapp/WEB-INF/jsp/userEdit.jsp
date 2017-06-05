<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%@include file="reusable/header.jspf"%>
</head>

<body>
<div class="container">
    <header>
        <div class="container">
            <%@include file="reusable/greeting.jspf"%>
            <h3><fmt:message key="user.edit" bundle="${bundle}"/></h3>
        </div>
    </header>

    <nav><%@include file="reusable/nav.jspf"%></nav>

    <article>
        <div>
            <%@include file="reusable/statusMessage.jspf"%>
            <form class="form-horizontal" action="races" method="POST">
                <div>
                    <input class="form-control" name="action" value="userSaveEdited" type="hidden"/>
                    <input class="form-control" name="userId" type="hidden" value="${user.id}"/>
                    <input class="form-control" name="accountId" type="hidden" value="${user.account.id}"/>
                </div>

                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="name"><fmt:message key="user.name" bundle="${bundle}"/>:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="name" name="name" value="${user.name}" type="text" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="login"><fmt:message key="login.login" bundle="${bundle}"/>:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="login" name="login" value="${user.login}" type="text" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="password"><fmt:message key="login.password" bundle="${bundle}"/>:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="password" name="password" value="${user.password}" type="text" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="role"><fmt:message key="user.role" bundle="${bundle}"/>:</label>
                    </div>
                    <div class="col-sm-3">
                        <select class="form-control" id="role" name="userType">
                            <option disabled hidden>Choose user role:</option>
                            <c:forEach var="userType" items="${userTypes}">
                                <option <c:if test="${userType == user.userType}">selected
                                        </c:if>
                                    value="${userType}">${userType.toString()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="balance"><fmt:message key="user.balance" bundle="${bundle}"/>:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="balance" name="balance" value="${user.account.balance.value}"
                               type="number" min="0" step="0.01"/>
                    </div>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-floppy-disk">
                        </span> <fmt:message key="user.saveEdited" bundle="${bundle}"/></button>
                </div>
            </form>

            <form class="form-horizontal" action="races" method="GET">
                <div>
                    <input class="form-control" name="action" value="users" type="hidden"/>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left">
                        </span> <fmt:message key="app.returnToUsers" bundle="${bundle}"/></button>
                </div>
            </form>
        </div>
    </article>

    <footer><%@include file="reusable/footer.jspf"%></footer>
</div>
</body>
</html>


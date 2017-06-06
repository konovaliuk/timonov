<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="reusable/header.jspf"%>
</head>

<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <%@include file="reusable/greeting.jspf"%>
                <h3><fmt:message key="users.inSystem" bundle="${bundle}"/></h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>
                <h3><fmt:message key="app.users" bundle="${bundle}"/></h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th><fmt:message key="login.name" bundle="${bundle}"/></th>
                        <th><fmt:message key="login.login" bundle="${bundle}"/></th>
                        <th><fmt:message key="login.password" bundle="${bundle}"/></th>
                        <th><fmt:message key="user.role" bundle="${bundle}"/></th>
                        <th><fmt:message key="user.balance" bundle="${bundle}"/></th>
                        <th><fmt:message key="user.bets" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.edit" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.delete" bundle="${bundle}"/></th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.login}</td>
                            <td>${user.password}</td>
                            <td>${user.userType.toString()}</td>
                            <td align="right">${user.account.balance.value}</td>
                            <td>
                                <c:if test="${user.userType.ordinal() > 1}">
                                    <a href="races?action=betsByUser&userId=${user.id}">
                                        <fmt:message key="user.bets" bundle="${bundle}"/></a>
                                </c:if>
                            </td>
                            <td><a href="races?action=userEdit&userId=${user.id}">
                                <fmt:message key="app.edit" bundle="${bundle}"/>
                            </a></td>
                            <td><a href="races?action=userDelete&userId=${user.id}">
                                <fmt:message key="app.delete" bundle="${bundle}"/></a></td>
                        </tr>
                    </c:forEach>
                </table>

                <form class="form-inline" action="races" method="POST">
                    <div class="form-group col-sm-2">
                        <input class="form-control" name="action" value="userAdd" type="hidden"/>
                        <label class="control-label" for="name"><fmt:message key="user.addName" bundle="${bundle}"/></label>
                        <input class="form-control" id="name" name="name" type="text"
                               placeholder="<fmt:message key="login.name" bundle="${bundle}"/>" required
                               value="${userWithInputError.name}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="login"><fmt:message key="login.login" bundle="${bundle}"/>:</label>
                        <input class="form-control" id="login" name="login" type="text"
                               placeholder="<fmt:message key="login.login" bundle="${bundle}"/>" required
                               value="${userWithInputError.login}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="password"><fmt:message key="login.password" bundle="${bundle}"/>:</label>
                        <input class="form-control" id="password" name="password" type="password"
                               placeholder="<fmt:message key="login.password" bundle="${bundle}"/>" required
                               value="${userWithInputError.password}"
                        />
                    </div>
                    <div class="form-group col-sm-1">
                        <label class="control-label" for="role"><fmt:message key="user.role" bundle="${bundle}"/>:</label>
                        <select class="form-control" id="role" name="userType">
                            <option selected disabled hidden><fmt:message key="login.name" bundle="${bundle}"/></option>
                            <c:forEach var="userType" items="${userTypes}">
                                <option value="${userType}"
                                        <c:if test="${userWithInputError.userType == userType}">
                                            selected
                                        </c:if>
                                >${userType.toString()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="balance"><fmt:message key="user.balance" bundle="${bundle}"/>:</label>
                        <input class="form-control" id="balance" name="balance" type="number" min="0" step="0.01"
                               placeholder="<fmt:message key="user.balance" bundle="${bundle}"/>"
                               value="${userWithInputError.account.balance.value}"
                        />
                    </div>
                    <br>
                    <div class="form-group col-sm-1">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-plus-sign"></span> <fmt:message key="user.add" bundle="${bundle}"/></button>
                    </div>
                </form>
                <br>
                <br>
                <form class="form-horizontal" action="races" method="GET">
                    <div>
                        <input class="form-control" name="action" value="main" type="hidden"/>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left">
                            </span> <fmt:message key="app.returnToHomePage" bundle="${bundle}"/></button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>

</div>
</body>
</html>



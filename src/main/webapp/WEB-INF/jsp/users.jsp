<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="items/header.jspf"%>
</head>

<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <%@include file="items/greeting.jspf"%>
                <h3>Users of World Horse Racing tour</h3>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="items/statusMessage.jspf"%>
                <h3>Users</h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Login</th>
                        <th>Password</th>
                        <th>Role</th>
                        <th>Balance</th>
                        <th>User bets</th>
                        <th>Edit user</th>
                        <th>Delete user</th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.id}</td>
                            <td><a href="races?action=user&id=${user.id}">${user.name}</a></td>
                            <td>${user.login}</td>
                            <td>${user.password}</td>
                            <td>${user.userType.toString()}</td>
                            <td align="right">${user.account.balance.value}</td>
                            <td>
                                <c:if test="${user.userType.ordinal() > 1}">
                                    <a href="races?action=betsByUser&userId=${user.id}">User bets</a>
                                </c:if>
                            </td>
                            <td><a href="races?action=userEdit&userId=${user.id}">Edit user</a></td>
                            <td><a href="races?action=userDelete&userId=${user.id}">Delete user</a></td>
                        </tr>
                    </c:forEach>
                </table>

                <form class="form-inline" action="races" method="POST">
                    <div class="form-group col-sm-2">
                        <input class="form-control" name="action" value="userAdd" type="hidden"/>
                        <label class="control-label" for="name">Add user, name:</label>
                        <input class="form-control" id="name" name="name" type="text" placeholder="User name" required
                               value="${userWithInputError.name}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="login">Login:</label>
                        <input class="form-control" id="login" name="login" type="text" placeholder="User name" required
                               value="${userWithInputError.login}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="password">Password:</label>
                        <input class="form-control" id="password" name="password" type="text" placeholder="Password" required
                               value="${userWithInputError.password}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="role">Role:</label>
                        <select class="form-control" id="role" name="userType">
                            <option selected disabled hidden>Choose user role:</option>
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
                        <label class="control-label" for="balance">Balance:</label>
                        <input class="form-control" id="balance" name="balance" type="number" min="0" step="0.01"
                               placeholder="balance"
                               value="${userWithInputError.account.balance.value}"
                        />
                    </div>
                    <br>
                    <div class="form-group col-sm-1">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-plus-sign"></span> Add user</button>
                    </div>
                </form>
                <br>
                <br>
                <form class="form-horizontal" action="races?action=home" method="GET">
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left"></span> Return to home page</button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>

</div>
</body>
</html>



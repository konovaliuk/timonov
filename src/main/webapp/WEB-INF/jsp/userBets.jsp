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
            <%@include file="reusable/greeting.jspf"%>
            <div class="container">
                <h3><fmt:message key="user.betsPageName" bundle="${bundle}"/>Your account data and bets</h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>

                <h4></h4>
                <table class="table table-striped">
                    <tr>
                        <th><fmt:message key="user.name" bundle="${bundle}"/></th>
                        <th><fmt:message key="login.login" bundle="${bundle}"/></th>
                        <th><fmt:message key="user.balance" bundle="${bundle}"/></th>
                    </tr>
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.login}</td>
                        <td>${user.account.balance.toString()}</td>
                    </tr>
                </table>

                <h4><fmt:message key="user.yourBets" bundle="${bundle}"/></h4>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th><fmt:message key="bet.betType" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.betRate" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.status" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.name" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.betSum" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.wonSum" bundle="${bundle}"/></th>
                    </tr>
                    <c:forEach var="bet" items="${userBets}" varStatus="loop">
                        <tr>
                            <td>${bet.odds.betType.toString()}</td>
                            <td align="center">${bet.odds.getValue()}</td>
                            <td>${bet.betStatus.toString()}</td>
                            <td>${listBetHorses.get(loop.index).horse.name}</td>
                            <td>${bet.sum.toString()}</td>
                                <%--TODO with custom tag--%>
                            <td>
                                <c:if test="${bet.betStatus == betStatusPaid}">
                                    ${bet.sum.toString()} * ${bet.odds.oddsValue}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <form action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="races" type="hidden"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left">
                            </span> <fmt:message key="app.returnToRaces" bundle="${bundle}"/></button>
                    </div>
                </form>

            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>

</body>
</html>

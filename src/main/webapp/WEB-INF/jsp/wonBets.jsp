<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="reusable/header.jspf"%>
</head>

<body>
<%@include file="/WEB-INF/jsp/reusable/i18n.jspf"%>
<div>
    <div class="container">
        <header>
            <%@include file="reusable/greeting.jspf"%>
            <div class="container">
                <h3><fmt:message key="race.wonBetsPage" bundle="${bundle}"/> ${race.location.name}, ${race.location.country.name}
                    <fmt:message key="race.on" bundle="${bundle}"/> ${race.date}</h3>
            </div>
        </header>

        <nav class="navbar navbar-default">
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>

                <h4><fmt:message key="race.info" bundle="${bundle}"/></h4>
                <table class="table table-striped">
                    <tr>
                        <th><fmt:message key="app.location" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.country" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.date" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.status" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.betSum" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.paidSum" bundle="${bundle}"/></th>
                    </tr>
                    <tr>
                        <td>${race.location.name}</td>
                        <td>${race.location.country.name}</td>
                        <td>${race.date}</td>
                        <td>${race.raceStatus.toString()}</td>
                        <td>${race.betSum.toString()}</td>
                        <td>${race.paidSum.toString()}</td>
                    </tr>
                </table>

                <h4><fmt:message key="race.wonBets" bundle="${bundle}"/></h4>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th><fmt:message key="bet.clientName" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.clientLogin" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.betType" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.betRate" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.name" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.betSum" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.wonSum" bundle="${bundle}"/></th>
                    </tr>
                    <c:forEach var="bet" items="${wonBets}" varStatus="loop">
                        <tr>
                            <td>${bet.user.name}</td>
                            <td>${bet.user.login}</td>
                            <td>${bet.odds.betType.toString()}</td>
                            <td align="center">${bet.odds.getValue()}</td>
                            <td>${listBetHorses.get(loop.index).horse.name}</td>
                            <td>${bet.sum.toString()}</td>
                            <td><custom:wonSum betWonSum="${bet}"/></td>
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

                <form action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="raceManage" type="hidden"/>
                        <input class="form-control" name="raceId" value="${race.id}" type="hidden"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-right">
                            </span> <fmt:message key="race.manage" bundle="${bundle}"/></button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>

</body>
</html>
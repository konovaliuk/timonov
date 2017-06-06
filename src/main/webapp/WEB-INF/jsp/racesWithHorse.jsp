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
            <div class="container">
                <%@include file="reusable/greeting.jspf"%>
                <h3><fmt:message key="races.pageName" bundle="${bundle}"/></h3>
            </div>
        </header>

        <nav class="navbar navbar-default">
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div>
                <%@include file="reusable/statusMessage.jspf"%>
                <h3><fmt:message key="horse.name" bundle="${bundle}"/></h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th><fmt:message key="horse.name" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.yearOfBirth" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.totalRaces" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.wonRaces" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.edit" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.delete" bundle="${bundle}"/></th>
                    </tr>
                    <tr>
                        <td>${horse.name}</td>
                        <td>${horse.yearOfBirth}</td>
                        <td>${horse.totalRaces}</td>
                        <td>${horse.wonRaces}</td>
                        <td><a href="races?action=horseEdit&horseId=${horse.id}">
                            <fmt:message key="app.edit" bundle="${bundle}"/></a></td>
                        <td><a href="races?action=horseDelete&horseId=${horse.id}">
                            <fmt:message key="app.delete" bundle="${bundle}"/>
                        </a></td>
                    </tr>
                </table>

                <h3><fmt:message key="app.races" bundle="${bundle}"/></h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th><fmt:message key="app.date" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.location" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.country" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.status" bundle="${bundle}"/></th>
                        <th><fmt:message key="races.raceBets" bundle="${bundle}"/></th>
                    </tr>
                    <c:forEach var="race" items="${races}">
                        <tr>
                            <td>${race.date}</td>
                            <td>${race.location.name}</td>
                            <td>${race.location.country.name}</td>
                            <td>${race.raceStatus.toString()}</td>
                            <td><a href="races?action=race&raceId=${race.id}"><fmt:message key="races.raceBets" bundle="${bundle}"/>
                            </a></td>
                        </tr>
                    </c:forEach>
                </table>

                <form class="form-horizontal" action="races" method="GET">
                    <div>
                        <input class="form-control" name="action" value="horses" type="hidden"/>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left">
                            </span> <fmt:message key="app.returnToHorses" bundle="${bundle}"/></button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>

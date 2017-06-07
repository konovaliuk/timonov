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
                <h3><fmt:message key="race.raceAt" bundle="${bundle}"/> ${race.location.name},
                    ${race.location.country.name}<fmt:message key="race.on" bundle="${bundle}"/> ${race.date}
                </h3>
            </div>
        </header>

        <nav class="navbar navbar-default">
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <%@include file="reusable/statusMessage.jspf"%>
            <div>
                <h4><fmt:message key="race.info" bundle="${bundle}"/></h4>
                <table class="table table-striped">
                    <tr>
                        <th><fmt:message key="app.location" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.country" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.date" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.status" bundle="${bundle}"/></th>
                    </tr>
                    <tr>
                        <td>${race.location.name}</td>
                        <td>${race.location.country.name}</td>
                        <td>${race.date}</td>
                        <td>${race.raceStatus.toString()}</td>
                    </tr>
                </table>

                <h4><fmt:message key="app.horsesInRace" bundle="${bundle}"/></h4>
                <table class="table table-striped">
                    <tr>
                        <th><fmt:message key="horse.name" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.yearOfBirth" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.totalRaces" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.wonRaces" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.bestBet" bundle="${bundle}"/></th>
                        <c:if test="${userRole == roleClient && race.raceStatus == openRaceStatus}">
                            <th><fmt:message key="horse.allOdds" bundle="${bundle}"/></th>
                        </c:if>
                        <c:if test="${userRole == roleBookie}">
                            <c:choose>
                                <c:when test="${race.raceStatus.ordinal() <= openRaceStatus.ordinal()}">
                                    <th><fmt:message key="horse.setBetRates" bundle="${bundle}"/></th>
                                </c:when>
                                <c:otherwise>
                                    <th><fmt:message key="horse.viewBetRates" bundle="${bundle}"/></th>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${userRole == roleAdmin}">
                            <th><fmt:message key="horse.viewBetRates" bundle="${bundle}"/></th>
                        </c:if>
                        <th><fmt:message key="horse.placeAtFinish" bundle="${bundle}"/></th>
                    </tr>
                    <c:forEach var="horseInRace" items="${horsesInRace}">
                        <tr>
                            <c:set var="oddsValues" value="${horseInRace.oddsValues}"/>
                            <td>${horseInRace.horse.name}</td>
                            <td>${horseInRace.horse.yearOfBirth}</td>
                            <td>${horseInRace.horse.totalRaces}</td>
                            <td>${horseInRace.horse.wonRaces}</td>
                            <c:choose>
                                <c:when test="${oddsValues.size() > 0}">
                                    <td>${oddsValues[0].betType.toString()}:
                                        ${oddsValues[0].total} / ${oddsValues[0].chances}</td>
                                </c:when>
                                <c:otherwise>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${userRole == roleClient && race.raceStatus == openRaceStatus}">
                                <td>
                                    <a href="races?action=horseInRace&horseInRaceId=${horseInRace.id}">
                                        <fmt:message key="horse.allOdds" bundle="${bundle}"/>
                                    </a>
                                </td>
                            </c:if>

                            <c:if test="${userRole == roleBookie}">
                                <td>
                                    <a href="races?action=horseInRaceBookie&horseInRaceId=${horseInRace.id}">
                                    <c:choose>
                                        <c:when test="${race.raceStatus.ordinal() <= openRaceStatus.ordinal()}">
                                            <fmt:message key="horse.setBetRates" bundle="${bundle}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="horse.viewBetRates" bundle="${bundle}"/>
                                        </c:otherwise>
                                    </c:choose>
                                    </a>
                                </td>
                            </c:if>
                            <c:if test="${userRole == roleAdmin}">
                                <td>
                                    <a href="races?action=horseInRaceBookie&horseInRaceId=${horseInRace.id}">
                                        <fmt:message key="horse.viewBetRates" bundle="${bundle}"/>
                                    </a>
                                </td>
                            </c:if>
                            <td>
                                <c:if test = "${horseInRace.finishPlace > 0}">${horseInRace.finishPlace}</c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <form class="form-horizontal" action="races" method="GET">
                    <div>
                        <input class="form-control" name="action" value="races" type="hidden"/>
                    </div>
                    <div class="col-sm-2">
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


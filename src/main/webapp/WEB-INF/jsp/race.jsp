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
                <h3>Race results</h3>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div>
                <h4>Race info</h4>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Location</th>
                        <th>Country</th>
                        <th>Date</th>
                        <th>Status</th>
                    </tr>
                    <tr>
                        <td>${race.id}</td>
                        <td>${race.location.name}</td>
                        <td>${race.location.country.name}</td>
                        <td>${race.date}</td>
                        <td>${race.raceStatus.toString()}</td>
                    </tr>
                </table>

                <h4>Horses in race</h4>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year of birth</th>
                        <th>Total races</th>
                        <th>Won races</th>
                        <th>Best Bet</th>
                        <th>Odds</th>
                        <th>More bets</th>
                        <th>Set odds</th>
                        <th>Place at finish</th>
                    </tr>
                    <c:forEach var="horseInRace" items="${horsesInRace}">
                        <tr>
                            <c:set var="oddsValues" value="${horseInRace.oddsValues}"/>

                            <td>${horseInRace.horse.id}</td>
                            <td><a href="races?action=horse&horseId=${horse.id}">${horseInRace.horse.name}</a></td>
                            <td>${horseInRace.horse.yearOfBirth}</td>
                            <td>${horseInRace.horse.totalRaces}</td>
                            <td>${horseInRace.horse.wonRaces}</td>
                            <c:choose>
                                <c:when test="${oddsValues.size() > 0}">
                                    <td>${oddsValues[0].betType.toString()}</td>
                                    <td align="center">${oddsValues[0].total} / ${oddsValues[0].chances}</td>
                                </c:when>
                                <c:otherwise>
                                    <td></td>
                                    <td></td>
                                </c:otherwise>
                            </c:choose>
                            <td><a href="races?action=horseInRace&horseInRaceId=${horseInRace.id}">Make bet</a></td>
                            <td><a href="races?action=horseInRaceBookie&horseInRaceId=${horseInRace.id}">Set odds</a></td>
                            <td><c:if test = "${horseInRace.finishPlace > 0}">${horseInRace.finishPlace}</c:if></td>
                        </tr>
                    </c:forEach>
                </table>
                <form class="form-horizontal" action="races" method="GET">
                    <div>
                        <input class="form-control" name="action" value="races" type="hidden"/>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left"></span> Return to races</button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>


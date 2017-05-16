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
                <h2>Race results</h2>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div>
                <h2>Race info</h2>
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
                        <td>${race.location}</td>
                        <td>${race.country}</td>
                        <td>${race.date}</td>
                        <td>${race.raceStatus}</td>
                    </tr>
                </table>
                <br>
                <h3>Horses in race</h3>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year of birth</th>
                        <th>Total races</th>
                        <th>Won races</th>
                        <th>Bet type</th>
                        <th>Odds</th>
                        <th>More bets</th>
                    </tr>
                    <c:forEach var="horseInRace" items="${horsesInRace}">
                        <tr>
                            <c:set var="oddsValues" value="${horseInRace.oddsValues}"/>

                            <td>${horseInRace.horse.id}</td>
                            <td><a href="${raceUrl}">${horseInRace.horse.name}</a></td>
                            <td>${horseInRace.horse.yearOfBirth}</td>
                            <td>${horseInRace.horse.totalRaces}</td>
                            <td>${horseInRace.horse.wonRaces}</td>
                            <%--TODO if there is no values in oddsValues--%>
                            <td>${oddsValues[0].betType}</td>
                            <td>${oddsValues[0].total} / ${oddsValues[0].chances}</td>
                            <td><a href="/races?action=horse_in_race&id=${horseInRace.id}">More bets</a></td>
                            <td><c:if test = "${horseInRace.finishPlace > 0}">${horseInRace.finishPlace}</c:if></td>
                        </tr>
                    </c:forEach>
                </table>

            </div>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>


</div>
</body>
</html>


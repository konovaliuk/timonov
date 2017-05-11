<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/WEB-INF/css/index.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Horse Racing. Race</title>
</head>

<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <h2>Race results</h2>
            </div>
            <div class="signblock">
            </div>
        </header>

        <nav>
            <ul>
                <li><a href="/races?action=races">Races</a></li>
                <li><a href="/library/books">456</a></li>
                <li><a href="/library/classes">789</a></li>
                <li><a href="/error">ERROR</a></li>
            </ul>
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

                <h3>Horses in race</h3>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year of birth</th>
                        <th>Total races</th>
                        <th>Won races</th>
                        <th>Bet type</th>
                        <th>Rate</th>
                        <th>Place at finish</th>
                    </tr>
                    <c:forEach var="horseInRace" items="${horsesInRace}">
                        <c:url var="raceUrl" value="/horseracing/races"/>
                        <tr>
                            <td>${horseInRace.horse.id}</td>
                            <td><a href="${raceUrl}">${horseInRace.horse.name}</a></td>
                            <td>${horseInRace.horse.yearOfBirth}</td>
                            <td>${horseInRace.horse.totalRaces}</td>
                            <td>${horseInRace.horse.wonRaces}</td>
                            <td>${horseInRace.betType}</td>
                            <td>${horseInRace.odds.total} / ${horseInRace.odds.chances}</td>
                            <td>${horseInRace.finishPlace}</td>
                        </tr>
                    </c:forEach>
                </table>

            </div>
        </article>

        <footer>Copyright &copy; Alexey Timonov</footer>
    </div>


</div>
</body>
</html>


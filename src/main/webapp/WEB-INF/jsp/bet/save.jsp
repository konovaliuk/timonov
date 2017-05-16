<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="../items/header.jspf"%>
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
            <ul>
                <li><a href="/races?action=races">Races</a></li>
                <li><a href="/error">ERROR</a></li>
            </ul>
        </nav>

        <article>
            <div class="form-horizontal">
                <h2>You have made a bet:</h2>

                <c:set var="horse" value="${bet.horseInRace.horse}"/>
                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">Race location:</label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${race.location}, ${race.country}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">Race status & date:</label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${race.raceStatus}, ${race.date}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">Horse name & year of birth: </label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${horse.name}, ${horse.yearOfBirth}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">Total races / won races: </label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${horse.totalRaces} / ${horse.wonRaces}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">Bet type & sum: </label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${bet.betType}, ${bet.sum}</label>
                    </div>
                </div>

                <p><a href="/races?action=horse_in_race&id=${bet.horseInRace.id}">More bets</a></p>
                <form class="form-inline" action="/races?action=horse_in_race&id=${bet.horseInRace.id}" method="GET">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left"></span> Return to bets</button>
                </form>

                <form class="form-inline" action="/races?action=race&id=${race.id}" method="GET">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left"></span> Return to race</button>
                </form>

            </div>
        </article>

        <footer><%@include file="../items/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>


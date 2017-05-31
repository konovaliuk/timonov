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
                <h3>Made bet</h3>
            </div>
        </header>

        <%@include file="../items/nav.jspf"%>

        <article>
            <%@include file="../items/statusMessage.jspf"%>
            <div class="form-horizontal">
                <h2>You have made a bet:</h2>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">Race location:</label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${race.location.name}, ${race.location.country.name}</label>
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
                        <label class="control-label">${bet.odds.betType}, ${bet.sum}</label>
                    </div>
                </div>

                <form class="form-inline" action="races" method="GET">
                    <input class="form-control" name="action" value="horseInRace" type="hidden"/>
                    <input class="form-control" name="horseInRaceId" value="${bet.odds.horseInRaceId}" type="hidden"/>
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-right"></span> One more bet
                    </button>
                </form>

                <form class="form-inline" action="races" method="GET">
                    <input class="form-control" name="action" value="race" type="hidden"/>
                    <input class="form-control" name="raceId" value="${race.id}" type="hidden"/>
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


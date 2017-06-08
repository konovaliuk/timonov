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
            <div class="container">
                <h3><fmt:message key="bet.madeBet" bundle="${bundle}"/>t</h3>
            </div>
        </header>

        <%@include file="reusable/nav.jspf"%>

        <article>
            <%@include file="reusable/statusMessage.jspf"%>
            <div class="form-horizontal">
                <h2><fmt:message key="bet.youveMadeBet" bundle="${bundle}"/></h2>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label"><fmt:message key="app.location" bundle="${bundle}"/>:</label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${race.location.name}, ${race.location.country.name}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">
                            <fmt:message key="bet.raceStarusAndDate" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${race.raceStatus.toString()}, ${race.date}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label">
                            <fmt:message key="bet.horseNameAndYear" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${horse.name}, ${horse.yearOfBirth}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label"><fmt:message key="bet.totalAndWonRaces" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${horse.totalRaces} / ${horse.wonRaces}</label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-4">
                        <label class="control-label"><fmt:message key="bet.typeAndSum" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-8">
                        <label class="control-label">${bet.odds.betType.toString()}, ${bet.sum}</label>
                    </div>
                </div>

                <form class="form-inline" action="races" method="GET">
                    <input class="form-control" name="action" value="horseInRace" type="hidden"/>
                    <input class="form-control" name="horseInRaceId" value="${bet.odds.horseInRaceId}" type="hidden"/>
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-right">
                        </span> <fmt:message key="bet.oneMore" bundle="${bundle}"/>
                    </button>
                </form>

                <form class="form-inline" action="races" method="GET">
                    <input class="form-control" name="action" value="race" type="hidden"/>
                    <input class="form-control" name="raceId" value="${race.id}" type="hidden"/>
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left">
                        </span> <fmt:message key="app.returnToRace" bundle="${bundle}"/></button>
                </form>

            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>


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
                <%@include file="reusable/greeting.jspf"%>
                <h3>Make bet on horse ${horseInRace.horse.name}, race at ${race.location.name},
                ${race.location.country.name}</h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div>
                <%@include file="reusable/raceAndHorse.jspf"%>
                <br>
                <h3>Available bets</h3>
                <table class="table table-striped">
                    <tr>
                        <th></th>
                        <th>Bet type</th>
                        <th>Odds</th>
                        <th>Input bet sum</th>
                        <th>Push to bet</th>
                    </tr>
                    <c:forEach var="odds" items="${horseInRace.oddsValues}">
                        <tr>
                            <form class="form-horizontal" method="POST" action="races">
                                <div class="form-group">
                                    <td>
                                        <div>
                                            <input class="form-control" name="action" type="hidden" value="makeBet"/>
                                            <%--<input class="form-control" name="horse_in_race" type="hidden" value="${horseInRace.id}"/>--%>
                                            <input class="form-control" name="oddsId" type="hidden" value="${odds.id}"/>
                                            <%--<input class="form-control" name="userId" type="hidden" value="${user.id}"/>--%>
                                        </div>
                                    </td>
                                    <td>${odds.betType.toString()}</td>
                                    <td align="center">${odds.total} / ${odds.chances}</td>
                                    <td>
                                        <div>
                                            <input class="form-control" name="sum" type="number" min="0.01" step="0.01"/>
                                        </div>
                                    </td>
                                    <td><button class="btn btn-primary" type="submit">
                                            <span class="glyphicon glyphicon-floppy-disk"></span> Make bet!</button>
                                    </td>
                                </div>
                            </form>
                        </tr>
                    </c:forEach>
                </table>
                <form class="form-horizontal" action="/races" method="GET">
                    <div>
                        <input class="form-control" name="action" value="race" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left"></span> Return to race</button>
                    </div>
                </form>

            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>


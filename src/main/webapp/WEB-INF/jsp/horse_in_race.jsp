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
                <h2>Horse in Race</h2>
                <h3>Horse: ${horseInRace.horse.name}, ${horseInRace.horse.yearOfBirth}</h3>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div>
                <h3>Race</h3>
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
                <h3>Horse</h3>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year of birth</th>
                        <th>Total races</th>
                        <th>Won races</th>
                    </tr>
                    <tr>
                        <c:set var="horse" value="${horseInRace.horse}"/>
                        <td>${horse.id}</td>
                        <td><a href="${raceUrl}">!!!${horse.name}</a></td>
                        <td>${horse.yearOfBirth}</td>
                        <td>${horse.totalRaces}</td>
                        <td>${horse.wonRaces}</td>
                    </tr>
                </table>
                <br>
                <h3>Available bets</h3>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Bet type</th>
                        <th>Odds</th>
                        <th>Input bet sum</th>
                        <th>Push to bet</th>
                    </tr>
                    <c:forEach var="odds" items="${horseInRace.oddsValues}">
                        <tr>
                            <form class="form-horizontal" method="POST" action="/races">
                                <div class="form-group">
                                    <td>
                                        <div>
                                            <input class="form-control" name="action" type="hidden" value="create_bet"/>
                                            <input class="form-control" name="horse_in_race" type="hidden" value="${horseInRace.id}"/>
                                            <input class="form-control" name="odds" type="hidden" value="${odds.id}"/>
                                        </div>
                                    </td>
                                    <td>${odds.betType}</td>
                                    <td>${odds.total} / ${odds.chances}</td>
                                    <td>
                                        <div>
                                            <input class="form-control" name="sum" type="number"/>
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

            </div>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>


</div>
</body>
</html>


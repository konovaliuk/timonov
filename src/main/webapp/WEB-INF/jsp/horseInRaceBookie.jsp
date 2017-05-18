<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="items/header.jspf"%>
</head>

<body>
<div class="container">
    <header>
        <div class="container">
            <h3>Horse in Race for Bookmaker</h3>
        </div>
    </header>

    <nav><%@include file="items/nav.jspf"%></nav>

    <article>
    <div>
        <h4>Race</h4>
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
                <td>${race.raceStatus.toString()}</td>
            </tr>
        </table>
        <h4>Horse</h4>
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
                <td><a href="/races?action=horse&id=${horse.id}">${horse.name}</a></td>
                <td>${horse.yearOfBirth}</td>
                <td>${horse.totalRaces}</td>
                <td>${horse.wonRaces}</td>
            </tr>
        </table>
        <h4>Existing bets</h4>
        <table class="table table-striped">
            <tr>
                <th>ID</th>
                <th>Bet type</th>
                <th>Odds</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="odds" items="${horseInRace.oddsValues}">
                <tr>
                    <div class="form-group">
                        <td>${odds.id}</td>
                        <td>${odds.betType.toString()}</td>
                        <td>${odds.total} / ${odds.chances}</td>
                        <td><a href="/races?action=oddsEdit&id=${odds.id}&horseInRace=${horseInRace.id}">Edit</a></td>
                        <td><a href="/races?action=oddsDelete&id=${odds.id}&horseInRace=${horseInRace.id}">Delete</a></td>
                    </div>
                </tr>
            </c:forEach>
            <tr>
        </table>
        <form class="form-inline" action="/races" method="POST">
            <div class="form-group col-sm-4">
                <input class="form-control" name="action" value="oddsSave" type="hidden"/>
                <input class="form-control" name="horseInRace" type="hidden" value="${horseInRace.id}"/>
                <label class="control-label">Add odds:</label>
                <select class="form-control" name="betType">
                    <option selected disabled hidden>Choose bet type:</option>
                    <c:forEach var="betType" items="${betTypes}">
                        <option value=${betType}>${betType.toString()}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group col-sm-4">
                <label class="control-label">Total odds:</label>
                <input class="form-control" type="number" name="total" placeholder="total">
            </div>
            <div class="form-group col-sm-4">
                <label class="control-label">Odds to win:</label>
                <input class="form-control" type="number" name="chances" placeholder="chances">
            </div>
            <br>
            <br>
            <div class="col-sm-2">
                <button class="btn btn-primary" type="submit">
                    <span class="glyphicon glyphicon-plus-sign"></span> Add odds</button>
            </div>
        </form>
        <br>
        <br>
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

    <footer><%@include file="items/footer.jspf"%></footer>
</div>
</body>
</html>

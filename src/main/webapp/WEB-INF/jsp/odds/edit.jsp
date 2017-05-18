<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="../items/header.jspf"%>
</head>

<body>
<div class="container">
    <header>
        <div class="container">
            <h3>Horse in Race for Bookmaker</h3>
        </div>
    </header>

    <nav><%@include file="../items/nav.jspf"%></nav>

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
            <h4>Edit existing odds:</h4>
            <form class="form-horizontal" action="/races" method="POST">
                <div>
                    <input class="form-control" name="action" value="oddsSave" type="hidden"/>
                    <input class="form-control" name="horseInRace" type="hidden" value="${horseInRace.id}"/>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="id">ID:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="id" value="${odds.id}" readonly name="id"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="betType">Bet type:</label>
                    </div>
                    <div class="col-sm-3">
                        <select class="form-control" id="betType" name="betType">
                            <option disabled>Choose bet type:</option>
                            <c:forEach var="betType" items="${betTypes}">
                                <option <c:if test="${betType == odds.betType}">selected</c:if>
                                        value=${betType}>${betType.toString()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="total">Total odds:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="total" name="total" value="${odds.total}" type="number" min="1" step="1"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="chances">Odds to win:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="chances" name="chances" value="${odds.chances}" type="number" min="1" step="1"/>
                    </div>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-floppy-disk"></span> Save edited odds</button>
                </div>
            </form>

            <form class="form-horizontal" action="/races" method="GET">
                <div>
                    <input class="form-control" name="action" value="horseInRaceBookie" type="hidden"/>
                    <input class="form-control" name="horseInRace" type="hidden" value="${horseInRace.id}"/>
                </div>
                <div class="col-sm-1">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left"></span> Return without saving</button>
                </div>
            </form>
        </div>
    </article>

    <footer><%@include file="../items/footer.jspf"%></footer>
</div>
</body>
</html>
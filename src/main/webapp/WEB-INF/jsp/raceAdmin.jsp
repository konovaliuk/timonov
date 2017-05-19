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
                <h3>Fixate race results</h3>
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

                <form class="form-inline" action="/races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="raceStatusSave" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>
                    <div class="col-sm-3">
                        <label class="control-label" for="raceStatus">Change race status:</label>
                    </div>
                    <div class="col-sm-3">
                        <select class="form-control" id="raceStatus" name="raceStatus">
                            <option disabled>Choose race status:</option>
                            <c:forEach var="raceStatus" items="${raceStatuses}">
                                <c:if test="${raceStatus >= race.raceStatus}">
                                    <option <c:if test="${raceStatus == race.raceStatus}">selected</c:if>
                                            value=${raceStatus}>${raceStatus.toString()}
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-3">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-floppy-disk"></span> Change race status</button>
                    </div>
                </form>
                <br>
                <br>
                <h4>Horses in race</h4>
                <form class="form-inline" action="/races" method="POST">
                    <div class="form-group">
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
                                <th>Set odds</th>
                                <th>Place at finish</th>
                            </tr>
                            <c:forEach var="horseInRace" items="${horsesInRace}" varStatus="loop">
                                <tr>
                                    <c:set var="oddsValues" value="${horseInRace.oddsValues}"/>

                                    <td>${horseInRace.horse.id}</td>
                                    <td><a href="/races?action=horse&id=${horse.id}">${horseInRace.horse.name}</a></td>
                                    <td>${horseInRace.horse.yearOfBirth}</td>
                                    <td>${horseInRace.horse.totalRaces}</td>
                                    <td>${horseInRace.horse.wonRaces}</td>
                                        <c:choose>
                                            <c:when test="${oddsValues.size() > 0}">
                                                <td>${oddsValues[0].betType.toString()}</td>
                                                <td>${oddsValues[0].total} / ${oddsValues[0].chances}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td></td>
                                                <td></td>
                                            </c:otherwise>
                                        </c:choose>
                                    <td><a href="/races?action=horseInRace&id=${horseInRace.id}">More bets</a></td>
                                    <td><a href="/races?action=horseInRaceBookie&horseInRace=${horseInRace.id}">Set odds</a></td>
                                    <td>
                                        <input class="form-control" name="places${loop.index}"
                                        <c:if test = "${horseInRace.finishPlace > 0}">value="${horseInRace.finishPlace}"</c:if>
                                        />
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div>
                        <input class="form-control" name="action" value="racePlacesSave" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>
                    <div class="container">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-floppy-disk"></span> Save changes</button>
                    </div>
                </form>

                <form class="form-horizontal" action="/races" method="GET">
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


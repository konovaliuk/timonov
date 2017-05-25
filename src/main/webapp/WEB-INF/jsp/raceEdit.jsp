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
            <div class="container">
                <div class="error-message">
                    <h4>${errorMessage}</h4>
                </div>
                <h3>Edit race</h3>
                <form class="form-horizontal" action="/races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="raceSaveEdited" type="hidden"/>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="id">ID:</label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" id="id" name="id" value="${race.id}" readonly/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="location">Location:</label>
                        </div>
                        <div class="col-sm-3">
                            <select class="form-control" id="location" name="location">
                                <option disabled>Choose location:</option>
                                <c:forEach var="location" items="${locations}">
                                    <c:if test="${location.country.id == race.location.country.id}">
                                        <option <c:if test="${location.id == race.location.id}">selected</c:if>
                                                value=${location.id}>${location.name}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="country">Country:</label>
                        </div>
                        <div class="col-sm-3">
                            <select class="form-control" id="country" name="country">
                                <option disabled>Choose country:</option>
                                <c:forEach var="country" items="${countries}">
                                    <option <c:if test="${country.id == race.location.country.id}">selected</c:if>
                                            value=${country.id}>${country.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="date">Date:</label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" id="date" name="date" value="${race.date}" type="date"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="date">Status:</label>
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
                    </div>
                    <div class="container">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-floppy-disk"></span> Save edited race</button>
                    </div>
                </form>

                <%--    <table class="table table-striped">
                        <tr>
                            <th>ID</th>
                            <th>Location</th>
                            <th>Country</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Change status</th>
                        </tr>
                        <tr>
                            <td>${race.id}</td>
                            <td>${race.location.name}</td>
                            <td>${race.location.country.name}</td>
                            <td>${race.date}</td>
                            <td>
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
                            </td>
                            <td>
                                <button class="btn btn-primary" type="submit">
                                    <span class="glyphicon glyphicon-floppy-disk"></span> Change race status</button>
                            </td>
                        </tr>
                    </table>
                </form>--%>
                <br>
                <h3>Horses in race</h3>
                <form class="form-horizontal" action="/races" method="POST">
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
                            <th>Edit odds</th>
                            <th>Delete</th>
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
                                <td><a href="/races?action=horseInRaceDelete&horseInRace=${horseInRace.id}">Delete horse</a></td>
                                <td>
                                    <input class="form-control" name="places${loop.index}"
                                           <c:if test = "${horseInRace.finishPlace > 0}">value="${horseInRace.finishPlace}"</c:if>
                                    />
                                </td>
                            </tr>
                        </c:forEach>
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


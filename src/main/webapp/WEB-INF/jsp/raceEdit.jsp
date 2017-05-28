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
                <h3>Race admin page</h3>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="items/statusMessage.jspf"%>
                <h3>Edit race</h3>
                <form class="form-horizontal" action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="raceSaveEditedAttributes" type="hidden"/>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="id">ID:</label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" id="id" name="raceId" value="${race.id}" readonly/>
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
                                    <option <c:if test="${location.id == race.location.id}">selected</c:if>
                                                value=${location.id}>${location.name}, ${location.country.name}
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
                            <label class="control-label" for="raceStatus">Status:</label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" id="raceStatus" name="raceStatus"
                                   value="${race.raceStatus.toString()}" readonly/>
                        </div>
                    </div>
                    <div class="container">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-floppy-disk"></span> Save race attributes</button>
                    </div>
                </form>
                <br>
                <h3>Horses in race</h3>
                <form class="form-inline" action="races" method="POST">
                    <table class="table table-striped table-condensed">
                        <tr>
                            <th>Place at finish</th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Year of birth</th>
                            <th>Total races</th>
                            <th>Won races</th>
                            <th>Edit odds</th>
                            <th>Delete</th>
                        </tr>
                        <c:forEach var="horseInRace" items="${horsesInRace}" varStatus="loop">
                            <tr>
                                <c:set var="oddsValues" value="${horseInRace.oddsValues}"/>
                                <td>
                                    <input class="form-control" name="places${loop.index}"
                                           <c:if test = "${horseInRace.finishPlace > 0}">value="${horseInRace.finishPlace}"</c:if>
                                    />
                                </td>
                                <td>${horseInRace.horse.id}</td>
                                <td><a href="/races?action=horse&id=${horse.id}">${horseInRace.horse.name}</a></td>
                                <td>${horseInRace.horse.yearOfBirth}</td>
                                <td>${horseInRace.horse.totalRaces}</td>
                                <td>${horseInRace.horse.wonRaces}</td>
                                <td><a href="/races?action=horseInRaceBookie&horseInRace=${horseInRace.id}">Set odds</a></td>
                                <td><a href="/races?action=horseInRaceDelete&horseInRace=${horseInRace.id}">Delete horse</a></td>
                            </tr>
                        </c:forEach>
                    </table>

                    <div>
                        <input class="form-control" name="action" value="racePlacesSave" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>
                    <div class="container">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-floppy-disk"></span> Save finish places</button>
                    </div>
                </form>

                <form class="form-inline" action="races" method="POST">
                    <div class="form-group col-sm-2">
                        <input class="form-control" name="action" value="horseInRaceAdd" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>

                    <div class="form-group col-sm-2">
                        <label class="control-label" for="horse">Add horse:</label>
                    </div>

                    <div class="form-group col-sm-4">
                        <select class="form-control" id="horse" name="horseId">
                            <option selected disabled hidden>Choose horse:</option>
                            <c:forEach var="horse" items="${horses}">
                                <option value=${horse.id}>${horse.name}, ${horse.yearOfBirth}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-plus-sign"></span> Add chosen horse</button>
                    </div>
                </form>
                <br>
                <br>
                <div class="container">
                    <form class="form-horizontal" action="races" method="GET">
                        <div>
                            <input class="form-control" name="action" value="races" type="hidden"/>
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-triangle-left"></span> Return to races</button>
                        </div>
                    </form>

                    <form class="form-inline" action="races" method="GET">
                        <div>
                            <input class="form-control" name="action" value="changeRaceStatus" type="hidden"/>
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="submit"
                                    <c:if test="${race.raceStatus > raceStatuses[5]}">disabled
                                    </c:if>
                                <span class="glyphicon glyphicon-triangle-right"></span> \
                                Change race status to (${race.(raceStatus + 1).toString()})
                            </button>
                        </div>
                    </form>

                    <form class="form-inline" action="races" method="GET">
                        <div>
                            <input class="form-control" name="action" value="cancelRace" type="hidden"/>
                            <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-triangle-right"></span> Cancel race</button>
                        </div>
                    </form>
                </div>
            </div>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>
</div>

</body>
</html>


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


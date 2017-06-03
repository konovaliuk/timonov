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
                <h3>Race admin page</h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>
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
                            <select class="form-control" id="location" name="location"
                                <c:if test="${race.raceStatus.ordinal() > raceStatusBeingFormed.ordinal()}">disabled
                                </c:if>
                            >
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
                            <input class="form-control" id="date" name="date" value="${race.date}" type="date"
                                <c:if test="${race.raceStatus.ordinal() > raceStatusBeingFormed.ordinal()}">readonly
                                </c:if>
                            />
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
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit"
                            <c:if test="${race.raceStatus.ordinal() != raceStatusBeingFormed.ordinal()}">disabled
                            </c:if>
                        >
                            <span class="glyphicon glyphicon-floppy-disk"></span> Save race attributes</button>
                    </div>
                </form>
                <br>
                <h3>Horses in race</h3>

                <form class="form-horizontal" action="races" method="POST">
                    <table class="table table-striped table-condensed">
                        <tr>
                            <th>Place at finish</th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Year of birth</th>
                            <th>Total races</th>
                            <th>Won races</th>
                            <th>View odds</th>
                            <th>Remove</th>
                        </tr>
                        <c:forEach var="horseInRace" items="${horsesInRace}" varStatus="loop">
                            <tr>
                                <c:set var="oddsValues" value="${horseInRace.oddsValues}"/>
                                <td>
                                    <c:if test="${race.raceStatus.ordinal() == raceStatusFinished.ordinal()}">
                                        <input class="form-control" name="places${loop.index}"
                                               type="number" min="1" step="1" required
                                                <c:if test="${inputtedPlaces.get(loop.index) > 0}">
                                                value="${inputtedPlaces.get(loop.index)}"
                                            </c:if>
                                            <c:if test = "${horseInRace.finishPlace > 0}">value="${horseInRace.finishPlace}"
                                            </c:if>
                                        />
                                    </c:if>
                                    <c:if test="${race.raceStatus.ordinal() > raceStatusFinished.ordinal() &&
                                            race.raceStatus.ordinal() <= raceStatusWinsPaid.ordinal()}">
                                        ${horseInRace.finishPlace}
                                    </c:if>
                                </td>
                                <td>${horseInRace.horse.id}</td>
                                <td><a href="races?action=horse&id=${horse.id}">${horseInRace.horse.name}</a></td>
                                <td>${horseInRace.horse.yearOfBirth}</td>
                                <td>${horseInRace.horse.totalRaces}</td>
                                <td>${horseInRace.horse.wonRaces}</td>
                                <td><a href="races?action=horseInRaceBookie&horseInRaceId=${horseInRace.id}">View odds</a></td>
                                <td>
                                    <c:if test="${race.raceStatus.ordinal() == 0}">
                                        <a href="races?action=horseInRaceDelete&horseInRace=${horseInRace.id}">
                                            Remove horse</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <div>
                        <input class="form-control" name="action" value="racePlacesSave" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>

                    <c:if test="${race.raceStatus.ordinal() == raceStatusFinished.ordinal()}">
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-floppy-disk"></span> Save finish places</button>
                        </div>
                    </c:if>
                </form>

                <c:if test="${race.raceStatus.ordinal() == raceStatusBeingFormed.ordinal()}">
                    <form class="form-horizontal" action="races" method="POST">
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
                        <div class="form-group col-sm-4">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-plus-sign"></span> Add chosen horse</button>
                        </div>
                    </form>
                    <hr>
                </c:if>

                <form action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="races" type="hidden"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left"></span> Return to races</button>
                    </div>
                </form>

                <form action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="changeRaceStatus" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>
                    <div class="form-group col-sm-3">
                        <button class="btn btn-primary" type="submit"
                            <c:if test="${race.raceStatus.ordinal() >= raceStatusWinsPaid.ordinal()}"> disabled
                            </c:if>
                        >
                            <span class="glyphicon glyphicon-triangle-right"></span>
                            Change status to "${race.raceStatus.nextPossibleStatus().toString()}"
                        </button>
                    </div>
                </form>

                <form action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="cancelRace" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <button class="btn btn-primary" type="submit"
                                <c:if test="${race.raceStatus.ordinal() >= raceStatusFinished.ordinal()}"> disabled
                                </c:if>
                        >
                            <span class="glyphicon glyphicon-triangle-right"></span> Cancel race
                        </button>
                    </div>
                </form>

                <form action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="wonBets" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <button class="btn btn-primary" type="submit"
                                <c:if test="${race.raceStatus.ordinal() != raceStatusWinsPaid.ordinal()}"> hidden
                                </c:if>
                        >
                            <span class="glyphicon glyphicon glyphicon-euro"></span> Won bets
                        </button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
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


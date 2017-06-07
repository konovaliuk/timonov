<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="reusable/header.jspf"%>
</head>

<body>
<%@include file="/WEB-INF/jsp/reusable/i18n.jspf"%>
<div>
    <div class="container">
        <header>
            <div class="container">
                <%@include file="reusable/greeting.jspf"%>
                <h3><fmt:message key="race.manage" bundle="${bundle}"/></h3>
            </div>
        </header>

        <nav class="navbar navbar-default">
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>
                <h3><fmt:message key="race.attributes" bundle="${bundle}"/></h3>
                <form class="form-horizontal" action="races" method="POST">
                    <div>
                        <input class="form-control" name="action" value="raceSaveEditedAttributes" type="hidden"/>
                        <input class="form-control" name="raceId" value="${race.id}" type="hidden"/>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="location"><fmt:message key="app.location" bundle="${bundle}"/>:</label>
                        </div>
                        <div class="col-sm-3">
                            <select class="form-control" id="location" name="location"
                                <c:if test="${race.raceStatus.ordinal() > raceStatusBeingFormed.ordinal()}">disabled
                                </c:if>
                            >
                                <option disabled><fmt:message key="races.chooseLocation" bundle="${bundle}"/>:</option>
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
                            <label class="control-label" for="date"><fmt:message key="app.date" bundle="${bundle}"/>:</label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" id="date" name="date"  type="text"
                                   value="<custom:formatDate date="${race.date}" format="dd-MM-yyyy"/>"
                                   <c:if test="${race.raceStatus.ordinal() > raceStatusBeingFormed.ordinal()}">readonly
                            </c:if>
                            />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2">
                            <label class="control-label" for="raceStatus"><fmt:message key="app.status" bundle="${bundle}"/>:</label>
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
                            <span class="glyphicon glyphicon-floppy-disk">
                            </span> <fmt:message key="race.saveRaceAttr" bundle="${bundle}"/></button>
                    </div>
                </form>
                <br>
                <h3><fmt:message key="app.horsesInRace" bundle="${bundle}"/></h3>

                <form class="form-horizontal" action="races" method="POST">
                    <table class="table table-striped table-condensed">
                        <tr>
                            <th><fmt:message key="horse.placeAtFinish" bundle="${bundle}"/></th>
                            <th><fmt:message key="horse.name" bundle="${bundle}"/></th>
                            <th><fmt:message key="horse.yearOfBirth" bundle="${bundle}"/></th>
                            <th><fmt:message key="horse.totalRaces" bundle="${bundle}"/></th>
                            <th><fmt:message key="horse.wonRaces" bundle="${bundle}"/></th>
                            <th><fmt:message key="horse.currentBetRates" bundle="${bundle}"/></th>
                            <c:if test="${race.raceStatus.ordinal() == 0}">
                                <th><fmt:message key="horse.remove" bundle="${bundle}"/></th>
                            </c:if>
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
                                <td>${horseInRace.horse.name}</td>
                                <td>${horseInRace.horse.yearOfBirth}</td>
                                <td>${horseInRace.horse.totalRaces}</td>
                                <td>${horseInRace.horse.wonRaces}</td>
                                <td><a href="races?action=horseInRaceBookie&horseInRaceId=${horseInRace.id}">
                                    <fmt:message key="horse.currentBetRates" bundle="${bundle}"/></a></td>
                                <c:if test="${race.raceStatus.ordinal() == 0}">
                                    <td>
                                        <a href="races?action=horseInRaceDelete&horseInRace=${horseInRace.id}">
                                            <fmt:message key="horse.remove" bundle="${bundle}"/></a>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </table>

                    <div>
                        <input class="form-control" name="action" value="racePlacesSave" type="hidden"/>
                        <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                    </div>

                    <c:if test="${race.raceStatus == raceStatusFinished}">
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-floppy-disk">
                                </span> <fmt:message key="race.savePlaces" bundle="${bundle}"/></button>
                        </div>
                    </c:if>
                </form>

                <c:if test="${race.raceStatus == raceStatusBeingFormed}">
                    <form class="form-horizontal" action="races" method="POST">
                        <div class="form-group col-sm-2">
                            <input class="form-control" name="action" value="horseInRaceAdd" type="hidden"/>
                            <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                        </div>

                        <div class="form-group col-sm-2">
                            <label class="control-label" for="horse">
                                <fmt:message key="horse.add" bundle="${bundle}"/>:</label>
                        </div>

                        <div class="form-group col-sm-4">
                            <select class="form-control" id="horse" name="horseId">
                                <option selected disabled hidden><fmt:message key="race.chooseHorse" bundle="${bundle}"/>:</option>
                                <c:forEach var="horse" items="${horses}">
                                    <option value=${horse.id}>${horse.name}, ${horse.yearOfBirth}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-sm-4">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-plus-sign">
                                </span> <fmt:message key="race.addChosenHorse" bundle="${bundle}"/></button>
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
                            <span class="glyphicon glyphicon-triangle-left">
                            </span> <fmt:message key="app.returnToRaces" bundle="${bundle}"/></button>
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
                            <span class="glyphicon glyphicon-triangle-right">
                            </span><fmt:message key="race.changeStatusTo" bundle="${bundle}"/>&nbsp;
                            "${race.raceStatus.nextPossibleStatus().toString()}"
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
                            <span class="glyphicon glyphicon-triangle-right"></span>&nbsp;
                            <fmt:message key="race.cancel" bundle="${bundle}"/>
                        </button>
                    </div>
                </form>

                <c:if test="${race.raceStatus == raceStatusWinsPaid}">
                    <form action="races" method="POST">
                        <div>
                            <input class="form-control" name="action" value="wonBets" type="hidden"/>
                            <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                        </div>
                        <div class="form-group col-sm-2">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon glyphicon-euro"></span>&nbsp;
                                <fmt:message key="race.wonBets" bundle="${bundle}"/>
                            </button>
                        </div>
                    </form>
                </c:if>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>

</body>
</html>

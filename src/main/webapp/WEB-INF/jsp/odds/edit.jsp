<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="../reusable/header.jspf"%>
</head>

<body>
<div class="container">
    <header>
        <div class="container">
            <h3><fmt:message key="odds.edit" bundle="${bundle}"/></h3>
        </div>
    </header>

    <nav><%@include file="../reusable/nav.jspf"%></nav>

    <article>
        <div>
            <%@include file="../reusable/statusMessage.jspf"%>
            <%@include file="../reusable/raceAndHorse.jspf"%>
            <h4><fmt:message key="odds.edit" bundle="${bundle}"/>:</h4>
            <form class="form-horizontal" action="races" method="POST">
                <div>
                    <input class="form-control" name="action" value="oddsSaveEdited" type="hidden"/>
                    <input class="form-control" name="horseInRaceId" value="${odds.horseInRaceId}" type="hidden"/>
                    <input class="form-control" name="oddsId" value="${odds.id}" type="hidden"/>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="betType"><fmt:message key="bet.betType" bundle="${bundle}"/>:</label>
                    </div>
                    <div class="col-sm-3">
                        <select class="form-control" id="betType" name="betType">
                            <option disabled><fmt:message key="bet.chooseBetType" bundle="${bundle}"/></option>
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
                        <label class="control-label" for="total"><fmt:message key="bet.totalOdds" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="total" name="total" value="${odds.total}" type="number"
                               min="1" step="1"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="chances"><fmt:message key="bet.chancesOdds" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="chances" name="chances" value="${odds.chances}" type="number"
                               min="1" step="1"/>
                    </div>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-floppy-disk">
                        </span> <fmt:message key="odds.saveEdited" bundle="${bundle}"/></button>
                </div>
            </form>

            <form class="form-horizontal" action="races" method="GET">
                <div>
                    <input class="form-control" name="action" value="horseInRaceBookie" type="hidden"/>
                    <input class="form-control" name="horseInRaceId" type="hidden" value="${odds.horseInRaceId}"/>
                </div>
                <div class="col-sm-1">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left">
                        </span> <fmt:message key="odds.returnToOdds" bundle="${bundle}"/></button>
                </div>
            </form>
        </div>
    </article>

    <footer><%@include file="../reusable/footer.jspf"%></footer>
</div>
</body>
</html>
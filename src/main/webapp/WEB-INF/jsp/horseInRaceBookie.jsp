<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="reusable/header.jspf"%>
</head>

<body>
<div class="container">
    <header>
        <div class="container">
            <%@include file="reusable/greeting.jspf"%>
            <h3><fmt:message key="bookie.pageName" bundle="${bundle}"/></h3>
        </div>
    </header>

    <nav><%@include file="reusable/nav.jspf"%></nav>

    <article>
        <div class="container">
            <%@include file="reusable/statusMessage.jspf"%>
            <%@include file="reusable/raceAndHorse.jspf"%>
            <h3><fmt:message key="bookie.existingRates" bundle="${bundle}"/></h3>
            <table class="table table-striped">
                <tr>
                    <th><fmt:message key="bet.betType" bundle="${bundle}"/></th>
                    <th><fmt:message key="bet.rates" bundle="${bundle}"/></th>
                    <c:if test="${userRole == roleBookie && race.raceStatus.ordinal() <= raceStatusOpen.ordinal()}">
                        <th><fmt:message key="app.edit" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.delete" bundle="${bundle}"/></th>
                    </c:if>
                </tr>
                <c:forEach var="odds" items="${horseInRace.oddsValues}">
                    <tr>
                        <div class="form-group">
                            <td>${odds.betType.toString()}</td>
                            <td>${odds.total} / ${odds.chances}</td>
                            <c:if test="${userRole == roleBookie && race.raceStatus.ordinal() <= raceStatusOpen.ordinal()}">
                                <td><a href="races?action=oddsEdit&oddsId=${odds.id}">
                                    <fmt:message key="app.edit" bundle="${bundle}"/></a>
                                </td>
                                <td><a href="races?action=oddsDelete&oddsId=${odds.id}">
                                    <fmt:message key="app.delete" bundle="${bundle}"/></a>
                                </td>
                            </c:if>
                        </div>
                    </tr>
                </c:forEach>
                <tr>
            </table>
            <c:if test="${userRole == roleBookie && race.raceStatus.ordinal() <= raceStatusOpen.ordinal()}">
                <form class="form-inline" action="races" method="POST">
                    <div class="form-group col-sm-4">
                        <input class="form-control" name="action" value="oddsAdd" type="hidden"/>
                        <input class="form-control" name="horseInRaceId" type="hidden" value="${horseInRace.id}"/>
                        <label class="control-label">Add odds:</label>
                        <select class="form-control" name="betType">
                            <option selected disabled hidden><fmt:message key="bet.chooseBetType" bundle="${bundle}"/></option>
                            <c:forEach var="betType" items="${betTypes}">
                                <option value="${betType}"
                                    <c:if test="${oddsWithInputError.betType == betType}">
                                        selected
                                    </c:if>
                                >${betType.toString()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group col-sm-4">
                        <label class="control-label"><fmt:message key="bet.totalOdds" bundle="${bundle}"/></label>
                        <input class="form-control" type="number" name="total"
                               placeholder="<fmt:message key="app.total" bundle="${bundle}"/>" required
                               value="${oddsWithInputError.total}">
                    </div>
                    <div class="form-group col-sm-4">
                        <label class="control-label"><fmt:message key="bet.chancesOdds" bundle="${bundle}"/></label>
                        <input class="form-control" type="number" name="chances"
                               placeholder="<fmt:message key="odds.add" bundle="${bundle}"/>" required
                               value="${oddsWithInputError.chances}">
                    </div>
                    <br>
                    <br>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-plus-sign">
                            </span> <fmt:message key="odds.add" bundle="${bundle}"/></button>
                    </div>
                </form>
                <br>
                <br>
            </c:if>
            <form class="form-horizontal" action="races" method="GET">
                <div>
                    <input class="form-control" name="action" value="race" type="hidden"/>
                    <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left">
                        </span> <fmt:message key="app.returnToRace" bundle="${bundle}"/></button>
                </div>
            </form>
        </div>
    </article>

    <footer><%@include file="reusable/footer.jspf"%></footer>
</div>
</body>
</html>


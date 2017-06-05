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
                <h3><fmt:message key="races.pageName" bundle="${bundle}"/></h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div>
                <%@include file="reusable/statusMessage.jspf"%>
                <h3><fmt:message key="app.races" bundle="${bundle}"/></h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th><fmt:message key="app.date" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.location" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.country" bundle="${bundle}"/></th>
                        <th><fmt:message key="app.status" bundle="${bundle}"/></th>
                        <th><fmt:message key="races.raceBets" bundle="${bundle}"/></th>
                        <c:if test="${userRole == roleAdmin}">
                            <th><fmt:message key="races.betSum" bundle="${bundle}"/></th>
                            <th><fmt:message key="races.paidSum" bundle="${bundle}"/></th>
                            <th><fmt:message key="races.manage" bundle="${bundle}"/></th>
                            <th><fmt:message key="app.delete" bundle="${bundle}"/></th>
                        </c:if>
                    </tr>
                    <c:forEach var="race" items="${races}">
                        <tr>
                            <td>${race.date}</td>
                            <td>${race.location.name}</td>
                            <td>${race.location.country.name}</td>
                            <td>${race.raceStatus.toString()}</td>
                            <td><a href="races?action=race&raceId=${race.id}"><fmt:message key="races.raceBets" bundle="${bundle}"/>
                                </a></td>
                            <c:if test="${userRole == roleAdmin}">
                                <td align="right">${race.betSum.toString()}</td>
                                <td align="right">${race.paidSum.toString()}</td>
                                <td align="center"><a href="races?action=raceManage&raceId=${race.id}">
                                    <fmt:message key="races.manage" bundle="${bundle}"/></a></td>
                                <td><a href="races?action=raceDelete&raceId=${race.id}">
                                    <fmt:message key="app.delete" bundle="${bundle}"/></a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>

                <c:if test="${userRole == roleAdmin}">
                    <form class="form-inline" action="races" method="POST">
                        <div class="form-group col-sm-6">
                            <input class="form-control" name="action" value="raceAdd" type="hidden"/>
                            <label class="control-label" for="location">
                                <fmt:message key="races.addRaceLocation" bundle="${bundle}"/></label>
                            <select class="form-control" id="location" name="locationId" required>
                                <option selected disabled><fmt:message key="races.chooseLocation" bundle="${bundle}"/></option>
                                <c:forEach var="location" items="${locations}">
                                    <option value=${location.id}>${location.name}, ${location.country.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group col-sm-4">
                            <label class="control-label" for="date"><fmt:message key="app.date" bundle="${bundle}"/>:</label>
                            <input class="form-control" id="date" name="date" type="date" required/>
                        </div>
                        <div class="form-group col-sm-2">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-plus-sign">
                                </span> <fmt:message key="races.addRace" bundle="${bundle}"/></button>
                        </div>
                    </form>
                    <hr>
                    <hr>
                </c:if>

                <form class="form-horizontal" action="races" method="GET">
                    <div>
                        <input class="form-control" name="action" value="main" type="hidden"/>
                    </div>
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left">
                            </span> <fmt:message key="app.returnToHomePage" bundle="${bundle}"/></button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>

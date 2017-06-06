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
                <h3><fmt:message key="horses.inSystem" bundle="${bundle}"/></h3>
            </div>
        </header>

        <nav class="navbar navbar-default">
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>
                <h3><fmt:message key="app.horses" bundle="${bundle}"/></h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th><fmt:message key="horse.name" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.yearOfBirth" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.totalRaces" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.wonRaces" bundle="${bundle}"/></th>
                        <th><fmt:message key="horse.openRacesWithHorse" bundle="${bundle}"/></th>
                        <c:if test="${userRole == roleAdmin}">
                            <th><fmt:message key="app.edit" bundle="${bundle}"/></th>
                            <th><fmt:message key="app.delete" bundle="${bundle}"/></th>
                        </c:if>
                    </tr>
                    <c:forEach var="horse" items="${horses}">
                        <tr>
                            <td>${horse.name}</td>
                            <td>${horse.yearOfBirth}</td>
                            <td>${horse.totalRaces}</td>
                            <td>${horse.wonRaces}</td>
                            <td><a href="races?action=racesByHorse&horseId=${horse.id}">
                                <fmt:message key="horse.openRacesWithHorse" bundle="${bundle}"/></a></td>
                            <c:if test="${userRole == roleAdmin}">
                                <td><a href="races?action=horseEdit&horseId=${horse.id}">
                                    <fmt:message key="app.edit" bundle="${bundle}"/></a></td>
                                <td><a href="races?action=horseDelete&horseId=${horse.id}">
                                    <fmt:message key="app.delete" bundle="${bundle}"/>
                                </a></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>

                <c:if test="${userRole == roleAdmin}">
                    <form class="form-inline" action="races" method="POST">
                        <div class="form-group col-sm-2">
                            <input class="form-control" name="action" value="horseAdd" type="hidden"/>
                            <label class="control-label" for="name">
                                <fmt:message key="horse.addHorseName" bundle="${bundle}"/></label>
                            <input class="form-control" id="name" name="name" type="text"
                                   placeholder="<fmt:message key="horse.name" bundle="${bundle}"/>" required
                                value="${horseWithInputError.name}"
                            />
                        </div>
                        <div class="form-group col-sm-2">
                            <label class="control-label" for="year"><fmt:message key="horse.yearOfBirth" bundle="${bundle}"/>:</label>
                            <input class="form-control" id="year" name="year" type="number" min="2000" step="1"
                                   placeholder="<fmt:message key="horse.yearOfBirth" bundle="${bundle}"/>" required
                                value="${horseWithInputError.yearOfBirth}"
                            />
                        </div>
                        <div class="form-group col-sm-2">
                            <label class="control-label" for="totalRaces"><fmt:message key="horse.totalRaces" bundle="${bundle}"/>:</label>
                            <input class="form-control" id="totalRaces" name="totalRaces" type="number" min="0" step="1"
                                   placeholder="<fmt:message key="horse.totalRaces" bundle="${bundle}"/>" required
                                value="${horseWithInputError.totalRaces}"
                            />
                        </div>
                        <div class="form-group col-sm-2">
                            <label class="control-label" for="wonRaces"><fmt:message key="horse.wonRaces" bundle="${bundle}"/>:</label>
                            <input class="form-control" id="wonRaces" name="wonRaces" type="number" min="0" step="1"
                                   placeholder="<fmt:message key="horse.wonRaces" bundle="${bundle}"/>" required
                                value="${horseWithInputError.wonRaces}"
                            />
                        </div>
                        <br>
                        <div class="form-group col-sm-1">
                            <button class="btn btn-primary" type="submit">
                                <span class="glyphicon glyphicon-plus-sign"></span> <fmt:message key="horse.add" bundle="${bundle}"/></button>
                        </div>
                    </form>
                    <br>
                    <br>
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


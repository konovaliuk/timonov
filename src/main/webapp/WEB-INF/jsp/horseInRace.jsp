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
                <h3><fmt:message key="horse.makeBetOn" bundle="${bundle}"/> ${horseInRace.horse.name},
                    <fmt:message key="horse.raceAt" bundle="${bundle}"/> ${race.location.name},
                ${race.location.country.name}</h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div>
                <%@include file="reusable/raceAndHorse.jspf"%>
                <br>
                <h3><fmt:message key="race.availableBets" bundle="${bundle}"/></h3>
                <table class="table table-striped">
                    <tr>
                        <th></th>
                        <th><fmt:message key="bet.betType" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.rates" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.inputBetSum" bundle="${bundle}"/></th>
                        <th><fmt:message key="bet.pushToBet" bundle="${bundle}"/></th>
                    </tr>
                    <c:forEach var="odds" items="${horseInRace.oddsValues}">
                        <tr>
                            <form class="form-horizontal" method="POST" action="races">
                                <div class="form-group">
                                    <td>
                                        <div>
                                            <input class="form-control" name="action" type="hidden" value="makeBet"/>
                                            <input class="form-control" name="oddsId" type="hidden" value="${odds.id}"/>
                                        </div>
                                    </td>
                                    <td>${odds.betType.toString()}</td>
                                    <td align="center">${odds.total} / ${odds.chances}</td>
                                    <td>
                                        <div>
                                            <input class="form-control" name="sum" type="number" min="0.01" step="0.01"
                                            required/>
                                        </div>
                                    </td>
                                    <td><button class="btn btn-primary" type="submit">
                                            <span class="glyphicon glyphicon-floppy-disk">
                                            </span> <fmt:message key="bet.pushToBet" bundle="${bundle}"/></button>
                                    </td>
                                </div>
                            </form>
                        </tr>
                    </c:forEach>
                </table>
                <form class="form-horizontal" action="/races" method="GET">
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
</div>
</body>
</html>


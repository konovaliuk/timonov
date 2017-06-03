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
            <%@include file="reusable/greeting.jspf"%>
            <div class="container">
                <h3>Won bets in Race at ${race.location.name}, ${race.location.country.name} on ${race.date}</h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>

                <h4>Race info</h4>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Location</th>
                        <th>Country</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Bet sum</th>
                        <th>Paid sum</th>
                    </tr>
                    <tr>
                        <td>${race.id}</td>
                        <td>${race.location.name}</td>
                        <td>${race.location.country.name}</td>
                        <td>${race.date}</td>
                        <td>${race.raceStatus.toString()}</td>
                        <td>${race.betSum.toString()}</td>
                        <td>${race.paidSum.toString()}</td>
                    </tr>
                </table>

                <h4>Won bets</h4>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th>Client name</th>
                        <th>Client login</th>
                        <th>Bet type</th>
                        <th>Bet rate</th>
                        <th>Horse name</th>
                        <th>Bet sum</th>
                        <th>Won sum</th>
                    </tr>
                    <c:forEach var="bet" items="${wonBets}" varStatus="loop">
                        <tr>
                            <td>${bet.user.name}</td>
                            <td>${bet.user.login}</td>
                            <td>${bet.odds.betType.toString()}</td>
                            <td align="center">${bet.odds.getValue()}</td>
                            <td>${listBetHorses.get(loop.index).horse.name}</td>
                            <td>${bet.sum.toString()}</td>
                            <%--TODO with custom tag--%>
                            <td>${bet.sum.toString()} * ${bet.odds.oddsValue}</td>
                        </tr>
                    </c:forEach>
                </table>

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
                        <input class="form-control" name="action" value="raceEdit" type="hidden"/>
                        <input class="form-control" name="raceId" value="${race.id}" type="hidden"/>
                    </div>
                    <div class="form-group col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-right"></span> Edit race</button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <%@include file="items/header.jspf"%>
</head>

<body>
<div class="container">
    <header>
        <div class="container">
            <h3>Horse in Race for Bookmaker</h3>
        </div>
    </header>

    <nav><%@include file="items/nav.jspf"%></nav>

    <article>
    <div>
        <%@include file="items/statusMessage.jspf"%>
        <%@include file="items/raceAndHorse.jspf"%>
        <h3>Existing odds</h3>
        <table class="table table-striped">
            <tr>
                <th>ID</th>
                <th>Bet type</th>
                <th>Odds</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="odds" items="${horseInRace.oddsValues}">
                <tr>
                    <div class="form-group">
                        <td>${odds.id}</td>
                        <td>${odds.betType.toString()}</td>
                        <td>${odds.total} / ${odds.chances}</td>
                        <td><a href="races?action=oddsEdit&oddsId=${odds.id}">
                            Edit</a>
                        </td>
                        <td><a href="races?action=oddsDelete&oddsId=${odds.id}">
                            Delete</a>
                        </td>
                    </div>
                </tr>
            </c:forEach>
            <tr>
        </table>
        <form class="form-inline" action="races" method="POST">
            <div class="form-group col-sm-4">
                <input class="form-control" name="action" value="oddsAdd" type="hidden"/>
                <input class="form-control" name="horseInRaceId" type="hidden" value="${horseInRace.id}"/>
                <label class="control-label">Add odds:</label>
                <select class="form-control" name="betType">
                    <option selected disabled hidden>Choose bet type:</option>
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
                <label class="control-label">Total odds:</label>
                <input class="form-control" type="number" name="total" placeholder="total" required
                       value="${oddsWithInputError.total}">
            </div>
            <div class="form-group col-sm-4">
                <label class="control-label">Odds to win:</label>
                <input class="form-control" type="number" name="chances" placeholder="chances" required
                       value="${oddsWithInputError.chances}">
            </div>
            <br>
            <br>
            <div class="col-sm-2">
                <button class="btn btn-primary" type="submit">
                    <span class="glyphicon glyphicon-plus-sign"></span> Add odds</button>
            </div>
        </form>
        <br>
        <br>
        <form class="form-horizontal" action="races" method="GET">
            <div>
                <input class="form-control" name="action" value="race" type="hidden"/>
                <input class="form-control" name="raceId" type="hidden" value="${race.id}"/>
            </div>
            <div class="col-sm-2">
                <button class="btn btn-primary" type="submit">
                    <span class="glyphicon glyphicon-triangle-left"></span> Return to race</button>
            </div>
        </form>
    </div>
    </article>

    <footer><%@include file="items/footer.jspf"%></footer>
</div>
</body>
</html>


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
                <h3>All races</h3>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div>
                <%@include file="items/statusMessage.jspf"%>
                <h3>Races</h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th>ID</th>
                        <th>Location</th>
                        <th>Country</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Go to race</th>
                        <th>Edit race</th>
                        <th>Delete race</th>
                    </tr>
                    <c:forEach var="race" items="${races}">
                        <tr>
                            <td>${race.id}</td>
                            <td>${race.location.name}</td>
                            <td>${race.location.country.name}</td>
                            <td>${race.date}</td>
                            <td>${race.raceStatus.toString()}</td>
                            <td><a href="races?action=race&raceId=${race.id}">To race</a></td>
                            <td><a href="races?action=raceEdit&raceId=${race.id}">Edit race</a></td>
                            <td><a href="races?action=raceDelete&raceId=${race.id}">Delete race</a></td>
                        </tr>
                    </c:forEach>
                </table>

                <form class="form-inline" action="races" method="POST">
                    <div class="form-group col-sm-6">
                        <input class="form-control" name="action" value="raceAdd" type="hidden"/>
                        <label class="control-label" for="location">Add race, Location:</label>
                        <select class="form-control" id="location" name="locationId" required>
                            <option selected disabled>Choose location:</option>
                            <c:forEach var="location" items="${locations}">
                                <option value=${location.id}>${location.name}, ${location.country.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group col-sm-4">
                        <label class="control-label" for="date">Date:</label>
                        <input class="form-control" id="date" name="date" type="date" required/>
                    </div>
                    <div class="form-group col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-plus-sign"></span> Add race</button>
                    </div>
                </form>
                <hr>
                <hr>
                <form class="form-horizontal" action="races?action=home" method="GET">
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left"></span> Return to home page</button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>


</div>
</body>
</html>

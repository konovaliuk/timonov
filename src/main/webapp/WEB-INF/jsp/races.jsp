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
                <h2>Races</h2>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div>
                <h2>Races</h2>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Location</th>
                        <th>Country</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Go to race</th>
                        <th>Edit race</th>
                    </tr>
                    <c:forEach var="race" items="${races}">
                        <tr>
                            <td>${race.id}</td>
                            <td>${race.location.name}</td>
                            <td>${race.location.country.name}</td>
                            <td>${race.date}</td>
                            <td>${race.raceStatus.toString()}</td>
                            <td><a href="races?action=race&raceId=${race.id}">To race</a></td>
                            <td><a href="races?action=raceEdit&raceId=${race.id}">Edit races</a></td>
                        </tr>
                    </c:forEach>
                </table>
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

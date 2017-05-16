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
                        <th>Details</th>
                    </tr>
                    <c:forEach var="race" items="${races}">
                        <c:url var="raceUrl" value="/races?action=race&id=${race.id}"/>
                        <tr>
                            <td>${race.id}</td>
                            <td>${race.location}</td>
                            <td>${race.country}</td>
                            <td>${race.date}</td>
                            <td>${race.raceStatus}</td>
                            <td><a href="${raceUrl}">Race details</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>


</div>
</body>
</html>

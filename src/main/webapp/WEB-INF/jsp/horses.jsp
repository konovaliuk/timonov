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
                <h2>Horses</h2>
            </div>
        </header>

        <nav>
            <%@include file="items/nav.jspf"%>
        </nav>

        <article>
            <div>
                <h2>Horses</h2>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year of birth</th>
                        <th>Total races</th>
                        <th>Won races</th>
                    </tr>
                    <c:forEach var="horseInRace" items="${horses}">
                        <c:url var="raceUrl" value="/horseracing/races"/>
                        <tr>
                            <td>${horseInRace.id}</td>
                            <td><a href="${raceUrl}">${horseInRace.name}</a></td>
                            <td>${horseInRace.yearOfBirth}</td>
                            <td>${horseInRace.totalRaces}</td>
                            <td>${horseInRace.wonRaces}</td>
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


<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/WEB-INF/css/index.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Horse Racing. Races</title>
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
            <ul>
                <li><a href="/horseracing">Home page</a></li>
                <li><a href="/races?action=races">Races</a></li>
                <li><a href="/races?action=race">Race</a></li>
                <li><a href="/races?action=horses">Horses</a></li>
                <li><a href="/error">ERROR</a></li>
            </ul>
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

        <footer>Copyright &copy; Alexey Timonov</footer>
    </div>


</div>
</body>
</html>

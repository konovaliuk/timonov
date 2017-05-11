<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/WEB-INF/css/index.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Horse Racing. Horses</title>
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
            <ul>
                <li><a href="/horseracing">Home page</a></li>
                <li><a href="/races?action=races">Races</a></li>
                <li><a href="/races?action=race">Race</a></li>
                <li><a href="/error">ERROR</a></li>
            </ul>
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

        <footer>Copyright &copy; Alexey Timonov</footer>
    </div>


</div>
</body>
</html>


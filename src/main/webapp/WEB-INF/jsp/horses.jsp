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
                <h3>Horses in World Horse Racing tour</h3>
            </div>
        </header>

        <nav>
            <%@include file="reusable/nav.jspf"%>
        </nav>

        <article>
            <div class="container">
                <%@include file="reusable/statusMessage.jspf"%>
                <h3>Horses</h3>
                <table class="table table-striped table-condensed">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year of birth</th>
                        <th>Total races</th>
                        <th>Won races</th>
                        <th>Races with horse</th>
                        <th>Edit horse</th>
                        <th>Delete horse</th>
                    </tr>
                    <c:forEach var="horse" items="${horses}">
                        <tr>
                            <td>${horse.id}</td>
                            <td><a href="races?action=horse&id=${horse.id}">${horse.name}</a></td>
                            <td>${horse.yearOfBirth}</td>
                            <td>${horse.totalRaces}</td>
                            <td>${horse.wonRaces}</td>
                            <td><a href="races?action=racesByHorse&horseId=${horse.id}">Races with horse</a></td>
                            <td><a href="races?action=horseEdit&horseId=${horse.id}">Edit horse</a></td>
                            <td><a href="races?action=horseDelete&horseId=${horse.id}">Delete horse</a></td>
                        </tr>
                    </c:forEach>
                </table>

                <form class="form-inline" action="races" method="POST">
                    <div class="form-group col-sm-2">
                        <input class="form-control" name="action" value="horseAdd" type="hidden"/>
                        <label class="control-label" for="name">Add horse, name:</label>
                        <input class="form-control" id="name" name="name" type="text" placeholder="Horse name" required
                            value="${horseWithInputError.name}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="year">Year of birth:</label>
                        <input class="form-control" id="year" name="year" type="number" min="2000" step="1"
                               placeholder="year" required
                            value="${horseWithInputError.yearOfBirth}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="totalRaces">Total races:</label>
                        <input class="form-control" id="totalRaces" name="totalRaces" type="number" min="0" step="1"
                               placeholder="total races" required
                            value="${horseWithInputError.totalRaces}"
                        />
                    </div>
                    <div class="form-group col-sm-2">
                        <label class="control-label" for="wonRaces">Won races:</label>
                        <input class="form-control" id="wonRaces" name="wonRaces" type="number" min="0" step="1"
                               placeholder="won races" required
                            value="${horseWithInputError.wonRaces}"
                        />
                    </div>
                    <br>
                    <div class="form-group col-sm-1">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-plus-sign"></span> Add horse</button>
                    </div>
                </form>
                <br>
                <br>
                <form class="form-horizontal" action="races?action=home" method="GET">
                    <div class="col-sm-2">
                        <button class="btn btn-primary" type="submit">
                            <span class="glyphicon glyphicon-triangle-left"></span> Return to home page</button>
                    </div>
                </form>
            </div>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>


</div>
</body>
</html>


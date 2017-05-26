<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 25.05.2017
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Horse</h3>
<table class="table table-striped">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Year of birth</th>
        <th>Total races</th>
        <th>Won races</th>
    </tr>
    <tr>
        <c:set var="horse" value="${horseInRace.horse}"/>
        <td>${horse.id}</td>
        <td>${horse.name}</td>
        <td>${horse.yearOfBirth}</td>
        <td>${horse.totalRaces}</td>
        <td>${horse.wonRaces}</td>
    </tr>
</table>
</body>
</html>

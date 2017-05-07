<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <h2>Race results</h2>
            </div>
            <div class="signblock">
            </div>
        </header>

        <nav>
            <ul>
                <li><a href="/races?action=races">Races</a></li>
                <li><a href="/library/books">456</a></li>
                <li><a href="/library/classes">789</a></li>
                <li><a href="/error">ERROR</a></li>
            </ul>
        </nav>

        <article>
            <div>
                <h3>123</h3>
                <h4>456</h4>
                <h4>789</h4>

                <h3>Horses</h3>
                <table class="table table-striped">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Year of birth</th>
                        <th>Total races</th>
                        <th>Won races</th>
                    </tr>
                    <c:forEach var="horse" items="${horses}">
                        <c:url var="employeeUrl" value="/horseracing/races"/>
                        <tr>
                            <td>${horse.id}</td>
                            <td><a href="${employeeUrl}">${horse.name}</a></td>
                            <td>${horse.yearOfBirth}</td>
                            <td>${horse.totalRaces}</td>
                            <td>${horse.wonRaces}</td>
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


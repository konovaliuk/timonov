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
                <h2>Error page</h2>
            </div>
            <div class="signblock">
            </div>
        </header>

        <nav>
            <ul>
                <li><a href="/horseracing">123</a></li>
                <li><a href="/library/books">456</a></li>
                <li><a href="/library/classes">789</a></li>
            </ul>
        </nav>

        <article>
            <h1>ERROR</h1>
            <h3><a href="/horseracing">Back to main page</a></h3>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>


</div>
</body>
</html>

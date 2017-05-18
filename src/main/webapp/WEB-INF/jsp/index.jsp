<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <%@include file="items/header.jspf"%>
<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <h2>Main page</h2>
            </div>
        </header>

        <%@include file="items/nav.jspf"%>

        <article>
            <h3>Horse Racing at William Hill</h3>
            <p>For the last word in horse racing, look no further
                than William Hill - the Home of Betting. Covering
                all meetings in the UK, Ireland and a wide
                selection of global horse races, we aim to provide
                you with the best online betting options & odds.
                Find markets for Cheltenham, Ascot, the Grand
                National and more. Watch live races on your PC,
                smartphone or tablet and get up to the minute odds
                and updates. Join us and discover why William Hill
                should be your only bookmaker for horse racing
                betting.</p>
        </article>

        <footer><%@include file="items/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>

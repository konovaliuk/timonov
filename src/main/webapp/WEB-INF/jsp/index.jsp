<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/WEB-INF/css/index.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Horse Racing. Main page</title>
</head>

<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <h2>Main page</h2>
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

        <footer>Copyright &copy; Alexey Timonov</footer>
    </div>
</div>
</body>
</html>

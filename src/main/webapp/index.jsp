<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="/WEB-INF/jsp/items/header.jspf"%>
<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <h3>World Horse Racing tour</h3>
                <h4>Start page</h4>
            </div>
        </header>

        <article>
            <%@include file="/WEB-INF/jsp/items/statusMessage.jspf"%>
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
            <hr>
            <h4>Sign in</h4>
            <form class="form-horizontal" action="races" method="POST">
                <div class="form-group">
                    <div class="col-sm-2">
                        <input class="form-control" name="action" value="signIn" type="hidden"/>
                        <label class="control-label" for="login">Login:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="login" name="login" placeholder="login" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="password">Password:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="password" name="password" placeholder="password"
                               type="password" required>
                    </div>
                </div>
                <div class="col-sm-offset-2 col-sm-3">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-right"></span> Sign in</button>
                </div>
            </form>

            <br>
            <br>

            <h4>Don't have an account? Sign up</h4>
            <form class="form-horizontal" action="races" method="POST">
                <div class="col-sm-2">
                    <input class="form-control" name="action" value="getSignUpForm" type="hidden"/>
                </div>
                <div class="col-sm-3">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-right"></span> Sign up</button>
                </div>
            </form>
        </article>

        <footer><%@include file="/WEB-INF/jsp/items/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>


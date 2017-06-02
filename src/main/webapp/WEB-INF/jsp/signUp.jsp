<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="reusable/header.jspf"%>
<body>
<div>
    <div class="container">
        <header>
            <div class="container">
                <h3>World Horse Racing tour</h3>
                <h4>Sign up page</h4>
            </div>
        </header>

        <article>
            <%@include file="/WEB-INF/jsp/reusable/statusMessage.jspf"%>
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

            <h4>Login:</h4>
            <form class="form-horizontal" action="races" method="POST">
                <div>
                    <input class="form-control" name="action" value="signUp" type="hidden"/>
                </div>

                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="login">Enter your name:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="name" name="name" placeholder="name"
                               value="${user.name}" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="login">Enter your login:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="login" name="login" placeholder="login"
                               value="${user.login}" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="password">Enter your password:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="password" name="password" placeholder="password"
                               type="password" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="passwordConfirm">Confirm your password:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="passwordConfirm" name="passwordConfirm"
                               placeholder="confirm password" type="password" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-floppy-save"></span> Sign up</button>
                </div>
            </form>

            <form class="form-horizontal" action="races" method="POST">
                <div>
                    <input class="form-control" name="action" value="logout" type="hidden"/>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left"></span> Return to sign in</button>
                </div>
            </form>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>

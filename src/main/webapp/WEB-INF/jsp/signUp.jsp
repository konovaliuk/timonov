<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="reusable/header.jspf"%>
<body>
<%@include file="/WEB-INF/jsp/reusable/i18n.jspf"%>
<div>
    <div class="container">
        <header>
            <div class="container">
                <div class="greeting">
                    <h5>
                        <fmt:message key="app.language" bundle="${bundle}"/> ${lang} &nbsp;
                        <a href="races?action=setLangIndex&lang=en">EN</a>&nbsp;
                        <a href="races?action=setLangIndex&lang=ua">UA</a>
                    </h5>
                </div>
                <div class="headers">
                    <h3><fmt:message key="app.appName" bundle="${bundle}"/></h3>
                    <h4><fmt:message key="signup.pageName" bundle="${bundle}"/></h4>
                </div>
            </div>
        </header>

        <article>
            <%@include file="/WEB-INF/jsp/reusable/statusMessage.jspf"%>
            <h4><fmt:message key="signup.pleaseInputData" bundle="${bundle}"/></h4>
            <form class="form-horizontal" action="races" method="POST">
                <div>
                    <input class="form-control" name="action" value="signUp" type="hidden"/>
                </div>

                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="login"><fmt:message key="signup.name" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="name" name="name" placeholder="<fmt:message key="login.name" bundle="${bundle}"/>"
                               value="${user.name}" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="login"><fmt:message key="signup.login" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="login" name="login"
                               placeholder="<fmt:message key="login.login" bundle="${bundle}"/>"
                               value="${user.login}" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="password"><fmt:message key="signup.password" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="login.password" bundle="${bundle}"/>"
                               type="password" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-3">
                        <label class="control-label" for="passwordConfirm"><fmt:message key="signup.password" bundle="${bundle}"/></label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="passwordConfirm" name="passwordConfirm"
                               placeholder="<fmt:message key="login.password" bundle="${bundle}"/>" type="password" required>
                    </div>
                </div>

                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-floppy-save">
                        </span> <fmt:message key="login.signUp" bundle="${bundle}"/></button>
                </div>
            </form>

            <form class="form-horizontal" action="races" method="POST">
                <div>
                    <input class="form-control" name="action" value="logout" type="hidden"/>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left">
                        </span> <fmt:message key="signup.returnToSignIn" bundle="${bundle}"/></button>
                </div>
            </form>
        </article>

        <footer><%@include file="reusable/footer.jspf"%></footer>
    </div>
</div>
</body>
</html>

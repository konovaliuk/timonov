<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<%@include file="/WEB-INF/jsp/reusable/header.jspf"%>
<body>
<div>
    <div class="container">
        <%@include file="/WEB-INF/jsp/reusable/i18n.jspf"%>
        <header>
            <div class="text-left">
                <h5>
                    <fmt:message key="app.language" bundle="${bundle}"/> ${lang} &nbsp;
                    <a href="races?action=setLangIndex&lang=en">EN</a>&nbsp;
                    <a href="races?action=setLangIndex&lang=ua">UA</a>
                </h5>
            </div>
            <div class="container">
                <h3><fmt:message key="app.appName" bundle="${bundle}"/></h3>
                <h4><fmt:message key="login.pageName" bundle="${bundle}"/></h4>
            </div>
        </header>

        <article>
            <%@include file="/WEB-INF/jsp/reusable/statusMessage.jspf"%>
            <h3><fmt:message key="login.descriptionHeader" bundle="${bundle}"/></h3>
            <p><fmt:message key="login.description" bundle="${bundle}"/></p>
            <hr>
            <h4><fmt:message key="login.signIn" bundle="${bundle}"/></h4>
            <form class="form-horizontal" action="races" method="POST">
                <div class="form-group">
                    <div class="col-sm-2">
                        <input class="form-control" name="action" value="signIn" type="hidden"/>
                        <label class="control-label" for="login"><fmt:message key="login.login" bundle="${bundle}"/>:
                        </label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="login" name="login"
                               placeholder="<fmt:message key="login.login" bundle="${bundle}"/>" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="password"><fmt:message key="login.password" bundle="${bundle}"/>:
                        </label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="login.password" bundle="${bundle}"/>"
                               type="password" required>
                    </div>
                </div>
                <div class="col-sm-offset-2 col-sm-3">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-right"></span> <fmt:message key="login.signIn" bundle="${bundle}"/>
                    </button>
                </div>
            </form>

            <br>
            <br>

            <h4><fmt:message key="login.dontHaveAccount" bundle="${bundle}"/></h4>
            <form class="form-horizontal" action="races" method="POST">
                <div class="col-sm-2">
                    <input class="form-control" name="action" value="getSignUpForm" type="hidden"/>
                </div>
                <div class="col-sm-3">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-right"></span> <fmt:message key="login.signUp" bundle="${bundle}"/>
                    </button>
                </div>
            </form>
        </article>

        <footer><%@include file="/WEB-INF/jsp/reusable/footer.jspf"%></footer>
    </div>
</div>
</body>

</html>


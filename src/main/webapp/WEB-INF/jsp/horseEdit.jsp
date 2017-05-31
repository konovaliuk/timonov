<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%@include file="items/header.jspf"%>
</head>

<body>
<div class="container">
    <header>
        <div class="container">
            <h3>Edit horse</h3>
        </div>
    </header>

    <nav><%@include file="items/nav.jspf"%></nav>

    <article>
        <div>
            <%@include file="items/statusMessage.jspf"%>
            <h4>Edit existing horse:</h4>
            <form class="form-horizontal" action="races" method="POST">
                <div>
                    <input class="form-control" name="action" value="horseSaveEdited" type="hidden"/>
                    <input class="form-control" name="horseId" type="hidden" value="${horse.id}"/>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="id">ID:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="id" name="id" value="${horse.id}" readonly />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="name">Name:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="name" name="name" value="${horse.name}" type="text" required>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="year">Year of birth:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="year" name="year" value="${horse.yearOfBirth}" type="number"
                               min="2000" step="1" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="total">Total races:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="total" name="totalRaces" value="${horse.totalRaces}"
                               type="number" min="0" step="1" required/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">
                        <label class="control-label" for="won">Won races:</label>
                    </div>
                    <div class="col-sm-3">
                        <input class="form-control" id="won" name="wonRaces" value="${horse.wonRaces}"
                               type="number" min="0" step="1" required/>
                    </div>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-floppy-disk"></span> Save edited horse</button>
                </div>
            </form>

            <form class="form-horizontal" action="races" method="GET">
                <div>
                    <input class="form-control" name="action" value="horses" type="hidden"/>
                </div>
                <div class="col-sm-2">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-triangle-left"></span> Return to horses</button>
                </div>
            </form>
        </div>
    </article>

    <footer><%@include file="items/footer.jspf"%></footer>
</div>
</body>
</html>

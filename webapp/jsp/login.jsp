<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="info" uri="infotag"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<c:set var="foo" value="${pageContext.request.contextPath}/image/foo.png"/>
<c:set var = "user"  scope = "session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<fmt:setBundle basename="jsp" var="jsp"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css'>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="../css/button.css">
    <link rel="stylesheet" href="../css/select.css">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="../js/validator.js" type="text/javascript"></script>
    <script src="../js/select.js" type="text/javascript"></script>
    <script>require(['registerValidation'])</script>
    <script>
        $('.carousel').carousel();
    </script>
    <title>BooKing</title>
</head>
<body id="page">
    <c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
    <!--pdf object-->
    <!--<object data="/jsp/file.pdf" type="application/pdf" width="100%" height="500px"></object>-->
    <div class="carousel slide carousel-fade" data-ride="carousel">

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active">
            </div>
            <div class="item">
            </div>
            <div class="item">
            </div>
        </div>
    </div>


    <div id="content">
        <div class="container-fluid">
            <div class="row"></div>
            <div class="row">
                <div class=" col-sm-4">
                </div>
                <div class=" col-sm-4" id="loginBlock" style="border-radius: 10px">
                    <form action="/controller" method="post">

                        <div class="form-group">
                            <div class="question">
                                <input type="text" id="bookTitle" name="bookTitle" required/>
                                <label><fmt:message key="bookTitle" bundle="${booking}"/></label>
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="question">
                                <input type="text" id="emailInput" name="emailInput" required/>
                                <label><fmt:message key="email" bundle="${booking}"/></label>
                            </div>
                        </div>

                        <div class="question">
                            <input type="password" id=passwordInput name="passwordInput">
                            <label for="passwordInput"><fmt:message key="password" bundle="${booking}"/></label>
                        </div>


                        <div class="question">
                            <input type="submit" id="loginButton" name="loginButton" class="btn btn-default" value="<fmt:message key="entryButton" bundle="${booking}"/>">
                            <input type="hidden" name="command" value="login">
                        </div>
                    </form>
                    <form action="/jsp/registration.jsp" method="post">
                        <label><fmt:message key="registerButton" bundle="${booking}" var="registerButton"/></label>
                        <input type="submit" id="registerButton" name="registerButton" value="${registerButton}">
                    </form>
                    <div>
                        ${registerMessage}
                    </div>
                    <div>
                        ${errorLoginMessage}
                    </div>
                </div>
                <div class=" col-sm-4">
                </div>
            </div>
            <div class="row"></div>
        </div>
    </div>
    <c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

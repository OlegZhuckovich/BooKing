<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="js" uri="script" %>
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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <js:script/>
    <script>
        $(function(){
            $('.carousel').carousel({
                interval: 7000
            });
        });
    </script>
    <style>
        html,body {
            height:100%;
            width:100%;
            position:relative;
        }
    </style>
    <title>BooKing</title>
</head>
<body id="page">
    <c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
    <div id="background-carousel">
        <div id="myCarousel" class="carousel slide" data-ride="carousel">
            <div class="carousel-inner">
                <div class="item active" style="background-image:url('${pageContext.request.contextPath}/images/loginBackground1.jpg')"></div>
                <div class="item" style="background-image:url('${pageContext.request.contextPath}/images/loginBackground2.jpg')"></div>
                <div class="item" style="background-image:url('${pageContext.request.contextPath}/images/loginBackground3.jpg')"></div>
            </div>
        </div>
    </div>
    <div id="loginContent" class="container-fluid">
        <div class="row" id="firstLoginRow"></div>
        <div class="row" id="secondLoginRow">
            <div class="hidden-xs col-sm-4 col-md-4"></div>
            <div class="col-xs-12 col-sm-4 col-md-4" id="editAccountBlock">
                <div class="container-fluid">
                    <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="loginLogo">
                    <h1 id="loginTitle">BooKing</h1>
                    <div class="row sideColumn">
                        <div class="hidden-xs col-sm-1 col-md-1"></div>
                        <div class="hidden-xs col-sm-10 col-md-10"></div>
                        <form action="/controller" method="post">
                            <div class="form-group">
                                <div class="inputGroup loginInputMargin" style="padding-top: 5px;">
                                    <input type="text" id="emailUser" name="emailUser" style="width: 80%"  required/>
                                    <label><fmt:message key="email" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="inputGroup loginInputMargin">
                                    <input type="password" id="passwordUser" name="passwordUser" style="width: 80%" required/>
                                    <label><fmt:message key="password" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--кнопка submit-->
                            <div class="form-group">
                                <div class="inputGroup loginInputMargin">
                                    <button type="submit" id="loginButton" name="loginButton"><fmt:message key="entryButton" bundle="${booking}"/></button>
                                    <input type="hidden" name="command" value="login">
                                </div>
                            </div>
                        </form>
                        <form action="/jsp/registration.jsp" method="post">
                            <div class="form-group">
                                <div class="inputGroup loginInputMargin">
                                    <button type="submit" id="registerButton" name="registerButton"><fmt:message key="registerButton" bundle="${booking}"/></button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="hidden-xs col-sm-1 col-md-1"></div>
                </div>
            </div>
        </div>
        <div class="hidden-xs col-sm-4 col-md-4"></div>
    </div>
    <c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
    <c:if test="${ not empty loginError}">
        <script>swal('<fmt:message key="loginErrorTitle" bundle="${booking}"/>','<fmt:message key="loginErrorBody" bundle="${booking}"/> ', "warning");</script>
        <c:remove var="loginError" scope="session"/>
    </c:if>
    <c:if test="${ not empty registrationResult}">
        <script>swal('<fmt:message key="registrationResultSuccessTitle" bundle="${booking}"/>','<fmt:message key="registrationResultSuccessBody1" bundle="${booking}"/> ' + '${userRegistration}' + ' <fmt:message key="registrationResultSuccessBody2" bundle="${booking}"/>', "success");</script>
        <c:remove var="registrationResult" scope="session"/>
        <c:remove var="user" scope="session"/>
    </c:if>
</body>
</html>
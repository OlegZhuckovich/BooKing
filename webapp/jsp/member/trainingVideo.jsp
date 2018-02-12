<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/select.js" type="text/javascript"></script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="addBookContent" class="container-fluid content" style="background: url('${pageContext.request.contextPath}/images/trainingVideoBackground.png'); background-size: 100% 100%;">
    <div class="row" id="firstRow"></div>
    <div class="row" id="secondRow">
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
        <div class="col-xs-12 col-sm-10 col-md-8" id="addBookBlock" style="background-color:#D50000; height:100%">
            <div class="container-fluid">
                <div>
                    <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="bookingLogo">
                    <h1 style="margin-left: 50px"><fmt:message key="trainingVideo" bundle="${booking}"/></h1>
                </div>
                <div class="row sideColumn">
                    <div class="col-xs-10 col-sm-10 col-md-10 sideColumn" style="margin-left: 8%">
                        <iframe width="600" height="340" src="https://www.youtube.com/embed/06kBakNco8s"></iframe>
                    </div>
                </div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
    </div>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>






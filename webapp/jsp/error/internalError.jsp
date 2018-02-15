<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="js" uri="script" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <js:script/>
    <title>BooKingErrorPage</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid" style="background: url('${pageContext.request.contextPath}/images/administratorMenuBackground.png'); background-size: 100% 100%;">
    <div class="row" id="firstErrorRow"></div>
    <div class="row" id="secondErrorRow">
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
        <div class="col-xs-12 col-sm-10 col-md-8" id="errorBlock">
            <div class="container-fluid">
                <div class="row sideColumn">
                    <div class="col-xs-4 col-sm-4 col-md-4 sideColumn">
                        <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="errorBlockLogo">
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-8 sideColumn">
                        <h1 id="errorPageTitle"><fmt:message key="errorPageTitle" bundle="${booking}"/> 500</h1><br/>
                        <h3 id="errorPageBody"><fmt:message key="error500PageBody" bundle="${booking}"/></h3>
                        <p>Request from ${pageContext.errorData.requestURI} is failed </p><br/>
                        <p>Exception: ${pageContext.exception}</p><br/>
                        <p>Message from exception: ${pageContext.exception.message}</p>
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

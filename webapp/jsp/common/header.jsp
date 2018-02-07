<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<c:set var="foo" value="${pageContext.request.contextPath}/image/foo.png"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select.css">
    <script src="${pageContext.request.contextPath}/js/select.js" type="text/javascript"></script>
</head>
<body>
<header style="display: flex">
    <form action="/controller" method="get" id="logoutHeader" name="logoutHeader">
        <img src="../../images/BooKingLogo.svg" height="100%" width="100px" onClick="document.forms['logoutHeader'].submit();"/>
        <input type="hidden" name="command" value="logout">
    </form>
    <h1>BooKing</h1>
    <ul>
        <div id="headerMenu">
            <div><li>Контакты</li></div>
            <div><li>Меню</li></div>
            <div><li>Epam</li></div>
            <div><li>Карта сайта</li></div>
            <c:if test="${ not empty user}">
                <div><img src="${pageContext.request.contextPath}/image/${sessionScope.user.id}" height="100%" width="100%"/></div>
                <div><li>${sessionScope.user.name} ${sessionScope.user.surname}</li></div>
            </c:if>
        </div>
    </ul>
    <form method="post"> 
        <select id="language" name="language" onchange="submit()"> 
            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}><fmt:message key="russian" bundle="${booking}"/></option> 
            <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}><fmt:message key="english" bundle="${booking}"/></option> 
            <option value="be_BY" ${language == 'be_BY' ? 'selected' : ''}><fmt:message key="belarusian" bundle="${booking}"/></option> 
        </select>
    </form>
</header>
</body>
</html>
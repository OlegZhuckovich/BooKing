<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="common/web.jsp"/>
    <script>require(['registerValidation'])</script>
    <title>BooKing</title>
</head>
<body id="page">
    <c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
    <div id="content">
        <form action="/controller" method="post" id="registerForm">
            <div>
                <label for="nameRegister"><fmt:message key="nameRegister" bundle="${booking}"/></label>
                <input type="text" id="nameRegister" name="nameRegister">
            </div>

            <div>
                <label for="surnameRegister"><fmt:message key="surnameRegister" bundle="${booking}"/></label>
                <input type="text" id="surnameRegister" name="surnameRegister">
            </div>

            <div>
                <label for="emailRegister"><fmt:message key="emailRegister" bundle="${booking}"/></label>
                <input type="email" id="emailRegister" name="emailRegister">
            </div>

            <div>
                <label for="passwordRegister"><fmt:message key="passwordRegister" bundle="${booking}"/></label>
                <input type="password" id="passwordRegister" name="passwordRegister">
            </div>

            <div>
                <label for="repeatPasswordRegister"><fmt:message key="repeatPasswordRegister" bundle="${booking}"/></label>
                <input type="password" id="repeatPasswordRegister" name="repeatPasswordRegister">
            </div>

            <div>
                <label><fmt:message key="registerButton" bundle="${booking}" var="registerButton"/></label>
                <input type="submit" id="registerButton" name="registerButton" value="${registerButton}">
                <input type="hidden" name="command" value="register">
            </div>
        </form>
    </div>
    <c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

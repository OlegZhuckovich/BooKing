<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../common/web.jsp"/>
    <script>require(['editAccount'])</script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content">

    <form action="/controller" method="post">

        <div>
            <label for="bookTitle"><fmt:message key="bookTitle" bundle="${booking}"/></label>
            <input type="text" id="bookTitle" name="bookTitle">
        </div>

        <div>
            <label><fmt:message key="bookGenre" bundle="${booking}"/></label>
            <select name="bookGenre">
            <c:forEach var="book" items="${bookList}" varStatus="count">
                <option value="${count}"><c:out value="${book}"/></option>
            </c:forEach>
            </select>
        </div>

        <div>
            <label for="bookPublishingHouse"><fmt:message key="bookPublishingHouse" bundle="${booking}"/></label>
            <input type="text" id="bookPublishingHouse" name="bookPublishingHouse">
        </div>

        <div>
            <label><fmt:message key="bookYear" bundle="${booking}"/></label>
            <select name="bookYear">
            </select>
        </div>

        <div>
            <label for="bookPages"><fmt:message key="bookPages" bundle="${booking}"/></label>
            <input type="text" id="bookPages" name="bookPages">
        </div>

        <div>
            <label for="bookQuantity"><fmt:message key="bookQuantity" bundle="${booking}"/></label>
            <input type="number" min="1" max="100" id="bookQuantity" name="bookQuantity">
        </div>

        <div>
            <label for="bookAuthor"><fmt:message key="bookAuthor" bundle="${booking}"/></label>
            <input type="text" id="bookAuthor" name="bookAuthor">
        </div>

        <div>
            <label><fmt:message key="saveChanges" bundle="${booking}" var="saveChanges"/></label>
            <input type="submit" id="saveChangesButton" name="saveChangesButton" value="${saveChanges}">
            <input type="hidden" name="command" value="edit_account">
        </div>
    </form>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>


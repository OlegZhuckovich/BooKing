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
    <script>require(['orderBookValidation'])</script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content">
    <table>
        <c:forEach var="readingRoomOrder" items="${readingRoomOrderList}" varStatus="id">
            <form action="/controller" method="post" id="deleteBookForm">
                <tr>
                    <td><p><c:out value="${readingRoomOrder.user.name}"/></p></td>
                    <td><p><c:out value="${readingRoomOrder.user.surname}"/></p></td>
                    <td><p><c:out value="${readingRoomOrder.user.email}"/></p></td>
                    <td><p><c:out value="${readingRoomOrder.book.title}"/></p></td>
                    <td><p><c:out value="${readingRoomOrder.book.genre}"/></p></td>
                    <td><p><c:out value="${readingRoomOrder.book.publishingHouse}"/></p></td>
                    <td><p><c:out value="${readingRoomOrder.book.numberInformation.year}"/></p></td>
                    <td><p><c:out value="${readingRoomOrder.book.numberInformation.pages}"/></p></td>
                    <td>
                        <input type="hidden" name="memberID" value="${readingRoomOrder.user.id}">
                        <input type="hidden" name="bookID" value="${readingRoomOrder.book.id}">
                        <input type="hidden" name="command" value="reading_room_issue">
                        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#divIDNo${theCount.count}"><fmt:message key="issueBook" bundle="${booking}"/></button>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>
    ${operationMessage}
</div>

<div id="divIDNo${theCount.count}" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="deleteBookConfirmHeader" bundle="${booking}"/></h4>
            </div>
            <div class="modal-body">
                <p><fmt:message key="deleteBookConfirmBody" bundle="${booking}"/></p>
            </div>
            <div class="modal-footer">
                <input type="submit" form="deleteBookForm" id="deleteBookYesConfirmButton" name="deleteBookYesConfirmButton" class="btn btn-default" value="<fmt:message key="deleteBookYesConfirmButton" bundle="${booking}"/>">
                <button type="button"  class="btn btn-default" data-dismiss="modal"><fmt:message key="deleteBookNoConfirmButton" bundle="${booking}"/></button>
            </div>
        </div>
    </div>
</div>

<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

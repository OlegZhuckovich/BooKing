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
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content">
    <c:choose>
        <c:when test="${ empty orderList}">
            <!--Блок если нет записей в таблице-->
            <p>Нет записей в таблице</p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <td><p><fmt:message key="bookTitle" bundle="${booking}"/></p></td>
                    <td><p><fmt:message key="bookGenre" bundle="${booking}"/></p></td>
                    <td><p><fmt:message key="bookPublishingHouse" bundle="${booking}"/></p></td>
                    <td><p><fmt:message key="bookYear" bundle="${booking}"/></p></td>
                    <td><p><fmt:message key="bookPages" bundle="${booking}"/></p></td>
                    <td><p><fmt:message key="bookOrderDate" bundle="${booking}"/></p></td>
                    <td><p><fmt:message key="bookReturnDate" bundle="${booking}"/></p></td>
                    <td><p><fmt:message key="bookOrderType" bundle="${booking}"/></p></td>
                </tr>
                <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td><p><c:out value="${order.book.title}"/></p></td>
                            <td><p><c:out value="${order.book.genre}"/></p></td>
                            <td><p><c:out value="${order.book.publishingHouse}"/></p></td>
                            <td><p><c:out value="${order.book.numberInformation.year}"/></p></td>
                            <td><p><c:out value="${order.book.numberInformation.pages}"/></p></td>
                            <c:choose>
                                <c:when test="${order.orderType == 'SUBSCRIPTION'}">
                                    <td><p><c:out value="${order.orderDate}"/></p></td>
                                    <td><p><c:out value="${order.returnDate}"/></p></td>
                                    <td><p><fmt:message key="subscription" bundle="${booking}"/></p></td>
                                </c:when>
                                <c:otherwise>
                                    <td><p><fmt:message key="readingRoom" bundle="${booking}"/></p></td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <form action="/bookController" method="get">
                                    <input type="hidden" name="bookID" value="${order.book.id}"/>
                                    <input type="hidden" name="command" value="read_book">
                                    <input type="submit" id="readBookButton" name="readBookButton" value="<fmt:message key="readBook" bundle="${booking}"/>">
                                </form>
                            </td>
                            <td>
                                <button type="button"  data-toggle="modal" data-target="#${order.book.id}"><fmt:message key="returnBook" bundle="${booking}"/></button>
                                <form action="/controller" method="post">
                                    <input type="hidden" name="command" value="return_book">
                                    <!--modal window-->
                                    <div id="${order.book.id}" class="modal fade" role="dialog">
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
                                                    <input type="submit" name="deleteBookYesConfirmButton" class="btn btn-default" value="<fmt:message key="deleteBookYesConfirmButton" bundle="${booking}"/>">
                                                    <button type="button"  class="btn btn-default" data-dismiss="modal"><fmt:message key="deleteBookNoConfirmButton" bundle="${booking}"/></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--modal window-->
                                </form>
                            </td>
                        </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
    ${operationMessage}
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

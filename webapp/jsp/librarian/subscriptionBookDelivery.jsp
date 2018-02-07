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
        <c:forEach var="subscriptionOrder" items="${subscriptionOrderList}" varStatus="id">
            <form action="/controller" method="post" id="issueSubscriptionForm">
                <tr>
                    <td><p><c:out value="${subscriptionOrder.user.name}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.user.surname}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.user.email}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.user.address.city}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.user.address.street}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.user.address.house}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.user.address.telephoneNumber}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.book.title}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.book.genre}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.book.publishingHouse}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.book.numberInformation.year}"/></p></td>
                    <td><p><c:out value="${subscriptionOrder.book.numberInformation.pages}"/></p></td>
                    <td>
                        <input type="hidden" name="memberID" value="${subscriptionOrder.user.id}">
                        <input type="hidden" name="bookID" value="${subscriptionOrder.book.id}">
                        <input type="hidden" name="command" value="subscription_issue">
                        <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#divIDNo${id.count}"><fmt:message key="issueBook" bundle="${booking}"/></button>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>
    ${operationMessage}
</div>

<div id="divIDNo${id.count}" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><fmt:message key="subscriptionConfirmHeader" bundle="${booking}"/></h4>
            </div>
            <div class="modal-body">
                <p><fmt:message key="subscriptionConfirmBody" bundle="${booking}"/></p>
            </div>
            <div class="modal-footer">
                <input type="submit" form="issueSubscriptionForm" id="issueSubscriptionBookButton" name="deleteBookYesConfirmButton" class="btn btn-default" value="<fmt:message key="subscriptionYesConfirmButton" bundle="${booking}"/>">
                <button type="button"  class="btn btn-default" data-dismiss="modal"><fmt:message key="subscriptionNoConfirmButton" bundle="${booking}"/></button>
            </div>
        </div>
    </div>
</div>

<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

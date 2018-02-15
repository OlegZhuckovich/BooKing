<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="js" uri="script" %>
<c:set var="language" value="${ not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <js:script/>
    <script src="${pageContext.request.contextPath}/js/librarianMenu.js" type="text/javascript"></script>
    <script>
        function confirmDeleteAccount() {
            swal({
                title: '<fmt:message key="deleteAccountConfirmTitle" bundle="${booking}"/>',
                text: '<fmt:message key="deleteAccountConfirmBody" bundle="${booking}"/>',
                icon: "warning",
                buttons: true,
                dangerMode: true
            }).then(function(willDelete){
                if (willDelete) {
                    $('#deleteAccountForm').submit();
                } else {
                    swal('<fmt:message key="deleteAccountCancel" bundle="${booking}"/>');
                }
            });
        }
    </script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid" style="background: url('${pageContext.request.contextPath}/images/librarianMenuBackground.png'); background-size: 100% 100%;">
    <div class="row" id="firstRow"></div>
    <div class="row" id="menuRow">
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
        <div id="menuImages" class="col-xs-12 col-sm-10 col-md-8 sideColumn">
            <div class="row menuInnerRow">
                <div id="readingRoomBookDelivery" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="subscriptionBookDelivery" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="addAuthor" class="col-xs-4 col-sm-4 col-md-4"></div>
            </div>
            <div class="row menuInnerRow">
                <div id="editBook" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="editAccount" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="deleteAccount" onclick="confirmDeleteAccount()" class="col-xs-4 col-sm-4 col-md-4"></div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
    </div>
    <div class="row titleBlockRow">
        <div class="hidden-xs col-sm-1 col-md-2"></div>
        <div id="titleBlock" class="col-xs-12 col-sm-10 col-md-8">
            <div class="col-xs-2 col-sm-3 col-md-3"></div>
            <div class="col-xs-8 col-sm-6 col-md-6">
                <label id="readingRoomBookDeliveryLabel" class="hiddenLabel"><fmt:message key="readingRoomBookDelivery" bundle="${booking}"/></label>
                <label id="subscriptionBookDeliveryLabel" class="hiddenLabel"><fmt:message key="subscriptionBookDelivery" bundle="${booking}"/></label>
                <label id="addAuthorLabel" class="hiddenLabel"><fmt:message key="addAuthor" bundle="${booking}"/></label>
                <label id="editBookLabel" class="hiddenLabel"><fmt:message key="editBook" bundle="${booking}"/></label>
                <label id="editAccountLabel" class="hiddenLabel"><fmt:message key="editAccount" bundle="${booking}"/></label>
                <label id="deleteAccountLabel" class="hiddenLabel"><fmt:message key="deleteAccount" bundle="${booking}"/></label>
            </div>
            <div class="col-xs-2 col-sm-3 col-md-3"></div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2"></div>
    </div>
</div>
<!--forms for different actions-->
<form id="readingRoomBookDeliveryForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="reading_room_menu"></form>
<form id="subscriptionBookDeliveryForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="subscription_menu"></form>
<form id="addAuthorForm" action="/jsp/librarian/addAuthor.jsp" class="hiddenForm"></form>
<form id="editBookForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="edit_books_menu"></form>
<form id="editAccountForm" action="/jsp/common/editAccount.jsp" class="hiddenForm"></form>
<form id="deleteAccountForm" action="/controller" method="get" class="hiddenForm">
    <input type="hidden" name="command" value="delete_account">
    <input type="hidden" name="deleteAccount" value="librarian">
</form>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
<c:if test="${not empty authorAddedResult}">
    <c:choose>
        <c:when test="${authorAddedResult eq 'success'}">
            <script>swal('<fmt:message key="addBookSuccess" bundle="${booking}"/>','<fmt:message key="bookAuthor" bundle="${booking}"/> ' + '${author}' + ' <fmt:message key="addBookAdded" bundle="${booking}"/>', "success");</script>
        </c:when>
        <c:otherwise>
            <script>swal('<fmt:message key="addBookErrorAddedTitle" bundle="${booking}"/>','<fmt:message key="bookAuthor" bundle="${booking}"/> ' + '${author}' + ' <fmt:message key="addBookErrorAdded" bundle="${booking}"/>', "error");</script>
        </c:otherwise>
    </c:choose>
    <c:remove var="authorAddedResult" scope="session"/>
    <c:remove var="author" scope="session"/>
</c:if>
<c:if test="${not empty emptyReadingRoomDelivery}">
    <script>swal('','<fmt:message key="readingRoomDeliveryEmptyBody" bundle="${booking}"/>', "success");</script>
    <c:remove var="emptyReadingRoomDelivery" scope="session"/>
</c:if>
<c:if test="${not empty emptySubscriptionDelivery}">
    <script>swal('','<fmt:message key="subscriptionDeliveryEmptyBody" bundle="${booking}"/>', "success");</script>
    <c:remove var="emptySubscriptionDelivery" scope="session"/>
</c:if>
</body>
</html>

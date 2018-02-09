<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${ not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css'>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style1.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/table_new.css">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="../../js/librarianMenu.js" type="text/javascript"></script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<%--https://www.bls.gov/ooh/images/2217.jpg  нормальная картинка--%>
<!--content of the page-->
<div id="content" class="container-fluid">
    <div class="row" style="height: 10%"></div>
    <div class="row" style="height: 60%">
        <div class="hidden-xs col-sm-1 col-md-2" style="height: 100%"></div>
        <div id="menuImages" class="col-xs-12 col-sm-10 col-md-8" style="height: 100%">
            <div class="row" style="height: 50%">
                <div id="readingRoomBookDelivery" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="subscriptionBookDelivery" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="addAuthor" class="col-xs-4 col-sm-4 col-md-4" style="background: url('https://24smi.org/public/media/2017/6/10/01_NMBSs1L.jpg') no-repeat;background-size: 100% 100%; height: 100%"></div>
            </div>
            <div class="row" style="height: 50%">
                <div id="editBook" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="editAccount" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="deleteAccount" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2" style="height: 100%"></div>
    </div>
    <div class="row" style="height: 8%">
        <div class="hidden-xs col-sm-1 col-md-2"></div>
        <div id="titleBlock" class="col-xs-12 col-sm-10 col-md-8" style="background-color: #F06060; height:100%; text-align: center; display: none">
            <div class="col-xs-2 col-sm-3 col-md-3"></div>
            <div class="col-xs-8 col-sm-6 col-md-6">
                <label id="readingRoomBookDeliveryLabel" style="display: none"><fmt:message key="readingRoomBookDelivery" bundle="${booking}"/></label>
                <label id="subscriptionBookDeliveryLabel" style="display: none"><fmt:message key="subscriptionBookDelivery" bundle="${booking}"/></label>
                <label id="addAuthorLabel" style="display: none"><fmt:message key="addAuthor" bundle="${booking}"/></label>
                <label id="editBookLabel" style="display: none"><fmt:message key="editBook" bundle="${booking}"/></label>
                <label id="editAccountLabel" style="display: none"><fmt:message key="editAccount" bundle="${booking}"/></label>
                <label id="deleteAccountLabel" style="display: none"><fmt:message key="deleteAccount" bundle="${booking}"/></label>
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
<form id="editBookForm" action="/jsp/librarian/editBook.jsp" class="hiddenForm"><input type="hidden" name="command" value="view_authors"></form>
<form id="editAccountForm" action="/jsp/common/editAccount.jsp" class="hiddenForm"></form>
<form id="deleteAccountForm" action="/controller" method="get" class="hiddenForm">
    <input type="hidden" name="command" value="delete_account">
    <input type="hidden" name="deleteAccount" value="librarian">
</form>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
<c:if test="${not empty authorAddedResult}">
    <c:choose>
        <c:when test="${authorAddedResult eq 'success'}">
            <script>swal('<fmt:message key="addBookSuccess" bundle="${booking}"/>','<fmt:message key="addBookBook" bundle="${booking}"/> ' + '${author}' + ' <fmt:message key="addBookAdded" bundle="${booking}"/>', "success");</script>
        </c:when>
        <c:otherwise>
            <script>swal('<fmt:message key="addBookErrorAddedTitle" bundle="${booking}"/>','<fmt:message key="addBookBook" bundle="${booking}"/> ' + '${author}' + ' <fmt:message key="addBookErrorAdded" bundle="${booking}"/>', "error");</script>
        </c:otherwise>
    </c:choose>
    <c:remove var="authorAddedResult" scope="session"/>
    <c:remove var="author" scope="session"/>
</c:if>
</body>
</html>

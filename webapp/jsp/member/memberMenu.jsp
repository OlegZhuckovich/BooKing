<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${ not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/memberMenu.js" type="text/javascript"></script>
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
        <div id="content" class="container-fluid" style="background: url('${pageContext.request.contextPath}/images/memberMenuBackground.png'); background-size: 100% 100%;">
            <div class="row" id="firstRow"></div>
            <div class="row" id="menuRow">
                <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
                <div id="menuImages" class="col-xs-12 col-sm-10 col-md-8 sideColumn">
                    <div class="row menuInnerRow">
                        <div id="orderBook" class="col-xs-4 col-sm-4 col-md-4"></div>
                        <div id="viewOrderedBooks" class="col-xs-4 col-sm-4 col-md-4"></div>
                        <div id="editAccount" class="col-xs-4 col-sm-4 col-md-4"></div>
                    </div>
                    <div class="row menuInnerRow">
                        <div id="authorGallery" class="col-xs-4 col-sm-4 col-md-4"></div>
                        <div id="trainingVideo" class="col-xs-4 col-sm-4 col-md-4"></div>
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
                        <label id="orderBookLabel" class="hiddenLabel"><fmt:message key="orderBook" bundle="${booking}"/></label>
                        <label id="viewOrderedBooksLabel" class="hiddenLabel"><fmt:message key="viewOrderedBooks" bundle="${booking}"/></label>
                        <label id="editAccountLabel" class="hiddenLabel"><fmt:message key="editAccount" bundle="${booking}"/></label>
                        <label id="authorGalleryLabel" class="hiddenLabel"><fmt:message key="authorGallery" bundle="${booking}"/></label>
                        <label id="trainingVideoLabel" class="hiddenLabel"><fmt:message key="trainingVideo" bundle="${booking}"/></label>
                        <label id="deleteAccountLabel" class="hiddenLabel"><fmt:message key="deleteAccount" bundle="${booking}"/></label>
                    </div>
                    <div class="col-xs-2 col-sm-3 col-md-3"></div>
                </div>
                <div class="hidden-xs col-sm-1 col-md-2"></div>
            </div>
        </div>
        <form id="orderBookForm" action="/jsp/member/orderBook.jsp" class="hiddenForm"></form>
        <form id="viewOrderedBooksForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="view_ordered_books"></form>
        <form id="editAccountForm" action="/jsp/common/editAccount.jsp" class="hiddenForm"></form>
        <form id="authorGalleryForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="view_authors"></form>
        <form id="trainingVideoForm" action="/jsp/member/trainingVideo.jsp" class="hiddenForm"></form>
        <form id="deleteAccountForm" action="/controller" method="get"  class="hiddenForm">
            <input type="hidden" name="command" value="delete_account">
            <input type="hidden" name="deleteAccount" value="user">
        </form>
    <c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
    <c:if test="${not empty deleteAccount}">
        <c:choose>
            <c:when test="${deleteAccount == 'success'}">
                <script>swal('','<fmt:message key="deleteAccountSuccessBody" bundle="${booking}"/>', "success");</script>
            </c:when>
            <c:otherwise>
                <script>swal('','<fmt:message key="deleteAccountErrorBody" bundle="${booking}"/>', "error");</script>
            </c:otherwise>
        </c:choose>
        <c:remove var="deleteAccount" scope="session"/>
    </c:if>
    <c:if test="${not empty orderOperationResult}">
        <script>swal('','<fmt:message key="viewOrderedBooksWarningBody" bundle="${booking}"/>', "warning");</script>
        <c:remove var="orderOperationResult" scope="request"/>
    </c:if>
</body>
</html>
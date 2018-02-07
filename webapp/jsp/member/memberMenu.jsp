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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="../../js/memberMenu.js" type="text/javascript"></script>
    <title>BooKing</title>
</head>
<body id="page">
    <c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
        <div id="content" class="container-fluid">
            <div class="row" style="height: 10%"></div>
            <div class="row" style="height: 60%">
                <div class="hidden-xs col-sm-1 col-md-2" style="height: 100%"></div>
                <div id="menuImages" class="col-xs-12 col-sm-10 col-md-8" style="height: 100%">
                    <div class="row" style="height: 50%">
                        <div id="orderBook" class="col-xs-4 col-sm-4 col-md-4" style="background: url('https://mainehumanities.org/wp-content/uploads/2015/05/student-in-library.jpg') no-repeat;background-size: 100% 100%; height: 100%"></div>
                        <div id="viewOrderedBooks" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                        <div id="editAccount" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                    </div>
                    <div class="row" style="height: 50%">
                        <div id="authorGallery" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                        <div id="trainingVideo" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
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
                        <label id="orderBookLabel" style="display: none"><fmt:message key="orderBook" bundle="${booking}"/></label>
                        <label id="viewOrderedBooksLabel" style="display: none"><fmt:message key="viewOrderedBooks" bundle="${booking}"/></label>
                        <label id="editAccountLabel" style="display: none"><fmt:message key="editAccount" bundle="${booking}"/></label>
                        <label id="authorGalleryLabel" style="display: none"><fmt:message key="authorGallery" bundle="${booking}"/></label>
                        <label id="trainingVideoLabel" style="display: none"><fmt:message key="trainingVideo" bundle="${booking}"/></label>
                        <label id="deleteAccountLabel" style="display: none"><fmt:message key="deleteAccount" bundle="${booking}"/></label>
                    </div>
                    <div class="col-xs-2 col-sm-3 col-md-3"></div>
                </div>
                <div class="hidden-xs col-sm-1 col-md-2"></div>
            </div>
        </div>
        <form id="orderBookForm" action="/jsp/member/orderBook.jsp" style="display: none"></form>
        <form id="viewOrderedBooksForm" action="/controller" style="display: none"><input type="hidden" name="command" value="view_ordered_books"></form>
        <form id="editAccountForm" action="/jsp/common/editAccount.jsp" style="display: none"></form>
        <form id="authorGalleryForm" action="/controller" style="display: none"><input type="hidden" name="command" value="view_authors"></form>
        <form id="trainingVideoForm" action="/jsp/member/trainingVideo.jsp" style="display: none"></form>
        <form id="deleteAccountForm" action="/controller" method="get" style="display: none">
            <input type="hidden" name="command" value="delete_account">
            <input type="hidden" name="deleteAccount" value="user">
        </form>
        <form action="/jsp/member/memberMenu.jsp" name="languageForm" method="post" style="display: none">
            <select id="languageSelect" name="language"  onchange="document.languageForm.submit()">
                <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}><fmt:message key="russian" bundle="${booking}"/></option>
                <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}><fmt:message key="english" bundle="${booking}"/></option>
            </select>
        </form>
    <c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>
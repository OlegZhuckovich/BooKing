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
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="../../js/administratorMenu.js" type="text/javascript"></script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<!--content of the page-->
<div id="content" class="container-fluid">
    <div class="row" style="height: 10%"></div>
    <div class="row" style="height: 60%">
        <div class="hidden-xs col-sm-1 col-md-2" style="height: 100%"></div>
        <div id="menuImages" class="col-xs-12 col-sm-10 col-md-8" style="height: 100%">
            <div class="row" style="height: 50%">
                <div id="addBook" class="col-xs-4 col-sm-4 col-md-4" style="background: url('https://www.thehagueuniversity.com/images/default-source/studievoorzieningen/zoeken-en-vinden.jpg?sfvrsn=275152ee_4&size=600') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="addLibrarian" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="editAccount" class="col-xs-4 col-sm-4 col-md-4" style="background: url('../../css/Library.png') no-repeat;background-size: 100% 100%; height: 100%"></div>
            </div>
            <div class="row" style="height: 50%">
                <div id="deleteBookMenu" class="col-xs-4 col-sm-4 col-md-4" style="background: url('https://az616578.vo.msecnd.net/files/2016/05/19/635992798386672964-148953701_Stack-of-books-great-education.jpg') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="deleteLibrarianMenu" class="col-xs-4 col-sm-4 col-md-4" style="background: url('https://bloximages.newyork1.vip.townnews.com/collegian.psu.edu/content/tncms/assets/v3/editorial/2/0e/20e79ee0-9437-11e7-bcc3-67e65471482e/59b1f5c76ef4b.image.jpg?resize=1200%2C800') no-repeat;background-size: 100% 100%; height: 100%"></div>
                <div id="deleteMemberMenu" class="col-xs-4 col-sm-4 col-md-4" style="background: url('https://agescotland.files.wordpress.com/2012/06/two-men-in-library.jpg') no-repeat;background-size: 100% 100%; height: 100%"></div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2" style="height: 100%"></div>
    </div>
    <div class="row" style="height: 8%">
        <div class="hidden-xs col-sm-1 col-md-2"></div>
        <div id="titleBlock" class="col-xs-12 col-sm-10 col-md-8" style="background-color: #F06060; height:100%; text-align: center; display: none">
            <div class="col-xs-2 col-sm-3 col-md-3"></div>
            <div class="col-xs-8 col-sm-6 col-md-6">
                <label id="addBookLabel" style="display: none"><fmt:message key="addBook" bundle="${booking}"/></label>
                <label id="addLibrarianLabel" style="display: none"><fmt:message key="addLibrarian" bundle="${booking}"/></label>
                <label id="editAccountLabel" style="display: none"><fmt:message key="editAccount" bundle="${booking}"/></label>
                <label id="deleteBookMenuLabel" style="display: none"><fmt:message key="deleteBook" bundle="${booking}"/></label>
                <label id="deleteLibrarianMenuLabel" style="display: none"><fmt:message key="deleteLibrarian" bundle="${booking}"/></label>
                <label id="deleteMemberMenuLabel" style="display: none"><fmt:message key="deleteMember" bundle="${booking}"/></label>
            </div>
            <div class="col-xs-2 col-sm-3 col-md-3"></div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2"></div>
    </div>
</div>
<!--forms for different actions-->
<form id="addBookForm" action="/controller" style="display: none"><input type="hidden" name="command" value="add_book_menu"></form>
<form id="addLibrarianForm" action="/jsp/administrator/addLibrarian.jsp" style="display: none"></form>
<form id="editAccountForm" action="/jsp/common/editAccount.jsp" style="display: none"></form>
<form id="deleteBookMenuForm" action="/controller" style="display: none"><input type="hidden" name="command" value="delete_book_menu"></form>
<form id="deleteLibrarianMenuForm" action="/controller" style="display: none"><input type="hidden" name="command" value="delete_librarian_menu"></form>
<form id="deleteMemberMenuForm" action="/controller" method="get" style="display: none"><input type="hidden" name="command" value="delete_member_menu"></form>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
<c:if test="${not empty bookAddedResult}">
    <c:choose>
        <c:when test="${bookAddedResult eq 'success'}">
            <script>swal('<fmt:message key="addBookSuccess" bundle="${booking}"/>','<fmt:message key="addBookBook" bundle="${booking}"/> ' + '${bookTitle}' + ' <fmt:message key="addBookAdded" bundle="${booking}"/>', "success");</script>
        </c:when>
        <c:otherwise>
            <script>swal('<fmt:message key="addBookErrorAddedTitle" bundle="${booking}"/>','<fmt:message key="addBookBook" bundle="${booking}"/> ' + '${bookTitle}' + ' <fmt:message key="addBookErrorAdded" bundle="${booking}"/>', "error");</script>
        </c:otherwise>
    </c:choose>
    <c:remove var="bookAddedResult" scope="session"/>
    <c:remove var="bookTitle" scope="session"/>
</c:if>
</body>
</html>

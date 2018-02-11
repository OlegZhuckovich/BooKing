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
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/table_new.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/administratorMenu.js" type="text/javascript"></script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid">
    <div class="row" id="firstRow"></div>
    <div class="row" id="menuRow">
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
        <div id="menuImages" class="col-xs-12 col-sm-10 col-md-8 sideColumn">
            <div class="row menuInnerRow">
                <div id="addBook" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="addLibrarian" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="editAccount" class="col-xs-4 col-sm-4 col-md-4"></div>
            </div>
            <div class="row menuInnerRow">
                <div id="deleteBookMenu" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="deleteLibrarianMenu" class="col-xs-4 col-sm-4 col-md-4"></div>
                <div id="deleteMemberMenu" class="col-xs-4 col-sm-4 col-md-4"></div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
    </div>
    <div class="row titleBlockRow">
        <div class="hidden-xs col-sm-1 col-md-2"></div>
        <div id="titleBlock" class="col-xs-12 col-sm-10 col-md-8">
            <div class="col-xs-2 col-sm-3 col-md-3"></div>
            <div class="col-xs-8 col-sm-6 col-md-6">
                <label id="addBookLabel"><fmt:message key="addBook" bundle="${booking}"/></label>
                <label id="addLibrarianLabel"><fmt:message key="addLibrarian" bundle="${booking}"/></label>
                <label id="editAccountLabel"><fmt:message key="editAccount" bundle="${booking}"/></label>
                <label id="deleteBookMenuLabel"><fmt:message key="deleteBook" bundle="${booking}"/></label>
                <label id="deleteLibrarianMenuLabel"><fmt:message key="deleteLibrarian" bundle="${booking}"/></label>
                <label id="deleteMemberMenuLabel"><fmt:message key="deleteMember" bundle="${booking}"/></label>
            </div>
            <div class="col-xs-2 col-sm-3 col-md-3"></div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2"></div>
    </div>
</div>
<!--forms for different actions-->
<form id="addBookForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="add_book_menu"></form>
<form id="addLibrarianForm" action="/jsp/administrator/addLibrarian.jsp" class="hiddenForm"></form>
<form id="editAccountForm" action="/jsp/common/editAccount.jsp" class="hiddenForm"></form>
<form id="deleteBookMenuForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="delete_book_menu"></form>
<form id="deleteLibrarianMenuForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="delete_librarian_menu"></form>
<form id="deleteMemberMenuForm" action="/controller" method="get" class="hiddenForm"><input type="hidden" name="command" value="delete_member_menu"></form>
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
<c:if test="${not empty emptyDeleteBook}">
    <script>swal('<fmt:message key="addBookErrorAddedTitle" bundle="${booking}"/>','<fmt:message key="addBookBook" bundle="${booking}"/> ' + '${bookTitle}' + ' <fmt:message key="addBookErrorAdded" bundle="${booking}"/>', "error");</script>
    <c:remove var="emptyDeleteBook" scope="session"/>
</c:if>
<c:if test="${not empty emptyDeleteLibrarian}">
    <script>swal('<fmt:message key="addBookErrorAddedTitle" bundle="${booking}"/>','<fmt:message key="addBookBook" bundle="${booking}"/> ' + '${bookTitle}' + ' <fmt:message key="addBookErrorAdded" bundle="${booking}"/>', "error");</script>
    <c:remove var="emptyDeleteLibrarian" scope="session"/>
</c:if>
<c:if test="${not empty emptyDeleteMember}">
    <script>swal('<fmt:message key="addBookErrorAddedTitle" bundle="${booking}"/>','<fmt:message key="addBookBook" bundle="${booking}"/> ' + '${bookTitle}' + ' <fmt:message key="addBookErrorAdded" bundle="${booking}"/>', "error");</script>
    <c:remove var="emptyDeleteMember" scope="session"/>
</c:if>
</body>
</html>

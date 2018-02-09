<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<c:set var="foo" value="${pageContext.request.contextPath}/image/foo.png"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select.css">
    <script src="${pageContext.request.contextPath}/js/select.js" type="text/javascript"></script>
</head>
<body>
<header style="display: flex; background-color: #35B54F;">
    <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" width="100px" style="height: 70px; margin-top: 7px;"/>
    <c:choose>
    <c:when test="${pageContext.request.requestURI eq '/jsp/administrator/administratorMenu.jsp' || pageContext.request.requestURI eq '/jsp/librarian/librarianMenu.jsp' || pageContext.request.requestURI eq '/jsp/member/memberMenu.jsp'}">
        <h1 style="margin-top: 25px;" onClick="document.forms['logoutHeader'].submit();">BooKing</h1>
        <form action="/controller" id="logoutHeader" name="logoutHeader"><input type="hidden" name="command" value="logout"></form>
    </c:when>
    <c:when test="${ not empty user}">
        <c:if test="${ user.userType eq 'ADMINISTRATOR'}">
            <h1 style="margin-top: 25px;" onClick="document.forms['administratorMenuHeader'].submit();">BooKing</h1>
            <form action="/jsp/administrator/administratorMenu.jsp" id="administratorMenuHeader" name="administratorMenuHeader"></form>
        </c:if>
        <c:if test="${ user.userType eq 'LIBRARIAN'}">
            <h1 style="margin-top: 25px;" onClick="document.forms['librarianMenuHeader'].submit();">BooKing</h1>
            <form action="/jsp/librarian/librarianMenu.jsp"  id="librarianMenuHeader" name="librarianMenuHeader"></form>
        </c:if>
        <c:if test="${ user.userType eq 'MEMBER'}">
            <h1 style="margin-top: 25px;" onClick="document.forms['memberMenuHeader'].submit();">BooKing</h1>
            <form action="/jsp/member/memberMenu.jsp" id="memberMenuHeader" name="memberMenuHeader"></form>
        </c:if>
    </c:when>
    <c:otherwise>
        <h1 style="margin-top: 25px;">BooKing</h1>
    </c:otherwise>
    </c:choose>
    <ul>
        <div id="headerMenu">
            <c:if test="${not empty user}">
                <c:if test="${user.userType eq 'ADMINISTRATOR'}">
                    <div onClick="document.forms['addBookHeaderForm'].submit();" style="margin-left: 10px;"><li><fmt:message key="addBook" bundle="${booking}"/><form id="addBookHeaderForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="add_book_menu"></form></li></div>
                    <div onClick="document.forms['addLibrarianHeaderForm'].submit();"><li><fmt:message key="addLibrarian" bundle="${booking}"/><form id="addLibrarianHeaderForm" action="/jsp/administrator/addLibrarian.jsp" class="hiddenForm"></form></li></div>
                    <div onClick="document.forms['deleteBookMenuHeaderForm'].submit();"><li><fmt:message key="deleteBook" bundle="${booking}"/><form id="deleteBookMenuHeaderForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="delete_book_menu"></form></div>
                    <div onClick="document.forms['deleteLibrarianMenuHeaderForm'].submit();"><li><fmt:message key="deleteLibrarian" bundle="${booking}"/><form id="deleteLibrarianMenuHeaderForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="delete_librarian_menu"></form></div>
                    <div onClick="document.forms['deleteMemberMenuHeaderForm'].submit();"><li><fmt:message key="deleteMember" bundle="${booking}"/><form id="deleteMemberMenuHeaderForm" action="/controller" method="get" class="hiddenForm"><input type="hidden" name="command" value="delete_member_menu"></form></div>
                </c:if>
                <c:if test="${user.userType eq 'LIBRARIAN'}">
                    <div onClick="document.forms['readingRoomBookDeliveryHeaderForm'].submit();" style="margin-left: 10px;"><li><fmt:message key="readingRoomBookDelivery" bundle="${booking}"/><form id="readingRoomBookDeliveryHeaderForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="reading_room_menu"></form></li></div>
                    <div onClick="document.forms['subscriptionBookDeliveryHeaderForm'].submit();"><li><fmt:message key="subscriptionBookDelivery" bundle="${booking}"/><form id="subscriptionBookDeliveryHeaderForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="subscription_menu"></form></li></div>
                    <div onClick="document.forms['addAuthorHeaderForm'].submit();"><li><fmt:message key="addAuthor" bundle="${booking}"/><form id="addAuthorHeaderForm" action="/jsp/librarian/addAuthor.jsp" class="hiddenForm"></form></li></div>
                    <div onClick="document.forms['editBookHeaderForm'].submit();"><li><fmt:message key="editBook" bundle="${booking}"/><form id="editBookHeaderForm" action="/jsp/librarian/editBook.jsp" class="hiddenForm"><input type="hidden" name="command" value="view_authors"></form></li></div>
                    <div onClick="document.forms['deleteAccountHeaderForm'].submit();">
                        <li>
                            <fmt:message key="deleteAccount" bundle="${booking}"/>
                            <form id="deleteAccountHeaderForm" action="/controller" method="get" class="hiddenForm">
                                <input type="hidden" name="command" value="delete_account">
                                <input type="hidden" name="deleteAccount" value="librarian">
                            </form>
                        </li>
                    </div>
                </c:if>
                <c:if test="${user.userType eq 'MEMBER'}">
                    <div style="margin-left: 10px;" onClick="document.forms['orderBookHeaderForm'].submit();"><li><fmt:message key="orderBook" bundle="${booking}"/><form id="orderBookHeaderForm" action="/jsp/member/orderBook.jsp" class="hiddenForm"></form></li></div>
                    <div onClick="document.forms['viewOrderedBooksHeaderForm'].submit();"><li><fmt:message key="viewOrderedBooks" bundle="${booking}"/><form id="viewOrderedBooksHeaderForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="view_ordered_books"></form></li></div>
                    <div onClick="document.forms['authorGalleryHeaderForm'].submit();"><li><fmt:message key="editAccount" bundle="${booking}"/><form id="authorGalleryHeaderForm" action="/controller" class="hiddenForm"><input type="hidden" name="command" value="view_authors"></form></li></div>
                    <div onClick="document.forms['trainingVideoHeaderForm'].submit();"><li><fmt:message key="trainingVideo" bundle="${booking}"/><form id="trainingVideoHeaderForm" action="/jsp/member/trainingVideo.jsp" class="hiddenForm"></form></li></div>
                    <div onClick="document.forms['deleteAccountHeaderForm'].submit();">
                        <li>
                            <fmt:message key="deleteAccount" bundle="${booking}"/>
                            <form id="deleteAccountHeaderForm" action="/controller" method="get" class="hiddenForm">
                                <input type="hidden" name="command" value="delete_account">
                                <input type="hidden" name="deleteAccount" value="user">
                            </form>
                        </li>
                    </div>
                </c:if>
                <img src="${pageContext.request.contextPath}/image/${sessionScope.user.id}" height="85px"/>
                <div onClick="document.forms['editAccountHeaderForm'].submit();"><li>${sessionScope.user.name} ${sessionScope.user.surname}<form id="editAccountHeaderForm" action="/jsp/common/editAccount.jsp" class="hiddenForm"></form></li></div>
            </c:if>
        </div>
    </ul>
    <form method="post"> 
        <select id="language" name="language" onchange="submit()"> 
            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}><fmt:message key="russian" bundle="${booking}"/></option> 
            <option value="en_EN" ${language == 'en_EN' ? 'selected' : ''}><fmt:message key="english" bundle="${booking}"/></option> 
            <option value="be_BY" ${language == 'be_BY' ? 'selected' : ''}><fmt:message key="belarusian" bundle="${booking}"/></option> 
        </select>
    </form>
</header>
</body>
</html>
<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="js" uri="script" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <js:script/>
    <script src="${pageContext.request.contextPath}/js/select.js" type="text/javascript"></script>
    <script>
        function validation() {
            var title = $('#bookTitle').val();
            var publishingHouse = $('#bookPublishingHouse').val();
            var quantity = $('#bookQuantity').val();
            var year = $('#bookYear').val();
            var pages = $('#bookPages').val();
            if(!title || title.length > 250){
                swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addBookTitleError" bundle="${booking}"/>', "error"); return false;
            } else if (!publishingHouse || publishingHouse.length > 250){
                swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addBookPublishingHouseError" bundle="${booking}"/>', "error"); return false;
            } else if (!quantity || quantity > 100 || quantity < 1){
                swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addBookQuantityError" bundle="${booking}"/>', "error"); return false;
            } else if(!year || year < 1500 || year > 2018){
                swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addBookYearError" bundle="${booking}"/>', "error"); return false;
            } else if(!pages || pages < 1 || pages > 10000){
                swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addBookPagesError" bundle="${booking}"/>', "error"); return false;
            }
        }
    </script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="addBookContent" class="container-fluid content">
    <div class="row" id="firstRow"></div>
    <div class="row" id="secondRow">
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
        <div class="col-xs-12 col-sm-10 col-md-8" id="addBookBlock">
            <div class="container-fluid">
                <div>
                    <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="bookingLogo">
                    <h1 id="pageTitle"><fmt:message key="editBook" bundle="${booking}"/></h1>
                </div>
                <div class="row sideColumn">
                    <div class="col-xs-2 col-sm-2 col-md-2 sideColumn">
                        <img src="${pageContext.request.contextPath}/images/book1.svg" class="topImage">
                        <img src="${pageContext.request.contextPath}/images/book3.svg" class="bottomImage">
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-8 sideColumn">
                        <form action="/controller" method="post" id="addBookForm" onsubmit="return validation()" enctype="multipart/form-data">
                            <!--название книги-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="bookTitle" name="bookTitle" value="${book.title}" required/>
                                    <label><fmt:message key="bookTitle" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--издательство книги-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="bookPublishingHouse" name="bookPublishingHouse" value="${book.publishingHouse}" required/>
                                    <label><fmt:message key="bookPublishingHouse" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--количество экземпляров-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="number" min="1" max="100" id="bookQuantity" name="bookQuantity" value="${book.numberInformation.quantity}" required/>
                                    <label for="bookQuantity"><fmt:message key="bookQuantity" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--год издания-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="bookYear" name="bookYear" maxlength="4" value="${book.numberInformation.year}" onkeypress='return event.charCode >= 48 && event.charCode <= 57' required/>
                                    <label><fmt:message key="bookYear" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--количество страниц-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="bookPages" name="bookPages" maxlength="5" value="${book.numberInformation.pages}" onkeypress='return event.charCode >= 48 && event.charCode <= 57' required/>
                                    <label><fmt:message key="bookPages" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--блок выпадающих списков-->
                            <div class="form-group">
                                <div class="form-inline row">
                                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                        <!--жанр книги-->
                                        <select id="bookGenre" name="bookGenre" class="selectpicker" title="<fmt:message key="bookGenre" bundle="${booking}"/>" data-live-search="true" required>
                                            <c:forEach var="genre" items="${genreList}" varStatus="count">
                                                <c:choose>
                                                    <c:when test="${book.genre == genre}">
                                                        <option selected="selected"><c:out value="${genre}"/></option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option><c:out value="${genre}"/></option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                        <!--авторы книги-->
                                        <select id="bookAuthor" name="bookAuthor" class="selectpicker" multiple data-max-options="3" data-size="10" data-live-search="true" title="<fmt:message key="chooseNewAuthor" bundle="${booking}"/>" required>
                                            <c:forEach var="author" items="${authorList}">
                                                <option id="${author.id}" value="${author.id}"><c:out value="${author.name}"/> <c:out value="${author.surname}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <!--загружаемая книга-->
                            <div class="form-group">
                                <div class="form-inline row">
                                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                        <div class="uploadButton">
                                            <input type="file" accept="application/pdf" name="bookContent" id="bookContent" hidden />
                                        </div>
                                    </div>
                                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                        <!--кнопка submit-->
                                        <div class="form-group">
                                            <div class="inputGroup">
                                                <input type="hidden" name="bookID" value="${book.id}">
                                                <button type="submit" class="submitButton"><fmt:message key="addBook" bundle="${booking}"/></button>
                                                <input type="hidden" name="command" value="edit_current_book">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-xs-2 col-sm-2 col-md-2 sideColumn">
                        <img src="${pageContext.request.contextPath}/images/book2.svg" class="topImage">
                        <img src="${pageContext.request.contextPath}/images/book4.svg" class="bottomImage">
                    </div>
                </div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
    </div>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

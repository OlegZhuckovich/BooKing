<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style1.css">
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="../../css/select.css">
    <script src="../../js/select.js"></script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid">

    <div class="row" style="height: 10%"></div>
    <div class="row" style="height: 70%">
        <div class="hidden-xs col-sm-2 col-md-3" style="height: 100%"></div>

        <form action="/controller" method="post" id="editAccountForm" enctype="multipart/form-data">

        <div class="col-xs-12 col-sm-8 col-md-6" style="background-color: #F06060; border-radius: 20px; overflow: scroll; height: 100%">
            <div class="row" style="height: 100%;">
                <div class="col-xs-4 col-sm-4 col-md-4">
                    <img src="${pageContext.request.contextPath}/image/${sessionScope.user.id}" width="100%" style="margin-top: 10%"/>

                    <p>Дата регистрации</p>
                    <p>21/10/2018</p>

                    <div>
                        <input type="file" name="avatarUser"/>
                    </div>

                    <div>
                        <label><fmt:message key="saveChanges" bundle="${booking}" var="saveChanges"/></label>
                        <input type="submit" class="btn btn-default" id="saveChangesButton" name="saveChangesButton" value="${saveChanges}">
                        <input type="hidden" name="command" value="edit_account">
                    </div>

                </div>


                <div class="col-xs-8 col-sm-8 col-md-8">

                    <div style="margin-top: 3%">
                        <label for="nameUser"><fmt:message key="nameUser" bundle="${booking}"/></label>
                        <input type="text" id="nameUser" class="form-control" name="nameUser" value="${user.name}">
                    </div>

                    <div>
                        <label for="surnameUser"><fmt:message key="surnameUser" bundle="${booking}"/></label>
                        <input type="text" id="surnameUser" class="form-control" name="surnameUser" value="${user.surname}">
                    </div>

                    <div>
                        <label for="emailUser"><fmt:message key="emailUser" bundle="${booking}"/></label>
                        <input type="email" id="emailUser" class="form-control" name="emailUser" value="${user.email}">
                    </div>

                    <div>
                        <label for="passwordUser"><fmt:message key="passwordUser" bundle="${booking}"/></label>
                        <input type="password" id="passwordUser" class="form-control" name="passwordUser">
                    </div>

                    <div>
                        <label for="repeatPasswordUser"><fmt:message key="repeatPasswordUser" bundle="${booking}"/></label>
                        <input type="password" id="repeatPasswordUser" class="form-control" name="repeatPasswordUser">
                    </div>

                    <div class="container-fluid" style="height: auto;">
                        <div class="row" style="height: 50%;">
                            <div class="col-xs-6 col-sm-6 col-md-6" style="padding-left:0;">
                                <label for="streetUser"><fmt:message key="streetUser" bundle="${booking}"/></label>
                                <c:choose>
                                    <c:when test="${ not empty user.address.street}">
                                        <input type="text" id="streetUser" class="form-control" name="streetUser" value="${user.address.street}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" id="streetUser" class="form-control" name="streetUser">
                                    </c:otherwise>
                                </c:choose>
                                <label for="houseUser"><fmt:message key="houseUser" bundle="${booking}"/></label>
                                <c:choose>
                                    <c:when test="${ not empty user.address.house}">
                                        <input type="text" id="houseUser" class="form-control" name="houseUser" value="${user.address.house}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" id="houseUser" class="form-control" name="houseUser">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6" style="padding-left:0; padding-right: 0">
                                <label for="cityUser"><fmt:message key="cityUser" bundle="${booking}"/></label>
                                <c:choose>
                                    <c:when test="${ not empty user.address.city}">
                                        <input type="text" id="cityUser" class="form-control" name="cityUser" value="${user.address.city}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" id="cityUser" class="form-control" name="cityUser">
                                    </c:otherwise>
                                </c:choose>

                                <label for="telephoneUser"><fmt:message key="telephoneUser" bundle="${booking}"/></label>
                                <c:choose>
                                    <c:when test="${ not empty user.address.telephoneNumber}">
                                        <input type="text" id="telephoneUser" class="form-control" name="telephoneUser" value="${user.address.telephoneNumber}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" id="telephoneUser" class="form-control" name="telephoneUser">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="row" style="height: 50%"></div>
                    </div>
                </div>
            </div>
        </div>
        </form>
        <div class="hidden-xs col-sm-2 col-md-3" style="height: 100%"></div>
    </div>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>



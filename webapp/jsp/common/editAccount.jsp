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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table_new.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid">
    <div class="row firstEditRow"></div>
    <div class="row secondEditRow">
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
        <div class="col-xs-12 col-sm-10 col-md-8" id="editAccountBlock">
            <div class="container-fluid">
                <div>
                    <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="bookingLogo"><h1 id="pageTitle"><fmt:message key="editAccount" bundle="${booking}"/></h1>
                </div>
                <div class="row thirdEditRow">
                    <form action="/controller" method="post" id="editAccountForm" onsubmit="return validation()" enctype="multipart/form-data">
                        <div class="col-xs-12 col-sm-4 col-md-4">
                            <img src="${pageContext.request.contextPath}/image/${sessionScope.user.id}" id="editAccountAvatar"/>
                            <div class="uploadButton">
                                <input type="file" accept="image/jpeg" id="avatarUser" name="avatarUser" hidden required/>
                            </div>
                            <h4 style="text-align: center"><fmt:message key="editAccount" bundle="${booking}"/>: <fmt:formatDate value="${sessionScope.user.registrationDate}"/></h4>
                        </div>
                        <div class="col-xs-12 col-sm-4 col-md-4 sideColumn">
                            <div>
                                <img src="${pageContext.request.contextPath}/images/editAccount1.svg" class="blockImage">
                                <h3 class="blockTitle"><fmt:message key="editAccountMainInformation" bundle="${booking}"/></h3>
                            </div>
                            <!--имя пользователя-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="nameUser" name="nameUser" value="${user.name}" required/>
                                    <label><fmt:message key="nameUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--фамилия пользователя-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="surnameUser" name="surnameUser" value="${user.surname}" required/>
                                    <label><fmt:message key="surnameUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--email пользователя-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="email" id="emailUser" name="emailUser" value="${user.email}" required/>
                                    <label><fmt:message key="emailUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/editAccount3.svg" class="blockImage">
                                <h3 class="blockTitle" style="padding-bottom: 10px"><fmt:message key="editAccountSecurity" bundle="${booking}"/></h3>
                            </div>
                            <!--новый пароль-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="password" id="passwordUser" name="passwordUser">
                                    <label><fmt:message key="passwordUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--повтор пароля-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="password" id="repeatPasswordUser" name="repeatPasswordUser">
                                    <label><fmt:message key="repeatPasswordUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--кнопка submit-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <button type="submit" class="editAccountSubmitButton"><fmt:message key="saveChanges" bundle="${booking}"/></button>
                                    <input type="hidden" name="command" value="edit_account">
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-4 col-md-4 sideColumn">
                            <div>
                                <img src="${pageContext.request.contextPath}/images/editAccount2.svg" class="blockImage">
                                <h3 class="blockTitle"><fmt:message key="editAccountAddress" bundle="${booking}"/></h3>
                            </div>
                            <!--населённый пункт-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <c:choose>
                                        <c:when test="${not empty user.address.city}">
                                            <input type="text" id="cityUser" name="cityUser" style="margin-top: 55px;" value="${user.address.city}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" id="cityUser" name="cityUser" style="margin-top: 55px;">
                                        </c:otherwise>
                                    </c:choose>
                                    <label><fmt:message key="cityUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--улица-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="streetUser"  name="streetUser">
                                    <label><fmt:message key="streetUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--дом-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="houseUser" name="houseUser" maxlength="4"  onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
                                    <label><fmt:message key="houseUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/editAccount4.svg" class="blockImage"><h3 class="blockTitle" style="padding-bottom: 10px;"><fmt:message key="editAccountTelephoneNumber" bundle="${booking}"/></h3>
                            </div>
                            <!--номер телефона-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="telephoneUser" name="telephoneUser" minlength="7" maxlength="12"  onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
                                    <label><fmt:message key="telephoneUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/editAccount5.svg" class="blockImage">
                                <h5 class="blockTitle"><fmt:message key="editAccountSubscriptionInfo" bundle="${booking}"/></h5>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
    </div>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

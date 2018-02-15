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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <js:script/>
    <script>
        function validation() {
            var name = $('#nameUser').val();
            var surname = $('#surnameUser').val();
            var email = $('#emailUser').val();
            var password = $('#passwordUser').val();
            var repeatPassword = $('#repeatPasswordUser').val();
            var city = $('#cityUser').val();
            var street = $('#streetUser').val();
            var house = $('#houseUser').val();
            var telephone = $('#telephoneUser').val();
            var nameSurnameRegex = new RegExp("[A-ZА-Я][a-zа-я]+-?[A-ZА-Я]?[a-zа-я]+?");
            var emailRegex = new RegExp("[\\w\\.]{2,40}@[a-z]{2,10}\\.[a-z]{2,10}");
            var passwordRegex = new RegExp("[\\w]{5,40}");
            var cityStreetRegex = new RegExp("[A-ZА-Я][a-zа-я]{2,40}");
            if(!nameSurnameRegex.test(name)){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianNameError" bundle="${booking}"/>', "error"); return false;
            } else if (!nameSurnameRegex.test(surname)){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianSurnameError" bundle="${booking}"/>', "error"); return false;
            } else if (!emailRegex.test(email)){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianEmailError" bundle="${booking}"/>', "error"); return false;
            } else if(password || repeatPassword){
                if (!passwordRegex.test(password)){
                    swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianPasswordError" bundle="${booking}"/>', "error"); return false;
                } else if (password !== repeatPassword){
                    swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianRepeatPasswordError" bundle="${booking}"/>', "error"); return false;
                }
            } else if(city || street || house || telephone){
                if(!cityStreetRegex.test(city)){
                    swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="cityErrorValidation" bundle="${booking}"/>', "error"); return false;
                } else if (!cityStreetRegex.test(street)){
                    swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="streetErrorValidation" bundle="${booking}"/>', "error"); return false;
                } else if (house < 1 || house > 2000){
                    swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="houseErrorValidation" bundle="${booking}"/>', "error"); return false;
                } else if (!telephone){
                    swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="telephoneErrorValidation" bundle="${booking}"/>', "error"); return false;
                }
            }
        }
    </script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid" style="background: url('${pageContext.request.contextPath}/images/editAccountBackground.png'); background-size: 100% 100%;">
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
                                <input type="file" accept="image/jpeg" id="avatarUser" name="avatarUser" hidden />
                            </div>
                            <h4 style="text-align: center"><fmt:message key="registrationDate" bundle="${booking}"/>: <fmt:formatDate value="${sessionScope.user.registrationDate}"/></h4>
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
                                    <input type="email" id="emailUser" name="emailUser" value="${user.email}" style="margin-top: 30px" disabled/>
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
                                    <c:choose>
                                        <c:when test="${not empty user.address.street}">
                                            <input type="text" id="streetUser"  name="streetUser" value="${user.address.street}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" id="streetUser"  name="streetUser">
                                        </c:otherwise>
                                    </c:choose>
                                    <label><fmt:message key="streetUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--дом-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <c:choose>
                                        <c:when test="${not empty user.address.house}">
                                            <input type="text" id="houseUser" name="houseUser" maxlength="4"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' value="${user.address.house}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" id="houseUser" name="houseUser" maxlength="4"  onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
                                        </c:otherwise>
                                    </c:choose>
                                    <label><fmt:message key="houseUser" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <div>
                                <img src="${pageContext.request.contextPath}/images/editAccount4.svg" class="blockImage"><h3 class="blockTitle" style="padding-bottom: 10px;"><fmt:message key="editAccountTelephoneNumber" bundle="${booking}"/></h3>
                            </div>
                            <!--номер телефона-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <c:choose>
                                        <c:when test="${not empty user.address.telephoneNumber}">
                                            <input type="text" id="telephoneUser" name="telephoneUser" minlength="7" maxlength="12"  onkeypress='return event.charCode >= 48 && event.charCode <= 57' value="${user.address.telephoneNumber}">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" id="telephoneUser" name="telephoneUser" minlength="7" maxlength="12"  onkeypress='return event.charCode >= 48 && event.charCode <= 57'>
                                        </c:otherwise>
                                    </c:choose>
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
<c:if test="${not empty operationSuccess}">
    <c:choose>
        <c:when test="${operationSucces == 'success'}">
            <script>swal('<fmt:message key="orderBookResultErrorTitle" bundle="${booking}"/>', '<fmt:message key="editAccountErrorBody" bundle="${booking}"/>', "error");</script>
        </c:when>
        <c:otherwise>
            <script>swal('<fmt:message key="orderBookResultSuccessTitle" bundle="${booking}"/>', '<fmt:message key="editAccountSuccessBody" bundle="${booking}"/>', "error");</script>
        </c:otherwise>
    </c:choose>
    <c:remove var="operationSuccess" scope="session"/>
</c:if>
</body>
</html>

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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <js:script/>
    <script>
        function validation() {
            var name = $('#nameUser').val();
            var surname = $('#surnameUser').val();
            var email = $('#emailUser').val();
            var password = $('#passwordUser').val();
            var repeatPassword = $('#repeatPasswordUser').val();
            var photo = $('#avatarUser').val();
            var expansion = photo.substring(photo.lastIndexOf('.')+1);
            //Регулярные выражения для проверки
            var nameSurnameRegex = new RegExp("[A-ZА-Я][a-zа-я]+-?[A-ZА-Я]?[a-zа-я]+?");
            var emailRegex = new RegExp("[\\w\\.]{2,40}@[a-z]{2,10}\\.[a-z]{2,10}");
            var passwordRegex = new RegExp("[\\w]{5,40}");
            if(!nameSurnameRegex.test(name)){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianNameError" bundle="${booking}"/>', "error"); return false;
            } else if (!nameSurnameRegex.test(surname)){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianSurnameError" bundle="${booking}"/>', "error"); return false;
            } else if (!emailRegex.test(email)){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianEmailError" bundle="${booking}"/>', "error"); return false;
            } else if (!passwordRegex.test(password)){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianPasswordError" bundle="${booking}"/>', "error"); return false;
            } else if (password !== repeatPassword){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianRepeatPasswordError" bundle="${booking}"/>', "error"); return false;
            } else if (expansion !== 'jpg'){
                swal('<fmt:message key="registerLibrarianError" bundle="${booking}"/>', '<fmt:message key="registerLibrarianPhotoError" bundle="${booking}"/>', "error"); return false;
            }
        }
    </script>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid" style="background: url('${pageContext.request.contextPath}/images/registrationBackground.png') no-repeat; background-size: 100% 100%;">
    <div class="row" id="firstRow"></div>
    <div class="row" id="secondRow">
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
        <div class="col-xs-12 col-sm-10 col-md-8" id="addLibrarianBlock" style="background-color:#3786FF;">
            <div class="container-fluid">
                <div>
                    <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="bookingLogo">
                    <h1 id="pageTitle"><fmt:message key="registerMember" bundle="${booking}"/></h1>
                </div>
                <div class="row sideColumn">
                    <div class="col-xs-2 col-sm-2 col-md-2 sideColumn">
                        <img src="${pageContext.request.contextPath}/images/register1.svg" class="topImage">
                        <img src="${pageContext.request.contextPath}/images/register3.svg" class="bottomImage">
                    </div>
                    <div class="col-xs-8 col-sm-8 col-md-8 sideColumn">
                        <form action="/controller" method="post" id="registerForm" onsubmit="return validation()" enctype="multipart/form-data">
                            <!--имя библиотекаря-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="nameUser" name="nameUser" required/>
                                    <label><fmt:message key="nameRegister" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--фамилия библиотекаря-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="text" id="surnameUser" name="surnameUser" required/>
                                    <label><fmt:message key="surnameRegister" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--email пользователя-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="email" id="emailUser" name="emailUser" required/>
                                    <label><fmt:message key="emailRegister" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--пароль-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="password" id="passwordUser" name="passwordUser" required/>
                                    <label><fmt:message key="passwordRegister" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <!--повтор пароля-->
                            <div class="form-group">
                                <div class="inputGroup">
                                    <input type="password" id="repeatPasswordUser" name="repeatPasswordUser" required/>
                                    <label><fmt:message key="repeatPasswordRegister" bundle="${booking}"/></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-inline row">
                                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                        <!--загружаемое фото-->
                                        <div class="uploadButton">
                                            <input type="file" accept="image/jpeg" id="avatarUser" name="avatarUser" hidden required/>
                                        </div>
                                    </div>
                                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                        <!--кнопка submit-->
                                        <div class="form-group">
                                            <div class="inputGroup">
                                                <button type="submit" class="submitButton" style="width: 100%;"><fmt:message key="registerButton" bundle="${booking}"/></button>
                                                <input type="hidden" name="command" value="register">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-xs-2 col-sm-2 col-md-2 sideColumn">
                        <img src="${pageContext.request.contextPath}/images/register2.svg" class="topImage">
                        <img src="${pageContext.request.contextPath}/images/register4.svg" class="bottomImage">
                    </div>
                </div>
            </div>
        </div>
        <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
    </div>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
<c:if test="${ not empty registrationResult}">
    <script>swal('<fmt:message key="registrationResultErrorTitle" bundle="${booking}"/>','<fmt:message key="registrationResultErrorBody" bundle="${booking}"/>', "error");</script>
    <c:remove var="registrationResult" scope="session"/>
</c:if>
</body>
</html>

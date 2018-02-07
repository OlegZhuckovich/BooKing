<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<c:set var="successOperation"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css'>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style1.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="../../css/addAuthor.css">
    <link rel="stylesheet" href="../../css/select.css">
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="../../js/validator.js" type="text/javascript"></script>
    <script src="../../js/select.js" type="text/javascript"></script>
    <script>
        $(document).ready(function () {
            $("#biographyAuthor").focusout(function(){ $(this).css("color", "white"); });
        });

        function validation(){
            var name = $('#nameAuthor').val();
            var surname = $('#surnameAuthor').val();
            var photo = $('#photoAuthor').val();
            var expansion = photo.substring(content.lastIndexOf('.')+1);
            var nameSurnameRegex = new RegExp("[A-ZА-Я][a-zа-я]+-?[A-ZА-Я]?[a-zа-я]+?");
            if(nameSurnameRegex.test(name)){
                if(nameSurnameRegex.test(surname)){
                    if(!photo || expansion !== 'jpg'){
                        swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addAuthorPhotoError" bundle="${booking}"/>', "error"); return false;
                    }
                } else {
                    swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addAuthorSurnameError" bundle="${booking}"/>', "error"); return false;
                }
            } else {
                swal('<fmt:message key="addBookError" bundle="${booking}"/>', '<fmt:message key="addAuthorNameError" bundle="${booking}"/>', "error"); return false;
            }
        }
    </script>
    <title>BooKing</title>
</head>
<body id="page">
    <c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
    <div id="content" class="container-fluid">
        <div class="row" style="height: 10%"></div>
        <div class="row" style="height: 70%">
            <div class="hidden-xs col-sm-1 col-md-2" style="height: 100%"></div>
            <div class="col-xs-12 col-sm-10 col-md-8" style="min-height: 95%; background-color: #33c4ba; border-radius: 20px">
                <div class="container-fluid">
                    <div>
                        <img src="../../images/BooKingLogo.svg" style="float: left; width: 35px;"><h1 style="margin-left: 8%;"><fmt:message key="addAuthor" bundle="${booking}"/></h1>
                    </div>
                    <div class="row" style="height: 90%">
                        <div class="col-xs-2 col-sm-2 col-md-2" style="height: 100%">
                            <img src="../../images/author1.svg" style="width: 100%; margin-top: 10%;">
                            <img src="../../images/author3.svg" style="width: 100%; margin-top: 100%;">
                        </div>
                        <div class="col-xs-8 col-sm-8 col-md-8" style="height: 100%">
                            <form action="/controller" method="post" id="addAuthorForm" onsubmit="return validation()" enctype="multipart/form-data">
                                <!--имя автора-->
                                <div class="form-group">
                                    <div class="question">
                                        <input type="text" id="nameAuthor" name="nameAuthor" required/>
                                        <label><fmt:message key="nameAuthor" bundle="${booking}"/></label>
                                    </div>
                                </div>
                                <!--фамилия автора-->
                                <div class="form-group">
                                    <div class="question">
                                        <input type="text" id="surnameAuthor" name="surnameAuthor" required/>
                                        <label><fmt:message key="surnameAuthor" bundle="${booking}"/></label>
                                    </div>
                                </div>
                                <!--биография автора-->
                                <div class="form-group">
                                    <div class="question">
                                        <textarea id="biographyAuthor" name="biographyAuthor" cols="40" rows="5" style="color: white;height: 100px;" onfocus="this.style.color='#33c4ba';" required></textarea>
                                        <label style="position: relative; top: -30px;"><fmt:message key="biographyAuthor" bundle="${booking}"/></label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-inline row">
                                        <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                            <div style="background-color:white; border-radius: 5px">
                                                <input title="<fmt:message key="authorAvatar" bundle="${booking}"/>" type="file" accept="image/jpeg" name="photoAuthor" id="photoAuthor" hidden required/>
                                            </div>
                                        </div>
                                        <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                            <div class="question">
                                                <button type="submit" style="margin: 0; width:100%"><fmt:message key="addAuthor" bundle="${booking}"/></button>
                                                <input type="hidden" name="command" value="add_author">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-xs-2 col-sm-2 col-md-2" style="height: 100%">
                            <img src="../../images/author2.svg" style="width: 100%; margin-top: 10%;">
                            <img src="../../images/author4.svg" style="width: 100%; margin-top: 100%;">
                        </div>
                    </div>
                </div>
            </div>
            <div class="hidden-xs col-sm-1 col-md-2" style="height: 100%"></div>
        </div>
    </div>
    <c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>


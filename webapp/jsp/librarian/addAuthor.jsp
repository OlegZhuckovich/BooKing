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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script>
        $(document).ready(function () {
            $("#biographyAuthor").focusout(function(){ $(this).css("color", "white"); });
        });
        function validation(){
            var name = $('#nameAuthor').val();
            var surname = $('#surnameAuthor').val();
            var photo = $('#photoAuthor').val();
            var expansion = photo.substring(photo.lastIndexOf('.')+1);
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
    <style>
        form .inputGroup input:focus{
            color:#33c4ba;
        }
    </style>
    <title>BooKing</title>
</head>
<body id="page">
    <c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
    <div id="content" class="container-fluid" style="background: url('${pageContext.request.contextPath}/images/addAuthorBackground.png'); background-size: 100% 100%;">
        <div class="row" id="firstRow"></div>
        <div class="row" id="secondRow">
            <div class="hidden-xs col-sm-1 col-md-2 sideColumn"></div>
            <div class="col-xs-12 col-sm-10 col-md-8" id="addAuthorBlock" style="min-height: 85%">
                <div class="container-fluid">
                    <div>
                        <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="bookingLogo"><h1 id="pageTitle"><fmt:message key="addAuthor" bundle="${booking}"/></h1>
                    </div>
                    <div class="row thirdEditRow">
                        <div class="col-xs-2 col-sm-2 col-md-2 sideColumn">
                            <img src="${pageContext.request.contextPath}/images/author1.svg" class="topImage">
                            <img src="${pageContext.request.contextPath}/images/author3.svg" class="bottomImage">
                        </div>
                        <div class="col-xs-8 col-sm-8 col-md-8 sideColumn">
                            <form action="/controller" method="post" id="addAuthorForm" onsubmit="return validation()" enctype="multipart/form-data">
                                <!--имя автора-->
                                <div class="form-group">
                                    <div class="inputGroup">
                                        <input type="text" id="authorName" name="authorName" required/>
                                        <label><fmt:message key="nameAuthor" bundle="${booking}"/></label>
                                    </div>
                                </div>
                                <!--фамилия автора-->
                                <div class="form-group">
                                    <div class="inputGroup">
                                        <input type="text" id="authorSurname" name="authorSurname" required/>
                                        <label><fmt:message key="surnameAuthor" bundle="${booking}"/></label>
                                    </div>
                                </div>
                                <!--биография автора-->
                                <div class="form-group">
                                    <div class="inputGroup">
                                        <textarea id="authorBiography" name="authorBiography" cols="40" rows="5" style="color: white;height: 100px;" onfocus="this.style.color='#33c4ba';" required></textarea>
                                        <label style="position: relative; top: -30px;"><fmt:message key="biographyAuthor" bundle="${booking}"/></label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="form-inline row">
                                        <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                            <div class="uploadButton">
                                                <input title="<fmt:message key="authorAvatar" bundle="${booking}"/>" type="file" accept="image/jpeg" name="authorPhoto" id="authorPhoto" hidden required/>
                                            </div>
                                        </div>
                                        <div class="form-group col-xs-12 col-sm-6 col-md-6">
                                            <div class="inputGroup">
                                                <button type="submit" class="editAccountSubmitButton"><fmt:message key="addAuthor" bundle="${booking}"/></button>
                                                <input type="hidden" name="command" value="add_author">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-xs-2 col-sm-2 col-md-2 sideColumn">
                            <img src="${pageContext.request.contextPath}/images/author2.svg" class="topImage">
                            <img src="${pageContext.request.contextPath}/images/author4.svg" class="bottomImage">
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


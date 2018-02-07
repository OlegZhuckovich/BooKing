<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/BooKingLogo.png" type="image/x-icon">
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css'>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/button.css">
    <link rel="stylesheet" href="../../css/select.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/material-design-lite/1.1.0/material.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.material.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/googleButton.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="../../js/validator.js" type="text/javascript"></script>
    <script src="../../js/select.js" type="text/javascript"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/dataTables.material.min.js" type="text/javascript"></script>
    <script>
        $(document).ready(function () {
            $('#example').DataTable( {"language": {
                "zeroRecords": '<fmt:message key="tableZeroRecords" bundle="${booking}"/>',
                "info": '<fmt:message key="tableInfo" bundle="${booking}"/>',
                "infoEmpty": '<fmt:message key="tableInfoEmpty" bundle="${booking}"/>',
                "infoFiltered": '<fmt:message key="tableInfoFiltered" bundle="${booking}"/>',
                "search":'<fmt:message key="tableSearch" bundle="${booking}"/>'
            },
                "dom": '<"toolbar">frtip',
                "scrollX": true,
                "lengthMenu": [[5], [5]],
                "pagingType": "numbers",
                columnDefs: [
                    {
                        targets: [ 0, 1, 2 ],
                        className: 'mdl-data-table__cell--non-numeric'
                    }
                ]
            });
            $("#textField").focusout(function(){ $(this).css("color", "white"); });
            $("#bookTitle").focus(function () {
                $("#submitButton").css("margin-top", "10px");
            });
            $("div.toolbar").html('');
        } );
    </script>
    <style>
        #example_filter{
            margin-bottom: 1%;
            margin-right: 30px;
        }
        #example_filter label{
            color:white;
        }
        .mdl-button{
            color: white;
        }
        #example_info{
            color:white;
        }
        .btn dropdown-toggle btn-default{
            margin-top:0;
        }
        table.dataTable thead .sorting,
        table.dataTable thead .sorting_asc,
        table.dataTable thead .sorting_desc {
            background : none;
        }
        form .question input:valid {
            margin-top: 10px;
        }
        form .question input:focus {
            outline: none;
            background: white;
            color: #33c4ba;
            margin-top: 10px;
        }
        .mdl-button--raised.mdl-button--colored{
        }
        .mdl-button--raised.mdl-button--colored:hover{
        }
    </style>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>

<div id="content" class="container-fluid" style="min-height: 110%;">
    <div class="row" style="height: 3%"></div>
    <div class="row" style="height: 97%">
        <div class="hidden-xs hidden-sm col-md-1" style="height: 100%"></div>

        <div class="col-xs-12 col-sm-12 col-md-10" style="height: 100%;">
            <!--Для поля поиска-->
            <div class="row" style="min-height: 20%; border-radius: 20px; background-color: #424c93">
                <img src="../../images/BooKingLogo.svg" style="float: left; width: 35px;margin-top: 20px; margin-left: 2%;">
                <h1 style="margin-left: 70px;"><fmt:message key="orderBook" bundle="${booking}"/></h1>
                <form action="/controller" method="get" id="searchBookByCriteriaForm">
                    <div class="col-xs-12 col-sm-5 col-md-5" style="height: 100%">
                        <div class="question">
                            <input type="text" id="searchField" name="searchField" required/>
                            <label><fmt:message key="searchField" bundle="${booking}"/></label>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-4 col-md-4" style="height: 100%">
                        <div style="margin-top: -30px">
                            <select name="criteriaSelect" class=" form-group selectpicker" style="margin-top: 0 !important;" title="<fmt:message key="searchCriteria" bundle="${booking}"/>" required>
                                <option value="title"><fmt:message key="titleCriteria" bundle="${booking}"/></option>
                                <option value="author.surname"><fmt:message key="authorCriteria" bundle="${booking}"/></option>
                                <option value="genre"><fmt:message key="genreCriteria" bundle="${booking}"/></option>
                                <option value="publishing_house"><fmt:message key="publishingHouseCriteria" bundle="${booking}"/></option>
                                <option value="year"><fmt:message key="yearCriteria" bundle="${booking}"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-3 col-md-3" style="height: 100%">
                        <div class="question">
                            <button type="submit" id="searchButton" name="searchButton" style="margin: 0; width: 100%;"><fmt:message key="searchButton" bundle="${booking}"/></button>
                            <input type="hidden" name="command" value="search">
                        </div>
                    </div>
                </form>
            </div>
            <!--Для таблицы-->


            <c:if test="${ not empty bookList}">
            <div class="row" style="min-height: 80%; margin-top: 20px;">
                <table id="example" class="mdl-data-table" style="border-top-left-radius: 20px; border-top-right-radius: 20px" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th><fmt:message key="bookTitle" bundle="${booking}"/></th>
                        <th><fmt:message key="bookAuthor" bundle="${booking}"/></th>
                        <th><fmt:message key="bookGenre" bundle="${booking}"/></th>
                        <th><fmt:message key="bookPublishingHouse" bundle="${booking}"/></th>
                        <th><fmt:message key="bookYear" bundle="${booking}"/></th>
                        <th style="width: 5%"><fmt:message key="bookPages" bundle="${booking}"/></th>
                        <th></th>
                        <c:if test="${not empty user.address.city}"><th></th></c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="book" items="${bookList}">
                        <tr>
                            <td><c:out value="${book.title}"/></td>
                            <td>
                                <c:forEach var="author" items="${book.authors}">
                                    <c:out value="${author.name} ${author.surname}"/><br/>
                                </c:forEach>
                            </td>
                            <td><c:out value="${book.genre}"/></td>
                            <td><c:out value="${book.publishingHouse}"/></td>
                            <td><c:out value="${book.numberInformation.year}"/></td>
                            <td style="width: 5%"><c:out value="${book.numberInformation.pages}"/></td>
                            <td>
                                <form action="/controller" method="post">
                                    <input type="hidden" name="bookID" value="${book.id}">
                                    <input type="hidden" name="action" value="reading_room">
                                    <input type="hidden" name="command" value="order_book">
                                    <input type="submit" class="googleButton" value="<fmt:message key="readingRoomBookOrder" bundle="${booking}"/>">
                                </form>
                            </td>
                            <c:if test="${not empty user.address.city}">
                                <td>
                                    <form action="/controller" method="post">
                                        <input type="hidden" name="bookID" value="${book.id}">
                                        <input type="hidden" name="action" value="subscription">
                                        <input type="hidden" name="command" value="order_book">
                                        <input type="submit" class="googleButton" value="<fmt:message key="subscriptionBookOrder" bundle="${booking}"/>">
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            </c:if>
            </div>
        <div class="hidden-xs hidden-sm col-md-1" style="height: 100%"></div>
    </div>
</div>
    <c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

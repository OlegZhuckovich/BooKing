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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/material-design-lite/1.1.0/material.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.material.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table_new.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="https://cdn.datatables.net/1.10.16/js/dataTables.material.min.js" type="text/javascript"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
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
                        targets: '_all',
                        className: 'mdl-data-table__cell--non-numeric'
                    }
                ]
            });
        });
    </script>
    <style>
        .mdl-button--raised.mdl-button--colored,.mdl-button--raised.mdl-button--colored:hover{
            background-color: #9C27B0;
        }
    </style>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid tableRow content" style="background: url('${pageContext.request.contextPath}/images/subscriptionBookDeliveryBackground.png'); background-size: 100% 100%;">
    <div class="row-fluid firstTableRow"></div>
    <div class="row secondTableRow">
        <div class="hidden-xs hidden-sm col-md-1 sideColumn"></div>
        <div class="col-xs-12 col-sm-12 col-md-10 sideColumn">
            <!--Для поля поиска-->
            <div class="row deleteMemberHeader" style="background-color: #9C27B0;">
                <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="bookingTableLogo">
                <h1 class="googleTableTitle"><fmt:message key="subscriptionBookDelivery" bundle="${booking}"/></h1>
                <div class="col-xs-12 col-sm-12 col-md-12 sideColumn">
                    <span><fmt:message key="subscriptionBookDeliveryBody" bundle="${booking}"/></span>
                </div>
            </div>
            <c:if test="${ not empty subscriptionOrderList}">
                <div class="row googleTableRow">
                    <table id="example" class="mdl-data-table googleTable" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th><fmt:message key="nameUser" bundle="${booking}"/></th>
                            <th><fmt:message key="surnameUser" bundle="${booking}"/></th>
                            <th><fmt:message key="emailUser" bundle="${booking}"/></th>
                            <th><fmt:message key="cityUser" bundle="${booking}"/></th>
                            <th><fmt:message key="streetUser" bundle="${booking}"/></th>
                            <th><fmt:message key="houseUser" bundle="${booking}"/></th>
                            <th><fmt:message key="telephoneUser" bundle="${booking}"/></th>
                            <th><fmt:message key="bookTitle" bundle="${booking}"/></th>
                            <th><fmt:message key="bookGenre" bundle="${booking}"/></th>
                            <th><fmt:message key="bookPublishingHouse" bundle="${booking}"/></th>
                            <th><fmt:message key="bookYear" bundle="${booking}"/></th>
                            <th><fmt:message key="bookPages" bundle="${booking}"/></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="subscriptionOrder" items="${subscriptionOrderList}">
                            <tr>
                                <td><c:out value="${subscriptionOrder.user.name}"/></td>
                                <td><c:out value="${subscriptionOrder.user.surname}"/></td>
                                <td><c:out value="${subscriptionOrder.user.email}"/></td>
                                <td><c:out value="${subscriptionOrder.user.address.city}"/></td>
                                <td><c:out value="${subscriptionOrder.user.address.street}"/></td>
                                <td><c:out value="${subscriptionOrder.user.address.house}"/></td>
                                <td><c:out value="${subscriptionOrder.user.address.telephoneNumber}"/></td>
                                <td><c:out value="${subscriptionOrder.book.title}"/></td>
                                <td><c:out value="${subscriptionOrder.book.genre}"/></td>
                                <td><c:out value="${subscriptionOrder.book.publishingHouse}"/></td>
                                <td><c:out value="${subscriptionOrder.book.numberInformation.year}"/></td>
                                <td><c:out value="${subscriptionOrder.book.numberInformation.pages}"/></td>
                                <td>
                                    <form action="/controller" method="post" id="issueSubscriptionForm">
                                        <input type="hidden" name="memberID" value="${subscriptionOrder.user.id}">
                                        <input type="hidden" name="bookID" value="${subscriptionOrder.book.id}">
                                        <input type="hidden" name="command" value="subscription_issue">
                                        <input type="submit" class="googleButton" style="background-color: #9C27B0;" value="<fmt:message key="issueBook" bundle="${booking}"/>">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </div>
        <div class="hidden-xs hidden-sm col-md-1 sideColumn"></div>
    </div>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

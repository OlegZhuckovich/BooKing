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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/material-design-lite/1.1.0/material.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/dataTables.material.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/BooKingStyle.css">
    <js:script/>
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
                        targets: '_all',
                        className: 'mdl-data-table__cell--non-numeric'
                    }
                ]
            });
        });
    </script>
    <style>
        .mdl-button--raised.mdl-button--colored,.mdl-button--raised.mdl-button--colored:hover{
            background-color: #D50000;
        }
    </style>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content" class="container-fluid tableRow content" style="background: url('${pageContext.request.contextPath}/images/deleteBook.png'); background-size: 100% 100%;">
    <div class="row-fluid firstTableRow"></div>
    <div class="row secondTableRow">
        <div class="hidden-xs hidden-sm col-md-1 sideColumn"></div>
        <div class="col-xs-12 col-sm-12 col-md-10 sideColumn">
            <div class="row deleteMemberHeader" style="background-color: #D50000;">
                <img src="${pageContext.request.contextPath}/images/BooKingLogo.svg" id="bookingTableLogo">
                <h1 class="googleTableTitle"><fmt:message key="deleteBook" bundle="${booking}"/></h1>
                <div class="col-xs-12 col-sm-12 col-md-12 sideColumn">
                    <span><fmt:message key="deleteBookInfo" bundle="${booking}"/></span>
                </div>
            </div>
            <c:if test="${ not empty bookList}">
                <div class="row googleTableRow">
                    <table id="example" class="mdl-data-table googleTable" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th><fmt:message key="bookTitle" bundle="${booking}"/></th>
                                <th><fmt:message key="bookAuthor" bundle="${booking}"/></th>
                                <th><fmt:message key="bookGenre" bundle="${booking}"/></th>
                                <th><fmt:message key="bookPublishingHouse" bundle="${booking}"/></th>
                                <th><fmt:message key="bookYear" bundle="${booking}"/></th>
                                <th><fmt:message key="bookPages" bundle="${booking}"/></th>
                                <th><fmt:message key="bookQuantity" bundle="${booking}"/></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="book" items="${bookList}" varStatus="count">
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
                                <td><c:out value="${book.numberInformation.pages}"/></td>
                                <td><c:out value="${book.numberInformation.quantity}"/></td>
                                <td>
                                    <button type="button" class="googleButton" style="background-color: #D50000;" data-toggle="modal" data-target="#divIDNo${count.index}"><fmt:message key="deleteBook" bundle="${booking}"/></button>
                                    <div id="divIDNo${count.index}" class="modal fade" role="dialog">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title"><fmt:message key="confirmHeader" bundle="${booking}"/></h4>
                                                </div>
                                                <div class="modal-body">
                                                    <fmt:message key="deleteBookConfirmBody" bundle="${booking}"/>
                                                </div>
                                                <div class="modal-footer">
                                                    <form action="/controller" method="post" id="deleteBookForm">
                                                        <input type="hidden" name="bookID" value="${book.id}">
                                                        <input type="hidden" name="command" value="delete_book">
                                                        <input type="submit" name="deleteBookYesConfirmButton" class="googleButton" style="background-color: #D50000;" value="<fmt:message key="YesConfirmButton" bundle="${booking}"/>">
                                                    </form>
                                                    <button type="button"  class="googleButton" style="background-color: #D50000;" data-dismiss="modal"><fmt:message key="NoConfirmButton" bundle="${booking}"/></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
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

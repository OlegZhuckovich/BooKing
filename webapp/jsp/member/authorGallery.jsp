<%@ page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale }" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="booking" var="booking"/>
<!DOCTYPE html>
<html>
<head>
    <c:import url="../common/web.jsp"/>
    <title>BooKing</title>
</head>
<body id="page">
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content">
    <c:forEach var="author" items="${authorList}" varStatus="id">
        <div style="width: 500px; height: 150px; border-radius: 10px; background-color: white">
            <img src="${pageContext.request.contextPath}/image/author${author.id}" height="120px" width="100px"/>
                <td><p><c:out value="${author.name}"/></p></td>
                <td><p><c:out value="${author.biography}"/></p></td>
                <td><p><c:out value="${author.photo}"/></p></td>
        </div>
    </c:forEach>
    ${operationMessage}
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>


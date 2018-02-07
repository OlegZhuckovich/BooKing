<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:import url="../common/web.jsp"/>
    <title>BooKingErrorPage</title>
</head>
<body>
<c:import charEncoding="UTF-8"  url="${pageContext.request.contextPath}/jsp/common/header.jsp"/>
<div id="content">
    <label>500 Error Page</label><br/>
    <p>Request from ${pageContext.errorData.requestURI} is failed </p><br/>
    <p>Servlet name: ${pageContext.errorData.servletName}</p><br/>
    <p>Status code: ${pageContext.errorData.statusCode}</p><br/>
    <p>Exception: ${pageContext.exception}</p><br/>
    <p>Message from exception: ${pageContext.exception.message}</p>
</div>
<c:import charEncoding="UTF-8" url="/jsp/common/footer.jspf"/>
</body>
</html>

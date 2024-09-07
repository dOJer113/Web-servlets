<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Requests</title>

</head>
<body>

<h2>Requests</h2><br/>

<c:forEach var="request" items="${requestScope.requests}">
    <ul>
        <li>Request type: <c:out value="${request.requestType}"/></li><br>

        <li>Driver id: <c:out value="${request.driverId}"/></li><br>

        <c:choose>
            <c:when test="${fn:contains(request.getClass().name, 'HandlingRequest')}">
                <li>Product name: <c:out value="${request.productEnum}"/></li><br>

                <li>Product count: <c:out value="${request.productCount}"/></li><br>
            </c:when>
        </c:choose>

        <form method="post" action="<c:url value='/confirm'/>">
            <input type="submit" name="confirm" value="Confirm"/>
            <input type="number" hidden name="requestId" value="${request.requestId}" />
            <input type="text" hidden name="requestType" value="${request.requestType}" />
        </form>
        <form method="post" action="<c:url value='/reject'/>">
            <input type="submit" name="reject" value="Reject"/>
            <input type="number" hidden name="requestId" value="${request.requestId}" />
        </form>


    </ul>
    <hr/>

</c:forEach>


</body>
</html>
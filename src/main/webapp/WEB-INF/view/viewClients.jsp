<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Clients</title>
</head>
<body>
<h2>Clients</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Password</th>
        <th>Role</th>
        <th>Activity</th>
    </tr>
    <jsp:useBean id="clients" scope="request" type="java.util.List"/>
    <c:forEach var="client" items="${clients}">
        <tr>
            <td>${client.id}</td>
            <td>${client.login}</td>
            <td>${client.password}</td>
            <td>${client.role}</td>
            <td>${client.activity}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

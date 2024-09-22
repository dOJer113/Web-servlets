<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
<h1>Error!</h1>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Logout">
    <input type="hidden" name="command" value="LOGOUT">
</form>

</body>
</html>

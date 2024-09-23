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
    <input type="hidden" name="command" value="LOGOUT">
    <input type="submit" value="Logout">
</form>

</body>
</html>

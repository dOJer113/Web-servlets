<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Success</title>
</head>
<body>
<h1>Success!</h1>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="LOGIN">
    <input type="submit" value="Back">
</form>

</body>
</html>

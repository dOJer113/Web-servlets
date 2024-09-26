<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>UnBlock User</title>
</head>
<body>
<h2>UnBlocking client</h2>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <label for="login">LOGIN:</label>
    <input type="text" id="login" name="login" required><br>
    <input type="submit" value="UnBlock">
    <input type="hidden" name="command" value="UNBLOCK_CLIENT">
</form>
</body>
</html>

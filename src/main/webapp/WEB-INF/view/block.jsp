<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Block User</title>
</head>
<body>
<h2>Blocking client</h2>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <label for="login">LOGIN:</label>
    <input type="text" id="login" name="login" required><br>
    <input type="hidden" name="command" value="BLOCK_CLIENT">
    <input type="submit" value="Block">
</form>
</body>
</html>

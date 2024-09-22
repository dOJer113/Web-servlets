<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search User</title>
</head>
<body>
<h2>Searching user</h2>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <label for="login">LOGIN:</label>
    <input type="text" id="login" name="login" required><br>
    <input type="submit" value="Search">
    <input type="hidden" name="command" value="SHOW_FUNDED_USER">
</form>
</body>
</html>

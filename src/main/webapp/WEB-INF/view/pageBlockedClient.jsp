<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>You was blocked!</title>
</head>
<body>
<h2>Blocked!</h2>

You was blocked! Write to moderator.

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="LOGOUT">
    <input type="submit" value="Logout">
</form>

</body>
</html>

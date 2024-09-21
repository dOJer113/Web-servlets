<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>You was blocked!</title>
</head>
<body>
<h2>Blocked!</h2>

<form action="blocked" method="get">
    You was blocked! Write to moderator.
</form>

<form action="${pageContext.request.contextPath}/logout" method="get">
    <input type="submit" value="Logout">
</form>

</body>
</html>

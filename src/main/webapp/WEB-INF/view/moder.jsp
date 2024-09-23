<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Moderator menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Block Client">
    <input type="hidden" name="command" value="SHOW_BLOCK_CLIENT">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Unblock Client">
    <input type="hidden" name="command" value="SHOW_UNBLOCK_CLIENT">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Add products to store">
    <input type="hidden" name="command" value="SHOW_ADD_PRODUCT">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="LOGOUT">
    <input type="submit" value="Logout">
</form>

</body>
</html>

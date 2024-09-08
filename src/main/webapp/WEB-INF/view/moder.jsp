<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Moderator menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/blockClient" method="get">
    <input type="submit" value="Block Client">
</form>
<br>

<form action="${pageContext.request.contextPath}/unBlockClient" method="get">
    <input type="submit" value="Unblock Client">
</form>
<br>

<form action="${pageContext.request.contextPath}/addProduct" method="get">
    <input type="submit" value="Add products to store">
</form>
<br>

<form action="${pageContext.request.contextPath}/logout" method="get">
    <input type="submit" value="Logout">
</form>

</body>
</html>

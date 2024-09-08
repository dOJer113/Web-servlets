<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/addUser" method="get">
    <input type="submit" value="Add User">
</form>
<br>

<form action="${pageContext.request.contextPath}/deleteUser" method="get">
    <input type="submit" value="Delete User">
</form>
<br>

<form action="${pageContext.request.contextPath}/update" method="get">
    <input type="submit" value="Update User">
</form>
<br>

<form action="${pageContext.request.contextPath}/clients" method="get">
    <input type="submit" value="View Users">
</form>
<br>

<form action="${pageContext.request.contextPath}/logout" method="get">
    <input type="submit" value="Logout">
</form>


</body>
</html>

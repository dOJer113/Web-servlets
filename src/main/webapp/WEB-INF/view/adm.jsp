<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin menu</title>
</head>
<body>
<h2>Choose an action</h2>

<!-- Button 1 -->
<form action="${pageContext.request.contextPath}/add" method="get">
    <input type="submit" value="Add User">
</form>
<br>

<form action="${pageContext.request.contextPath}/delete" method="get">
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

<form action="${pageContext.request.contextPath}/delogin" method="get">
    <input type="submit" value="Logout">
</form>


</body>
</html>

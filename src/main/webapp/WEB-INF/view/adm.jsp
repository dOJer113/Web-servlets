<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Add User">
    <input type="hidden" name="command" value="SHOW_ADD_USER">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="SHOW_DELETE_USER">
    <input type="submit" value="Delete User">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Update User">
    <input type="hidden" name="command" value="SHOW_SEARCH_USER">

</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="submit" value="View Users">
    <input type="hidden" name="command" value="SHOW_CLIENTS">
</form>

<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="LOGOUT">
    <input type="submit" value="Logout">
</form>


</body>
</html>

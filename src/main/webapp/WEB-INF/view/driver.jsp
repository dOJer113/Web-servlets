<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Driver menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Make entry request">
    <input type="hidden" name="command" value="SHOW_ENTRY_REQUEST">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Make handling request">
    <input type="hidden" name="command" value="SHOW_HANDLING_REQUEST">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="submit" value="Logout">
    <input type="hidden" name="command" value="LOGOUT">
</form>


</body>
</html>

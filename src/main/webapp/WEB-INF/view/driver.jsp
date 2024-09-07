<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Driver menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/entryRequest" method="get">
    <input type="submit" value="Make entry request">
</form>
<br>

<form action="${pageContext.request.contextPath}/handlingRequest" method="get">
    <input type="submit" value="Make handling request">
</form>
<br>

<form action="${pageContext.request.contextPath}/logout" method="get">
    <input type="submit" value="Logout">
</form>


</body>
</html>

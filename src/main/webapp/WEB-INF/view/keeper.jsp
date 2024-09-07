<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Storekeeper menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/handleRequests" method="get">
  <input type="submit" value="Handling requests">
</form>
<br>

<form action="${pageContext.request.contextPath}/logout" method="get">
  <input type="submit" value="Logout">
</form>


</body>
</html>

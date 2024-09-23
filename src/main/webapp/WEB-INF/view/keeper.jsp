<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Storekeeper menu</title>
</head>
<body>
<h2>Choose an action</h2>

<form action="${pageContext.request.contextPath}/controller" method="get">
  <input type="submit" value="Handling requests">
    <input type="hidden" name="command" value="SHOW_HANDLE_REQUESTS_KEEPER">
</form>
<br>

<form action="${pageContext.request.contextPath}/controller" method="get">
  <input type="hidden" name="command" value="LOGOUT">
  <input type="submit" value="Logout">
</form>


</body>
</html>

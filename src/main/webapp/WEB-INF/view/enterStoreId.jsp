<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enter store id</title>
</head>
<body>
<h2>Enter store id for entry request</h2>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <label for="storeId">Store id:</label>
    <input type="number" id="storeId" name="storeId" required><br>
    <input type="hidden" name="command" value="ENTRY_REQUEST">
    <input type="submit" value="Make request">
</form>
<% if (request.getAttribute("errorMessage") != null) { %>
<div style="color: red;">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>
</body>
</html>

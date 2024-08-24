<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User</title>
</head>
<body>
<h2>Changing user</h2>
<form action="/update" method="post">
    <label for="id">Client id:</label>
    <input type="text" id="id" name="id" value="${client.id}" required><br><br>
    <label for="login">LOGIN:</label>
    <input type="text" id="login" name="login" value="${client.login}" required><br><br>
    <label for="password">PASSWORD:</label>
    <input type="text" id="password" name="password" value="${client.password}" required><br><br>
    <label for="role">ROLE:</label>
    <input type="text" id="role" name="role" value="${client.role}" required><br><br>
    <input type="submit" value="Change">
</form>
</body>
</html>

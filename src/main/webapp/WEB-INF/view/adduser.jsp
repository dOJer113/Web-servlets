<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<h2>Adding new user</h2>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <label for="id">ID:</label>
    <input type="number" id="id" name="id" required><br>
    <label for="login">LOGIN:</label>
    <input type="text" id="login" name="login" required><br>
    <label for="password">PASSWORD:</label>
    <input type="password" id="password" name="password" required><br>
    <label for="role">ROLE:</label>
    <select id="role" name="role">
        <option value="admin">ADMIN</option>
        <option value="moderator">MODER</option>
        <option value="driver">DRIVER</option>
        <option value="storekeeper">KEEPER</option>
    </select><br>
    <input type="hidden" name="command" value="ADD_USER">
    <input type="submit" value="ADD ">
</form>
</body>
</html>

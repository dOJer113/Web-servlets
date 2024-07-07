<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Delete User</title>
</head>
<body>
<h2>Deleting user</h2>
<form action="deleteUser" method="post">
    <label for="login">LOGIN:</label>
    <input type="text" id="login" name="login" required><br>
    <input type="submit" value="Delete ">
</form>
</body>
</html>

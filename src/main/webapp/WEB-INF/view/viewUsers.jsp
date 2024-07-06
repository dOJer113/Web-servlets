<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h2>Users</h2>
<table>
    <tr>
        <th>Login</th>
        <th>Role</th>
        <th>Buttons</th>
    </tr>

    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.login}</td>
            <td>${user.role}</td>
            <td>
                <form action="deleteUser" method="post" style="display:inline;">
                    <input type="hidden" name="login" value="${user.login}">
                    <input type="submit" value="Delete"
                           onclick="return confirm('Could u delete user?');">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

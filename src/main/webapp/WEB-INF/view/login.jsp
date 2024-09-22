<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>

</head>
<body>

<div class="form">

    <h1>Login</h1><br>
    <form method="post" action="">
        <input type="text" required placeholder="login" name="login"><br>
        <input type="password" required placeholder="password" name="password"><br><br>
        <%--<input type="hidden" name="command" value="LOGIN">--%>
        <input class="button" type="submit" value="Login">
    </form>
</div>
</body>
</html>

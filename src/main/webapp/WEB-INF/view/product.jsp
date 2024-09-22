<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add product to storage</title>
</head>
<body>
<h2>Adding product to store</h2>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <label for="storeid">Storeid:</label>
    <input type="number" id="storeid" name="storeId" required><br>
    <label for="productName">Product Name:</label>
    <select id="productName" name="productName">
        <option value="HORSES">HORSES</option>
        <option value="BALLS">BALLS</option>
        <option value="CARS">CARS</option>
    </select><br>
    <label for="count">Count:</label>
    <input type="number" id="count" name="count" required><br>
    <input type="submit" value="ADD ">
    <input type="hidden" name="command" value="ADD_PRODUCT">
</form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Load products to car</title>
</head>
<body>
<h2>Loading products to car</h2>
<form action="handlingRequest" method="post">
    <label for="productName">Product Name:</label>
    <select id="productName" name="productName">
        <option value="HORSES">HORSES</option>
        <option value="BALLS">BALLS</option>
        <option value="CARS">CARS</option>
    </select><br><br>
    <label for="requestType">Request type:</label>
    <select id="requestType" name="requestType">
        <option value="LOADING_TO_CAR">Load request</option>
        <option value="LOADING_TO_STORE">Unload request</option>
    </select><br><br>
    <label for="count">Count:</label>
    <input type="number" id="count" name="count" required><br><br>
    <input type="submit" value="Make request">
</form>
</body>
</html>

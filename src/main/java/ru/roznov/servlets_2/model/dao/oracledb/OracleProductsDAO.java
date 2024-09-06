package ru.roznov.servlets_2.model.dao.oracledb;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.ProductDAO;
import ru.roznov.servlets_2.model.ExceptionHandler;
import ru.roznov.servlets_2.objects.products.Product;
import ru.roznov.servlets_2.objects.products.ProductEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleProductsDAO implements ProductDAO {
    private Connection connection;

    public OracleProductsDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "select * from PRODUCTS";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    ProductEnum name = ProductEnum.valueOf(resultSet.getString(2));
                    list.add(new Product(id, name));
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting products", e);
        }
        return list;
    }
}

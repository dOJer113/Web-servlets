package ru.roznov.servlets_2.model.dao.oracledb;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.ExceptionHandler;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.cars.Car;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OracleCarsDAO implements CarDAO {
    private Connection connection;

    public OracleCarsDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addNewDriverWithCar(int driverId) {
        String sql = "insert into CAR_DRIVER (CARID, DRIVERID) VALUES (0,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, driverId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error inserting new driver", e);
        }

    }

    @Override
    public void deleteDriverFromCarDriver(int driverId) {
        String sql = "delete from CAR_DRIVER where DRIVERID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, driverId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error deleting driver from car_driver ", e);
        }
    }

    @Override
    public Map<Integer, Integer> getCarsIdsByDriversIds() {
        Map<Integer, Integer> map = new HashMap<>();
        String sql = "select * from CAR_DRIVER";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int carId = resultSet.getInt(1);
                    int driverId = resultSet.getInt(2);
                    map.put(carId, driverId);
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting cars by drivers ", e);
        }
        return map;
    }

    @Override
    public Map<ProductEnum, Integer> getProductsByCarId(int carId) {
        Map<ProductEnum, Integer> map = new HashMap<>();
        String sql = "SELECT NAME,COUNTPRODUCT FROM CAR_PRODUCTS INNER JOIN PRODUCTS P on P.ID = CAR_PRODUCTS.PRODUCTID\n" +
                "WHERE CARID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductEnum productName = ProductEnum.valueOf(resultSet.getString(1));
                    int count = resultSet.getInt(2);
                    map.put(productName, count);
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting products by car id ", e);
        }
        return map;
    }

    @Override
    public Map<Integer, Car> getCars() {
        Map<Integer, Car> map = new HashMap<>();
        String sql = "select id from CARS";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    Car car = new Car();
                    if (id != 0) {
                        car.setProductsFromMap((this.getProductsByCarId(id)));
                        map.put(id, car);
                    }
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting cars with products ", e);
        }
        return map;
    }

    @Override
    public void addNewProduct(int carId, int productId, int countProduct) {
        String sql = "insert into CAR_PRODUCTS(CARID, PRODUCTID, COUNTPRODUCT) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carId);
            statement.setInt(2, productId);
            statement.setInt(3, countProduct);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error inserting new product at car", e);
        }
    }

    @Override
    public void updateProductCount(int carId, int productId, int countProduct) {
        String sql = "update CAR_PRODUCTS\n" +
                "set COUNTPRODUCT = COUNTPRODUCT+ ?\n" +
                "where CARID = ? and PRODUCTID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, countProduct);
            statement.setInt(2, carId);
            statement.setInt(3, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error updating count product at car", e);
        }
    }


}

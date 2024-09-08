package ru.roznov.servlets_2.model.dao.oracledb;

import ru.roznov.servlets_2.model.ExceptionHandler;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.store.Store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OracleStorageDAO implements StorageDAO {
    private Connection connection;

    public OracleStorageDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void deleteStorekeeperFromStoreStorekeeper(int keeperId) {
        String sql = "delete from STORE_STOREKEEPER where KEEPERID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, keeperId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error deleting keeper from store_storekeeper ", e);
        }
    }

    @Override
    public Map<Integer, Integer> getStoragesIdByKeepersId() {
        Map<Integer, Integer> map = new HashMap<>();
        String sql = "select *\n" +
                "from STORE_STOREKEEPER\n";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int storageId = resultSet.getInt(1);
                    int keeperId = resultSet.getInt(2);
                    map.put(keeperId, storageId);
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting storage by keeper ", e);
        }
        return map;
    }

    @Override
    public Map<ProductEnum, Integer> getProductsByStoreId(int storeId) {
        Map<ProductEnum, Integer> map = new HashMap<>();
        String sql =
                "select NAME,COUNTPRODUCT\n" +
                        "from STORE_PRODUCTS\n" +
                        "         inner join PRODUCTS P on P.ID = STORE_PRODUCTS.PRODUCTID\n" +
                        "where STOREID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, storeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductEnum productName = ProductEnum.valueOf(resultSet.getString(1));
                    int count = resultSet.getInt(2);
                    map.put(productName, count);
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting products by store id ", e);
        }
        return map;
    }


    @Override
    public Map<Integer, Store> getStorages() {
        Map<Integer, Store> map = new HashMap<>();
        String sql = "select STOREID from STORAGES";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int storeId = resultSet.getInt(1);
                    Store store = new Store();
                    if (storeId != 0) {
                        store.setProductsFromMap(this.getProductsByStoreId(storeId));
                        store.setCarsAtStorageFromSetIds(this.getCarIdsAtStore(storeId));
                        map.put(storeId, store);
                    }
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting storages with products ", e);
        }
        return map;
    }

    @Override
    public void addNewProduct(int storeId, int productId, int countProduct) {
        String sql = "insert into STORE_PRODUCTS (storeid, productid, countproduct)  values ?, ?, ? ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, storeId);
            statement.setInt(2, productId);
            statement.setInt(3, countProduct);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error inserting new product at store", e);
        }
    }

    @Override
    public void updateProductCount(int storeId, int productId, int countProduct) {
        String sql = "UPDATE STORE_PRODUCTS\n" +
                "SET COUNTPRODUCT = COUNTPRODUCT + ?\n" +
                "where PRODUCTID = ? and STOREID = ? ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, countProduct);
            statement.setInt(2, productId);
            statement.setInt(3, storeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error updating count product at store", e);
        }
    }

    @Override
    public void addNewKeeperWithStore(int keeperId) {
        String sql = "insert into STORE_STOREKEEPER(storeid, keeperid)  values (0,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, keeperId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error inserting new keeper", e);
        }
    }

    @Override
    public Set<Integer> getCarIdsAtStore(int storeID) {
        Set<Integer> cars = new HashSet<>();
        String sql = "SELECT * FROM CARS_AT_STORE WHERE STOREID = 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int carId = resultSet.getInt(1);
                    cars.add(carId);
                }
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error getting cars at store", e);
        }
        return cars;
    }

    @Override
    public void addCarToStore(int storeId, int carId) {
        String sql = "insert into CARS_AT_STORE(storeid, carid) values (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, storeId);
            statement.setInt(2, carId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error loading car to store", e);
        }
    }

    @Override
    public void removeCarFromStore(int storeId, int carId) {
        String sql = "delete from CARS_AT_STORE where CARID = ? and STOREID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, storeId);
            statement.setInt(2, carId);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error loading car from store", e);
        }

    }
}

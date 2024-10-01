package ru.roznov.servlets_2.model.dao.oracledb;


import ru.roznov.servlets_2.model.dao.DAOinterfeices.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

public class OracleDBDAOFactory extends DAOFactory {
    public static final String URL = "jdbc:oracle:thin:@//localhost:1521/ORCL";
    public static final String USER = "C##ALEX";
    public static final String PASSWORD = "12345";
    private static volatile OracleDBDAOFactory instance;
    private Connection connection;

    private OracleDBDAOFactory() {
    }

    public static OracleDBDAOFactory getInstance()
            throws ClassNotFoundException, SQLException {
        OracleDBDAOFactory factory = instance;
        if (instance == null) {
            synchronized (OracleDBDAOFactory.class) {
                instance = factory = new OracleDBDAOFactory();
                factory.connected();
            }
        }
        return factory;
    }

    private void connected() throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        props.setProperty("user", OracleDBDAOFactory.USER);
        props.setProperty("password", OracleDBDAOFactory.PASSWORD);
        connection = DriverManager.getConnection(OracleDBDAOFactory.URL, props);
    }

    @Override
    public UsersDAO getUsersDAO() {
        return new OracleUsersDAO(connection);
    }

    @Override
    public ClientActivityDAO getClientActivityDAO() {
        return new OracleClientActivityDAO(connection);
    }

    @Override
    public StorageDAO getStorageDAO() {
        return new OracleStorageDAO(connection);
    }

    @Override
    public CarDAO getCarDAO() {
        return new OracleCarsDAO(connection);
    }

    @Override
    public ProductDAO getProductDAO() {
        return new OracleProductsDAO(connection);
    }

}

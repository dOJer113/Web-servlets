package ru.roznov.servlets_2.model.dao.oracledb;


import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.dao.DynamicResult;


import java.sql.*;


public class OracleUsersDAO implements UsersDAO {
    private Connection connection;

    public OracleUsersDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public DynamicResult getUsers() throws SQLException {
        DynamicResult dynamicResult = new DynamicResult();
        String sql = "select * from users";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                dynamicResult = OracleUsersDAO.containDynamicResult(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dynamicResult;
    }

    @Override
    public void insertNewUser(String login, int password, String role) throws SQLException {
        String sql = "INSERT INTO users (id,login, password, role) VALUES ( ?, ?, ?,?)";
        int countUsers = this.getUsers().getCountRows() + 1;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, countUsers);
            statement.setString(2, login);
            statement.setInt(3, password);
            statement.setString(4, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static DynamicResult containDynamicResult(ResultSet resultSet) throws SQLException {
        DynamicResult dynamicResult = new DynamicResult();
        int countColumns = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= countColumns; i++) {
                dynamicResult.setField(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
            }
        }
        return dynamicResult;
    }
}

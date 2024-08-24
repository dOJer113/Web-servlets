package ru.roznov.servlets_2.model.dao.oracledb;


import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.dao.DynamicResult;


import java.sql.*;


public class OracleUsersDAO implements UsersDAO {
    private Connection connection;

    public OracleUsersDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public DynamicResult getUsers() {
        DynamicResult dynamicResult = new DynamicResult();
        String sql = "select * from users";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                dynamicResult = DynamicResult.containDynamicResult(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting user " + e.getMessage());
        }
        return dynamicResult;
    }

    @Override
    public void insertNewUser(int id, String login, int password, String role) throws SQLException {
        if (UsersSearcher.isExistsUser(login)) {
            throw new SQLException("This login already used");
        }
        String sql = "INSERT INTO users (id,login, password, role) VALUES ( ?, ?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, login);
            statement.setInt(3, password);
            statement.setString(4, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting user " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(String login) {
        String sql = "delete from users where login = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting user" + e.getMessage());
        }
    }

    @Override
    public void updateUser(int id, String login, int password, String role) {
        String sql = "UPDATE users SET login = ?,password = ?,role = ?WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setInt(2, password);
            statement.setString(3, role);
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating user" + e.getMessage());
        }
    }


}

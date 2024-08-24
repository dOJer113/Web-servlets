package ru.roznov.servlets_2.model.dao.oracledb;

import ru.roznov.servlets_2.model.client.ClientActivitySearcher;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ClientActivityDAO;
import ru.roznov.servlets_2.model.dao.DynamicResult;
import ru.roznov.servlets_2.model.exceptions.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleClientActivityDAO implements ClientActivityDAO {
    private Connection connection;

    public OracleClientActivityDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public DynamicResult getClients() {
        DynamicResult dynamicResult = new DynamicResult();
        String sql = "select * from users_activity";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                dynamicResult = DynamicResult.containDynamicResult(resultSet);
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting client activity ", e);
        }
        return dynamicResult;
    }


    @Override
    public void insertNewClient(int id) throws SQLException {
        if (ClientActivitySearcher.isExistsClient(id)) {
            throw new SQLException("This id already used");
        }
        String sql = "INSERT INTO users_activity (id,activity) VALUES ( ?, 0)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error inserting users activity ", e);
        }
    }

    @Override
    public void deleteClient(int id) {
        String sql = "delete from users_activity where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error deleting users activity ", e);
        }
    }

    @Override
    public void updateClient(int id, int activity) {
        String sql = "UPDATE users_activity SET activity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, activity);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error updating users activity ", e);
        }
    }

    @Override
    public void makeAllUnActive() {
        String sql = "UPDATE users_activity SET activity = 0";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error making all client un active ", e);
        }
    }

    @Override
    public DynamicResult getUsersWithActivity() {
        DynamicResult dynamicResult = new DynamicResult();

        String sql = "select u.id, u.login, u.password, u.role, users_activity.activity from users u inner join users_activity on u.id = users_activity.id where users_activity.activity = 1 ORDER BY u.id asc";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                dynamicResult = DynamicResult.containDynamicResult(resultSet);
            }
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error selecting users with activity ", e);
        }
        return dynamicResult;
    }

}

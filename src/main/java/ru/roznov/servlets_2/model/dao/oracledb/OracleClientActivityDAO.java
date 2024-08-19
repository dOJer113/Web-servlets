package ru.roznov.servlets_2.model.dao.oracledb;

import ru.roznov.servlets_2.model.client.ClientActivitySearcher;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ClientActivityDAO;
import ru.roznov.servlets_2.model.dao.DynamicResult;

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
    public DynamicResult getClients() throws SQLException {
        DynamicResult dynamicResult = new DynamicResult();
        String sql = "select * from users_activity";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                dynamicResult = DynamicResult.containDynamicResult(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting client activity" + e.getMessage());
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
            this.commitChanges();
        } catch (SQLException e) {
            System.err.println("Error inserting users activity" + e.getMessage());
        }
    }

    @Override
    public void deleteClient(int id) throws SQLException {
        String sql = "delete from users_activity where id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            this.commitChanges();
        } catch (SQLException e) {
            System.err.println("Error deleting users activity" + e.getMessage());
        }
    }

    @Override
    public void updateClient(int id, int activity) throws SQLException {
        String sql = "UPDATE users_activity SET activity = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, activity);
            statement.setInt(2, id);
            statement.executeUpdate();
            this.commitChanges();
        } catch (SQLException e) {
            System.err.println("Error updating users activity" + e.getMessage());
        }
    }

    @Override
    public DynamicResult getUsersWithActivity() throws SQLException {
        DynamicResult dynamicResult = new DynamicResult();

        String sql = "select u.id, u.login, u.password, u.role, users_activity.activity from users u inner join users_activity on u.id = users_activity.id where users_activity.activity = 1 ORDER BY u.id asc";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                dynamicResult = DynamicResult.containDynamicResult(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error selecting users with activity" + e.getMessage());
        }
        return dynamicResult;
    }

    private void commitChanges() {
       /* try {
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error commit changes " + e.getMessage());
        }*/
    }
}

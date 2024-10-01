package ru.roznov.servlets_2.model.dao.oracledb;


import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OracleUsersDAO implements UsersDAO {
    private Connection connection;

    public OracleUsersDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Client getUserByLogin(String loginToSearch) {
        String sql = "select * from users";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    String login = resultSet.getString(2);
                    if (login.equals(loginToSearch)) {
                        int id = resultSet.getInt(1);
                        int password = resultSet.getInt(3);
                        RoleEnum role = RoleEnum.valueOf(resultSet.getString(4).toUpperCase());
                        return new Client(id, login, password, role);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by login " + e.getMessage());
        }
        return new Client();
    }

    @Override
    public Client getUserById(int id) {
        String sql = "select * from users";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt(1);
                    if (id == userId) {
                        String login = resultSet.getString(2);
                        int password = resultSet.getInt(3);
                        RoleEnum role = RoleEnum.valueOf(resultSet.getString(4).toUpperCase());
                        return new Client(id, login, password, role);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by id " + e.getMessage());
        }
        return new Client();
    }

    @Override
    public void insertNewUser(int id, String login, int password, String role) throws SQLException {
        if (UsersSearcher.isExistsUser(login,this)) {
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

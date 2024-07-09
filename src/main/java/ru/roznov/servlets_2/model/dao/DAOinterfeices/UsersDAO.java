package ru.roznov.servlets_2.model.dao.DAOinterfeices;


import ru.roznov.servlets_2.model.dao.DynamicResult;

import java.sql.SQLException;

public interface UsersDAO {
    DynamicResult getUsers() throws SQLException;

    void insertNewUser(int id, String login, int password, String role) throws SQLException;

    void deleteUser(String login) throws SQLException;

    void updateUser(int id, String login, int password, String role) throws SQLException;
}

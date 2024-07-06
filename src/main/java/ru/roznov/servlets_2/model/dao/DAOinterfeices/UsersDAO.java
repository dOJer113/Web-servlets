package ru.roznov.servlets_2.model.dao.DAOinterfeices;


import ru.roznov.servlets_2.model.dao.DynamicResult;

import java.sql.SQLException;

public interface UsersDAO {
    DynamicResult getUsers() throws SQLException;

    void insertNewUser(String login, int password, String role) throws SQLException;
}

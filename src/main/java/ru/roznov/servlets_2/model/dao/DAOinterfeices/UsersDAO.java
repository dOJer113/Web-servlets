package ru.roznov.servlets_2.model.dao.DAOinterfeices;


import ru.roznov.servlets_2.objects.clients.Client;

import java.sql.SQLException;

public interface UsersDAO {
    Client getUserByLogin(String loginToSearch);

    Client getUserById(int id);

    void insertNewUser(int id, String login, int password, String role) throws SQLException;

    void deleteUser(String login);

    void updateUser(int id, String login, int password, String role);
}

package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.user.UserManager;
import ru.roznov.servlets_2.model.user.UsersSearcher;

import java.sql.SQLException;

public class UserAndActivityManager {
    public static void deleteUserAndActivity(String login) throws SQLException {
        ClientActivityManager.deleteUser(UsersSearcher.getClientByLogin(login).getId());
        UserManager.deleteUser(login);
    }

    public static void addUserAndActivity(int id, String login, int password, String role) throws SQLException {
        UserManager.addUser(id, login, password, role);
        ClientActivityManager.addUser(id);
    }
}

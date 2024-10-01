package ru.roznov.servlets_2.model.user;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;

import java.sql.SQLException;

public class UserManager {
    public static void addUser(CommandParameters commandParameters) throws SQLException {
        UsersDAO usersDAO =  commandParameters.getParameter("UsersDAO", UsersDAO.class);
        int id = commandParameters.getParameter("id", Integer.class);
        String login = commandParameters.getParameter("login", String.class);
        int password = commandParameters.getParameter("password", Integer.class);
        String role = commandParameters.getParameter("role", String.class);
        usersDAO.insertNewUser(id, login, password, role);
    }

    public static void updateUser(CommandParameters commandParameters) {
        UsersDAO usersDAO = (UsersDAO) commandParameters.getParameter("UsersDAO", UsersDAO.class);
        int id = commandParameters.getParameter("id", Integer.class);
        String login = commandParameters.getParameter("login", String.class);
        int password = commandParameters.getParameter("password", Integer.class);
        String role = commandParameters.getParameter("role", String.class).toLowerCase();
        usersDAO.updateUser(id, login, password, role);
    }

    public static void deleteUser(CommandParameters commandParameters) {
        UsersDAO usersDAO = (UsersDAO) commandParameters.getParameter("UsersDAO", UsersDAO.class);
        String login = commandParameters.getParameter("login", String.class);
        usersDAO.deleteUser(login);
    }
}

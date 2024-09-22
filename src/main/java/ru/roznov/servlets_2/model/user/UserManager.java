package ru.roznov.servlets_2.model.user;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;

import java.sql.SQLException;

public class UserManager {
    public static void addUser(CommandParameters commandParameters) throws SQLException {
        int id = commandParameters.getParameter("id", Integer.class);
        String login = commandParameters.getParameter("login", String.class);
        int password = commandParameters.getParameter("password", Integer.class);
        String role = commandParameters.getParameter("role", String.class);
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().insertNewUser(id, login, password, role);
    }

    public static void updateUser(CommandParameters commandParameters) throws SQLException {
        int id = commandParameters.getParameter("id", Integer.class);
        String login = commandParameters.getParameter("login", String.class);
        int password = commandParameters.getParameter("password", Integer.class);
        String role = commandParameters.getParameter("role", String.class).toLowerCase();
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().updateUser(id, login, password, role);
    }

    public static void deleteUser(CommandParameters commandParameters) throws SQLException {
        String login = commandParameters.getParameter("login", String.class);
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().deleteUser(login);
    }

    public static void getValuesFromOracleDB(CommandParameters commandParameters) {
        UsersSearcher.result = DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUsers();
    }
}

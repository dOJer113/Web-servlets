package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;

import java.sql.SQLException;

public class UserManager {
    public static void addUser(int id,String login, int password, String role) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().insertNewUser(id,login, password, role);
    }

    public static void deleteUser(String login) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().deleteUser(login);
    }
}

package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.objects.RoleEnum;

import java.sql.SQLException;

public class UserManager {
    public static void addUser(int id, String login, int password, String role) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().insertNewUser(id, login, password, role);
    }

    public static void updateUser(int id, String login, int password, RoleEnum role) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().updateUser(id, login, password, role.toString().toLowerCase());
    }


    public static void deleteUser(String login) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().deleteUser(login);
    }
}

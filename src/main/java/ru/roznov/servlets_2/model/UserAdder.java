package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;

import java.sql.SQLException;

public class UserAdder {
    public static void addUser(String login, int password, String role) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().insertNewUser(login, password, role);
    }
}

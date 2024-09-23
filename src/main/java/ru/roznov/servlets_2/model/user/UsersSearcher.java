package ru.roznov.servlets_2.model.user;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;


public class UsersSearcher {
    public static Client getClientByLogin(String login) {
        return DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUserByLogin(login);
    }

    public static int getIdByLogin(String login) {
        return DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUserByLogin(login).getId();
    }

    public static boolean isExistsUser(String login) {
        return DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUserByLogin(login).getId() != 0;
    }

    public static boolean isExistsUser(int id) {
        return DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUserById(id).getId() != 0;
    }

    public static RoleEnum getRoleByLogin(String login) {
        return DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUserByLogin(login).getRole();
    }


}

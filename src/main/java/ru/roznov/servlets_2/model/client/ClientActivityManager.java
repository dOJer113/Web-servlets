package ru.roznov.servlets_2.model.client;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.objects.RoleEnum;

import java.sql.SQLException;

public class ClientActivityManager {

    public static boolean isClientActive(int clientId) {
        return ClientActivitySearcher.isActiveClient(clientId);
    }

    public static void makeClientUnActive(int id) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().updateClient(id, 0);

    }

    public static void makeClientActive(int id) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().updateClient(id, 1);
    }

    public static void addUser(int id) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().insertNewClient(id);
    }


    public static void deleteUser(int id) throws SQLException {
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().deleteClient(id);
    }


}

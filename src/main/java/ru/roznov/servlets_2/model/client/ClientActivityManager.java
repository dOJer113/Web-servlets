package ru.roznov.servlets_2.model.client;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.ExceptionHandler;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;

import java.sql.SQLException;

public class ClientActivityManager {

    public static void makeClientUnActive(CommandParameters parameters) throws SQLException {
        int id = parameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().updateClient(id, 0);
    }

    public static void makeClientActive(CommandParameters parameters) throws SQLException {
        int id = parameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().updateClient(id, 1);
    }

    public static void addClient(CommandParameters parameters) throws SQLException {
        int id = parameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().insertNewClient(id);
    }


    public static void deleteClient(CommandParameters parameters) throws SQLException {
        int id = parameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().deleteClient(id);
    }

    public static void getClientsFromOracleDB(CommandParameters parameters) {
        try {
            ClientActivitySearcher.result = DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().getClients();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error getting clients activity", e);
        }
    }

    public static void getValuesByTwoTables(CommandParameters parameters) {
        ClientActivitySearcher.result = DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().getUsersWithActivity();
    }

    public static void makeAllClientsUnActive(CommandParameters parameters) {
        DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().makeAllUnActive();
    }

}

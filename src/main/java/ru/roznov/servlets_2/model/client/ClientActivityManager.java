package ru.roznov.servlets_2.model.client;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ClientActivityDAO;
import ru.roznov.servlets_2.objects.clients.UserWithActivity;

import java.sql.SQLException;
import java.util.List;

public class ClientActivityManager {

    public static List<UserWithActivity> getUserWithActivity(ClientActivityDAO activityDAO) {
        return activityDAO.getUsersWithActivity();
    }

    public static void makeClientUnActive(CommandParameters parameters) {
        ClientActivityDAO activityDAO = parameters.getParameter("ActivityDAO", ClientActivityDAO.class);
        int id = parameters.getParameter("id", Integer.class);
        activityDAO.updateClient(id, 0);
    }

    public static void makeClientActive(CommandParameters parameters) {
        ClientActivityDAO activityDAO = parameters.getParameter("ActivityDAO", ClientActivityDAO.class);
        int id = parameters.getParameter("id", Integer.class);
        activityDAO.updateClient(id, 1);
    }

    public static void addClient(CommandParameters parameters) throws SQLException {
        ClientActivityDAO activityDAO = parameters.getParameter("ActivityDAO", ClientActivityDAO.class);
        int id = parameters.getParameter("id", Integer.class);
        activityDAO.insertNewClient(id);
    }


    public static void deleteClient(CommandParameters parameters) {
        ClientActivityDAO activityDAO = parameters.getParameter("ActivityDAO", ClientActivityDAO.class);
        int id = parameters.getParameter("id", Integer.class);
        activityDAO.deleteClient(id);
    }

    public static void makeAllClientsUnActive(CommandParameters parameters) {
        ClientActivityDAO activityDAO = parameters.getParameter("ActivityDAO", ClientActivityDAO.class);
        activityDAO.makeAllUnActive();
    }

}

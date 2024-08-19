package ru.roznov.servlets_2.model.client;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.model.dao.DynamicResult;
import ru.roznov.servlets_2.model.exceptions.ExceptionHandler;
import ru.roznov.servlets_2.objects.ClientActivity;
import ru.roznov.servlets_2.objects.RoleEnum;
import ru.roznov.servlets_2.objects.UserWithActivity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientActivitySearcher {
    private static DynamicResult result;

    public static void getValuesFromOracleDB() {
        try {
            ClientActivitySearcher.result = DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().getClients();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error getting clients activity", e);
        }
    }

    public static void getValuesByTwoTables() {
        try {
            ClientActivitySearcher.result = DAOFactory.getInstance(DBType.ORACLE).getClientActivityDAO().getUsersWithActivity();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error getting clients with activity", e);
        }
    }

    public static boolean isActiveClient(int id) {
        if (ClientActivitySearcher.isExistsClient(id)) {
            List<ClientActivity> clientActivities = ClientActivitySearcher.getClientActivityListFromDynamicResults();
            Iterator<ClientActivity> clientActivityIterator = clientActivities.iterator();
            while (clientActivityIterator.hasNext()) {
                ClientActivity clientActivity = clientActivityIterator.next();
                if (clientActivity.getId() == id && clientActivity.getActivity() == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isExistsClient(int id) {
        ClientActivitySearcher.getValuesFromOracleDB();
        if (result.containsField("ID")) {
            List<Integer> ids = result.getField("ID");
            return ids.contains(id);
        }
        return false;
    }

    public static List<UserWithActivity> getUserWithActivity() {
        ClientActivitySearcher.getValuesByTwoTables();
        List<UserWithActivity> userWithActivities = new ArrayList<>();
        Iterator<BigDecimal> ids = result.getField("ID").iterator();
        Iterator<String> logins = result.getField("LOGIN").iterator();
        Iterator<BigDecimal> passwords = result.getField("PASSWORD").iterator();
        Iterator<String> roles = result.getField("ROLE").iterator();
        Iterator<BigDecimal> activities = result.getField("ACTIVITY").iterator();
        while (ids.hasNext()) {
            int id = ids.next().intValue();
            int password = passwords.next().intValue();
            int activity = activities.next().intValue();
            userWithActivities.add(new UserWithActivity(id, logins.next(), password, RoleEnum.valueOf(roles.next().toUpperCase()), activity));
        }
        return userWithActivities;
    }

    private static List<ClientActivity> getClientActivityListFromDynamicResults() {
        List<ClientActivity> clientActivities = new ArrayList<>();
        if (result.containsField("ID") && result.containsField("ACTIVITY")) {
            Iterator<Integer> ids = result.getField("ID").iterator();
            Iterator<Integer> activities = result.getField("ACTIVITY").iterator();
            while (ids.hasNext()) {
                ClientActivity clientActivity = new ClientActivity(ids.next(), activities.next());
                clientActivities.add(clientActivity);
            }
        }
        return clientActivities;
    }
}

package ru.roznov.servlets_2.model.client;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DynamicResult;
import ru.roznov.servlets_2.objects.clients.ClientActivity;
import ru.roznov.servlets_2.objects.clients.RoleEnum;
import ru.roznov.servlets_2.objects.clients.UserWithActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientActivitySearcher {
    public static DynamicResult result;

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
        CommandController.executeCommand(CommandName.GET_CLIENTS_FROM_ORACLE_DB, new CommandParameters());
        if (result.containsField("ID")) {
            List<Integer> ids = result.getField("ID");
            return ids.contains(id);
        }
        return false;
    }

    public static List<UserWithActivity> getUserWithActivity() {

        CommandController.executeCommand(CommandName.GET_VALUES_BY_TWO_TABLES, new CommandParameters());
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

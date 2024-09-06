package ru.roznov.servlets_2.model.user;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;

import ru.roznov.servlets_2.model.dao.DynamicResult;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class UsersSearcher {
    public static DynamicResult result;


    public static Client getClientByLogin(String login) {
        if (result.containsField("LOGIN")) {
            Iterator<BigDecimal> ids = result.getField("ID").iterator();
            Iterator<String> logins = result.getField("LOGIN").iterator();
            Iterator<BigDecimal> passwords = result.getField("PASSWORD").iterator();
            Iterator<String> roles = result.getField("ROLE").iterator();
            while (ids.hasNext()) {
                Client client = new Client(ids.next().intValue(), logins.next(), passwords.next().intValue(), RoleEnum.valueOf(roles.next().toUpperCase()));
                if (client.getLogin().equals(login)) {
                    return client;
                }
            }

        }
        return new Client();
    }

    public static int getIdByLogin(String login) {
        if (result.containsField("LOGIN")) {
            Iterator<BigDecimal> ids = result.getField("ID").iterator();
            Iterator<String> logins = result.getField("LOGIN").iterator();
            while (logins.hasNext()) {
                int id = ids.next().intValue();
                if (logins.next().equals(login)) {
                    return id;
                }
            }

        }
        return 0;
    }

    public static boolean isExistsUser(String login) {
        CommandController.executeCommand(CommandName.GET_USERS_FROM_ORACLE_DB, new CommandParameters());
        if (result.containsField("LOGIN")) {
            List logins = result.getField("LOGIN");
            return logins.contains(login);
        }
        return false;
    }

    public static boolean isExistsUser(int id) {
        CommandController.executeCommand(CommandName.GET_USERS_FROM_ORACLE_DB, new CommandParameters());
        if (result.containsField("ID")) {
            List ids = result.getField("ID");
            return ids.contains(id);
        }
        return false;
    }

    public static RoleEnum getRoleByLogin(String login) {
        List<String> logins = result.getField("LOGIN");
        List<String> roles = result.getField("ROLE");
        Iterator<String> iteratorLogins = logins.iterator();
        Iterator<String> iteratorRoles = roles.iterator();
        while (iteratorLogins.hasNext()) {
            RoleEnum role = RoleEnum.valueOf(iteratorRoles.next().toUpperCase());
            if (iteratorLogins.next().equals(login)) {
                return role;
            }
        }
        return RoleEnum.UNKNOWN;
    }

    public static List<Client> getClientsListByDynamicResult() {
        //CommandController.executeCommand(CommandName.GET_VALUES_FROM_ORACLE_DB, new CommandParameters());
        List<Client> clients = new ArrayList<>();
        Iterator<BigDecimal> ids = result.getField("ID").iterator();
        Iterator<String> logins = result.getField("LOGIN").iterator();
        Iterator<BigDecimal> passwords = result.getField("PASSWORD").iterator();
        Iterator<String> roles = result.getField("ROLE").iterator();
        while (ids.hasNext()) {
            int id = ids.next().intValue();
            int password = passwords.next().intValue();
            clients.add(new Client(id, logins.next(), password, RoleEnum.valueOf(roles.next().toUpperCase())));
        }
        return clients;
    }

}

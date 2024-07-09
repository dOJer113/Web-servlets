package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.model.dao.DynamicResult;
import ru.roznov.servlets_2.objects.Client;
import ru.roznov.servlets_2.objects.RoleEnum;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class UsersSearcher {
    private static DynamicResult result;

    public static void getValuesFromOracleDB() {
        try {
            result = DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUsers();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error checking existing user", e);
        }
    }

    public static Client getClientByLogin(String login) {
        UsersSearcher.getValuesFromOracleDB();
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

    public static boolean isExistsUser(String login) {
        UsersSearcher.getValuesFromOracleDB();
        if (result.containsField("LOGIN")) {
            List logins = result.getField("LOGIN");
            return logins.contains(login);
        }
        return false;
    }

    public static boolean isExistsUser(int id) {
        UsersSearcher.getValuesFromOracleDB();
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
        UsersSearcher.getValuesFromOracleDB();
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

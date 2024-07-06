package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.model.dao.DynamicResult;
import ru.roznov.servlets_2.model.dao.ExceptionHandler;
import ru.roznov.servlets_2.objects.RoleEnum;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class UsersSearcher {
    private static DynamicResult result;

    public static void getValuesFromOracleDB() {
        try {
            result = DAOFactory.getInstance(DBType.ORACLE).getUsersDAO().getUsers();
        } catch (SQLException e) {
            ExceptionHandler.handleException("Error checking existing user", e);
        }
    }

    public static boolean isExistsUser(String login) {
        if (result.containsField("LOGIN")) {
            List logins = result.getField("LOGIN");
            return logins.contains(login);
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
}

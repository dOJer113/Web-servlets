package ru.roznov.servlets_2.model.user;

import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.objects.clients.Client;
import ru.roznov.servlets_2.objects.clients.RoleEnum;


public class UsersSearcher {
    public static Client getClientByLogin(String login, UsersDAO usersDAO) {
        return usersDAO.getUserByLogin(login);
    }

    public static int getIdByLogin(String login, UsersDAO usersDAO) {
        return usersDAO.getUserByLogin(login).getId();
    }

    public static boolean isExistsUser(String login, UsersDAO usersDAO) {
        return usersDAO.getUserByLogin(login).getId() != 0;
    }

    public static boolean isExistsUser(int id, UsersDAO usersDAO) {
        return usersDAO.getUserById(id).getId() != 0;
    }

    public static RoleEnum getRoleByLogin(String login, UsersDAO usersDAO) {
        return usersDAO.getUserByLogin(login).getRole();
    }


}

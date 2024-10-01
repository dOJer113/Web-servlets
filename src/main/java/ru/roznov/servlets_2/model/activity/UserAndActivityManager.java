package ru.roznov.servlets_2.model.activity;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.UsersDAO;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

public class UserAndActivityManager {
    public static void deleteUserAndActivity(CommandParameters commandParameters) {
        String login = commandParameters.getParameter("login", String.class);
        UsersDAO usersDAO = commandParameters.getParameter("UsersDAO", UsersDAO.class);
        int id = UsersSearcher.getIdByLogin(login, usersDAO);
        if (id != 0) {
            commandParameters.addParameter("id", id);
            CommandController.executeCommand(CommandName.DELETE_CLIENT, commandParameters);
            CommandController.executeCommand(CommandName.DELETE_KEEPER_OR_DRIVER, commandParameters);
            CommandController.executeCommand(CommandName.DELETE_USER, commandParameters);
        }
    }

    public static void addUserAndActivity(CommandParameters commandParameters) {
        CommandController.executeCommand(CommandName.ADD_USER, commandParameters);
        CommandController.executeCommand(CommandName.ADD_CLIENT, commandParameters);
        CommandController.executeCommand(CommandName.ADD_KEEPER_OR_DRIVER, commandParameters);
    }

    public static void deleteKeeperOrDriver(CommandParameters commandParameters) {
        UsersDAO usersDAO = commandParameters.getParameter("UsersDAO", UsersDAO.class);
        RoleEnum roleEnum = UsersSearcher.getRoleByLogin(commandParameters.getParameter("login", String.class), usersDAO);
        if (roleEnum.equals(RoleEnum.DRIVER)) {
            CommandController.executeCommand(CommandName.DELETE_DRIVER, commandParameters);
        } else if (roleEnum.equals(RoleEnum.STOREKEEPER)) {
            CommandController.executeCommand(CommandName.DELETE_KEEPER, commandParameters);
        }
    }

    public static void addKeeperOrDriver(CommandParameters commandParameters) {
        UsersDAO usersDAO = commandParameters.getParameter("UsersDAO", UsersDAO.class);
        RoleEnum roleEnum = UsersSearcher.getRoleByLogin(commandParameters.getParameter("login", String.class), usersDAO);
        if (roleEnum.equals(RoleEnum.DRIVER)) {
            CommandController.executeCommand(CommandName.ADD_NEW_DRIVER, commandParameters);
        } else if (roleEnum.equals(RoleEnum.STOREKEEPER)) {
            CommandController.executeCommand(CommandName.ADD_NEW_KEEPER, commandParameters);
        }
    }
}

package ru.roznov.servlets_2.model.activity;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.user.UserManager;
import ru.roznov.servlets_2.model.user.UsersSearcher;
import ru.roznov.servlets_2.objects.clients.RoleEnum;

import java.sql.SQLException;

public class UserAndActivityManager {
    public static void deleteUserAndActivity(CommandParameters commandParameters) {
        String login = commandParameters.getParameter("login", String.class);
        commandParameters.addParameter("id", UsersSearcher.getIdByLogin(login));
        CommandController.executeCommand(CommandName.DELETE_CLIENT, commandParameters);
        CommandController.executeCommand(CommandName.DELETE_KEEPER_OR_DRIVER, commandParameters);
        CommandController.executeCommand(CommandName.DELETE_USER, commandParameters);
    }

    public static void addUserAndActivity(CommandParameters commandParameters) {
        CommandController.executeCommand(CommandName.ADD_USER, commandParameters);
        CommandController.executeCommand(CommandName.ADD_CLIENT, commandParameters);
        CommandController.executeCommand(CommandName.ADD_KEEPER_OR_DRIVER, commandParameters);
    }

    public static void deleteKeeperOrDriver(CommandParameters commandParameters) {
        RoleEnum roleEnum = UsersSearcher.getRoleByLogin(commandParameters.getParameter("login", String.class));
        if (roleEnum.equals(RoleEnum.DRIVER)) {
            CommandController.executeCommand(CommandName.DELETE_DRIVER, commandParameters);
        } else if (roleEnum.equals(RoleEnum.STOREKEEPER)) {
            CommandController.executeCommand(CommandName.DELETE_KEEPER, commandParameters);
        }
    }

    public static void addKeeperOrDriver(CommandParameters commandParameters) {
        RoleEnum roleEnum = UsersSearcher.getRoleByLogin(commandParameters.getParameter("login", String.class));
        if (roleEnum.equals(RoleEnum.DRIVER)) {
            CommandController.executeCommand(CommandName.ADD_NEW_DRIVER, commandParameters);
        } else if (roleEnum.equals(RoleEnum.STOREKEEPER)) {
            CommandController.executeCommand(CommandName.ADD_NEW_KEEPER, commandParameters);
        }
    }
}
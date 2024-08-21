package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.user.UserManager;
import ru.roznov.servlets_2.model.user.UsersSearcher;

import java.sql.SQLException;

public class UserAndActivityManager {
    public static void deleteUserAndActivity(CommandParameters commandParameters) throws SQLException {
        String login = commandParameters.getParameter("login", String.class);
        CommandParameters parameters = new CommandParameters();
        parameters.addParameter("id", UsersSearcher.getIdByLogin(login));
        CommandController.executeCommand(CommandName.DELETE_CLIENT,parameters);
        CommandController.executeCommand(CommandName.DELETE_USER,commandParameters);
    }

    public static void addUserAndActivity(CommandParameters commandParameters) throws SQLException {
        int id = commandParameters.getParameter("id", Integer.class);
        CommandController.executeCommand(CommandName.ADD_USER,commandParameters);
        CommandParameters parameters = new CommandParameters();
        parameters.addParameter("id", id);
        CommandController.executeCommand(CommandName.ADD_CLIENT,parameters);
    }
}

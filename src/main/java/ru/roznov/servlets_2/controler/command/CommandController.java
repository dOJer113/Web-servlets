package ru.roznov.servlets_2.controler.command;

import ru.roznov.servlets_2.controler.AuthFilter;
import ru.roznov.servlets_2.controler.FrontController;
import ru.roznov.servlets_2.model.UserAndActivityManager;
import ru.roznov.servlets_2.model.block.ClientBlocker;
import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.exceptions.ExceptionHandler;
import ru.roznov.servlets_2.model.user.UserManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandController {
    private static final Map<CommandName, Command> commandMap = new HashMap<>();
    private static final List<CommandName> commandLog = new ArrayList<>();

    private CommandController() {
    }

    public static void init() {
        commandMap.put(CommandName.DELETE_USER_AND_ACTIVITY, UserAndActivityManager::deleteUserAndActivity);
        commandMap.put(CommandName.ADD_USER_AND_ACTIVITY, UserAndActivityManager::addUserAndActivity);
        commandMap.put(CommandName.MAKE_CLIENT_UNACTIVE, ClientActivityManager::makeClientUnActive);
        commandMap.put(CommandName.MAKE_CLIENT_ACTIVE, ClientActivityManager::makeClientActive);
        commandMap.put(CommandName.ADD_CLIENT, ClientActivityManager::addClient);
        commandMap.put(CommandName.DELETE_CLIENT, ClientActivityManager::deleteClient);
        commandMap.put(CommandName.GET_CLIENTS_FROM_ORACLE_DB, ClientActivityManager::getClientsFromOracleDB);
        commandMap.put(CommandName.GET_VALUES_BY_TWO_TABLES, ClientActivityManager::getValuesByTwoTables);
        commandMap.put(CommandName.ADD_USER, UserManager::addUser);
        commandMap.put(CommandName.UPDATE_USER, UserManager::updateUser);
        commandMap.put(CommandName.DELETE_USER, UserManager::deleteUser);
        commandMap.put(CommandName.GET_VALUES_FROM_ORACLE_DB, UserManager::getValuesFromOracleDB);
        commandMap.put(CommandName.BLOCK_CLIENT, ClientBlocker::blockClient);
        commandMap.put(CommandName.UNBLOCK_CLIENT, ClientBlocker::unblockClient);
        commandMap.put(CommandName.MOVE_TO_MENU, FrontController::moveToMenu);
        commandMap.put(CommandName.AUTHORIZE_CLIENT, FrontController::authorizeClient);
    }

    public static void executeCommand(CommandName name, CommandParameters commandParameters) {
        Command command = commandMap.get(name);
        if (command != null) {
            try {
                command.execute(commandParameters);
            } catch (ServletException | IOException | SQLException e) {
                ExceptionHandler.handleException("Error executing command ", e);
            }
        } else {
            System.err.println("Command not found: " + name);
        }
        commandLog.add(name);
    }

    public List<CommandName> getCommandLog() {
        return commandLog;
    }
}

package ru.roznov.servlets_2.controler.businesCommand;

import ru.roznov.servlets_2.controler.global.LoginController;
import ru.roznov.servlets_2.model.CarManager;
import ru.roznov.servlets_2.model.ExceptionHandler;
import ru.roznov.servlets_2.model.ProductManager;
import ru.roznov.servlets_2.model.StoreManager;
import ru.roznov.servlets_2.model.activity.UserAndActivityManager;
import ru.roznov.servlets_2.model.block.ClientBlocker;
import ru.roznov.servlets_2.model.client.ClientActivityManager;
import ru.roznov.servlets_2.model.user.UserManager;
import ru.roznov.servlets_2.objects.cars.DriverUtils;
import ru.roznov.servlets_2.objects.requests.RequestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
        commandMap.put(CommandName.GET_USERS_FROM_ORACLE_DB, UserManager::getValuesFromOracleDB);
        commandMap.put(CommandName.BLOCK_CLIENT, ClientBlocker::blockClient);
        commandMap.put(CommandName.UNBLOCK_CLIENT, ClientBlocker::unblockClient);
        commandMap.put(CommandName.MOVE_TO_MENU, LoginController::moveToMenu);
        commandMap.put(CommandName.AUTHORIZE_CLIENT, LoginController::authorizeClient);
        commandMap.put(CommandName.MAKE_ALL_UN_ACTIVE, ClientActivityManager::makeAllClientsUnActive);
        commandMap.put(CommandName.RE_AUTHORIZE_CLIENT, LoginController::reAuthorizeClient);
        commandMap.put(CommandName.IS_CLIENT_BLOCKED, ClientBlocker::isClientBlocked);
        commandMap.put(CommandName.DELETE_KEEPER, StoreManager::deleteKeeper);
        commandMap.put(CommandName.DELETE_DRIVER, CarManager::deleteDriver);
        commandMap.put(CommandName.DELETE_KEEPER_OR_DRIVER, UserAndActivityManager::deleteKeeperOrDriver);
        commandMap.put(CommandName.ADD_NEW_DRIVER, CarManager::addNewDriverWithCar);
        commandMap.put(CommandName.ADD_NEW_KEEPER, StoreManager::addNewKeeperWithStore);
        commandMap.put(CommandName.ADD_KEEPER_OR_DRIVER, UserAndActivityManager::addKeeperOrDriver);
        commandMap.put(CommandName.ADD_CAR_TO_STORE, StoreManager::addCarToStore);
        commandMap.put(CommandName.REMOVE_CAR_FROM_STORE, StoreManager::removeCarFromStore);
        commandMap.put(CommandName.CONFIRM_HANDLING_REQUEST, RequestController::confirmHandlingRequest);
        commandMap.put(CommandName.CONFIRM_LOADING_TO_CAR_REQUEST, RequestController::confirmLoadingToCarRequest);
        commandMap.put(CommandName.CONFIRM_LOADING_TO_STORE_REQUEST, RequestController::confirmLoadingToStoreRequest);
        commandMap.put(CommandName.CONFIRM_ENTRY_REQUEST, RequestController::confirmEntryRequest);
        commandMap.put(CommandName.CHANGE_COUNT_PRODUCT_AT_STORE, StoreManager::changeCountProductsAtStore);
        commandMap.put(CommandName.CHANGE_COUNT_PRODUCT_AT_CAR, CarManager::changeCountProductsAtCar);
        commandMap.put(CommandName.GET_PRODUCTS, ProductManager::getProductsFromDB);
        commandMap.put(CommandName.GET_CARS, CarManager::getCars);
        commandMap.put(CommandName.GET_CARS_DRIVERS, CarManager::getCarsIdByDriversIdFromDB);
        commandMap.put(CommandName.GET_STORAGES, StoreManager::getStorages);
        commandMap.put(CommandName.GET_STORAGES_STOREKEEPERS, StoreManager::getStoragesIdByKeepersIdFromDB);
        commandMap.put(CommandName.UNBLOCK_ALL, ClientBlocker::unBlockAll);
        commandMap.put(CommandName.MAKE_ENTRY_REQUEST, DriverUtils::makeNewEntryRequest);
        commandMap.put(CommandName.MAKE_HANDLING_REQUEST, DriverUtils::makeNewHandlingRequest);
        commandMap.put(CommandName.REJECT_REQUEST, RequestController::rejectRequest);
    }

    public static CommandName getCommandNameFromRequest(HttpServletRequest request) {
        CommandName name = CommandName.valueOf(request.getParameter("command"));
        return name;
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


    public static List<CommandName> getCommandLog() {
        return CommandController.commandLog;
    }
}

package ru.roznov.servlets_2.objects.requests;

import ru.roznov.servlets_2.controler.businesCommand.CommandController;
import ru.roznov.servlets_2.controler.businesCommand.CommandName;
import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.CarManager;
import ru.roznov.servlets_2.model.StoreManager;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ProductDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.products.ProductsBase;
import ru.roznov.servlets_2.objects.store.Store;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class RequestController {
    private static final Set<AbstractRequest> requests = new ConcurrentSkipListSet<>();

    private RequestController() {
    }

    public static void addNewRequest(AbstractRequest request) {
        RequestController.requests.add(request);
    }

    public static List<AbstractRequest> getAllRequestsForKeeper(int keeperId, StorageDAO storageDAO) {
        List<AbstractRequest> requests = new ArrayList<>();
        int storeId = StoreManager.getStoreIdByKeeperId(keeperId, storageDAO);
        Iterator<AbstractRequest> iterator = RequestController.requests.iterator();
        while (iterator.hasNext()) {
            AbstractRequest request = iterator.next();
            if (request.getStoreId() == storeId) {
                requests.add(request);
            }
        }
        return requests;

    }

    public static void confirmHandlingRequest(CommandParameters commandParameters) {
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        ProductDAO productDAO = commandParameters.getParameter("ProductDAO", ProductDAO.class);
        AbstractRequest request = RequestController.getRequestById(commandParameters.getParameter("requestId", Integer.class));
        if (!request.equals(new NullRequest())) {
            HandlingRequest fundedRequest = (HandlingRequest) request;
            commandParameters.addParameter("productId", ProductsBase.getIdByProductName(fundedRequest.getProductEnum(), productDAO));
            commandParameters.addParameter("productCount", fundedRequest.getProductCount());
            commandParameters.addParameter("storeId", StoreManager.getStoreIdByKeeperId(commandParameters.getParameter("keeperId", Integer.class), storageDAO));
            commandParameters.addParameter("carId", CarManager.getCarIdByDriverId(fundedRequest.getDriverId(), carDAO));
            RequestType requestType = fundedRequest.getRequestType();
            if (requestType.equals(RequestType.LOADING_TO_CAR)) {
                RequestController.confirmLoadingToCarRequest(commandParameters);
            } else if (requestType.equals(RequestType.LOADING_TO_STORE)) {
                RequestController.confirmLoadingToStoreRequest(commandParameters);
            }
        }
    }

    public static void confirmLoadingToStoreRequest(CommandParameters commandParameters) {
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        ProductDAO productDAO = commandParameters.getParameter("ProductDAO", ProductDAO.class);
        Car car = CarManager.getCarById(commandParameters.getParameter("carId", Integer.class), carDAO);
        ProductEnum product = ProductsBase.getProductNameById(commandParameters.getParameter("productId", Integer.class), productDAO);
        Store store = StoreManager.getStoreById(commandParameters.getParameter("storeId", Integer.class), storageDAO);
        CommandParameters negativeCommandParameters = new CommandParameters();
        negativeCommandParameters.addParameter("carId", commandParameters.getParameter("carId", Integer.class));
        negativeCommandParameters.addParameter("productId", commandParameters.getParameter("productId", Integer.class));
        negativeCommandParameters.addParameter("StoreDAO",storageDAO);
        negativeCommandParameters.addParameter("ProductDAO",productDAO);
        negativeCommandParameters.addParameter("CarDAO",carDAO);
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        negativeCommandParameters.addParameter("productCount", -productCount);
        if (store.isCarAtStore(car)) {
            if (car.unLoadProduct(product, productCount)) {
                CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_CAR, negativeCommandParameters);
                CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_STORE, commandParameters);
                store.loadProducts(product, productCount);
                CommandController.executeCommand(CommandName.REMOVE_CAR_FROM_STORE, commandParameters);
            } else {
                System.err.println("Error not enough product at car");
            }
        } else {
            System.err.println("Error no car at store");
        }

        RequestController.deleteRequest(commandParameters);
    }

    public static void confirmLoadingToCarRequest(CommandParameters commandParameters) {
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        ProductDAO productDAO = commandParameters.getParameter("ProductDAO", ProductDAO.class);
        CommandParameters negativeCommandParameters = new CommandParameters();
        negativeCommandParameters.addParameter("storeId", commandParameters.getParameter("storeId", Integer.class));
        negativeCommandParameters.addParameter("productId", commandParameters.getParameter("productId", Integer.class));
        negativeCommandParameters.addParameter("StoreDAO",storageDAO);
        negativeCommandParameters.addParameter("ProductDAO",productDAO);
        negativeCommandParameters.addParameter("CarDAO",carDAO);
        Car car = CarManager.getCarById(commandParameters.getParameter("carId", Integer.class), carDAO);
        ProductEnum product = ProductsBase.getProductNameById(commandParameters.getParameter("productId", Integer.class), productDAO);
        Store store = StoreManager.getStoreById(commandParameters.getParameter("storeId", Integer.class), storageDAO);
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        negativeCommandParameters.addParameter("productCount", -productCount);
        if (store.isCarAtStore(car)) {
            if (store.unLoadProduct(product, productCount)) {
                CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_STORE, negativeCommandParameters);
                CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_CAR, commandParameters);
                car.loadProducts(product, productCount);
                CommandController.executeCommand(CommandName.REMOVE_CAR_FROM_STORE, commandParameters);
            } else {
                System.err.println("Error not enough product at store");
            }
        } else {
            System.err.println("Error no car at store");
        }

        RequestController.deleteRequest(commandParameters);
    }

    public static void confirmEntryRequest(CommandParameters commandParameters) {
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        AbstractRequest request = RequestController.getRequestById(commandParameters.getParameter("requestId", Integer.class));
        if (!request.equals(new NullRequest())) {
            EntryRequest fundedRequest = (EntryRequest) request;
            commandParameters.addParameter("storeId", fundedRequest.getStoreId());
            commandParameters.addParameter("carId", CarManager.getCarIdByDriverId(fundedRequest.getDriverId(), carDAO));
            CommandController.executeCommand(CommandName.ADD_CAR_TO_STORE, commandParameters);
            RequestController.deleteRequest(commandParameters);
        }
    }

    public static AbstractRequest getRequestById(int id) {
        return RequestController.requests.stream().filter(request -> request.getRequestId() == id).findFirst().orElse(new NullRequest());
    }

    public static void rejectRequest(CommandParameters commandParameters) {
        RequestController.deleteRequest(commandParameters);
    }


    public static void deleteRequest(CommandParameters commandParameters) {
        AbstractRequest fundedRequest = RequestController.requests.stream()
                .filter(request -> request.getRequestId() == commandParameters.getParameter("requestId", Integer.class))
                .findFirst()
                .orElse(new NullRequest());
        fundedRequest.deleteRequest();
        RequestController.requests.remove(fundedRequest);
    }


}

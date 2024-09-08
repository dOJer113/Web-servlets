package ru.roznov.servlets_2.objects.requests;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.cars.CarBase;
import ru.roznov.servlets_2.objects.cars.DriverIdByCarId;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.store.KeeperByStoreId;
import ru.roznov.servlets_2.objects.store.ProductsBase;
import ru.roznov.servlets_2.objects.store.StorageBase;
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

    public static List<AbstractRequest> getAllRequestsForKeeper(int keeperId) {
        List<AbstractRequest> requests = new ArrayList<>();
        int storeId = KeeperByStoreId.getStoreIdByKeeperId(keeperId);
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
        AbstractRequest request = RequestController.getRequestById(commandParameters.getParameter("requestId", Integer.class));
        if (!request.equals(new NullRequest())) {
            HandlingRequest fundedRequest = (HandlingRequest) request;
            commandParameters.addParameter("productId", ProductsBase.getIdByProductName(fundedRequest.getProductEnum()));
            commandParameters.addParameter("productCount", fundedRequest.getProductCount());
            commandParameters.addParameter("storeId", KeeperByStoreId.getStoreIdByKeeperId(commandParameters.getParameter("keeperId", Integer.class)));
            commandParameters.addParameter("carId", DriverIdByCarId.getCarIdByDriverId(fundedRequest.getDriverId()));
            RequestType requestType = fundedRequest.getRequestType();
            if (requestType.equals(RequestType.LOADING_TO_CAR)) {
                RequestController.confirmLoadingToCarRequest(commandParameters);
            } else if (requestType.equals(RequestType.LOADING_TO_STORE)) {
                RequestController.confirmLoadingToStoreRequest(commandParameters);
            }
        }
    }

    public static void confirmLoadingToStoreRequest(CommandParameters commandParameters) {
        Car car = CarBase.getCarById(commandParameters.getParameter("carId", Integer.class));
        ProductEnum product = ProductsBase.getProductNameById(commandParameters.getParameter("productId", Integer.class));
        Store store = StorageBase.getStoreById(commandParameters.getParameter("storeId", Integer.class));
        CommandParameters negativeCommandParameters = new CommandParameters();
        negativeCommandParameters.addParameter("carId", commandParameters.getParameter("carId", Integer.class));
        negativeCommandParameters.addParameter("productId", commandParameters.getParameter("productId", Integer.class));
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
        CommandParameters negativeCommandParameters = new CommandParameters();
        negativeCommandParameters.addParameter("storeId", commandParameters.getParameter("storeId", Integer.class));
        negativeCommandParameters.addParameter("productId", commandParameters.getParameter("productId", Integer.class));
        Car car = CarBase.getCarById(commandParameters.getParameter("carId", Integer.class));
        ProductEnum product = ProductsBase.getProductNameById(commandParameters.getParameter("productId", Integer.class));
        Store store = StorageBase.getStoreById(commandParameters.getParameter("storeId", Integer.class));
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
        AbstractRequest request = RequestController.getRequestById(commandParameters.getParameter("requestId", Integer.class));
        if (!request.equals(new NullRequest())) {
            EntryRequest fundedRequest = (EntryRequest) request;
            commandParameters.addParameter("storeId", fundedRequest.getStoreId());
            commandParameters.addParameter("carId", DriverIdByCarId.getCarIdByDriverId(fundedRequest.getDriverId()));
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

package ru.roznov.servlets_2.objects.requests;

import ru.roznov.servlets_2.controler.command.CommandController;
import ru.roznov.servlets_2.controler.command.CommandName;
import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.cars.CarBase;
import ru.roznov.servlets_2.objects.cars.DriverIdByCarId;
import ru.roznov.servlets_2.objects.store.*;

import java.util.ArrayList;
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

    public static List<AbstractRequest> getAllRequests() {
        return new ArrayList<>(RequestController.requests);
    }

    public static void confirmHandlingRequest(CommandParameters commandParameters) {
        HandlingRequest fundedRequest = (HandlingRequest) RequestController.getRequestById(commandParameters.getParameter("requestId", Integer.class));
        commandParameters.addParameter("productId", ProductsBase.getIdByProductName(fundedRequest.getProduct()));
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

    public static void confirmLoadingToStoreRequest(CommandParameters commandParameters) {
        Car car = CarBase.getCarById(commandParameters.getParameter("carId", Integer.class));
        ProductEnum product = ProductsBase.getProductNameById(commandParameters.getParameter("productId", Integer.class));
        Store store = StorageBase.getStoreById(commandParameters.getParameter("storeId", Integer.class));
        int requestId = commandParameters.getParameter("requestId", Integer.class);
        CommandParameters negativeCommandParameters = commandParameters;
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        negativeCommandParameters.addParameter("productCount", -productCount);
        if (car.unLoadProduct(product, productCount)) {
            CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_CAR, negativeCommandParameters);
            CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_STORE, commandParameters);
            store.loadProducts(product, productCount);
        } else {
            System.err.println("Error not enough product at car");
        }

        RequestController.deleteRequest(requestId);
    }

    public static void confirmLoadingToCarRequest(CommandParameters commandParameters) {
        CommandParameters negativeCommandParameters = commandParameters;
        Car car = CarBase.getCarById(commandParameters.getParameter("carId", Integer.class));
        ProductEnum product = ProductsBase.getProductNameById(commandParameters.getParameter("productId", Integer.class));
        Store store = StorageBase.getStoreById(commandParameters.getParameter("storeId", Integer.class));
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        int requestId = commandParameters.getParameter("requestId", Integer.class);
        negativeCommandParameters.addParameter("productCount", -productCount);
        if (store.unLoadProduct(product, productCount)) {
            CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_STORE, negativeCommandParameters);
            CommandController.executeCommand(CommandName.CHANGE_COUNT_PRODUCT_AT_CAR, commandParameters);
            car.loadProducts(product, productCount);
        } else {
            System.err.println("Error not enough product at store");
        }

        RequestController.deleteRequest(requestId);
    }

    public static void confirmEntryRequest(CommandParameters commandParameters) {
        int requestId = commandParameters.getParameter("requestId", Integer.class);
        RequestController.deleteRequest(requestId);
        EntryRequest fundedRequest = (EntryRequest) RequestController.getRequestById(requestId);
        commandParameters.addParameter("storeId", fundedRequest.getStoreId());
        commandParameters.addParameter("carId", DriverIdByCarId.getCarIdByDriverId(fundedRequest.getDriverId()));
        CommandController.executeCommand(CommandName.ADD_CAR_TO_STORE, commandParameters);
    }

    public static AbstractRequest getRequestById(int id) {
        return RequestController.requests.stream().filter(request -> request.getRequestId() == id).findFirst().orElse(new NullRequest());
    }

    public static void rejectRequest(int requestId) {
        RequestController.deleteRequest(requestId);
    }


    public static void deleteRequest(int id) {
        AbstractRequest fundedRequest = RequestController.requests.stream().filter(request -> request.getRequestId() == id).findFirst().orElse(new NullRequest());
        fundedRequest.deleteRequest();
        RequestController.requests.remove(fundedRequest);
    }


}

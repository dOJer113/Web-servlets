package ru.roznov.servlets_2.objects.cars;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.requests.*;

public class DriverUtils {

    private DriverUtils() {
    }

    public static void makeNewEntryRequest(CommandParameters commandParameters) {
        int driverId = commandParameters.getParameter("driverId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        AbstractRequest request = new EntryRequest(driverId, storeId);
        RequestController.addNewRequest(request);
    }

    public static void makeNewHandlingRequest(CommandParameters commandParameters) {
        int count = commandParameters.getParameter("count", Integer.class);
        ProductEnum product = commandParameters.getParameter("product", ProductEnum.class);
        RequestType requestType = commandParameters.getParameter("requestType", RequestType.class);
        int driverId = commandParameters.getParameter("driverId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        AbstractRequest request = new HandlingRequest(driverId, requestType, count, product, storeId);
        RequestController.addNewRequest(request);
    }
}

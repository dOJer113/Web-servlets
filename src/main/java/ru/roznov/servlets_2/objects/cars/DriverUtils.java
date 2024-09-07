package ru.roznov.servlets_2.objects.cars;

import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.requests.*;

public class DriverUtils {

    private DriverUtils() {
    }

    public static void makeNewEntryRequest(CommandParameters commandParameters) {
        AbstractRequest request = new EntryRequest(commandParameters.getParameter("driverId", Integer.class), commandParameters.getParameter("storeId", Integer.class));
        RequestController.addNewRequest(request);
    }

    public  static void makeNewHandlingRequest(CommandParameters commandParameters) {
        int count = commandParameters.getParameter("count", Integer.class);
        ProductEnum product = commandParameters.getParameter("product", ProductEnum.class);
        RequestType requestType = commandParameters.getParameter("requestType", RequestType.class);
        int driverId = commandParameters.getParameter("driverId", Integer.class);
        AbstractRequest request = new HandlingRequest(driverId, requestType, count, product);
        RequestController.addNewRequest(request);
    }
}

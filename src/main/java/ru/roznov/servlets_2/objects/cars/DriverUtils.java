package ru.roznov.servlets_2.objects.cars;

import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.requests.*;

public class DriverUtils {

    private DriverUtils() {
    }

    public void makeNewEntryRequest(int storeId, int driverId) {
        AbstractRequest request = new EntryRequest(driverId, storeId);
        RequestController.addNewRequest(request);
    }

    public void makeNewHandlingRequest(int count, ProductEnum product, RequestType requestType, int driverId) {
        AbstractRequest request = new HandlingRequest(driverId, requestType, count, product);
        RequestController.addNewRequest(request);
    }
}

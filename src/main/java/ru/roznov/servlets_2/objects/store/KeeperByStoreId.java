package ru.roznov.servlets_2.objects.store;

import ru.roznov.servlets_2.objects.cars.DriverIdByCarId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeeperByStoreId {
    private static Map<Integer, Integer> keeperByStoreId = new ConcurrentHashMap<>();

    private KeeperByStoreId() {
    }

    public static void setStoresFromMap(Map<Integer, Integer> storesMap) {
        KeeperByStoreId.keeperByStoreId = storesMap;
    }

    public static void deleteStoreKeeperPair(int keeperId) {
        KeeperByStoreId.keeperByStoreId.remove(KeeperByStoreId.getStoreIdByKeeperId(keeperId));
    }

    public static int getStoreIdByKeeperId(int keeperId) {
        for (Map.Entry<Integer, Integer> entry : keeperByStoreId.entrySet()) {
            if (entry.getValue().equals(keeperId)) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
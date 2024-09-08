package ru.roznov.servlets_2.objects.store;

import ru.roznov.servlets_2.objects.cars.Car;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorageBase {
    private static Map<Integer, Store> storeById = new ConcurrentHashMap<>();

    private StorageBase() {
    }

    public static boolean isCarAtAnyStore(Car car) {
        Iterator<Integer> iterator = storeById.keySet().iterator();
        while (iterator.hasNext()) {
            Store store = storeById.get(iterator.next());
            if (store.isCarAtStore(car)) {
                return true;
            }
        }
        return false;
    }

    public static Store getStoreById(int id) {
        return StorageBase.storeById.get(id);
    }

    public static boolean isStoreExists(int id) {
        return StorageBase.storeById.containsKey(id);
    }

    public static List<Store> getStores() {
        return new ArrayList<>(StorageBase.storeById.values());
    }

    public static void setStoragesFromMap(Map<Integer, Store> storeMap) {
        StorageBase.storeById = storeMap;
    }

}

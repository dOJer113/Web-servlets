package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ProductDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.StorageDAO;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.products.ProductsBase;
import ru.roznov.servlets_2.objects.store.Store;

import java.util.Iterator;
import java.util.Map;


public class StoreManager {
    private StoreManager() {
    }


    public static void deleteKeeper(CommandParameters commandParameters) {
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        int keeperId = commandParameters.getParameter("id", Integer.class);
        storageDAO.deleteStorekeeperFromStoreStorekeeper(keeperId);
    }

    public static void addNewKeeperWithStore(CommandParameters commandParameters) {
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        int keeperId = commandParameters.getParameter("id", Integer.class);
        storageDAO.addNewKeeperWithStore(keeperId);
    }

    public static void addCarToStore(CommandParameters commandParameters) {
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        int carId = commandParameters.getParameter("carId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        storageDAO.addCarToStore(storeId, carId);
    }

    public static void removeCarFromStore(CommandParameters commandParameters) {
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        int carId = commandParameters.getParameter("carId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        Store store = getStoreById(storeId, storageDAO);
        Car car = CarManager.getCarById(carId, carDAO);
        if (store.isCarAtStore(car)) {
            storageDAO.removeCarFromStore(storeId, carId);
        } else {
            System.err.println("No car at store");
        }
    }

    public static void changeCountProductsAtStore(CommandParameters commandParameters) {
        ProductDAO productDAO = commandParameters.getParameter("ProductDAO", ProductDAO.class);
        StorageDAO storageDAO = commandParameters.getParameter("StoreDAO", StorageDAO.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        int productId = commandParameters.getParameter("productId", Integer.class);
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        ProductEnum productName = ProductsBase.getProductNameById(productId, productDAO);
        if (isStoreExists(storeId, storageDAO)) {
            Store store = getStoreById(storeId, storageDAO);
            if (store.isProductAtStore(productName)) {
                storageDAO.updateProductCount(storeId, productId, productCount);
            } else {
                storageDAO.addNewProduct(storeId, productId, productCount);
            }
        } else {
            System.err.println("Error changing count products at store");
        }
    }


    public static int getStoreIdByKeeperId(int keeperId, StorageDAO storageDAO) {
        for (Map.Entry<Integer, Integer> entry : storageDAO.getStoragesIdByKeepersId().entrySet()) {
            if (entry.getKey().equals(keeperId)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public static int getStoreIdByCarId(int carId, StorageDAO storageDAO) {
        return storageDAO.getStoreIdByCarId(carId);
    }

    public static boolean isCarAtAnyStore(Car car, StorageDAO storageDAO) {
        Map<Integer, Store> storeById = storageDAO.getStorages();
        Iterator<Integer> iterator = storeById.keySet().iterator();
        while (iterator.hasNext()) {
            Store store = storeById.get(iterator.next());
            if (store.isCarAtStore(car)) {
                return true;
            }
        }
        return false;
    }

    public static Store getStoreById(int id, StorageDAO storageDAO) {
        return storageDAO.getStorages().get(id);
    }

    public static boolean isStoreExists(int id, StorageDAO storageDAO) {
        return storageDAO.getStorages().containsKey(id);
    }
}

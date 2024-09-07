package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.command.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.cars.CarBase;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.store.KeeperByStoreId;
import ru.roznov.servlets_2.objects.store.ProductsBase;
import ru.roznov.servlets_2.objects.store.StorageBase;
import ru.roznov.servlets_2.objects.store.Store;


public class StoreManager {
    private StoreManager() {
    }

    public static void getStoragesIdByKeepersIdFromDB(CommandParameters commandParameters) {
        KeeperByStoreId.setStoresFromMap(DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().getStoragesIdByKeepersId());
    }

    public static void getStorages(CommandParameters commandParameters) {
        StorageBase.setStoragesFromMap(DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().getStorages());
    }

    public static void deleteKeeper(CommandParameters commandParameters) {
        int keeperId = commandParameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().deleteStorekeeperFromStoreStorekeeper(keeperId);
        KeeperByStoreId.deleteStoreKeeperPair(keeperId);
    }

    public static void addNewKeeperWithStore(CommandParameters commandParameters) {
        int keeperId = commandParameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().addNewKeeperWithStore(keeperId);
    }

    public static void addCarToStore(CommandParameters commandParameters) {
        int carId = commandParameters.getParameter("carId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().addCarToStore(storeId, carId);
        StorageBase.getStoreById(storeId).addCarToStorage(CarBase.getCarById(carId));
    }

    public static void removeCarFromStore(CommandParameters commandParameters) {
        int carId = commandParameters.getParameter("carId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        Store store = StorageBase.getStoreById(storeId);
        Car car = CarBase.getCarById(carId);
        if (store.isCarAtStore(car)) {
            DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().removeCarFromStore(storeId, carId);
            store.removeCarFromStorage(car);
        } else {
            System.err.println("No car at store");
        }
    }

    public static void changeCountProductsAtStore(CommandParameters commandParameters) {
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        int productId = ProductsBase.getIdByProductName(commandParameters.getParameter("productName", ProductEnum.class));
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        ProductEnum productName = ProductsBase.getProductNameById(productId);
        if (StoreManager.checkProductCountToRemove(storeId, productId, productCount)) {
            if (StorageBase.isStoreExists(storeId)) {
                Store store = StorageBase.getStoreById(storeId);
                if (store.isProductAtStore(productName)) {
                    DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().updateProductCount(storeId, productId, productCount);
                } else {
                    DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().addNewProduct(storeId, productId, productCount);
                }
                store.loadProducts(productName, productCount);
            }
        }
        else{
            System.err.println("Error changing count products at store");
        }
    }

    private static boolean checkProductCountToRemove(int storeId, int productId, int productCountChanges) {
        Store store = StorageBase.getStoreById(storeId);
        ProductEnum productName = ProductsBase.getProductNameById(productId);
        if (productCountChanges > 1) {
            return true;
        }
        if (!store.isProductAtStore(productName)) {
            return false;
        }
        return Math.abs(productCountChanges) <= store.getProductMap().get(productName);
    }


}

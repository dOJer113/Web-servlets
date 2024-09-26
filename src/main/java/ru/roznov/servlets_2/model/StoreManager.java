package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
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
        int keeperId = commandParameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().deleteStorekeeperFromStoreStorekeeper(keeperId);
    }

    public static void addNewKeeperWithStore(CommandParameters commandParameters) {
        int keeperId = commandParameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().addNewKeeperWithStore(keeperId);
    }

    public static void addCarToStore(CommandParameters commandParameters) {
        int carId = commandParameters.getParameter("carId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().addCarToStore(storeId, carId);
    }

    public static void removeCarFromStore(CommandParameters commandParameters) {
        int carId = commandParameters.getParameter("carId", Integer.class);
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        Store store = getStoreById(storeId);
        Car car = CarManager.getCarById(carId);
        if (store.isCarAtStore(car)) {
            DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().removeCarFromStore(storeId, carId);
        } else {
            System.err.println("No car at store");
        }
    }

    public static void changeCountProductsAtStore(CommandParameters commandParameters) {
        int storeId = commandParameters.getParameter("storeId", Integer.class);
        int productId = commandParameters.getParameter("productId", Integer.class);
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        ProductEnum productName = ProductsBase.getProductNameById(productId);
        if (isStoreExists(storeId)) {
            Store store = getStoreById(storeId);
            if (store.isProductAtStore(productName)) {
                DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().updateProductCount(storeId, productId, productCount);
            } else {
                DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().addNewProduct(storeId, productId, productCount);
            }
        } else {
            System.err.println("Error changing count products at store");
        }
    }


    public static int getStoreIdByKeeperId(int keeperId) {
        for (Map.Entry<Integer, Integer> entry : DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().getStoragesIdByKeepersId().entrySet()) {
            if (entry.getKey().equals(keeperId)) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public static boolean isCarAtAnyStore(Car car) {
        Map<Integer, Store> storeById = DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().getStorages();
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
        return DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().getStorages().get(id);
    }

    public static boolean isStoreExists(int id) {
        return DAOFactory.getInstance(DBType.ORACLE).getStorageDAO().getStorages().containsKey(id);
    }
}

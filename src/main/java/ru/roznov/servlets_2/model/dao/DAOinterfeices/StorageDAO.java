package ru.roznov.servlets_2.model.dao.DAOinterfeices;

import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.store.Store;

import java.util.Map;
import java.util.Set;

public interface StorageDAO {

    Map<Integer, Integer> getStoragesIdByKeepersId();

    Map<ProductEnum, Integer> getProductsByStoreId(int storeId);

    Map<Integer, Store> getStorages();

    Set<Integer> getCarIdsAtStore(int storeID);

    void deleteStorekeeperFromStoreStorekeeper(int keeperId);

    void addNewProduct(int storeId, int productId, int countProduct);

    void updateProductCount(int storeId, int productId, int countProduct);

    void addNewKeeperWithStore(int keeperId);

    void addCarToStore(int storeId, int carId);

    void removeCarFromStore(int storeId, int carId);

    int getStoreIdByCarId(int carId);


}

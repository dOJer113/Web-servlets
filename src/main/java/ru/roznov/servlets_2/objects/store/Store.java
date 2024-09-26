package ru.roznov.servlets_2.objects.store;

import ru.roznov.servlets_2.model.CarManager;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.products.ProductEnum;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;


public class Store {
    private Map<ProductEnum, Integer> storage = new ConcurrentHashMap<>();
    private Set<Car> carsAtStorage = new ConcurrentSkipListSet<>();

    public Set<Car> getCarsAtThisStorage() {
        return this.carsAtStorage;
    }

    public void addCarToStorage(Car car) {
        this.carsAtStorage.add(car);
    }

    public void removeCarFromStorage(Car car) {
        this.carsAtStorage.remove(car);
    }

    public void loadProducts(ProductEnum product, int count) {
        if (this.storage.containsKey(product)) {
            this.storage.put(product, this.storage.get(product) + count);
        } else {
            this.storage.put(product, count);
        }
    }

    public boolean unLoadProduct(ProductEnum product, int count) {
        if (this.isStorageHaveProductAndCount(product, count)) {
            this.storage.put(product, this.storage.get(product) - count);
            return true;
        }
        return false;
    }

    public boolean isCarAtStore(Car car) {
        return this.carsAtStorage.contains(car);
    }

    public Map<ProductEnum, Integer> getProductMap() {
        return this.storage;
    }

    public boolean isProductAtStore(ProductEnum productName) {
        return this.storage.containsKey(productName);
    }

    public void setProductsFromMap(Map<ProductEnum, Integer> productCountMap) {
        productCountMap.forEach(this::loadProducts);
    }

    public void setCarsAtStorageFromSetIds(Set<Integer> carIds) {
        Iterator<Integer> iterator = carIds.iterator();
        while (iterator.hasNext()) {
            Car car = CarManager.getCarById(iterator.next());
            if (car != null) {
                this.carsAtStorage.add(car);
            }
        }
    }

    public boolean isStorageHaveProductAndCount(ProductEnum product, int count) {
        return this.storage.containsKey(product) && this.storage.get(product) >= Math.abs(count);
    }


}

package ru.roznov.servlets_2.objects.cars;

import ru.roznov.servlets_2.objects.products.ProductEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Car {
    private Map<ProductEnum, Integer> storage = new ConcurrentHashMap<>();

    public void loadProducts(ProductEnum product, int count) {
        if (this.storage.containsKey(product)) {
            this.storage.put(product, this.storage.get(product) + count);
        } else {
            this.storage.put(product, count);
        }
    }

    public boolean unLoadProduct(ProductEnum product, int count) {
        if (this.isCarStorageHaveProductAndCount(product, count)) {
            this.storage.put(product, this.storage.get(product) - count);
            return true;
        }
        return false;
    }


    public Map<ProductEnum, Integer> getProductMap() {
        return this.storage;
    }

    public void setProductsFromMap(Map<ProductEnum, Integer> productCountMap) {
        productCountMap.forEach(this::loadProducts);
    }

    public boolean isProductAtCar(ProductEnum productName) {
        return this.storage.containsKey(productName);
    }

    public boolean isCarStorageHaveProductAndCount(ProductEnum product, int count) {
        return storage.containsKey(product) && storage.get(product) >= count;
    }
}

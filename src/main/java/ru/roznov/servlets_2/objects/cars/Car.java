package ru.roznov.servlets_2.objects.cars;

import ru.roznov.servlets_2.objects.products.ProductEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Car implements Comparable<Car> {
    private final Map<ProductEnum, Integer> storage = new HashMap<>();

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


    public void setProductsFromMap(Map<ProductEnum, Integer> productCountMap) {
        productCountMap.forEach(this::loadProducts);
    }

    public boolean isProductAtCar(ProductEnum productName) {
        return this.storage.containsKey(productName);
    }

    public boolean isCarStorageHaveProductAndCount(ProductEnum product, int count) {
        return storage.containsKey(product) && storage.get(product) >= count;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(this.storage, car.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.storage);
    }


    @Override
    public int compareTo(Car o) {
        int thisTotal = this.storage.values().stream().mapToInt(Integer::intValue).sum();
        int otherTotal = o.storage.values().stream().mapToInt(Integer::intValue).sum();
        return Integer.compare(thisTotal, otherTotal);
    }
}

package ru.roznov.servlets_2.model.dao.DAOinterfeices;

import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.cars.Car;


import java.util.Map;

public interface CarDAO {
    Map<Integer, Integer> getCarsIdsByDriversIds();

    Map<ProductEnum, Integer> getProductsByCarId(int carId);

    Map<Integer, Car> getCars();

    void deleteDriverFromCarDriver(int driverId);

    void addNewDriverWithCar(int driverId);

    void addNewProduct(int carId, int productId, int countProduct);

    void updateProductCount(int carId, int productId, int countProduct);
}

package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.CarDAO;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.ProductDAO;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.products.ProductsBase;

import java.util.Map;


public class CarManager {
    private CarManager() {
    }

    public static int getCarIdByDriverId(int driverId, CarDAO carDAO) {
        for (Map.Entry<Integer, Integer> entry : carDAO.getCarsIdsByDriversIds().entrySet()) {
            if (entry.getValue().equals(driverId)) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public static void deleteDriver(CommandParameters commandParameters) {
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        int driverId = commandParameters.getParameter("id", Integer.class);
        carDAO.deleteDriverFromCarDriver(driverId);
    }

    public static void addNewDriverWithCar(CommandParameters commandParameters) {
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        int driverId = commandParameters.getParameter("id", Integer.class);
        carDAO.addNewDriverWithCar(driverId);
    }

    public static void changeCountProductsAtCar(CommandParameters commandParameters) {
        CarDAO carDAO = commandParameters.getParameter("CarDAO", CarDAO.class);
        ProductDAO productDAO = commandParameters.getParameter("ProductDAO", ProductDAO.class);
        int carId = commandParameters.getParameter("carId", Integer.class);
        int productId = commandParameters.getParameter("productId", Integer.class);
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        ProductEnum productName = ProductsBase.getProductNameById(productId, productDAO);
        if (isCarExists(carId, carDAO)) {
            Car car = getCarById(carId, carDAO);
            if (car.isProductAtCar(productName)) {
                carDAO.updateProductCount(carId, productId, productCount);
            } else {
                carDAO.addNewProduct(carId, productId, productCount);
            }
        }

    }


    public static Car getCarById(int id, CarDAO carDAO) {
        return carDAO.getCars().get(id);
    }

    public static boolean isCarExists(int id, CarDAO carDAO) {
        return carDAO.getCars().containsKey(id);
    }
}

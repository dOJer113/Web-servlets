package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.products.ProductsBase;

import java.util.Map;


public class CarManager {
    private CarManager() {
    }

    public static int getCarIdByDriverId(int driverId) {
        for (Map.Entry<Integer, Integer> entry : DAOFactory.getInstance(DBType.ORACLE).getCarDAO().getCarsIdsByDriversIds().entrySet()) {
            if (entry.getValue().equals(driverId)) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public static void deleteDriver(CommandParameters commandParameters) {
        int driverId = commandParameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getCarDAO().deleteDriverFromCarDriver(driverId);
    }

    public static void addNewDriverWithCar(CommandParameters commandParameters) {
        int driverId = commandParameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getCarDAO().addNewDriverWithCar(driverId);
    }

    public static void changeCountProductsAtCar(CommandParameters commandParameters) {
        int carId = commandParameters.getParameter("carId", Integer.class);
        int productId = commandParameters.getParameter("productId", Integer.class);
        int productCount = commandParameters.getParameter("productCount", Integer.class);
        ProductEnum productName = ProductsBase.getProductNameById(productId);
        if (isCarExists(carId)) {
            Car car = getCarById(carId);
            if (car.isProductAtCar(productName)) {
                DAOFactory.getInstance(DBType.ORACLE).getCarDAO().updateProductCount(carId, productId, productCount);
            } else {
                DAOFactory.getInstance(DBType.ORACLE).getCarDAO().addNewProduct(carId, productId, productCount);
            }
        }

    }


    public static Car getCarById(int id) {
        return DAOFactory.getInstance(DBType.ORACLE).getCarDAO().getCars().get(id);
    }

    public static boolean isCarExists(int id) {
        return DAOFactory.getInstance(DBType.ORACLE).getCarDAO().getCars().containsKey(id);
    }
}

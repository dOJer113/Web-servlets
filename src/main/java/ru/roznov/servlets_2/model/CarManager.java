package ru.roznov.servlets_2.model;

import ru.roznov.servlets_2.controler.businesCommand.CommandParameters;
import ru.roznov.servlets_2.model.dao.DAOinterfeices.DAOFactory;
import ru.roznov.servlets_2.model.dao.DBType;
import ru.roznov.servlets_2.objects.cars.Car;
import ru.roznov.servlets_2.objects.cars.CarBase;
import ru.roznov.servlets_2.objects.cars.DriverIdByCarId;
import ru.roznov.servlets_2.objects.products.ProductEnum;
import ru.roznov.servlets_2.objects.store.ProductsBase;


public class CarManager {
    private CarManager() {
    }

    public static void getCarsIdByDriversIdFromDB(CommandParameters commandParameters) {
        DriverIdByCarId.getCarsFromMap(DAOFactory.getInstance(DBType.ORACLE).getCarDAO().getCarsIdsByDriversIds());
    }

    public static void getCars(CommandParameters commandParameters) {
        CarBase.setCarsFromMap(DAOFactory.getInstance(DBType.ORACLE).getCarDAO().getCars());
    }

    public static void deleteDriver(CommandParameters commandParameters) {
        int driverId = commandParameters.getParameter("id", Integer.class);
        DAOFactory.getInstance(DBType.ORACLE).getCarDAO().deleteDriverFromCarDriver(driverId);
        DriverIdByCarId.deleteCarDriverPair(driverId);
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
        if (CarBase.isCarExists(carId)) {
            Car car = CarBase.getCarById(carId);
            if (car.isProductAtCar(productName)) {
                DAOFactory.getInstance(DBType.ORACLE).getCarDAO().updateProductCount(carId, productId, productCount);
            } else {
                DAOFactory.getInstance(DBType.ORACLE).getCarDAO().addNewProduct(carId, productId, productCount);
            }
        }

    }


}

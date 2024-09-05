package ru.roznov.servlets_2.objects.cars;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CarBase {
    private static Map<Integer, Car> carById = new ConcurrentHashMap<>();

    private CarBase() {
    }

    public static void setCarsFromMap(Map<Integer, Car> carById) {
        CarBase.carById = carById;
    }

    public static Car getCarById(int id) {
        return CarBase.carById.get(id);
    }

    public static void addNewCar(Car car, int id) {
        carById.put(id, car);
    }

    public static void deleteCar(int id) {
        carById.remove(id);
    }

    public static boolean isCarExists(int id){
        return CarBase.carById.containsKey(id);
    }


}

package ru.roznov.servlets_2.objects.cars;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverIdByCarId {
    private static Map<Integer, Integer> driverIdByCarId = new ConcurrentHashMap<>();

    private DriverIdByCarId() {
    }

    public static void getCarsFromMap(Map<Integer, Integer> carsMap) {
        DriverIdByCarId.driverIdByCarId = carsMap;
    }

    public static int getCarIdByDriverId(int driverId) {
        for (Map.Entry<Integer, Integer> entry : driverIdByCarId.entrySet()) {
            if (entry.getValue().equals(driverId)) {
                return entry.getKey();
            }
        }
        return 0;
    }

    public static void deleteCarDriverPair(int driverId) {
        DriverIdByCarId.driverIdByCarId.remove(DriverIdByCarId.getCarIdByDriverId(driverId));
    }

}

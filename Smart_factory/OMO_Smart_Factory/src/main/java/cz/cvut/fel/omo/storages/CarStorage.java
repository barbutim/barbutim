package cz.cvut.fel.omo.storages;

import cz.cvut.fel.omo.car.Car;
import cz.cvut.fel.omo.enums.CarType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * Storage for cars.
 */
public class CarStorage extends Storage {

    private final List<Car> cars = new ArrayList<>();

    public CarStorage(Integer storageCapacity) {
        super(storageCapacity);
    }

    /**
     * Add checks a storage capacity.
     * @param car - car to be added
     */
    public void add(Car car) {
        if (cars.size() < getStorageCapacity()) {
            cars.add(car);
        } else {
            logger.log(Level.WARNING, "Car " + car.getType().name() + " had to be thrown away");
        }
    }

    @Override
    public String toString() {
        HashMap<CarType, Integer> carList = new HashMap<>();
        List<String> carStrings = new ArrayList<>();
        double percent;
        for (Car car : cars) {
            percent = car.getPercent();
            if (percent == 100) {
                if (carList.containsKey(car.getType())) {
                    carList.put(car.getType(), carList.get(car.getType()) + 1);
                } else {
                    carList.put(car.getType(), 1);
                }
            } else {
                carStrings.add("+ 1 " + car.getType().name() + " car " + percent + "% finished");
            }
        }

        System.out.println("CAR STORAGE:");
        for (HashMap.Entry<CarType, Integer> entry : carList.entrySet()) {
            System.out.print(entry.getKey() + " car: ");
            System.out.println(entry.getValue() + "×");
        }
        for (String str : carStrings) {
            System.out.println(str);
        }
        return "";
    }
}

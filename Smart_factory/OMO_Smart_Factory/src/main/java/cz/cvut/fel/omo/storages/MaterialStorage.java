package cz.cvut.fel.omo.storages;

import cz.cvut.fel.omo.car.CarBlueprint;
import cz.cvut.fel.omo.car.CarBlueprintController;
import cz.cvut.fel.omo.enums.CarType;
import cz.cvut.fel.omo.enums.MaterialType;
import cz.cvut.fel.omo.factoryexceptions.MaterialMissing;

import java.util.HashMap;
import java.util.logging.Level;

/**
 * Storage for material.
 */
public class MaterialStorage extends Storage {

    private final HashMap<MaterialType, Integer> storageContents = new HashMap<>();
    private final CarBlueprintController blueprints;
    private Integer numberFilled = 0;


    public MaterialStorage(Integer capacity) {
        super(capacity);
        blueprints = CarBlueprintController.getInstance();
    }

    /**
     * Checks storage capacity. If storage is full, the left material is thrown away.
     * @param quantity - quantity of material
     * @param material - material to store
     */
    public void receiveMaterials(Integer quantity, MaterialType material) {
        Integer freeCapacity = getStorageCapacity() - getFullness();
        if (quantity > freeCapacity) {
            logger.log(Level.WARNING, "Material storage is full. " + (quantity - freeCapacity) + " pieces of " +
                    material.name() + " had to be thrown away.");
            quantity = freeCapacity;
        }
        if (storageContents.containsKey(material)) {
            storageContents.put(material, storageContents.get(material) + quantity);
        } else {
            storageContents.put(material, quantity);
        }
        increaseFullness(quantity);
    }

    /**
     * Checks if there is enough material for given car in the storage.
     * @param carType - car to check material for
     * @return - true if there is enough, false otherwise
     */
    public boolean checkMaterialAvailability(CarType carType) {
        CarBlueprint blueprint = blueprints.getBlueprint(carType);

        try {
            checkMaterials(blueprint.getElectronics());
            checkMaterials(blueprint.getEngine());
            checkMaterials(blueprint.getHood());
            checkMaterials(blueprint.getWheels().getKey(), blueprint.getWheels().getValue());
            checkMaterials(blueprint.getTires().getKey(), blueprint.getTires().getValue());
            checkMaterials(blueprint.getPaint());
        } catch (MaterialMissing exception) {
            logger.log(Level.WARNING, exception.getMessage());
            return false;
        }

        return true;
    }

    private void checkMaterials(MaterialType material) throws MaterialMissing {
        if (!storageContents.containsKey(material) || storageContents.get(material) <= 0) {
            throw new MaterialMissing("Material Missing - " + material.name());
        }
    }

    private void checkMaterials(MaterialType material, Integer number) throws MaterialMissing {
        if (!storageContents.containsKey(material) || storageContents.get(material) < number) {
            throw new MaterialMissing("Material Missing - " + material.name());
        }
    }

    public MaterialType getMaterial(MaterialType material) throws MaterialMissing {
        checkMaterials(material);
        storageContents.put(material, storageContents.get(material) - 1);
        return material;
    }

    public MaterialType getMaterial(MaterialType material, Integer number) throws MaterialMissing {
        checkMaterials(material, number);
        storageContents.put(material, storageContents.get(material) - number);
        return material;
    }

    private void increaseFullness(Integer quantity) {
        numberFilled += quantity;
    }

    public Integer getFullness() {
        return numberFilled;
    }

    @Override
    public String toString() {
        return "MATERIAL STORAGE CONTENTS: \n" + storageContents + "\n";
    }
}

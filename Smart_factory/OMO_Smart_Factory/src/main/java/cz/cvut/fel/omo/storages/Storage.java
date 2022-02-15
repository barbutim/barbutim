package cz.cvut.fel.omo.storages;

import java.util.logging.Logger;

/**
 * Storage where things are stored.
 */
public abstract class Storage {

    protected Logger logger = Logger.getLogger("Storage logger");

    private final Integer storageCapacity;

    Storage(Integer storageCapacity) {
        if (storageCapacity <= 0) {
            throw new IllegalArgumentException("Storage capacity can't be zero or less.");
        }
        this.storageCapacity = storageCapacity;
    }

    public Integer getStorageCapacity() {
        return storageCapacity;
    }
}

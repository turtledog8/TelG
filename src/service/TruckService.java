package service;

import save.DataStore;
import vehicle.*;

public class TruckService {

    private final DataStore dataStore;

    public TruckService(DataStore dataStore) {
        this.dataStore = dataStore;

        if (dataStore.getTrucks().isEmpty()) {
            initializeTrucks();
        }
    }

    private void initializeTrucks() {

        for (int i = 1001; i <= 1010; i++) {
            dataStore.getTrucks().add(new ScaniaTruck(String.valueOf(i)));
        }

        for (int i = 1011; i <= 1025; i++) {
            dataStore.getTrucks().add(new ManTruck(String.valueOf(i)));
        }

        for (int i = 1026; i <= 1040; i++) {
            dataStore.getTrucks().add(new ActrosTruck(String.valueOf(i)));
        }
    }

    public TransportVehicle findTruckById(String id) {
        for (TransportVehicle truck : dataStore.getTrucks()) {
            if (truck.getId().equals(id)) {
                return truck;
            }
        }
        return null;
    }
}

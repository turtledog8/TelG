package service;

import model.DeliveryPackage;
import save.DataStore;

public class PackageService {

    private final DataStore dataStore;

    public PackageService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addPackage(DeliveryPackage pkg) {
        dataStore.getPackages().add(pkg);
    }
}

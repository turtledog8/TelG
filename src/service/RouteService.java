package service;

import model.DeliveryRoute;
import save.DataStore;

public class RouteService {

    private final DataStore dataStore;

    public RouteService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addRoute(DeliveryRoute route) {
        dataStore.getRoutes().add(route);
    }
}

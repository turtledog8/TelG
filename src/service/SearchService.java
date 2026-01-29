package service;

import constants.Location;
import model.DeliveryRoute;
import save.DataStore;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    private final DataStore dataStore;

    public SearchService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public List<DeliveryRoute> searchRoutes(Location start, Location end) {

        List<DeliveryRoute> result = new ArrayList<>();

        for (DeliveryRoute route : dataStore.getRoutes()) {
            List<Location> locations = route.getLocations();

            int startIndex = locations.indexOf(start);
            int endIndex = locations.indexOf(end);

            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                result.add(route);
            }
        }

        return result;
    }
}

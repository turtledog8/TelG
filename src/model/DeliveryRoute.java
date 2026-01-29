package model;

import constants.Location;
import java.util.List;

public class DeliveryRoute {

    private final String id;
    private final List<Location> locations;

    public DeliveryRoute(String id, List<Location> locations) {
        this.id = id;
        this.locations = locations;
    }

    public String getId() {
        return id;
    }

    public List<Location> getLocations() {
        return locations;
    }
}

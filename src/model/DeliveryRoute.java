package model;

import constants.Location;
import vehicle.TransportVehicle;
import java.util.List;

public class DeliveryRoute {

    private final String id;
    private final List<Location> locations;
    private TransportVehicle assignedTruck;   // NEW

    public DeliveryRoute(String id, List<Location> locations) {
        this.id = id;
        this.locations = locations;
        this.assignedTruck = null;
    }

    public String getId() {
        return id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public TransportVehicle getAssignedTruck() {
        return assignedTruck;
    }

    public void setAssignedTruck(TransportVehicle truck) {
        this.assignedTruck = truck;
    }
}

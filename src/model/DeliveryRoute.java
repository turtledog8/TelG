package model;

import constants.Location;
import java.util.List;
import java.util.ArrayList;

public class DeliveryRoute {

    private final String id;
    private final List<Location> locations;

    private String departureTime;
    private final List<String> arrivalTimes;

    public DeliveryRoute(String id, List<Location> locations) {
        this.id = id;
        this.locations = locations;
        this.arrivalTimes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public List<String> getArrivalTimes() {
        return arrivalTimes;
    }

    public void addArrivalTime(String arrivalTime) {
        arrivalTimes.add(arrivalTime);
    }
}

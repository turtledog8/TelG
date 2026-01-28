package model;

import constants.Location;

public class DeliveryPackage {

    private final String id;
    private final Location startLocation;
    private final Location endLocation;
    private final double weight;
    private final String customerContact;

    public DeliveryPackage(String id,
                           Location startLocation,
                           Location endLocation,
                           double weight,
                           String customerContact) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.weight = weight;
        this.customerContact = customerContact;
    }

    public String getId() {
        return id;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public double getWeight() {
        return weight;
    }

    public String getCustomerContact() {
        return customerContact;
    }
}

package save;

import model.DeliveryPackage;
import model.DeliveryRoute;
import vehicle.TransportVehicle;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private final List<DeliveryPackage> packages;
    private final List<DeliveryRoute> routes;
    private final List<TransportVehicle> trucks;

    public DataStore() {
        this.packages = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.trucks = new ArrayList<>();
    }

    public List<DeliveryPackage> getPackages() {
        return packages;
    }

    public List<DeliveryRoute> getRoutes() {
        return routes;
    }

    public List<TransportVehicle> getTrucks() {return trucks;}
}

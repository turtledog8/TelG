package save;

import model.DeliveryPackage;
import model.DeliveryRoute;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private final List<DeliveryPackage> packages;
    private final List<DeliveryRoute> routes;

    public DataStore() {
        this.packages = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    public List<DeliveryPackage> getPackages() {
        return packages;
    }

    public List<DeliveryRoute> getRoutes() {
        return routes;
    }
}

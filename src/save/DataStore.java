package save;

import model.DeliveryPackage;
import java.util.ArrayList;
import java.util.List;
import model.DeliveryRoute;
import java.util.*;

public class DataStore {

    private final List<DeliveryPackage> packages;


    public DataStore() {this.packages = new ArrayList<>(); }
    private final List<DeliveryRoute> routes = new ArrayList<>();

    public List<DeliveryPackage> getPackages() {
        return packages;
    }

    public List<DeliveryRoute> getRoutes() {
        return routes;
    }


}

package save;

import model.DeliveryPackage;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private final List<DeliveryPackage> packages;

    public DataStore() {
        this.packages = new ArrayList<>();
    }

    public List<DeliveryPackage> getPackages() {
        return packages;
    }
}

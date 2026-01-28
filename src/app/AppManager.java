package app;
import save.DataStore;
import save.FileLoader;
import save.FileSaver;
import service.PackageService;
import service.RouteService;
import service.SearchService;
import service.TruckService;

public class AppManager {
    private final DataStore dataStore;

    private final PackageService packageService;
    private final RouteService routeService;
    private final TruckService truckService;
    private final SearchService searchService;

    public AppManager() {
        this.dataStore = new DataStore();
        this.packageService = new PackageService(dataStore);
        this.routeService = new RouteService(dataStore);
        this.truckService = new TruckService(dataStore);
        this.searchService = new SearchService(dataStore);
    }

    public void loadState() {
        new FileLoader(dataStore).load();
    }

    public void saveState() {
        new FileSaver(dataStore).save();
    }


    public void createPackage() {
    }

    public void createRoute() {
    }

    public void searchRoutes() {
    }

    public void assignTruckToRoute() {
    }

    public void assignPackageToRoute() {
    }

    public void viewRoutes() {
    }

    public void viewPackages() {
    }

    public void viewTrucks() {
    }

    public void viewUnassignedPackages() {
    }
}




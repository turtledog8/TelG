package app;

import constants.Location;
import model.DeliveryPackage;
import save.DataStore;
import save.FileLoader;
import save.FileSaver;
import service.PackageService;
import service.RouteService;
import service.SearchService;
import service.TruckService;
import model.DeliveryRoute;

import java.util.List;

public class AppManager {

    // Singleton
    private static AppManager instance;

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    private final DataStore dataStore;
    private final PackageService packageService;
    private final RouteService routeService;
    private final TruckService truckService;
    private final SearchService searchService;

    private AppManager() {
        this.dataStore = new DataStore();
        this.packageService = new PackageService(dataStore);
        this.routeService = new RouteService(dataStore);
        this.truckService = new TruckService(dataStore);
        this.searchService = new SearchService(dataStore);
    }

    //Lifecycle
    public void loadState() {
        new FileLoader(dataStore).load();
    }

    public void saveState() {
        new FileSaver(dataStore).save();
    }

    //FR1 Logic
    public void createPackage(Location start,
                              Location end,
                              double weight,
                              String contact) {

        String id = "PKG" + (dataStore.getPackages().size() + 1);

        DeliveryPackage pkg =
                new DeliveryPackage(id, start, end, weight, contact);

        packageService.addPackage(pkg);

        System.out.println("Package created successfully with ID: " + id);
    }

    public void viewPackages() {
        System.out.println("\n--- All Packages ---");

        if (dataStore.getPackages().isEmpty()) {
            System.out.println("No packages created yet.");
            return;
        }

        for (DeliveryPackage pkg : dataStore.getPackages()) {
            System.out.println(pkg.getId() + " | " +
                    pkg.getStartLocation() + " -> " +
                    pkg.getEndLocation() + " | " +
                    pkg.getWeight() + "kg | " +
                    pkg.getCustomerContact());
        }
    }

    public void createRoute(List<Location> locations) {

        if (locations.size() < 2) {
            System.out.println("Route must contain at least 2 locations.");
            return;
        }

        String id = "R" + (dataStore.getRoutes().size() + 1);
        DeliveryRoute route = new DeliveryRoute(id, locations);

        routeService.addRoute(route);
        System.out.println("Route created successfully with ID: " + id);
    }

    public void searchRoutes(Location start, Location end) {

        var routes = searchService.searchRoutes(start, end);

        System.out.println("\n--- Search Results ---");

        if (routes.isEmpty()) {
            System.out.println("No matching routes found.");
            return;
        }

        for (DeliveryRoute route : routes) {
            System.out.println(route.getId() + " | " + route.getLocations());
        }
    }
    public void assignTruckToRoute() {}
    public void assignPackageToRoute() {}

    public void viewRoutes() {
        System.out.println("\n--- All Routes ---");

        if (dataStore.getRoutes().isEmpty()) {
            System.out.println("No routes created yet.");
            return;
        }

        for (DeliveryRoute route : dataStore.getRoutes()) {
            System.out.println(route.getId() + " | " + route.getLocations());
        }
    }

    public void viewTrucks() {}
    public void viewUnassignedPackages() {}
}

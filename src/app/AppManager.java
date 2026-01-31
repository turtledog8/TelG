package app;

import constants.Location;
import model.DeliveryPackage;
import model.DeliveryRoute;
import save.DataStore;
import save.FileLoader;
import save.FileSaver;
import service.PackageService;
import service.RouteService;
import service.SearchService;
import service.TruckService;

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

    public void loadState() {
        new FileLoader(dataStore).load();
        new FileSaver(dataStore).save();
    }

    public void saveState() {
        new FileSaver(dataStore).save();
    }

    public void createPackage(Location start,
                              Location end,
                              double weight,
                              String contact) {

        String id = "PKG" + (dataStore.getPackages().size() + 1);

        DeliveryPackage pkg =
                new DeliveryPackage(id, start, end, weight, contact);

        packageService.addPackage(pkg);
        saveState();

        System.out.println("Package created successfully with ID: " + id);
    }

    public void viewPackages() {
        System.out.println("\n--- All Packages ---");

        if (dataStore.getPackages().isEmpty()) {
            System.out.println("No packages created yet.");
            return;
        }

        for (DeliveryPackage pkg : dataStore.getPackages()) {
            System.out.println(
                            pkg.getId() + " | " +
                            pkg.getStartLocation() + " -> " +
                            pkg.getEndLocation() + " | " +
                            pkg.getWeight() + "kg | " +
                            pkg.getCustomerContact()
            );
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
        saveState();

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

    public void assignTruckToRoute(String routeId, String truckId) {

        DeliveryRoute selectedRoute = null;

        for (DeliveryRoute r : dataStore.getRoutes()) {
            if (r.getId().equalsIgnoreCase(routeId)) {
                selectedRoute = r;
                break;
            }
        }

        if (selectedRoute == null) {
            System.out.println("Route not found.");
            return;
        }

        if (selectedRoute.getAssignedTruck() != null) {
            System.out.println("Route already has a truck assigned.");
            return;
        }

        var truck = truckService.findTruckById(truckId);

        if (truck == null) {
            System.out.println("Truck not found.");
            return;
        }

        for (DeliveryRoute r : dataStore.getRoutes()) {
            if (r.getAssignedTruck() != null &&
                    r.getAssignedTruck().getId().equals(truckId)) {
                System.out.println("Truck is already assigned to another route.");
                return;
            }
        }

        selectedRoute.setAssignedTruck(truck);
        saveState();

        System.out.println("Truck " + truckId + " assigned to route " + routeId);
    }

    public void assignPackageToRoute() {}

    public void viewRoutes() {
        System.out.println("\n--- All Routes ---");

        if (dataStore.getRoutes().isEmpty()) {
            System.out.println("No routes created yet.");
            return;
        }

        for (DeliveryRoute route : dataStore.getRoutes()) {
            System.out.println(route.getId() + " | " + route.getLocations());

            if (route.getDepartureTime() != null) {
                System.out.println("Departure: " + route.getDepartureTime());

                for (int i = 0; i < route.getArrivalTimes().size(); i++) {
                    System.out.println("Arrival time: " +
                            route.getArrivalTimes().get(i));
                }
            } else {
                System.out.println("No schedule set");
            }

            if (route.getAssignedTruck() != null) {
                System.out.println("Assigned truck: " +
                        route.getAssignedTruck().getId());
            } else {
                System.out.println("No truck assigned");
            }
        }
    }

    public void viewTrucks() {

        System.out.println("\n--- All Trucks ---");

        if (dataStore.getTrucks().isEmpty()) {
            System.out.println("No trucks available.");
            return;
        }

        for (var truck : dataStore.getTrucks()) {
            System.out.println(
                    truck.getId() + " | " +
                            truck.name() + " | " +
                            truck.getCapacityKg() + "kg | " +
                            truck.getMaxRangeKm() + "km"
            );
        }
    }

    public void viewUnassignedPackages() {}

    public void setRouteSchedule(String routeId,
                                 String departureTime,
                                 List<String> arrivalTimes) {

        DeliveryRoute route = null;

        for (DeliveryRoute r : dataStore.getRoutes()) {
            if (r.getId().equalsIgnoreCase(routeId)) {
                route = r;
                break;
            }
        }

        if (route == null) {
            System.out.println("Route not found.");
            return;
        }

        if (arrivalTimes.size() != route.getLocations().size() - 1) {
            System.out.println("You must enter exactly " +
                    (route.getLocations().size() - 1) + " arrival times.");
            return;
        }

        routeService.setDepartureTime(route, departureTime);

        route.getArrivalTimes().clear();
        for (String t : arrivalTimes) {
            routeService.addArrivalTime(route, t);
        }

        saveState();
        System.out.println("Schedule added to route " + route.getId());
    }
}

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
import java.util.Scanner;

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
    public void assignTruckToRoute(String routeId, String truckId) {

        DeliveryRoute selectedRoute = null;

        for (DeliveryRoute r : dataStore.getRoutes()) {
            if (r.getId().equals(routeId)) {
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

        // prevent double assignment
        for (DeliveryRoute r : dataStore.getRoutes()) {
            if (r.getAssignedTruck() != null &&
                    r.getAssignedTruck().getId().equals(truckId)) {
                System.out.println("Truck is already assigned to another route.");
                return;
            }
        }

        selectedRoute.setAssignedTruck(truck);
        System.out.println("Truck " + truckId + " assigned to route " + routeId);
    }
    public void assignPackageToRoute() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Assign Package to Route ---");
        System.out.print("Enter package ID (e.g. PKG1): ");
        String packageId = sc.nextLine().trim();

        System.out.print("Enter route ID (e.g. R1): ");
        String routeId = sc.nextLine().trim();


        DeliveryPackage pkg = null;
        for (DeliveryPackage p : dataStore.getPackages()) {
            if (p.getId().equalsIgnoreCase(packageId)) {
                pkg = p;
                break;
            }
        }
        if (pkg == null) {
            System.out.println("Package not found.");
            return;
        }


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


        if (route.hasPackage(pkg.getId())) {
            System.out.println("This package is already assigned to this route.");
            return;
        }


        for (DeliveryRoute r : dataStore.getRoutes()) {
            if (r.hasPackage(pkg.getId())) {
                System.out.println("Package is already assigned to another route (" + r.getId() + ").");
                return;
            }
        }


        int startIndex = route.getLocations().indexOf(pkg.getStartLocation());
        int endIndex = route.getLocations().indexOf(pkg.getEndLocation());

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            System.out.println("Package locations do not match the route.");
            return;
        }


        if (route.getAssignedTruck() == null) {
            System.out.println("Cannot assign package: no truck assigned to this route.");
            return;
        }

        double usedWeight = route.getAssignedWeight();
        double capacity = route.getAssignedTruck().getCapacityKg();

        if (usedWeight + pkg.getWeight() > capacity) {
            double remaining = capacity - usedWeight;
            System.out.println("Not enough capacity. Remaining: " + remaining + "kg");
            return;
        }


        route.addPackage(pkg);
        System.out.println("Package " + pkg.getId() + " assigned to route " + route.getId() + ".");
    }

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

                for (int stopIndex = 1; stopIndex < route.getLocations().size(); stopIndex++) {
                    String arrivalTime;

                    if (stopIndex - 1 < route.getArrivalTimes().size()) {
                        arrivalTime = route.getArrivalTimes().get(stopIndex - 1);
                    } else {
                        arrivalTime = "N/A";
                    }
                    System.out.println("Arrival time: " + arrivalTime);
                }

            }else {
                System.out.println("No schedule set");
            }
        }

    }

    public void viewTrucks() {}
    public void viewUnassignedPackages() {}

    public void setRouteSchedule(String routeId, String departureTime, List<String> arrivalTimes) {
        DeliveryRoute route = null;
        for (DeliveryRoute r : dataStore.getRoutes()){
            if (r.getId().equalsIgnoreCase(routeId)){
                route = r;
                break;
            }
        }
        if (route == null) {
            System.out.println("Route not found.");
            return;
        }

        routeService.setDepartureTime(route, departureTime);

        if (arrivalTimes.size() != route.getLocations().size() - 1){
            System.out.println("You must enter exactly " + (route.getLocations().size() - 1) + " arrival times.");
            return;
        }

        for (String t : arrivalTimes){
            routeService.addArrivalTime(route, t);
        }

        System.out.println("Schedule added to route " + route.getId() + " successfully.");
    }
}

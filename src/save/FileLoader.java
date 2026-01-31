package save;

import constants.DataPath;
import constants.Location;
import model.DeliveryPackage;
import model.DeliveryRoute;
import service.TruckService;
import vehicle.TransportVehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {

    private final DataStore dataStore;

    public FileLoader(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void load() {
        ensureDataFolderExists();
        ensureFilesExist();
        loadPackages();
        loadRoutes();
        System.out.println("Application state loaded.");
    }

    private void ensureDataFolderExists() {
        File folder = new File(DataPath.DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private void ensureFilesExist() {
        createFileWithHeaderIfMissing(
                DataPath.PACKAGES_FILE,
                "id,start,end,weight,contact"
        );

        createFileWithHeaderIfMissing(
                DataPath.ROUTES_FILE,
                "id,locations,departure,arrivals,assignedTruck"
        );
    }

    private void createFileWithHeaderIfMissing(String path, String header) {
        File file = new File(path);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(header + "\n");
            } catch (IOException e) {
                System.out.println("Error creating file " + path + ": " + e.getMessage());
            }
        }
    }

    private void loadPackages() {

        File file = new File(DataPath.PACKAGES_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",");

                DeliveryPackage pkg = new DeliveryPackage(
                        parts[0],
                        Location.valueOf(parts[1]),
                        Location.valueOf(parts[2]),
                        Double.parseDouble(parts[3]),
                        parts[4]
                );

                dataStore.getPackages().add(pkg);
            }

        } catch (IOException e) {
            System.out.println("Error loading packages: " + e.getMessage());
        }
    }

    private void loadRoutes() {

        File file = new File(DataPath.ROUTES_FILE);

        TruckService truckService = new TruckService(dataStore);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1);

                String id = parts[0];

                String[] locParts = parts[1].split(" ");
                List<Location> locations = new ArrayList<>();
                for (String l : locParts) {
                    locations.add(Location.valueOf(l));
                }

                DeliveryRoute route = new DeliveryRoute(id, locations);

                if (!parts[2].isEmpty()) {
                    route.setDepartureTime(parts[2]);
                }

                if (!parts[3].isEmpty()) {
                    String[] arrParts = parts[3].split(" ");
                    for (String t : arrParts) {
                        route.addArrivalTime(t);
                    }
                }

                if (!parts[4].isEmpty()) {
                    TransportVehicle truck =
                            truckService.findTruckById(parts[4]);
                    if (truck != null) {
                        route.setAssignedTruck(truck);
                    }
                }

                dataStore.getRoutes().add(route);
            }

        } catch (IOException e) {
            System.out.println("Error loading routes: " + e.getMessage());
        }
    }
}

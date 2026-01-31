package save;

import constants.DataPath;
import model.DeliveryPackage;
import model.DeliveryRoute;
import vehicle.TransportVehicle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {

    private final DataStore dataStore;

    public FileSaver(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void save() {
        ensureDataFolderExists();
        savePackages();
        saveRoutes();
        System.out.println("Application state saved.");
    }

    private void ensureDataFolderExists() {
        File folder = new File(DataPath.DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    private void savePackages() {
        try (FileWriter writer = new FileWriter(DataPath.PACKAGES_FILE)) {

            writer.write("id,start,end,weight,contact\n");

            for (DeliveryPackage pkg : dataStore.getPackages()) {
                writer.write(
                        pkg.getId() + "," +
                                pkg.getStartLocation() + "," +
                                pkg.getEndLocation() + "," +
                                pkg.getWeight() + "," +
                                pkg.getCustomerContact() + "\n"
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving packages: " + e.getMessage());
        }
    }

    private void saveRoutes() {
        try (FileWriter writer = new FileWriter(DataPath.ROUTES_FILE)) {

            writer.write("id,locations,departure,arrivals,assignedTruck\n");

            for (DeliveryRoute route : dataStore.getRoutes()) {

                // locations
                StringBuilder locs = new StringBuilder();
                for (var loc : route.getLocations()) {
                    locs.append(loc).append(" ");
                }

                // arrivals
                StringBuilder arrivals = new StringBuilder();
                for (var t : route.getArrivalTimes()) {
                    arrivals.append(t).append(" ");
                }

                // assigned truck
                TransportVehicle truck = route.getAssignedTruck();
                String truckId = (truck == null) ? "" : truck.getId();

                writer.write(
                        route.getId() + "," +
                                locs.toString().trim() + "," +
                                (route.getDepartureTime() == null ? "" : route.getDepartureTime()) + "," +
                                arrivals.toString().trim() + "," +
                                truckId + "\n"
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving routes: " + e.getMessage());
        }
    }
}

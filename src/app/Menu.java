package app;

import constants.Location;
import java.util.Scanner;

public class Menu {

    private final AppManager manager;
    private final Scanner scanner;
    private boolean running;
    private final String menuText;

    public Menu(AppManager manager){
        this.manager = manager;
        this.scanner = new Scanner(System.in);
        this.menuText = buildMenuText();
        this.running = true;
    }

    public void start() {
        while (running) {
            showMenu();
            int choice = readChoice();
            handleChoice(choice);
        }
    }

    private String buildMenuText() {
        return """
                ==== LOGISTICS MANAGEMENT SYSTEM ====
                1. Create delivery package
                2. Create delivery route
                3. Search delivery routes
                4. Assign truck to route
                5. Assign package to route
                6. View routes
                7. View packages
                8. View trucks
                9. View unassigned packages
                0. Save & Exit
                
                Select option:\s""";
    }

    private void showMenu() {
        System.out.print(menuText);
    }

    private int readChoice() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a number: ");
            }
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> createPackageUI();
            case 2 -> manager.createRoute();
            case 3 -> manager.searchRoutes();
            case 4 -> manager.assignTruckToRoute();
            case 5 -> manager.assignPackageToRoute();
            case 6 -> manager.viewRoutes();
            case 7 -> manager.viewPackages();
            case 8 -> manager.viewTrucks();
            case 9 -> manager.viewUnassignedPackages();
            case 0 -> exit();
            default -> System.out.println("Invalid option.");
        }
    }

    // ===== FR1 UI =====
    private void createPackageUI() {

        System.out.println("\n--- Create Delivery Package ---");

        System.out.print("Available locations: ");
        for (Location loc : Location.values()) {
            System.out.print(loc + " ");
        }
        System.out.println();

        System.out.print("Enter start location: ");
        Location start = Location.valueOf(scanner.nextLine().trim().toUpperCase());

        System.out.print("Enter end location: ");
        Location end = Location.valueOf(scanner.nextLine().trim().toUpperCase());

        System.out.print("Enter weight (kg): ");
        double weight = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Enter customer contact info: ");
        String contact = scanner.nextLine().trim();

        manager.createPackage(start, end, weight, contact);
    }

    private void exit(){
        manager.saveState();
        System.out.println("Sbogom");
        running = false;
    }
}

package app;

public class Main {

    public static void main(String[] args) {
        AppManager manager = AppManager.getInstance();
        manager.loadState();

        Menu menu = new Menu(manager);
        menu.start();
    }
}

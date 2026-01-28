package app;

public class Main {

    public static void main(String[] args) {
        AppManager manager = new AppManager();
        manager.loadState();

        Menu menu = new Menu(manager);
        menu.start();
    }
}

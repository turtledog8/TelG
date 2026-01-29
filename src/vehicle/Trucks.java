package vehicle;

public abstract class Trucks implements TransportVehicle {

    private final String id;

    protected Trucks(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}

package vehicle;

public class ManTruck extends Trucks {

    public ManTruck(String id) {
        super(id);
    }

    @Override
    public String name() {
        return "Man";
    }

    @Override
    public int getCapacityKg() {
        return 37000;
    }

    @Override
    public int getMaxRangeKm() {
        return 10000;
    }
}

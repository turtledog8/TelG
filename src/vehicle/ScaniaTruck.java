package vehicle;

public class ScaniaTruck extends Trucks {

    public ScaniaTruck(String id) {
        super(id);
    }

    @Override
    public String name() {
        return "Scania";
    }

    @Override
    public int getCapacityKg() {
        return 42000;
    }

    @Override
    public int getMaxRangeKm() {
        return 8000;
    }
}

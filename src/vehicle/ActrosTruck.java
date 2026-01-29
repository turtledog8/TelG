package vehicle;

public class ActrosTruck extends Trucks {

    public ActrosTruck(String id) {
        super(id);
    }

    @Override
    public String name() {
        return "Actros";
    }

    @Override
    public int getCapacityKg() {
        return 26000;
    }

    @Override
    public int getMaxRangeKm() {
        return 13000;
    }
}

package Entities;

public class FloweringPlants extends Plant {
	private double attackProb = 90.0;
	private double oxigenFromPLant = 6.0;

	public FloweringPlants(String type, String name, double mass, String status) {
		super(type, name, mass, status);
	}

	@Override
	public double getOxygenFromPlant() { return oxigenFromPLant; }

	@Override
	public double getAttackProb() { return attackProb / 100.0; }

    @Override
    public String toString() {
        return "FloweringPlants";
    }
}

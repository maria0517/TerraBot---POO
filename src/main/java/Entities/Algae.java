package Entities;

public class Algae extends Plant {
	private double attackProb = 20;
	private double oxigenFromPLant = 0.5;

	public Algae(String type, String name, double mass, String status) {
		super(type, name, mass, status);
	}

	@Override
	public double getOxygenFromPlant() { return oxigenFromPLant; }

	@Override
	public double getAttackProb() { return attackProb / 100.0; }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}

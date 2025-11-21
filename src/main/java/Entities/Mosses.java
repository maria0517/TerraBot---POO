package Entities;

public class Mosses extends Plant {
	private double attackProb = 40;
	private double oxigenFromPLant = 0.8;

	public Mosses(String type, String name, double mass, String status) {
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

package Entities;

public class GymnospermsPlants extends Plant{
	private double attackProb = 60.0;
	private double oxigenFromPLant = 0.0;

	public GymnospermsPlants(String type,  String name, double mass, String status) {
		super(type, name, mass, status);
	}

	@Override
	public double getOxygenFromPlant() { return oxigenFromPLant; }

	@Override
	public double getAttackProb() { return 0.6; }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}

package Entities;

public class Carnivores extends Animal {
	private double attackPos = 30;
	// momentan nu stiu daca le trebuie ceva specific
	public Carnivores(String type, String name, double mass, String state) {
		super(type, name, mass, state);
	}

	// calc prob de atac
	public double attackProbability() {
		return (100 - this.attackPos) / 10.0;
	}

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}

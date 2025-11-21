package Entities;

public abstract class Entity {
    // aici am atributele comune tuturor, doar cu definitiile
    protected String name;
    protected double mass;

    // constructor
    public Entity(String name, double mass) {
        this.name = name;
        this.mass = mass;
    }

    // getter + setter pentru name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // getter + setter pentru mass
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}

package entities;

/**
 * Clasa de capatai pentru toate entitatile
 */
public abstract class Entity {
    // aici am atributele comune tuturor, doar cu definitiile
    protected String name;
    protected double mass;

    /** constructor simplu pentru
     */
    public Entity(final String name, final double mass) {
        this.name = name;
        this.mass = mass;
    }

    // getter + setter pentru name

    /**
     *  getter nume
     */
    public String getName() {
        return name;
    }

    /**
     *  setter nume
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *  getter masa
     */
    public double getMass() {
        return mass;
    }

    /**
     *  setter masa
     */
    public void setMass(final double mass) {
        this.mass = mass;
    }
}

package entities;

import constants.Const;

public final class Detritivores extends Animal {
    private static final double ATTACKPOS = 90;
    // momentan nu stiu daca le trebuie ceva specific
    public Detritivores(final String type, final String name,
               final double mass, final String state) {
        super(type, name, mass, state);
    }

    /**
     * @return attack
     */
    public double attackProbability() {
        return (Const.O_SUTA_DBL - this.ATTACKPOS) / Const.UN_ZECE;
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}

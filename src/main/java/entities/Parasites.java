package entities;

import constants.Const;

public final class Parasites extends Animal {
    private static final double ATTACKPOS = 10;
    // momentan nu stiu daca le trebuie ceva specific
    public Parasites(final String type, final String name, final double mass, final String state) {
        super(type, name, mass, state);
    }

    /**
     *
     * @return attackProb
     */
    public double attackProbability() {
        return (Const.O_SUTA_DBL - this.ATTACKPOS) / Const.UN_ZECE;
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}

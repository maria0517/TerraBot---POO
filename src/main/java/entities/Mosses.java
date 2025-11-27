package entities;

import constants.Const;

public final class Mosses extends Plant {
    private static final double ATTACKPROB = 40;
    private static final double OXIGENFROMPLANT = 0.8;

    public Mosses(final String type, final String name, final double mass, final String status) {
        super(type, name, mass, status);
    }

    @Override
    public double getOxygenFromPlant() {
        return OXIGENFROMPLANT;
    }

    @Override
    public double getAttackProb() {
        return ATTACKPROB / Const.O_SUTA_DBL;
    }

    @Override
    public String toString() {
        return (name != null ? name : "-");
    }

}

package entities;

import constants.Const;

public final class Omnivores extends Animal {
    private static final double ATTACKPOS = 60;
    public Omnivores(final String type, final String name, final double mass, final String state) {
        super(type, name, mass, state);
    }

    // calc prob de atac
    /**
     *
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
